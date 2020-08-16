package com.xnktyu.dangdang;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xnktyu.utils.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

public class CatchBookTable
{
	private static final DBHelper local_db = new DBHelper("localhost", "root", "flyint8", "dang");

	private static class tbase
	{
		protected String pack(String field)
		{
			return field.toLowerCase();
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName().toLowerCase();
		}
	}

	private static final class t_book extends tbase
	{
		public final String bookId = pack("bookId");
		public final String title = pack("title");
		public final String author = pack("author");
		public final String time = pack("time");
		public final String vip_link = pack("vip_link");
		public final String count_per = pack("count_per");
		public final String word_count = pack("word_count");
		public final String group = pack("group");
		public final String publisher = pack("publisher");
		public final String price = pack("price");
		public final String url = pack("url");
		public final String cover = pack("cover");
		public final String des = pack("des");
		public final String title_descript = pack("title_descript");
	}

	private static final t_book t_book = new t_book();


	private static boolean catchDetail(String url, File dir, JSONObject detail, String bookId)
	{
		String text = HttpUtils.doHttpGet(url);
		if (TextUtils.isEmpty(text))
		{
			LOG.e("error doHttpGet : " + url);
			return false;
		}

		Document doc = Jsoup.parse(text);

		if (detail == null)
			detail = new JSONObject(true);

		detail.put("url", url);
		detail.put("bookId", bookId);

		String cover = "";
		Elements bookcover_imgs = doc.select(".bookCover_area").select("img");
		for (Element img : bookcover_imgs)
		{
			if (!img.hasClass("promotion_icon"))
			{
				cover = img.attr("src").trim();
			}
		}
		detail.put("cover", cover);

		if (doc.select("#author").size() == 0)
		{
			LOG.e("error author : " + url);
//			File file = new File("D:\\wangzhiting\\work\\local\\book\\table\\tmp.txt");
//			FsUtils.writeText(file, doc.html());
			return false;
		}

		detail.put("title", doc.select(".title_words").text().trim());
		detail.put("author", doc.select("#author").text().trim().substring("作 者：".length()));
		detail.put("vip_link", doc.select("#vip_link").size() == 1);
		detail.put("title_descript", doc.select(".title_descript").text().trim());
		detail.put("count_per", doc.select(".count_per").text().trim());
		detail.put("publisher", doc.select("#publisher").text().trim().substring("出 版 社：".length()));
		Elements explain_box_ps = doc.select(".explain_box").select("p");
		for (Element p : explain_box_ps)
		{
			String str = p.text().trim();
			if (str.startsWith("出版时间："))
				detail.put("time", str.substring("出版时间：".length()));
			else if (str.startsWith("字 数："))
				detail.put("word_count", str.substring("字 数：".length()));
			else if (str.startsWith("所属分类： "))
				detail.put("group", str.substring("所属分类： ".length()));
		}

//		LOGJson.log(detail.toString());

		FsUtils.createDir(dir);
		File file = new File(dir, FsUtils.checkFileName(String.format("[%s]%s.json", detail.getString("bookId"), detail.getString("title"))));
		FsUtils.writeText(file, detail.toString());
//		FsUtils.writeText(file, LOGJson.getStr(detail.toString()));

		return true;
	}

	public static void catchTable(File htmlFile)
	{
		File dir = new File(htmlFile.getParentFile(), FsUtils.getNameWithoutSuffix(htmlFile));

		String text = FsUtils.readText(htmlFile);
		if (TextUtils.isEmpty(text))
			return;

		Document doc = Jsoup.parse(text);

		final JSONArray books = new JSONArray();

		final Map<String, JSONObject> bookMap = new HashMap<String, JSONObject>();
		dir.listFiles(new FileFilter()
		{
			public boolean accept(File file)
			{
				if (file.isFile())
				{
					if (file.getName().toLowerCase().endsWith(".json"))
					{
						String bookId = file.getName().substring("[".length(), file.getName().indexOf("]"));
						JSONObject book = JsonHelper.getJSONObject(FsUtils.readText(file));
						if (book != null)
						{
							books.add(book);
							bookMap.put(bookId, book);
						}
						else
							LOG.e("read fail : " + file);
					}
				}
				return false;
			}
		});

		Elements book_list = doc.select("#book_list");
		Elements alist = book_list.select("a");
		LOG.v("alist size : " + alist.size());
		for (Element a : alist)
		{
			String href = a.attr("href").trim();
			if (href.startsWith("./"))
				href = href.replaceFirst("\\./", "http://e.dangdang.com/");

			String bookId = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));

			if (!bookMap.containsKey(bookId))
			{
				LOG.v("catch : " + href);
				try
				{
					Elements bookinfo = a.select(".bookinfo");
					String priceStr = bookinfo.select(".now").text().trim();
					if (priceStr.startsWith("促销价:"))
						priceStr = priceStr.substring("促销价:".length());
					priceStr = priceStr.substring("￥".length());
					Float price = Float.valueOf(priceStr);
					String des = bookinfo.select(".des").text().trim();

					JSONObject book = new JSONObject(true);
					book.put("price", price);
					book.put("des", des);
					if (catchDetail(href, dir, book, bookId))
					{
						books.add(book);
						bookMap.put(bookId, book);
					}
					Thread.sleep(3000);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

//		File file = new File(htmlFile.getParentFile(), String.format("%s.json", FsUtils.getNameWithoutSuffix(htmlFile)));
//		FsUtils.writeText(file, books.toString());
//		FsUtils.writeText(file, LOGJson.getStr(books.toString()));

		LOG.v("books size : " + books.size());
		LOG.v("bookMap size : " + bookMap.size());

		// 检测重复
		Map<String, JSONObject> tmpMap = new HashMap<String, JSONObject>();
		for (int i = 0; i < books.size(); i++)
		{
			JSONObject book = books.getJSONObject(i);

			String bookId = book.getString("bookId");
			if (tmpMap.containsKey(bookId))
			{
				LOG.v("find repeat");
				LOGJson.log(book.toString());
				LOGJson.log(tmpMap.get(bookId).toString());
			}
			else
			{
				tmpMap.put(bookId, book);
			}
		}
	}

	public static void uploadRecord(File dir)
	{
		local_db.exeSql(String.format("create table if not exists %s(" + t_book.bookId + " varchar(100)" + //
				", " + t_book.title + " text" + //
				", " + t_book.author + " text" + //
				", " + t_book.time + " date" + //
				", " + t_book.vip_link + " tinyint" + //
				", " + t_book.count_per + " int" + //
				", " + t_book.word_count + " int" + //
				", " + t_book.group + " text" + //
				", " + t_book.publisher + " text" + //
				", " + t_book.price + " float" + //
				", " + t_book.url + " text" + //
				", " + t_book.cover + " text" + //
				", " + t_book.des + " text" + //
				", " + t_book.title_descript + " text" + //
				");", t_book), null);

		dir.listFiles(new FileFilter()
		{
			public boolean accept(File file)
			{
				if (file.isFile())
				{
					if (file.getName().toLowerCase().endsWith(".json"))
					{
						JSONObject book = JsonHelper.getJSONObject(FsUtils.readText(file));
						if (book != null)
						{
							if (!local_db.hasRecord(t_book, t_book.bookId, book.getString("bookId")))
							{
								local_db.insert(t_book, //
										t_book.bookId, book.getString("bookId"), //
										t_book.title, book.getString("title"), //
										t_book.author, book.getString("author"), //
										t_book.time, book.getString("time"), //
										t_book.vip_link, book.getString("vip_link"), //
										t_book.count_per, book.getString("count_per"), //
										t_book.word_count, book.getString("word_count"), //
										t_book.group, book.getString("group"), //
										t_book.publisher, book.getString("publisher"), //
										t_book.price, book.getString("price"), //
										t_book.url, book.getString("url"), //
										t_book.cover, book.getString("cover"), //
										t_book.des, book.getString("des"), //
										t_book.title_descript, book.getString("title_descript"));
							}
						}
						else
							LOG.e("read fail : " + file);
					}
				}
				return false;
			}
		});
	}
}

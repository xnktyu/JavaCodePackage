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
					Thread.sleep(4000);
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
}

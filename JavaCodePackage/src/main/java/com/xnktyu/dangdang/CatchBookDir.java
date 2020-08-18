package com.xnktyu.dangdang;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xnktyu.utils.FsUtils;
import com.xnktyu.utils.LOG;
import com.xnktyu.utils.LOGJson;
import com.xnktyu.utils.TextUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

/**
 * 抓取当当租阅书的目录(仅限PC阅读的抓不到)
 */
public class CatchBookDir
{
	private static JSONArray genDirData(Elements cata_list)
	{
		JSONArray dirs = new JSONArray();
		Elements lis = cata_list.select("li");
		for (Element li : lis)
		{
			String level = li.attr("class");
			Elements a = li.select("a").eq(0);
			String pagecount = a.attr("pagecount");
			String name = a.text().trim();
			name = name.substring(0, name.length() - pagecount.length());

			JSONObject dir = new JSONObject(true);
			dir.put("level", Integer.valueOf(level.substring("level".length())));
			dir.put("name", name);
			dir.put("page", Integer.valueOf(pagecount));
			dirs.add(dir);
		}
		return dirs;
	}

	public static void catchDir(File htmlFile, File dir)
	{
		String text = FsUtils.readText(htmlFile);
		if (TextUtils.isEmpty(text))
			return;

		Document doc = Jsoup.parse(text);

//		FsUtils.createDir(dir);
//		File tmpFile = new File(dir, "tmp.html");
//		FsUtils.writeText(tmpFile, doc.html());

		String href = doc.select("#ddclick-tool-left-book-link").attr("href").trim();
		String bookId = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));

		Elements other_out = doc.select(".other-out").eq(0);
		Elements title = other_out.select(".title").eq(0);
		String bookName = FsUtils.checkFileName(title.text().trim());

		LOG.v(bookId + bookName);

		File bookDir = new File(dir, bookId + bookName);
		FsUtils.createDir(bookDir);

		Elements cata_list = other_out.select(".cata-list").eq(0);

		File markFile = new File(bookDir, "mark.json");
		JSONObject book = new JSONObject(true);
		book.put("bookId", bookId);
		book.put("name", bookName);
		book.put("dirs", genDirData(cata_list));
		FsUtils.writeText(markFile, LOGJson.getStr(book.toString(), 2));
	}
}

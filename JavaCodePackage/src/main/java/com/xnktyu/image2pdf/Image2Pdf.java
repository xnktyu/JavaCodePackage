package com.xnktyu.image2pdf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xnktyu.dangdang.CatchBookTable;
import com.xnktyu.utils.FsUtils;
import com.xnktyu.utils.JsonHelper;
import com.xnktyu.utils.LOGJson;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 这个类可以将图片转换为pdf
 */
public class Image2Pdf
{
	/**
	 * 为pdf添加书签
	 *
	 * @param bookmark
	 * @param dirTree
	 */
	private static void addBookmark(PDOutlineNode bookmark, JSONArray dirTree)
	{
		for (int i = 0; i < dirTree.size(); i++)
		{
			JSONObject dirNode = dirTree.getJSONObject(i);
			PDOutlineItem item = new PDOutlineItem();
			item.setTitle(dirNode.getString("name"));
			PDPageXYZDestination dest = new PDPageXYZDestination();
			dest.setPageNumber(dirNode.getInteger("page") - 1);
			item.setDestination(dest);
			bookmark.addLast(item);
			addBookmark(item, dirNode.getJSONArray("dirTree"));
		}
	}

	private static String getBookId(File bookDir)
	{
		File markFile = new File(bookDir, "mark.json");
		if (markFile.exists())
		{
			JSONObject book = JsonHelper.getJSONObject(FsUtils.readText(markFile));
			return book.getString("bookId");
		}
		return null;
	}

	/**
	 * 根据mark.json添加书签
	 *
	 * @param bookDir
	 * @param pageOffset
	 * @param outline
	 */
	private static void addMark(File bookDir, Integer pageOffset, PDDocumentOutline outline)
	{
		JSONObject book;
		File markFile = new File(bookDir, "mark.json");
		if (markFile.exists())
		{
			book = JsonHelper.getJSONObject(FsUtils.readText(markFile));
		}
		else
		{
			book = new JSONObject(true);
			JSONArray dirs = new JSONArray();

			JSONObject dir = new JSONObject(true);
			dir.put("level", 0);
			dir.put("name", "封面");
			dir.put("page", 1);
			dirs.add(dir);

			book.put("dirs", dirs);
		}
		FsUtils.writeText(markFile, LOGJson.getStr(book.toString(), 2));

		JSONArray dirTree = new JSONArray();
		JSONObject dirNode0 = null, dirNode1 = null, dirNode2 = null, dirNode3 = null;
		JSONArray dirTree0 = null, dirTree1 = null, dirTree2 = null, dirTree3 = null;
		JSONArray dirs = book.getJSONArray("dirs");
		for (int i = 0; i < dirs.size(); i++)
		{
			JSONObject dir = dirs.getJSONObject(i);
			if (dir.getInteger("level") == 0)
			{
				dirNode0 = new JSONObject(true);
				dirTree0 = new JSONArray();
				dirNode0.put("name", dir.getString("name"));
				dirNode0.put("page", dir.getInteger("page") + pageOffset);
				dirNode0.put("dirTree", dirTree0);
				dirTree.add(dirNode0);
			}
			else if (dir.getInteger("level") == 1)
			{
				dirNode1 = new JSONObject(true);
				dirTree1 = new JSONArray();
				dirNode1.put("name", dir.getString("name"));
				dirNode1.put("page", dir.getInteger("page") + pageOffset);
				dirNode1.put("dirTree", dirTree1);
				dirTree0.add(dirNode1);
			}
			else if (dir.getInteger("level") == 2)
			{
				dirNode2 = new JSONObject(true);
				dirTree2 = new JSONArray();
				dirNode2.put("name", dir.getString("name"));
				dirNode2.put("page", dir.getInteger("page") + pageOffset);
				dirNode2.put("dirTree", dirTree2);
				dirTree1.add(dirNode2);
			}
			else if (dir.getInteger("level") == 3)
			{
				dirNode3 = new JSONObject(true);
				dirTree3 = new JSONArray();
				dirNode3.put("name", dir.getString("name"));
				dirNode3.put("page", dir.getInteger("page") + pageOffset);
				dirNode3.put("dirTree", dirTree3);
				dirTree2.add(dirNode3);
			}
		}

		addBookmark(outline, dirTree);
	}

	/**
	 * 根据图片，生成pdf文件
	 *
	 * @param bookDir    存放图片的目录，文件名按数字排序，如没有书签文件mark.json，会创建默认书签文件，对书签文件手动编辑后，再调用此方法
	 * @param pageOffset 书签页码偏移，编辑书签的时候，可以参考目录图片编辑，但目录图片的书签名往往跟文件不对应，可用此偏移矫正
	 */
	public static void convert(File bookDir, Integer pageOffset)
	{
		if (!bookDir.exists())
			return;
		if (!bookDir.isDirectory())
			return;
		String bookName = bookDir.getName();
		File pdfDir = new File(bookDir.getParentFile(), "pdf");
		FsUtils.createDir(pdfDir);
		File outFile = new File(pdfDir, bookName + ".pdf");
		if (outFile.exists())
		{
			CatchBookTable.updateNote(getBookId(bookDir), "catched");
			return;
		}
		final List<File> fileList = new ArrayList<File>();
		bookDir.listFiles(new FileFilter()
		{
			public boolean accept(File file)
			{
				if (file.isFile())
				{
					if (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg") || file.getName().toLowerCase().endsWith(".png"))
					{
						fileList.add(file);
					}
				}
				return false;
			}
		});
		if (fileList.size() == 0)
			return;
		Collections.sort(fileList, new Comparator<File>()
		{
			public int compare(File file1, File file2)
			{
				String name1 = file1.getName().substring(0, file1.getName().lastIndexOf("."));
				String name2 = file2.getName().substring(0, file2.getName().lastIndexOf("."));
				try
				{
					Integer num1 = Integer.valueOf(name1);
					Integer num2 = Integer.valueOf(name2);
					return num1.compareTo(num2);
				}
				catch (Exception e)
				{
					return name1.compareTo(name2);
				}
			}
		});
		try
		{
			PDDocument document = new PDDocument();
			for (int i = 0; i < fileList.size(); i++)
			{
				File file = fileList.get(i);
				PDPage page = new PDPage();
				PDImageXObject image = PDImageXObject.createFromFile(file.getAbsolutePath(), document);
				page.setMediaBox(new PDRectangle(image.getWidth(), image.getHeight()));
				PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
				float scale = 1f;
				contentStream.drawImage(image, 0, 0, image.getWidth() * scale, image.getHeight() * scale);
				contentStream.close();
				document.addPage(page);
			}
			PDDocumentOutline outline = new PDDocumentOutline();
			addMark(bookDir, pageOffset, outline);
			document.getDocumentCatalog().setDocumentOutline(outline);
			document.save(outFile);
			document.close();

			CatchBookTable.updateNote(getBookId(bookDir), "catched");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

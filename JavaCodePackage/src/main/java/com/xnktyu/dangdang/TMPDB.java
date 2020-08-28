package com.xnktyu.dangdang;

import com.xnktyu.utils.DBHelper;
import com.xnktyu.utils.LOG;

import java.util.UUID;

public class TMPDB
{
	private static final DBHelper local_db = new DBHelper("xxxxxxxx", "root", "xxxxxxx", "xxxxxx");

	private static class Base
	{
		protected String pack(String str)
		{
			return toString() + "_" + str;
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName().toLowerCase();
		}
	}

	public static final class Exam_Point extends Base
	{
		public final String id = pack("id");
		public final String chapter = pack("chapter");
		public final String point = pack("point");
		public final String difficulty = pack("difficulty");
		public final String importance = pack("importance");
		public final String frequency = pack("frequency");
		public final String note = pack("note");
		public final String ts = pack("ts");
	}

	public static final Exam_Point examPoint = new Exam_Point();


	public static void updateNote(String chapter, String point, String difficulty, String importance, String frequency, String note)
	{
		boolean success = local_db.insert("t_" + examPoint, //
				examPoint.id, UUID.randomUUID().toString(), //
				examPoint.chapter, chapter.trim(), //
				examPoint.point, point.trim(), //
				examPoint.difficulty, difficulty.trim(), //
				examPoint.importance, importance.trim(), //
				examPoint.frequency, frequency.trim(), //
				examPoint.note, note.trim());
		if (!success)
		{
			LOG.v("fail");
		}
	}
}

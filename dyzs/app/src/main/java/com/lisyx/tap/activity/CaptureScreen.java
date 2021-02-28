package com.lisyx.tap.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.utils.FsUtils;
import com.lisyx.tap.utils.LOG;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

// 远程静默抓屏
public class CaptureScreen extends BaseActivity
{
	public static final int CAPTURE_CODE = 0x123;

	public static void start(Context context)
	{
		Intent intent = new Intent(context, CaptureScreen.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setFinishOnTouchOutside(false);
		capture();
	}

	private void capture()
	{
		MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
		Intent intent = projectionManager.createScreenCaptureIntent();
		startActivityForResult(intent, CAPTURE_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAPTURE_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
				final MediaProjection mediaProjection = projectionManager.getMediaProjection(resultCode, data);
				if (mediaProjection != null)
				{
					DisplayMetrics metrics = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(metrics);

					ImageReader mImageReader = ImageReader.newInstance(metrics.widthPixels, metrics.heightPixels, PixelFormat.RGBA_8888, 1);
					final VirtualDisplay virtualDisplay = mediaProjection.createVirtualDisplay(CaptureScreen.class.getSimpleName() + "-capture", //
							metrics.widthPixels, metrics.heightPixels, metrics.densityDpi, //
							DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, //
							mImageReader.getSurface(), null, null);

					mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener()
					{
						private boolean isOver = false;

						@Override
						public void onImageAvailable(ImageReader imageReader)
						{
							if (isOver)
								return;
							isOver = true;

							Image image = imageReader.acquireLatestImage();
							int width = image.getWidth();
							int height = image.getHeight();
							Image.Plane[] planes = image.getPlanes();
							ByteBuffer buffer = planes[0].getBuffer();
							int pixelStride = planes[0].getPixelStride();
							int rowStride = planes[0].getRowStride();
							int rowPadding = rowStride - pixelStride * width;
							Bitmap tmpBitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
							tmpBitmap.copyPixelsFromBuffer(buffer);
							Bitmap bitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, width, height);
							tmpBitmap.recycle();
							image.close();

							if (virtualDisplay != null)
								virtualDisplay.release();
							if (mediaProjection != null)
								mediaProjection.stop();

							try
							{
								File screenFile = new File(Environment.getExternalStorageDirectory(), "screen.png");

								FsUtils.delete(screenFile);

								FileOutputStream os = new FileOutputStream(screenFile);
								bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
								os.close();
								LOG.v("capture screen over");

								if (screenFile.exists())
								{
									LOG.toast(context, "截屏成功");

									Intent intent = new Intent();
									intent.setAction(DyAccessibilityService.Action_screen(context));
									context.sendBroadcast(intent);

//									MyUpload.doUpload(context, screenFile, new Protocol.OnCallback()
//									{
//										@Override
//										public void onResponse(int code, String data, String msg)
//										{
//											if (code == 200)
//											{
//												LOG.toast(context, "截屏成功");
//											}
//										}
//									});
								}
								else
								{
									LOG.toast(context, "截屏失败");
								}
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}

							finish();
						}
					}, null);
				}
				else
				{
					finish();
				}
			}
			else
			{
				finish();
			}
		}
	}
}

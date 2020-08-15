package com.xnktyu.utils;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtils
{
	private static final Map<Long, OkHttpClient> clientMap = new HashMap<Long, OkHttpClient>();

	private static SSLSocketFactory getSSLSocketFactory()
	{
		try
		{
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, getTrustManager(), new SecureRandom());
			return sslContext.getSocketFactory();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static TrustManager[] getTrustManager()
	{
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager()
		{
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{
			}

			@Override
			public X509Certificate[] getAcceptedIssuers()
			{
				return new X509Certificate[]{};
			}
		}};
		return trustAllCerts;
	}

	private static HostnameVerifier getHostnameVerifier()
	{
		HostnameVerifier hostnameVerifier = new HostnameVerifier()
		{
			@Override
			public boolean verify(String s, SSLSession sslSession)
			{
				return true;
			}
		};
		return hostnameVerifier;
	}

	private static OkHttpClient getHttpClient()
	{
		Long threadId = Thread.currentThread().getId();
		if (clientMap.containsKey(threadId))
		{
			return clientMap.get(threadId);
		}
		else
		{
			OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
			httpBuilder.connectTimeout(10, TimeUnit.SECONDS);
			httpBuilder.writeTimeout(60, TimeUnit.SECONDS);
			httpBuilder.readTimeout(60, TimeUnit.SECONDS);
			httpBuilder.sslSocketFactory(getSSLSocketFactory());
			httpBuilder.hostnameVerifier(getHostnameVerifier());
			httpBuilder.cookieJar(new CookieJar()
			{
				private Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();

				@Override
				public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
				{
//					LOG.v(String.format("save cookie : %s : %s : %s", url.host(), cookies.size(), cookies));
					cookiesMap.put(url.host(), cookies);
				}

				@Override
				public List<Cookie> loadForRequest(HttpUrl url)
				{
					List<Cookie> cookies = cookiesMap.get(url.host());
					if (cookies == null)
						cookies = new ArrayList<Cookie>();
//					LOG.v(String.format("load cookie : %s : %s : %s", url.host(), cookies.size(), cookies));
					return cookies;
				}
			});
			OkHttpClient client = httpBuilder.build();
			clientMap.put(threadId, client);
			return client;
		}
	}

	public static boolean doDownload(String httpUrl, File file)
	{
		boolean success = false;
//		LOG.v("download : " + httpUrl);
		file.delete();
		File tmpFile = new File(file.getAbsolutePath() + ".dld");
		tmpFile.delete();
		Response response = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(httpUrl);
			Request request = builder.build();
			response = client.newCall(request).execute();
			if (response != null && response.isSuccessful())
			{
				is = response.body().byteStream();
				fos = new FileOutputStream(tmpFile);
				byte[] buffer = new byte[1024];
				int hasRead = 0;
				while ((hasRead = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, hasRead);
				}
				success = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (fos != null)
					fos.close();
				if (is != null)
					is.close();
				if (response != null)
					response.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
//		LOG.v("will...");
		if (success)
			tmpFile.renameTo(file);
		else
			tmpFile.delete();
//		LOG.v("success : " + success);
		return success;
	}

	public static String doHttpGet(String httpUrl)
	{
		String str = null;
		Response response = null;
		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(httpUrl);
			Request request = builder.build();
			response = client.newCall(request).execute();
			if (response != null)
			{
				if (response.isSuccessful())
				{
					str = response.body().string();
				}
				else
				{
//					LOG.v("response code is " + response.code());
					str = "";
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (response != null)
					response.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return str;
	}

	public static byte[] doHttpGetBytes(String httpUrl)
	{
		byte[] bytes = null;
		Response response = null;
		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(httpUrl);
			Request request = builder.build();
			response = client.newCall(request).execute();
			if (response != null && response.isSuccessful())
			{
				bytes = response.body().bytes();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (response != null)
					response.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return bytes;
	}

	public static String doHttpPostImpl(String httpUrl, RequestBody requestBody)
	{
		String str = null;
		Response response = null;
		try
		{
			OkHttpClient client = getHttpClient();
			Request.Builder builder = new Request.Builder();
			builder.url(httpUrl);
			if (requestBody != null)
				builder.post(requestBody);
			Request request = builder.build();
			response = client.newCall(request).execute();
			if (response != null && response.isSuccessful())
			{
				str = response.body().string();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (response != null)
					response.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return str;
	}

	public static String doHttpPost(String httpUrl, String str)
	{
		return doHttpPostImpl(httpUrl, RequestBody.create(MediaType.parse("application/json;charset=utf-8"), str));
	}

	public static String doHttpPost(String httpUrl, Map<String, String> map)
	{
		if (map != null && map.size() > 0)
		{
			FormBody.Builder builder = new FormBody.Builder();
			for (Map.Entry<String, String> entry : map.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				if (value == null)
					value = "";
				builder.add(key, value);
			}
			return doHttpPostImpl(httpUrl, builder.build());
		}
		else
		{
			return doHttpPostImpl(httpUrl, null);
		}
	}
}

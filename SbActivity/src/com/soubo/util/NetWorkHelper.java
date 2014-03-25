package com.soubo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class NetWorkHelper {
	/*
	 * ��ȡDefaultHttpClientʵ�� charset�������ϣ���Ϊ�� ����DefaultHttpClient����
	 */
	public static DefaultHttpClient getDefaultHttpClient(final String charset) {
		// ���� HttpParams ���������� HTTP ����
		HttpParams httpParams = new BasicHttpParams();

		// �������ӳ�ʱ�� Socket ��ʱ���Լ� Socket �����С
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);

		// �����ض���ȱʡΪ true
		HttpClientParams.setRedirecting(httpParams, true);
		// ����User agent
		// �һ���ҳ�ϵ�ͼƬ���ٵ㸴��ͼƬ��ַ�Ϳ���ճ�����ڶ����ɵ�ͼƬ�������ˡ� ����汾��Ϣ
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		return new DefaultHttpClient(httpParams);
	}

	// ��get��ʽ��ȡ��������
	public static final String getDataDoGet(String url) {
		String result = "";
		try {
			//�õ����������
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//ע�⣺������Ҫ�����֤����Ȼ��ȡ��������Ȩ��
			BasicCredentialsProvider bcp = new BasicCredentialsProvider();
            String userName = "administrator";
            String password = "Psd123";
            bcp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                    userName, password));
            httpClient.setCredentialsProvider(bcp);
         // ���� HttpGet �������÷������Զ����� URL ��ַ���ض���
			HttpUriRequest request = new HttpGet(url);
			//�ûس������������������
			HttpResponse response = httpClient.execute(request);
			//�õ���������Ӧ������
			HttpEntity entity = response.getEntity();
			int status = response.getStatusLine().getStatusCode();
			System.out.println("status"+status);
			if (status == HttpStatus.SC_OK) {
				//�õ���Ӧ������
				result = EntityUtils.toString(entity);
			}
			// ��һ��DefaultHttpClientʵ��������Ҫ����Ҫ���뷶Χʱ��
			// �������������ӹ������������ClientConnectionManager#shutdown()�����ر�
			// ��һЩ���õ���
			httpClient.getConnectionManager().shutdown();
			return result;
		} catch (Exception e) {
			Log.e("NetHelper", "______________��ȡ����ʧ��" + e.toString()
					+ "_____________");
			return "";
		}
	}
	
	
	//post����ʽ
	public static String doPost(String url,List<NameValuePair> params){
		//����post����
		HttpPost post=new HttpPost(url);
		//�õ����������
		DefaultHttpClient httpClient=new DefaultHttpClient();
		//ע�⣺������Ҫ�����֤����Ȼ��ȡ��������Ȩ��
		BasicCredentialsProvider bcp = new BasicCredentialsProvider();
        String userName = "administrator";
        String password = "Psd123";
        bcp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                userName, password));
        httpClient.setCredentialsProvider(bcp);
        //�淵�ؽ���ı���
		String strResult="";
		try {
			//�������������������
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//�������󲢵ȴ���Ӧ
			HttpResponse response=httpClient.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				//ȡ�÷�������Ӧ��ֵ
				strResult=EntityUtils.toString(response.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResult;
	}

	// �жϵ�ǰ�����Ƿ�����
	private static boolean isWIFIConnected(Context context) {
		//������������������
		ConnectivityManager cManger = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);
		//��ȡ�̶���������Ϣ
		NetworkInfo info = cManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (info != null) {
			//��������
			return info.isConnected();
		}
		return false;
	}

	/**
	 * �ж����繤��
	 */
	public static final Uri PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");

	/**
	 * �ж����� �Ƿ������� ������������ͨ������
	 */
	public static boolean cheackNet(Context context) {
		// �ж�WIFI�Ƿ����
		boolean isWIFI = isWIFIConnected(context);
		// �ж�mobilepan�Ƿ��������
		if (isWIFI == false) { // ���Ϊfalse �������û���������
			return false;
		}
		return false;
	}

	public static boolean networkIsAvailable(Context context) {
		/**
		 * ����ConnectivityManager ConnectivityManager��Ҫ���������������صĲ���
		 * ��ص�TelephonyManager�������ֻ�����Ӫ�̵ȵ������Ϣ��WifiManager������wifi��ص���Ϣ��
		 * ���������״̬�����ȵ����Ȩ��<uses-permission
		 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
		 * NetworkInfo������˶�wifi��mobile��������ģʽ���ӵ���ϸ����
		 * ,ͨ����getState()������ȡ��State����������� ���ӳɹ�����״̬��
		 * 
		 */
		ConnectivityManager cManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ��ȡ��������״̬��NetWorkInfo����(��ȡ���õ�����)
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		if (info.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * ����ͼƬ������
	 * @author folder
	 * @param url ͼƬ����·��
	 */
	public static Drawable loadImageFromUrlWithStore(String folder, String url) {
		// ע��url���ܰ���?���������Ҫ��?ǰ�ض�
		if (url.indexOf("?") > 0) {
			folder = url.substring(0, url.indexOf("?"));
		}

		try {
			// ѡȡ�ļ���
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			//��String���ָ�ʽ�滻ΪASCII����
			String encodeFileName = URLEncoder.encode(fileName);
			//�õ�imageUrl·�� (fileName��replace�滻ΪencodeFileName��
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			//������ͼƬ��ȡ���ڴ���
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			//��ͼƬ��תΪbitmap����
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			//�õ�SD����״̬
			String status = Environment.getExternalStorageState();
			if (status.equals(Environment.MEDIA_MOUNTED)) {//sd����������
				//����ͼƬ��Ŀ¼���ļ��У�
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName;
				//ѹ��ͼƬ ��100Ϊ��ѹ������ʾѹ����Ϊ0����outFilenameͼƬѹ���󱣴��λ��
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(
						outFilename));
				//����sd���ϵ�ͼƬ����ʾ�������ϣ�outFilename��sdcard��ͼƬ�ľ���·��
				Bitmap bitmapCompress = BitmapFactory.decodeFile(outFilename);
				Drawable drawable = new BitmapDrawable(bitmapCompress);
				return drawable;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ����ͼƬ
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable loadImageFromUrl(String url) {
		InputStream is = null;
		try {
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			is = (InputStream) imageUrl.getContent();
		} catch (Exception e) {
			Log.e("There", e.toString());
		}
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}
	
	/**
	 * ��ȡ������
	 */
	private static byte[] readInputStream(InputStream inStream)
			throws IOException {
		//�ڴ���ByteArrayOutputStreamʱ��
		//���Զ�����һ�����Զ������Ļ������������ݶ�ȡ�����һ��ͳһд�������Ͳ����������������
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();//��Ҫ�����������
		/*
		 * 4096=4*1024=4K ������Ϊ�ǿ��ٻ���������С4K�� ��br�ж����ݵ�buffer��С��4096
		 */
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			// ÿ��д��4096���ֽڣ�out������Զ�����һ�������������Զ����Ӵ�С
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
}

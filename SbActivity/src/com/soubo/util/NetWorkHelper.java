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
	 * 获取DefaultHttpClient实例 charset参数集合，可为空 返回DefaultHttpClient对象
	 */
	public static DefaultHttpClient getDefaultHttpClient(final String charset) {
		// 创建 HttpParams 以用来设置 HTTP 参数
		HttpParams httpParams = new BasicHttpParams();

		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);

		// 设置重定向，缺省为 true
		HttpClientParams.setRedirecting(httpParams, true);
		// 设置User agent
		// 右击网页上的图片，再点复制图片地址就可以粘贴到摆渡帖吧的图片链接里了。 火狐版本信息
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		return new DefaultHttpClient(httpParams);
	}

	// 用get方式读取网络数据
	public static final String getDataDoGet(String url) {
		String result = "";
		try {
			//得到内置浏览器
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//注意：局域网要身份验证，不然获取不到访问权限
			BasicCredentialsProvider bcp = new BasicCredentialsProvider();
            String userName = "administrator";
            String password = "Psd123";
            bcp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                    userName, password));
            httpClient.setCredentialsProvider(bcp);
         // 创建 HttpGet 方法，该方法会自动处理 URL 地址的重定向
			HttpUriRequest request = new HttpGet(url);
			//敲回车键向服务器发送请求
			HttpResponse response = httpClient.execute(request);
			//得到服务器响应的数据
			HttpEntity entity = response.getEntity();
			int status = response.getStatusLine().getStatusCode();
			System.out.println("status"+status);
			if (status == HttpStatus.SC_OK) {
				//得到响应的内容
				result = EntityUtils.toString(entity);
			}
			// 当一个DefaultHttpClient实例不再需要而且要脱离范围时，
			// 和它关联的连接管理器必须调用ClientConnectionManager#shutdown()方法关闭
			// 做一些有用的事
			httpClient.getConnectionManager().shutdown();
			return result;
		} catch (Exception e) {
			Log.e("NetHelper", "______________读取数据失败" + e.toString()
					+ "_____________");
			return "";
		}
	}
	
	
	//post请求方式
	public static String doPost(String url,List<NameValuePair> params){
		//创建post对象
		HttpPost post=new HttpPost(url);
		//得到内置浏览器
		DefaultHttpClient httpClient=new DefaultHttpClient();
		//注意：局域网要身份验证，不然获取不到访问权限
		BasicCredentialsProvider bcp = new BasicCredentialsProvider();
        String userName = "administrator";
        String password = "Psd123";
        bcp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                userName, password));
        httpClient.setCredentialsProvider(bcp);
        //存返回结果的变量
		String strResult="";
		try {
			//添加请求参数到请求对象
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//发送请求并等待响应
			HttpResponse response=httpClient.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				//取得服务器响应的值
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

	// 判断当前网络是否连接
	private static boolean isWIFIConnected(Context context) {
		//创建连接网络管理对象
		ConnectivityManager cManger = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);
		//获取固定的网络信息
		NetworkInfo info = cManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (info != null) {
			//连接网络
			return info.isConnected();
		}
		return false;
	}

	/**
	 * 判断网络工具
	 */
	public static final Uri PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");

	/**
	 * 判断网络 是否含有网络 是哪种渠道的通信网络
	 */
	public static boolean cheackNet(Context context) {
		// 判断WIFI是否可用
		boolean isWIFI = isWIFIConnected(context);
		// 判断mobilepan是否可以连接
		if (isWIFI == false) { // 如果为false 就提醒用户设置网络
			return false;
		}
		return false;
	}

	public static boolean networkIsAvailable(Context context) {
		/**
		 * 测试ConnectivityManager ConnectivityManager主要管理和网络连接相关的操作
		 * 相关的TelephonyManager则管理和手机、运营商等的相关信息；WifiManager则管理和wifi相关的信息。
		 * 想访问网络状态，首先得添加权限<uses-permission
		 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
		 * NetworkInfo类包含了对wifi和mobile两种网络模式连接的详细描述
		 * ,通过其getState()方法获取的State对象则代表着 连接成功与否等状态。
		 * 
		 */
		ConnectivityManager cManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取代表联网状态的NetWorkInfo对象(获取可用的网络)
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
	 * 下载图片到本地
	 * @author folder
	 * @param url 图片下载路径
	 */
	public static Drawable loadImageFromUrlWithStore(String folder, String url) {
		// 注意url可能包含?的情况，需要在?前截断
		if (url.indexOf("?") > 0) {
			folder = url.substring(0, url.indexOf("?"));
		}

		try {
			// 选取文件夹
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			//将String文字格式替换为ASCII类型
			String encodeFileName = URLEncoder.encode(fileName);
			//得到imageUrl路径 (fileName用replace替换为encodeFileName）
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			//将网络图片读取到内存中
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			//将图片流转为bitmap类型
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			//得到SD卡的状态
			String status = Environment.getExternalStorageState();
			if (status.equals(Environment.MEDIA_MOUNTED)) {//sd卡正常挂载
				//创建图片的目录（文件夹）
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName;
				//压缩图片 （100为不压缩，表示压缩率为0），outFilename图片压缩后保存的位置
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(
						outFilename));
				//载入sd卡上的图片，显示到界面上；outFilename：sdcard上图片的绝对路径
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
	 * 下载图片
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
	 * 读取输入流
	 */
	private static byte[] readInputStream(InputStream inStream)
			throws IOException {
		//在创建ByteArrayOutputStream时，
		//会自动创建一个以自动增长的缓存区，当数据读取完后再一起统一写出来，就不会有乱码的问题了
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();//主要解决乱码问题
		/*
		 * 4096=4*1024=4K 可以认为是开辟缓冲区。大小4K， 从br中读内容到buffer大小是4096
		 */
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			// 每次写入4096个字节，out对象会自动创建一个反冲区，并自动增加大小
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
}

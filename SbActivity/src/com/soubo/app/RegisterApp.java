package com.soubo.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.soubo.db.DBHelper;
import com.soubo.db.UserHelper;
import com.soubo.entity.UrlAddress;
import com.soubo.entity.UserInfo;
import com.soubo.util.NetWorkHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RegisterApp extends Activity {
	// 定义控件变量
	private EditText user_name_edit, pwd_edit, confirm_pwd_edit, e_mail_edit;
	// 定义值变量
	private String user_name, pwd, confirm_pwd, email, md5_pwd;
	// 本地数据保存库的变量
	private UserHelper userHelper;
	// 是否同意协议
	private ToggleButton isAgree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registration);
		init();
	}

	// 界面初始化
	public void init() {
		// 得到控件
		user_name_edit = (EditText) findViewById(R.id.user_name_edit);
		pwd_edit = (EditText) findViewById(R.id.pwd);
		confirm_pwd_edit = (EditText) findViewById(R.id.confirm_pwd);
		e_mail_edit = (EditText) findViewById(R.id.e_mail);
		isAgree = (ToggleButton) findViewById(R.id.isAgree);
		// 实例化数据库帮助类
		userHelper = new UserHelper(getApplicationContext());
	}

	// 按钮的点击事件
	public void onAction(View v) {
		// 取控件的值
		user_name = user_name_edit.getText().toString();
		pwd = pwd_edit.getText().toString();
		confirm_pwd = confirm_pwd_edit.getText().toString();
		email = e_mail_edit.getText().toString();
		md5_pwd = RegisterApp.md5(pwd);
		boolean flag = RegisterApp.isEmail(email);
		// 按钮点击时
		switch (v.getId()) {
		case R.id.login_image:// 登录
			Intent intent = new Intent(this, LoginApp.class);
			startActivity(intent);
			finish();
			break;

		case R.id.register_image:// 注册
			// 注册信息有误
			if ("".equals(user_name) || "".equals(pwd)
					|| "".equals(confirm_pwd)) {
				this.alterExit("注册提示：", "亲，注册信息有误，请确认是否已完整填入！");
			} else if (!pwd.equals(confirm_pwd)) {
				this.alterExit("注册提示：", "亲，前后两次密码不一致，请确认后重新输入！");
				pwd_edit.setText("");
				confirm_pwd_edit.setText("");
			} else if (!flag) {
				this.alterExit("注册提示：", "亲，邮箱格式有误(格式为：as@1as.com)，请确认后重新输入！");
				e_mail_edit.setText("");
			} else {// 注册信息无误
				if (isAgree.isChecked()) {
					Toast.makeText(getApplicationContext(), "您还没有同意本站协议！请确认！",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					System.out.println("begin...");
					new registerThread().start();
					System.out.println("end...");
				}
			}
			break;
		}
	}

	/**
	 * 构造出一个提示对话框
	 * 
	 * @param title
	 * @param message
	 */
	public void alterExit(String title, String message) {
		// 实例化一个AlterDialog.Builder的对象
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 设置对话框的标题
		builder.setTitle(title);
		// 设置对话框的显示信息
		builder.setMessage(message);
		// 设置对话框的按钮
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// 返回
						
						return;
					}
				}).setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// 返回
						return;
					}
				});
		// 创建对话框
		builder.create();
		// 显示对话框
		builder.show();
	}

	// 注册网络线程
	public class registerThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				// 存参数的集合
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userName", user_name));
				params.add(new BasicNameValuePair("userPsd", md5_pwd
						.toLowerCase()));
				params.add(new BasicNameValuePair("eMail", email));

				String user_id = NetWorkHelper.doPost(UrlAddress.register_uel,
						params);

				int max = user_id.lastIndexOf("</int>");

				int min = user_id.substring(0, max).lastIndexOf(">");


				String id = user_id.substring(min, max);
				if (id != null) {
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.obj = id;
					mHandler.sendMessage(msg);
				}
			} catch (Exception e) {
				// 如果出现异常，通过handler发送消息提示用户连接服务器失败
				Message msg = mHandler.obtainMessage();
				msg.what = 2;
				mHandler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

	// 注册时与网络连用处理消息
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			/*
			 * case 0: Toast.makeText(RegisterApp.this, "连接服务器超时，请检查网络连接",
			 * Toast.LENGTH_SHORT).show(); break;
			 */
			case 1:// 注册成功
				String user_id = msg.obj.toString();
				System.out.println("register==>" + user_id);
				if ("-2".equals(user_id)) {
					Toast.makeText(getApplicationContext(), "该用户已存在，请另外注册账号！",
							Toast.LENGTH_LONG).show();
					return;
				} else if ("-1".equals(user_id)) {
					Toast.makeText(getApplicationContext(), "注册失败！",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					UserInfo user = new UserInfo();
					user.setEmail(email);
					user.setPassword(pwd);
					user.setUser_name(user_name);
					user.setStatus(0);
					user.setUser_id(user_id);
					boolean flag1 = userHelper.adduser(user);
					System.out.println("flag==>" + flag1);
					Toast.makeText(getApplicationContext(), "注册成功！",
							Toast.LENGTH_LONG).show();
				}
				break;
			case 2:
				Toast.makeText(RegisterApp.this, "服务器连接失败，请检查网络连接",
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	// 使用MD5给用户名和密码加密
	public static String md5(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	// 得到加密后的字符串
	private static String toHexString(byte[] keyData) {
		if (keyData == null) {
			return null;
		}
		int expectedStringLen = keyData.length * 2;
		StringBuilder sb = new StringBuilder(expectedStringLen);
		for (int i = 0; i < keyData.length; i++) {
			String hexStr = Integer.toString(keyData[i] & 0x00FF, 16);
			if (hexStr.length() == 1) {
				hexStr = "0" + hexStr;
			}
			sb.append(hexStr);
		}
		return sb.toString();

	}

	// 验证邮箱
	public static boolean isEmail(String strEmail) {
		String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(userHelper!=null){
			userHelper.close();
		}
	}
}

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
	// ����ؼ�����
	private EditText user_name_edit, pwd_edit, confirm_pwd_edit, e_mail_edit;
	// ����ֵ����
	private String user_name, pwd, confirm_pwd, email, md5_pwd;
	// �������ݱ����ı���
	private UserHelper userHelper;
	// �Ƿ�ͬ��Э��
	private ToggleButton isAgree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ȥ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registration);
		init();
	}

	// �����ʼ��
	public void init() {
		// �õ��ؼ�
		user_name_edit = (EditText) findViewById(R.id.user_name_edit);
		pwd_edit = (EditText) findViewById(R.id.pwd);
		confirm_pwd_edit = (EditText) findViewById(R.id.confirm_pwd);
		e_mail_edit = (EditText) findViewById(R.id.e_mail);
		isAgree = (ToggleButton) findViewById(R.id.isAgree);
		// ʵ�������ݿ������
		userHelper = new UserHelper(getApplicationContext());
	}

	// ��ť�ĵ���¼�
	public void onAction(View v) {
		// ȡ�ؼ���ֵ
		user_name = user_name_edit.getText().toString();
		pwd = pwd_edit.getText().toString();
		confirm_pwd = confirm_pwd_edit.getText().toString();
		email = e_mail_edit.getText().toString();
		md5_pwd = RegisterApp.md5(pwd);
		boolean flag = RegisterApp.isEmail(email);
		// ��ť���ʱ
		switch (v.getId()) {
		case R.id.login_image:// ��¼
			Intent intent = new Intent(this, LoginApp.class);
			startActivity(intent);
			finish();
			break;

		case R.id.register_image:// ע��
			// ע����Ϣ����
			if ("".equals(user_name) || "".equals(pwd)
					|| "".equals(confirm_pwd)) {
				this.alterExit("ע����ʾ��", "�ף�ע����Ϣ������ȷ���Ƿ����������룡");
			} else if (!pwd.equals(confirm_pwd)) {
				this.alterExit("ע����ʾ��", "�ף�ǰ���������벻һ�£���ȷ�Ϻ��������룡");
				pwd_edit.setText("");
				confirm_pwd_edit.setText("");
			} else if (!flag) {
				this.alterExit("ע����ʾ��", "�ף������ʽ����(��ʽΪ��as@1as.com)����ȷ�Ϻ��������룡");
				e_mail_edit.setText("");
			} else {// ע����Ϣ����
				if (isAgree.isChecked()) {
					Toast.makeText(getApplicationContext(), "����û��ͬ�ⱾվЭ�飡��ȷ�ϣ�",
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
	 * �����һ����ʾ�Ի���
	 * 
	 * @param title
	 * @param message
	 */
	public void alterExit(String title, String message) {
		// ʵ����һ��AlterDialog.Builder�Ķ���
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// ���öԻ���ı���
		builder.setTitle(title);
		// ���öԻ������ʾ��Ϣ
		builder.setMessage(message);
		// ���öԻ���İ�ť
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
						
						return;
					}
				}).setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
						return;
					}
				});
		// �����Ի���
		builder.create();
		// ��ʾ�Ի���
		builder.show();
	}

	// ע�������߳�
	public class registerThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				// ������ļ���
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
				// ��������쳣��ͨ��handler������Ϣ��ʾ�û����ӷ�����ʧ��
				Message msg = mHandler.obtainMessage();
				msg.what = 2;
				mHandler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

	// ע��ʱ���������ô�����Ϣ
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			/*
			 * case 0: Toast.makeText(RegisterApp.this, "���ӷ�������ʱ��������������",
			 * Toast.LENGTH_SHORT).show(); break;
			 */
			case 1:// ע��ɹ�
				String user_id = msg.obj.toString();
				System.out.println("register==>" + user_id);
				if ("-2".equals(user_id)) {
					Toast.makeText(getApplicationContext(), "���û��Ѵ��ڣ�������ע���˺ţ�",
							Toast.LENGTH_LONG).show();
					return;
				} else if ("-1".equals(user_id)) {
					Toast.makeText(getApplicationContext(), "ע��ʧ�ܣ�",
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
					Toast.makeText(getApplicationContext(), "ע��ɹ���",
							Toast.LENGTH_LONG).show();
				}
				break;
			case 2:
				Toast.makeText(RegisterApp.this, "����������ʧ�ܣ�������������",
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	// ʹ��MD5���û������������
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

	// �õ����ܺ���ַ���
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

	// ��֤����
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

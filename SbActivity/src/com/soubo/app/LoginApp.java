package com.soubo.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.soubo.db.DBHelper;
import com.soubo.db.UserHelper;
import com.soubo.entity.UrlAddress;
import com.soubo.entity.UserInfo;
import com.soubo.entity.goodsDetailInfo;
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
import android.widget.EditText;
import android.widget.Toast;

public class LoginApp extends Activity {
	//用户名、密码框
	private EditText login_name_edit,login_pwd_edit;
	//数据库存储帮助类
	private UserHelper userHelper;
	//判断现在登录的用户是否已经保存到本地数据库中
	private boolean flag;
	private String loginName,loginPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logins);
		init();
	}
	//界面初始化
	public void init(){
		login_name_edit=(EditText) findViewById(R.id.login_name);
		login_pwd_edit=(EditText) findViewById(R.id.login_pwd);
		//实例化数据库帮助类
		userHelper=new UserHelper(getApplicationContext());
	}
	//按钮的点击事件
	public void onAction(View v){
		//得到登录的用户名和密码
		 loginName=login_name_edit.getText().toString();
	     loginPwd=login_pwd_edit.getText().toString();
		
		switch (v.getId()) {
		case R.id.login_image://登录
			//给登录密码加密
			final String md5_loginPwd=RegisterApp.md5(loginPwd);
			if("".equals(loginName)||"".equals(loginPwd)){
				this.alterExit("登录提示：", "用户名和密码不能为空！");
				return;
			}else{
				//开启一个登录与网络相关的线程
				new Thread(new Runnable() {
					@Override
					public void run() {
						// 存参数的集合
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						//设置参数
						params.add(new BasicNameValuePair("userName",loginName));
						System.out.println("dsd==>"+md5_loginPwd);
						params.add(new BasicNameValuePair("userPsd", md5_loginPwd.toLowerCase()));
						//向服务器用post发送请求，并得到响应的值
						String loign_id=NetWorkHelper.doPost(UrlAddress.login_url, params);
						System.out.println("lkfl==>"+loign_id);
						//截取服务器返回的字符串，得到登录id信息
						int max = loign_id.lastIndexOf("</int>");
						int min = loign_id.substring(0, max).lastIndexOf(">");
						String id = loign_id.substring(min, max);
						//向handler发送消息
						Message msg=mHandler.obtainMessage();
						msg.what=1;
						msg.obj=id;
						mHandler.sendMessage(msg);
					}
				}).start();
				
			}
			break;

		case R.id .register_image://注册
			Intent intent=new Intent(this, RegisterApp.class);
			startActivity(intent);
			break;
		}
	}
	//登录消息界面处理
	Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			VariableInfo info=(VariableInfo) getApplication();
			if(msg.what==1){//登录成功
				String login_id=msg.obj.toString();
				//用户不存在
				if(login_id.equals("-1")){
					Toast.makeText(LoginApp.this, "对不起，该用户不存在，请注册！", Toast.LENGTH_LONG).show();
					return;
				}
				//用户存在
				ArrayList<UserInfo> user_list=userHelper.queryAllUser();
				for (UserInfo userInfo : user_list) {
					//本地已保存过该用户信息，那么就直接修改器登录状态
					if(login_id.equals(userInfo.getUser_id())){
						//修改数据库的信息
						flag=userHelper.updateUser(1,login_id);
						System.out.println("已存在时修改信息==》"+flag);
						info.setLogin_status(1);
						info.setLogin_id(login_id);
						// TODO Auto-generated method stub
					}else{
						flag=false;
					}
				}
				UserInfo user=new UserInfo();
				//本地还未保存过该用户信息，那么向本地数据库添加该用户信息
				if(flag==false){
					user.setEmail("");
					user.setPassword(loginPwd);
					user.setUser_name(loginName);
					user.setUser_id(login_id);
					user.setStatus(1);
					info.setLogin_status(1);
					info.setLogin_id(login_id);
					//添加
					boolean isAdd=userHelper.adduser(user);
				}
				//登录成功后跳转到购物车页面
				Intent intent=new Intent(LoginApp.this, MainHomeApp.class);
				startActivity(intent);
				finish();
			}else{
				//网络连接失败
				Toast.makeText(getApplicationContext(), "连接服务器失败，请检查网络！", Toast.LENGTH_LONG).show();
			}
		};
	};
	
	
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(userHelper!=null){
			userHelper.close();
		}
	}

}

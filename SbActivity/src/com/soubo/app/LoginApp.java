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
	//�û����������
	private EditText login_name_edit,login_pwd_edit;
	//���ݿ�洢������
	private UserHelper userHelper;
	//�ж����ڵ�¼���û��Ƿ��Ѿ����浽�������ݿ���
	private boolean flag;
	private String loginName,loginPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logins);
		init();
	}
	//�����ʼ��
	public void init(){
		login_name_edit=(EditText) findViewById(R.id.login_name);
		login_pwd_edit=(EditText) findViewById(R.id.login_pwd);
		//ʵ�������ݿ������
		userHelper=new UserHelper(getApplicationContext());
	}
	//��ť�ĵ���¼�
	public void onAction(View v){
		//�õ���¼���û���������
		 loginName=login_name_edit.getText().toString();
	     loginPwd=login_pwd_edit.getText().toString();
		
		switch (v.getId()) {
		case R.id.login_image://��¼
			//����¼�������
			final String md5_loginPwd=RegisterApp.md5(loginPwd);
			if("".equals(loginName)||"".equals(loginPwd)){
				this.alterExit("��¼��ʾ��", "�û��������벻��Ϊ�գ�");
				return;
			}else{
				//����һ����¼��������ص��߳�
				new Thread(new Runnable() {
					@Override
					public void run() {
						// ������ļ���
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						//���ò���
						params.add(new BasicNameValuePair("userName",loginName));
						System.out.println("dsd==>"+md5_loginPwd);
						params.add(new BasicNameValuePair("userPsd", md5_loginPwd.toLowerCase()));
						//���������post�������󣬲��õ���Ӧ��ֵ
						String loign_id=NetWorkHelper.doPost(UrlAddress.login_url, params);
						System.out.println("lkfl==>"+loign_id);
						//��ȡ���������ص��ַ������õ���¼id��Ϣ
						int max = loign_id.lastIndexOf("</int>");
						int min = loign_id.substring(0, max).lastIndexOf(">");
						String id = loign_id.substring(min, max);
						//��handler������Ϣ
						Message msg=mHandler.obtainMessage();
						msg.what=1;
						msg.obj=id;
						mHandler.sendMessage(msg);
					}
				}).start();
				
			}
			break;

		case R.id .register_image://ע��
			Intent intent=new Intent(this, RegisterApp.class);
			startActivity(intent);
			break;
		}
	}
	//��¼��Ϣ���洦��
	Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			VariableInfo info=(VariableInfo) getApplication();
			if(msg.what==1){//��¼�ɹ�
				String login_id=msg.obj.toString();
				//�û�������
				if(login_id.equals("-1")){
					Toast.makeText(LoginApp.this, "�Բ��𣬸��û������ڣ���ע�ᣡ", Toast.LENGTH_LONG).show();
					return;
				}
				//�û�����
				ArrayList<UserInfo> user_list=userHelper.queryAllUser();
				for (UserInfo userInfo : user_list) {
					//�����ѱ�������û���Ϣ����ô��ֱ���޸�����¼״̬
					if(login_id.equals(userInfo.getUser_id())){
						//�޸����ݿ����Ϣ
						flag=userHelper.updateUser(1,login_id);
						System.out.println("�Ѵ���ʱ�޸���Ϣ==��"+flag);
						info.setLogin_status(1);
						info.setLogin_id(login_id);
						// TODO Auto-generated method stub
					}else{
						flag=false;
					}
				}
				UserInfo user=new UserInfo();
				//���ػ�δ��������û���Ϣ����ô�򱾵����ݿ���Ӹ��û���Ϣ
				if(flag==false){
					user.setEmail("");
					user.setPassword(loginPwd);
					user.setUser_name(loginName);
					user.setUser_id(login_id);
					user.setStatus(1);
					info.setLogin_status(1);
					info.setLogin_id(login_id);
					//���
					boolean isAdd=userHelper.adduser(user);
				}
				//��¼�ɹ�����ת�����ﳵҳ��
				Intent intent=new Intent(LoginApp.this, MainHomeApp.class);
				startActivity(intent);
				finish();
			}else{
				//��������ʧ��
				Toast.makeText(getApplicationContext(), "���ӷ�����ʧ�ܣ��������磡", Toast.LENGTH_LONG).show();
			}
		};
	};
	
	
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(userHelper!=null){
			userHelper.close();
		}
	}

}

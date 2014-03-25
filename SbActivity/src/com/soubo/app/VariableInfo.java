package com.soubo.app;

import java.util.ArrayList;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;
/*
 * ���������ȫ�ֱ�������
 */
public class VariableInfo extends Application implements Parcelable{
	private static VariableInfo mInstance = null;
    public boolean m_bKeyRight = true;//�ж�key�Ƿ���ȷ
    BMapManager mBMapManager = null;//��ͼ����

    public static final String strKey = "a1GCFeILpeivSjvv6toHopTx";
	
	
	//��Ʒ�������ƷͼƬ������
	private int image_count;
	//��Ʒ����ĵ�ǰͼƬid
	private int current_index;
	//��¼״̬
	private int login_status;
	//��¼�û���id
	private String Login_id;
	//��Ʒ�ܼ�
	private double total_price;
	public double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}
	public String getLogin_id() {
		return Login_id;
	}
	public void setLogin_id(String login_id) {
		Login_id = login_id;
	}
	public int getLogin_status() {
		return login_status;
	}
	public void setLogin_status(int login_status) {
		this.login_status = login_status;
	}
	public int getImage_count() {
		return image_count;
	}
	public void setImage_count(int image_count) {
		this.image_count = image_count;
	}
	public int getCurrent_index() {
		return current_index;
	}
	public void setCurrent_index(int current_index) {
		this.current_index = current_index;
	}
	//��ȫ�ֱ�������ֵ
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setLogin_status(0);
		mInstance = this;
		initEngineManager(this);
	}
	//��ʼ����ͼ������
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(VariableInfo.getInstance().getApplicationContext(), 
                    "BMapManager��ʼ������!", Toast.LENGTH_LONG).show();
        }
	}
	
	public static VariableInfo getInstance() {
		return mInstance;
	}
	
	
	// �����¼���������������ͨ�������������Ȩ��֤�����
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(VariableInfo.getInstance().getApplicationContext(), "���������������",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(VariableInfo.getInstance().getApplicationContext(), "������ȷ�ļ���������",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }
        //�÷����ж�key�Ƿ�ӡ֤�ɹ�
        @Override
        public void onGetPermissionState(int iError) {
        	//����ֵ��ʾkey��֤δͨ��
            if (iError != 0) {
            	   //��ȨKey����
                Toast.makeText(VariableInfo.getInstance().getApplicationContext(), 
                        "���� DemoApplication.java�ļ�������ȷ����ȨKey,������������������Ƿ�������error: "+iError, Toast.LENGTH_LONG).show();
                VariableInfo.getInstance().m_bKeyRight = false;
            }
            else{
            	VariableInfo.getInstance().m_bKeyRight = true;
            	Toast.makeText(VariableInfo.getInstance().getApplicationContext(), 
                        "key��֤�ɹ�", Toast.LENGTH_LONG).show();
            }
        }
    }
	
	//��ƷͼƬȫͼ��ʾȫ�ֱ�������
	  private ArrayList<Bitmap> bitmap;
      public ArrayList<Bitmap> getBitmap() {
              return bitmap;
      }

      public void setBitmap(ArrayList<Bitmap> bitmap) {
              this.bitmap = bitmap;
      }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	/*public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		//dest.writeParcelableArray(bitmap,);
	}*/
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	 /* public static final Parcelable.Creator<VariableInfo> CREATOR = new Parcelable.Creator<VariableInfo>() {
          //��дCreator

                  @Override
                  public VariableInfo createFromParcel(Parcel source) {
                          VariableInfo p = new VariableInfo();
                    p.bitmap = source.readParcelable(Bitmap.class.getClassLoader());
                      return p;
                  }

                  @Override
                  public VariableInfo[] newArray(int size) {
                      return null;
                  }
              } ;*/
}

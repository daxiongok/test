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
 * 整个程序的全局变量设置
 */
public class VariableInfo extends Application implements Parcelable{
	private static VariableInfo mInstance = null;
    public boolean m_bKeyRight = true;//判断key是否正确
    BMapManager mBMapManager = null;//地图管理

    public static final String strKey = "a1GCFeILpeivSjvv6toHopTx";
	
	
	//商品详情的商品图片的总数
	private int image_count;
	//商品详情的当前图片id
	private int current_index;
	//登录状态
	private int login_status;
	//登录用户的id
	private String Login_id;
	//商品总价
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
	//给全局变量赋初值
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setLogin_status(0);
		mInstance = this;
		initEngineManager(this);
	}
	//初始化地图管理器
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(VariableInfo.getInstance().getApplicationContext(), 
                    "BMapManager初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
	
	public static VariableInfo getInstance() {
		return mInstance;
	}
	
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(VariableInfo.getInstance().getApplicationContext(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(VariableInfo.getInstance().getApplicationContext(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }
        //该方法判断key是否印证成功
        @Override
        public void onGetPermissionState(int iError) {
        	//非零值表示key验证未通过
            if (iError != 0) {
            	   //授权Key错误：
                Toast.makeText(VariableInfo.getInstance().getApplicationContext(), 
                        "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "+iError, Toast.LENGTH_LONG).show();
                VariableInfo.getInstance().m_bKeyRight = false;
            }
            else{
            	VariableInfo.getInstance().m_bKeyRight = true;
            	Toast.makeText(VariableInfo.getInstance().getApplicationContext(), 
                        "key认证成功", Toast.LENGTH_LONG).show();
            }
        }
    }
	
	//商品图片全图显示全局变量设置
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
          //重写Creator

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

package com.soubo.entity;

public class InfoMemberInfo {
	 private int Msd_id;

	 private String Context;
	protected String Title;

	 public InfoMemberInfo(){
		 super();
	 }
	 public InfoMemberInfo(int Msd_id,String Title,String Context){
		 super();
		 this.Msd_id=Msd_id;
		 this.Title=Title;
		 this.Context=Context;
	 }
	public int getMsd_id() {
		return Msd_id;
	}
	public void setMsd_id(int msd_id) {
		Msd_id = msd_id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	 

}

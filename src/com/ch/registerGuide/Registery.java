package com.ch.registerGuide;

import java.util.prefs.*;
import java.*;
import java.awt.event.KeyAdapter;
import java.text.SimpleDateFormat;
import java.util.*;

public class Registery {

	// 把相应的值储存到变量中去
	public void writeValue(String keys,String values) {
		// HKEY_LOCAL_MACHINE/Software/JavaSoft/prefs下写入注册表值.
		Preferences pre = Preferences.systemRoot().node("/javaplayer");
		if(!(keys.equals("systemTime")&&!this.getRegedit("systemTime").equals("")))
			pre.put(keys, values);
		else
			System.out.println("systemTime can not be update");
	}
	// 从注册表中循环读取键值对，单独根据名称读取
	public String getRegedit(String key) {
		Preferences pref = Preferences.systemRoot().node("/javaplayer");
		// 读取注册表值
		String RegeditValue= pref.get(key, "");
		//System.out.println(RegeditValue);
		return RegeditValue;
	}
	
	@SuppressWarnings("deprecation")
	//用户试用时，写入注册表，只有在这三个条目同时为空的时候，证明是第一次启动软件注册向导，否则证明是已经安装但未注册
	public void tryout(Date date,int times,String state){
		if(this.getRegedit("systemTime").equals("")
				&&this.getRegedit("restTimes").equals("")
				&&this.getRegedit("state").equals(""))
		{
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String reString=simpleDateFormat.format(new Date());
			this.writeValue("systemTime", reString);
			this.writeValue("restTimes", times+"");
			this.writeValue("state", state);
		}
		else
		{
			//若第二次以及之后启动软件注册向导，则会显示剩余注册天数，总30天
			System.out.println("软件已经安装");
			String initStr=getRegedit("systemTime");
			String string;
			int rest;
			string=initStr;
			int yyyy=Integer.parseInt(string.substring(0, 4));
			string=initStr;
			int MM=Integer.parseInt(string.substring(5, 7));
			string=initStr;
			int dd=Integer.parseInt(string.substring(8, 10));
			string=initStr;
			int HH=Integer.parseInt(string.substring(11, 13));
			string=initStr;
			int mm=Integer.parseInt(string.substring(14, 16));
			string=initStr;
			int SS=Integer.parseInt(string.substring(17, 19));
			Date initDate=new Date(yyyy-1900,MM-1,dd,HH,mm,SS);
			Date currentDate=new Date();
			rest=(int)((currentDate.getTime()-initDate.getTime())/86400000);
			System.out.println("剩余"+(30-rest)+"天");
		}
	}
	//写入注册信息
	public void register(Date date,int times,String state){
		if(this.getRegedit("systemTime").equals("")
				&&this.getRegedit("restTimes").equals("")
				&&this.getRegedit("state").equals(""))
		{
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String reString=simpleDateFormat.format(new Date());
			this.writeValue("systemTime", reString);
			this.writeValue("restTimes", times+"");
			this.writeValue("state", state);
		}
		else
		{
			System.out.println("软件已经安装");
			String initStr=getRegedit("systemTime");
			String string;
			int rest;
			string=initStr;
			int yyyy=Integer.parseInt(string.substring(0, 4));
			string=initStr;
			int MM=Integer.parseInt(string.substring(5, 7));
			string=initStr;
			int dd=Integer.parseInt(string.substring(8, 10));
			string=initStr;
			int HH=Integer.parseInt(string.substring(11, 13));
			string=initStr;
			int mm=Integer.parseInt(string.substring(14, 16));
			string=initStr;
			int SS=Integer.parseInt(string.substring(17, 19));
			Date initDate=new Date(yyyy-1900,MM-1,dd,HH,mm,SS);
			Date currentDate=new Date();
			rest=(int)((currentDate.getTime()-initDate.getTime())/86400000);
			System.out.println("剩余"+(30-rest)+"天");
		}
	}
	
	//输出信息，这里没怎么用
	public String getInformation(){
		return "systemTime: "+this.getRegedit("systemTime")+"\nrestTimes:  "+this.getRegedit("restTimes")+"\nstate:      "+this.getRegedit("state");
	}

}
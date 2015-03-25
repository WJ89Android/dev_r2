package com.lvdora.aqi.module;

import java.util.ArrayList;
import java.util.List;

import com.lvdora.aqi.model.City;
import com.lvdora.aqi.util.DataTool;
import com.lvdora.aqi.util.EnAndDecryption;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 所有有关缓存的工具类
 * 
 * @author xqp
 * @String fullName
 */
public class ModuleSPIO {
	// 类名
	// private Activity activity;
	public String fullName = "SharedPreferences InputStream and OutputStream";
	private SharedPreferences sp;
	
	// sp意义
	public final static String[] spName = {
			// 0 城市编辑界面中添加的城市
			"citydata",
			// 1 保存当前城市
			"cur_city",
			// 2 服务器返回数据
			"jsondata",
			// 3
			"sitedata",
			// 4
			"location",
			// 5
			"spitdata",
			// 6
			"sortSiteData",
			// 7 记录界面加载时间
			"autoupdate",
			// 8 进度条标志位，存GPS
			"isFlash" };

	public ModuleSPIO() {
	}

	public static void showCityData(Activity activity, String FromWhere) {
		SharedPreferences sp = activity.getSharedPreferences(spName[0], 0);
		List<City> citys = new ArrayList<City>();
		try {
			citys = EnAndDecryption.String2WeatherList(sp.getString("citys", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int cityCount = citys.size();
		Log.d("ModuleSPIO", "showCityData from" + FromWhere + " citys:" + cityCount + " " + citys.toString());
	}

	public void dataIsFlashSave(Activity activity, boolean trueOrFalse) {
		SharedPreferences sp;
		sp = activity.getSharedPreferences("isFlash", 0);
		sp.edit().putBoolean("isFlash", trueOrFalse).commit();
	}

}

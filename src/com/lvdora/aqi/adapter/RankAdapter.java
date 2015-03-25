package com.lvdora.aqi.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lvdora.aqi.R;
import com.lvdora.aqi.dao.CityAqiDao;
import com.lvdora.aqi.model.CityAqi;
import com.lvdora.aqi.util.GradeTool;

public class RankAdapter extends BaseAdapter {

	private List<Map<String, Object>> ranks;
	private Context context;
	private LayoutInflater mInflater;
	private SharedPreferences sp;
	private String cityName;

	public RankAdapter(List<Map<String, Object>> ranks, Context context) {

		this.ranks = ranks;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		sp = context.getSharedPreferences("location", 0);
		int cityId = sp.getInt("cityId", 0);
		CityAqiDao cityAqiDB = new CityAqiDao(context, "");
		CityAqi locationCity = cityAqiDB.getItem(cityId);
		cityName = locationCity.getCityName();
	}

	@Override
	public int getCount() {

		return ranks.size();
	}

	@Override
	public Object getItem(int position) {
		return ranks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		sp = context.getSharedPreferences("location", 0);
		int cityId = sp.getInt("cityId", 0);
		CityAqiDao cityAqiDB = new CityAqiDao(context, "");
		CityAqi locationCity = cityAqiDB.getItem(cityId);
		String cityName = locationCity.getCityName();
		// 如果缓存convertView为空，则需要创建View
		if (convertView == null) {
			holder = new ViewHolder();
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.rank_city_item, null);
			holder.rankText = (TextView) convertView.findViewById(R.id.lv_air_rank);
			holder.provinceText = (TextView) convertView.findViewById(R.id.lv_air_province);
			holder.cityText = (TextView) convertView.findViewById(R.id.lv_air_city);
			holder.aqiText = (TextView) convertView.findViewById(R.id.lv_air_aqi);

			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position % 2 == 0) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.GRAY);
		}

		// 设置定位城市列表背景色
		if (cityName.equals((String) ranks.get(position).get("cityName"))) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.location));
		} else {
			convertView.setBackgroundColor(Color.WHITE);
		}

		// 隔行变色
		/*
		 * if(position % 2 == 0){ convertView.setBackgroundColor(Color.WHITE);
		 * }else{ convertView.setBackgroundColor(Color.GRAY); }
		 */

		holder.rankText.setText(ranks.get(position).get("rank").toString());
		holder.provinceText.setText(ranks.get(position).get("provinceName").toString());
		holder.cityText.setText((String) ranks.get(position).get("cityName"));
		holder.aqiText.setText((String) ranks.get(position).get("aqi"));
		String aqiValue = (String) ranks.get(position).get("aqiCal");// 必须toString()
		holder.aqiText
				.setBackgroundDrawable(context.getResources().getDrawable(GradeTool.getAqiColorByIndex(aqiValue)));
		holder.aqiText.setTextColor(context.getResources().getColor(GradeTool.getTextColorByAqi(aqiValue)));

		return convertView;
	}

	static class ViewHolder {
		public TextView rankText;
		public TextView provinceText;
		public TextView cityText;
		public TextView aqiText;

	}

}

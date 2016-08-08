package com.itrane.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrane.common.util.model.DtAjaxData;

public class WebAppUtil {

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//JSONユーティリティ
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	/**
	 * オブジェクトを JSON 文字列に変換.
	 * 
	 * @param dt
	 * @return
	 */
	static public String toJson(Object dt) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(dt);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * JSON文字列から指定タイプのインスタンスを取得.
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	static public <T> T fromJson(String json, Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		T t = null;
		try {
			t = mapper.readValue(json, type);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	static public DtAjaxData getDtAjaxData(String qs) throws UnsupportedEncodingException {
		DtAjaxData dta = new DtAjaxData();
		if (qs!=null && qs.length() > 0) {
			String[] ps = URLDecoder.decode(qs, "UTF-8").split("&");
			if (ps.length > 0) {
				String json = ps[0];
				dta = fromJson(json, DtAjaxData.class);
			}
		}
		return dta;
		
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// 乗り換え案内
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	/**
	 * 乗換え案内のURLを返す
	 * 
	 * @param from
	 *            出発地　(中山)
	 * @param to
	 *            到着地 (横浜)
	 * @param date
	 *            日付 (2010/09/02)
	 * @param time
	 *            時刻 (15:00)
	 * @param type
	 *            出発｜到着 (dep|arr)
	 * @param express
	 *            有料特急の使用 (true|false)
	 */
	public static String getTransitURL(String from, String to, String date,
			String time, String type, boolean express) {
		StringBuilder sb = new StringBuilder();
		try {
			String encFrom = URLEncoder.encode(from, "utf-8");
			String encTo = URLEncoder.encode(to, "utf-8");
			String encDate = URLEncoder.encode(date, "utf-8");
			String encTime = URLEncoder.encode(time, "utf-8");

			sb.append("http://www.google.co.jp/maps?ie=UTF8&f=d&dirflg=r")
					.append("&saddr=").append(encFrom).append("&daddr=")
					.append(encTo).append("&date=").append(encDate)
					.append("&time=").append(encTime).append("&ttype=")
					.append(type).append("&sort=time");

			if (!express) {
				// 有料特急を使用しない場合
				sb.append("&noexp=1");
			}
		} catch (UnsupportedEncodingException e1) {
		}

		return sb.toString();
	}

}

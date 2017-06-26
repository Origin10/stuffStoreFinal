package com.stuffstoreE.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class USConstants {
	
	public final static String US = "US";
	
	public final static Map<String, String> mapOfUSStates = new HashMap<String, String>() {
		{
            put("台北市", "台北市");
            put("新北市", "新北市");
            put("桃園市", "桃園市");
            put("台中市", "台中市");
            put("台南市", "台南市");
            put("高雄市", "高雄市");
            put("基隆市", "基隆市");
            put("新竹縣", "新竹縣");
            put("苗栗縣", "苗栗縣");
            put("彰化縣", "彰化縣");
            put("南投縣", "南投縣");
            put("雲林縣", "雲林縣");
            put("嘉義縣", "嘉義縣");
            put("屏東縣", "屏東縣");
            put("宜蘭縣", "宜蘭縣");
            put("花蓮縣", "花蓮縣");
            put("台東縣", "台東縣");
            put("澎湖縣", "澎湖縣");

		}
	};
	
	public final static List<String> listOfUSStatesCode = new ArrayList<>(mapOfUSStates.keySet());
	public final static List<String> listOfUSStatesName = new ArrayList<>(mapOfUSStates.values());

}

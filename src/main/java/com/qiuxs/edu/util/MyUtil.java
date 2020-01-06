package com.qiuxs.edu.util;

import java.text.NumberFormat;
import java.util.UUID;

public class MyUtil {
	public static String getUUID(){
		UUID uuid=UUID.randomUUID();

		String uuidStr=uuid.toString();

		return uuidStr.replace("-", "");
	}
	
	public static String getPercent(Double sub,Double sum){
		
		return getPercent(sub, sum,true);
		
	}
	public static String getPercent(Double sub,Double sum,Boolean b){
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		
		// 设置精确到小数点后2位
		sub = sub==null?0:sub; 
		sum = sum==null?0:sum;
		if (sum == 0 ){
			return "0.00%";
		}
		numberFormat.setMaximumFractionDigits(2);
		
		if (b) {
			String result = numberFormat.format(  sub / sum * 100);
			return result+"%";
		}else{
			String result = numberFormat.format(  sub / sum);
			return result;
		}
		
	}

}

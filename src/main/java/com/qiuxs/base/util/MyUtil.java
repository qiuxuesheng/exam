package com.qiuxs.base.util;

import java.text.NumberFormat;
import java.util.UUID;

public class MyUtil {
	public static String getUUID(){
		UUID uuid=UUID.randomUUID();

		String uuidStr=uuid.toString();

		return uuidStr.replace("-", "");
	}
	
	public static String getPercent(double sub,double sum){
		
		return getPercent(sub, sum,true);
		
	}
	public static String getPercent(double sub,double sum,boolean b){
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		
		// 设置精确到小数点后2位
		if (sub==0 || sum == 0 ){
			return b?"0.00%":"0";
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

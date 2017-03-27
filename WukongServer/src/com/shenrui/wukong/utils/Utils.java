package com.shenrui.wukong.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Utils {

	/**
	 * 计算两个日期相差天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int calculateDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			// System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2 - day1;
		}
	}
	
	private static String[] SOURCE_CODE = {
		"0","1","2","3","4","5","6","7","8","9","10",  
        "a","b","c","d","e","f","g","h","i","j","k",
        "l","m","n","o","p","q","r","s","t","u","v",
        "w","x","y","z"
	};
	
	private static String[] FILL_CODE = {"S","R","H","X"};
	
	/**
	 * 对不足5位的邀请码进行填充
	 * @param tmpCode
	 * @return
	 */
	public static String getFillCode(String tmpCode){
		int length = tmpCode.length();
		Random random = new Random();
		List<Integer> listPosi = new ArrayList<Integer>();
		for(int i=0; i<5-length; i++){
			int tmp = random.nextInt(4);
			while(listPosi.contains(tmp)){
				tmp = random.nextInt(4);
			}
			listPosi.add(tmp);
		}
		Collections.sort(listPosi);
//		System.out.println("索引： "+listPosi.toString());
		
		List<String> listFill = new ArrayList<String>();
		for(Integer p : listPosi){
			listFill.add(FILL_CODE[p]);
		}
		System.out.println("填充字: " + listFill.toString());
		
		switch (length) {
		case 1:
			listFill.add(2,tmpCode);
			break;
		case 2:
			listFill.add(1, tmpCode.substring(0, 1));
			listFill.add(3,tmpCode.substring(1));
			break;
		case 3:
			listFill.add(0, tmpCode.substring(0, 1));
			listFill.add(2, tmpCode.substring(1, 2));
			listFill.add(4, tmpCode.substring(2));
			break;
		case 4:
			listFill.add(0,tmpCode.substring(0,2));
			listFill.add(2,tmpCode.substring(2));
			break;
		default:
			break;
		}
//		System.out.println(listFill.toString());
		
		String code = "";
		for(String str : listFill){
			code += str;
		}
		return code;
	}
	
	/**
	 * 生成邀请码
	 * @param userId
	 * @return
	 */
	public static String generateInviteCode(int userId){
		String tmpCode = "";
		while (userId != 0) {
			int mod = userId % 36;
			userId = userId / 36;
			tmpCode = tmpCode + SOURCE_CODE[mod];
		}
		
		System.out.println("tmpCode: " + tmpCode);
		
		if(tmpCode.length() < 5){
			
			tmpCode = getFillCode(tmpCode);
		}
		return tmpCode;
	}
}

package com.camwait.android.TouchCountApp.utils;

public class Util {

	/*
	 * 基本數字單位;
	 */
	private static final String[] units = { "千", "百", "十", "" };// 個位

	/*
	 * 大數字單位;
	 */
	private static final String[] bigUnits = { "萬", "億" };

	/*
	 * 中文數字;
	 */
//	private static final char[] numChars = { '一', '二', '三', '四', '五', '六', '七',
//			'八', '九' };

	 private static final char[] numChars =
	 { '壹', '貳', '參', '肆', '伍', '陸', '柒', '捌', '玖' };

	private static char numZero = '零';

	/**
	 * 將中文數字轉換為阿拉伯數字;
	 * 
	 * @param numberCN
	 * @return
	 */
	public static int numberCN2Arab(String numberCN) {

		String tempNumberCN = numberCN;

		// 異常數據處理;
		if (tempNumberCN == null) {
			return 0;
		}

		/*
		 * nums[0] 保存以千單位; nums[1] 保存以萬單位; nums[2] 保存以億單位;
		 */
		String[] nums = new String[bigUnits.length + 1];

		// 千位以內,直接處理;
		nums[0] = tempNumberCN;

		/*
		 * 分割大數字,以千為單位進行運算;
		 */
		for (int i = (bigUnits.length - 1); i >= 0; i--) {

			// 是否存在大單位(萬,億...);
			int find = tempNumberCN.indexOf(bigUnits[i]);

			if (find != -1) {
				String[] tempStrs = tempNumberCN.split(bigUnits[i]);

				// 清空千位內容;
				if (nums[0] != null) {
					nums[0] = null;
				}

				if (tempStrs[0] != null) {
					nums[i + 1] = tempStrs[0];
				}

				if (tempStrs.length > 1) {
					tempNumberCN = tempStrs[1];

					if (i == 0) {
						nums[0] = tempStrs[1];
					}

				} else {
					tempNumberCN = null;

					break;
				}
			}
		}

		String tempResultNum = "";

		for (int i = nums.length - 1; i >= 0; i--) {
			if (nums[i] != null) {
				tempResultNum += numberKCN2Arab(nums[i]);
			} else {
				tempResultNum += "0000";
			}
		}

		return Integer.parseInt(tempResultNum);
	}

	/**
	 * 將一位中文數字轉換為一位數字; eg: 一 返回 1;
	 * 
	 * @param onlyCNNumber
	 * @return
	 */
	public static int numberCharCN2Arab(char onlyCNNumber) {

		if (numChars[0] == onlyCNNumber) {
			return 1;
		} else if (numChars[1] == onlyCNNumber || onlyCNNumber == '兩') {// 處理中文習慣用法(二,兩)
			return 2;
		} else if (numChars[2] == onlyCNNumber) {
			return 3;
		} else if (numChars[3] == onlyCNNumber) {
			return 4;
		} else if (numChars[4] == onlyCNNumber) {
			return 5;
		} else if (numChars[5] == onlyCNNumber) {
			return 6;
		} else if (numChars[6] == onlyCNNumber) {
			return 7;
		} else if (numChars[7] == onlyCNNumber) {
			return 8;
		} else if (numChars[8] == onlyCNNumber) {
			return 9;
		}

		return 0;
	}

	/**
	 * 將一位數字轉換為一位中文數字; eg: 1 返回 一;
	 * 
	 * @param onlyArabNumber
	 * @return
	 */
	public static char numberCharArab2CN(char onlyArabNumber) {

		if (onlyArabNumber == '0') {
			return numZero;
		}

		if (onlyArabNumber > '0' && onlyArabNumber <= '9') {
			return numChars[onlyArabNumber - '0' - 1];
		}

		return onlyArabNumber;
	}

	/**
	 * 
	 * @param num
	 * @return
	 */
	public static String numberArab2CN(Integer num) {

		String tempNum = num + "";

		// 傳說中的分頁算法;
		int numLen = tempNum.length();
		int start = 0;
		int end = 0;
		int per = 4;
		int total = (int) ((numLen + per - 1) / per);
		int inc = numLen % per;

		/*
		 * 123,1234,1234 四位一段,進行處理;
		 */
		String[] numStrs = new String[total];

		for (int i = total - 1; i >= 0; i--) {
			start = (i - 1) * per + inc;

			if (start < 0) {
				start = 0;
			}

			end = i * per + inc;

			numStrs[i] = tempNum.substring(start, end);
		}

		String tempResultNum = "";

		int rempNumsLen = numStrs.length;
		for (int i = 0; i < rempNumsLen; i++) {

			// 小於1000補零處理;
			if (i > 0 && Integer.parseInt(numStrs[i]) < 1000) {
				tempResultNum += numZero
						+ numberKArab2CN(Integer.parseInt(numStrs[i]));
			} else {
				tempResultNum += numberKArab2CN(Integer.parseInt(numStrs[i]));
			}

			// 加上單位(萬,億....)
			if (i < rempNumsLen - 1) {
				tempResultNum += bigUnits[rempNumsLen - i - 2];
			}

		}

		// 去掉未位的零
		tempResultNum = tempResultNum.replaceAll(numZero + "$", "");

		return tempResultNum;
	}

	/**
	 * 將千以內的數字轉換為中文數字;
	 * 
	 * @param num
	 * @return
	 */
	private static String numberKArab2CN(Integer num) {

		char[] numChars = (num + "").toCharArray();

		String tempStr = "";

		int inc = units.length - numChars.length;

		for (int i = 0; i < numChars.length; i++) {
			if (numChars[i] != '0') {
				tempStr += numberCharArab2CN(numChars[i]) + units[i + inc];
			} else {
				tempStr += numberCharArab2CN(numChars[i]);
			}
		}

		// 將連續的零保留一個
		tempStr = tempStr.replaceAll(numZero + "+", numZero + "");

		// 去掉未位的零
		tempStr = tempStr.replaceAll(numZero + "$", "");

		return tempStr;

	}

	/**
	 * 處理千以內中文數字,返回4位數字字符串,位數不夠以"0"補齊;
	 * 
	 * @param numberCN
	 * @return
	 */
	private static String numberKCN2Arab(String numberCN) {

		if ("".equals(numberCN)) {
			return "";
		}

		int[] nums = new int[4];

		if (numberCN != null) {

			for (int i = 0; i < units.length; i++) {
				int idx = numberCN.indexOf(units[i]);

				if (idx > 0) {
					char tempNumChar = numberCN.charAt(idx - 1);

					int tempNumInt = numberCharCN2Arab(tempNumChar);

					nums[i] = tempNumInt;
				}
			}

			// 處理十位
			char ones = numberCN.charAt(numberCN.length() - 1);
			nums[nums.length - 1] = numberCharCN2Arab(ones);

			// 處理個位
			if ((numberCN.length() == 2 || numberCN.length() == 1)
					&& numberCN.charAt(0) == '十') {
				nums[nums.length - 2] = 1;
			}
		}

		// 返回結果
		String tempNum = "";
		for (int i = 0; i < nums.length; i++) {
			tempNum += nums[i];
		}

		return (tempNum);
	}
	
}

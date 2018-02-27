package com.viewscenes.util.report.util;

public final class Encoding {
	public static String strTemp ="为奇数字时乱码";
	/**
	 * 在url中转换中文等字符使得其变成ASCII码，防止乱码
	 * 类似%A4 %3F
	 * 英文字符不转换
	 * @author: liqing
	 * @param url
	 * @return String
	 */
    public static String strTOascii(String url) {
		byte[] bytes = null;
		String ascii = "";
		try {
			for (int i = 0; i < url.length(); i++) {
				bytes = (String.valueOf(url.toCharArray()[i])).getBytes();
				if (bytes.length == 1) { // 英文字符
					ascii = ascii + (new String(bytes));
				}
				if (bytes.length == 2) { // 中文字符
					ascii += "%"
							+ Integer.toHexString(bytes[0]).toUpperCase()
									.substring(6, 8);
					ascii += "%"
							+ Integer.toHexString(bytes[1]).toUpperCase()
									.substring(6, 8);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ascii;
	}
    
	/**
	 * 转义url的特殊字符(对非安全字符进行转义: <>\\#%{}|^~[]`")          
	 * @param url
	 *            要转换的url
	 * @return
	 */
	public static String getURL2Str(String url) {
		String changeUrl = url;
		if (changeUrl != null) {
			try {
				// 如果传入replaceAll 的第一個parameter不当成 regular expression 的话,用"\\Q"或"\\E"表示不是正则表达式
				String notRegular = "\\Q";
				// 换%符号一定要放在首位,因为转换后的特殊字符都含有%符号
				changeUrl = changeUrl.replaceAll("%(?!(2[02357]|3[ce]|7[bcde]|5[bcde]|60))","%25");
				changeUrl = changeUrl.replaceAll(notRegular + " ", "%20");
				changeUrl = changeUrl.replaceAll(notRegular + "<", "%3c");
				changeUrl = changeUrl.replaceAll(notRegular + ">", "%3e");
				changeUrl = changeUrl.replaceAll(notRegular + "\"", "%22");
				changeUrl = changeUrl.replaceAll(notRegular + "#", "%23");
				changeUrl = changeUrl.replaceAll(notRegular + "{", "%7b");
				changeUrl = changeUrl.replaceAll(notRegular + "}", "%7d");
				changeUrl = changeUrl.replaceAll(notRegular + "|", "%7c");
				changeUrl = changeUrl.replaceAll(notRegular + "\\", "%5c");
				changeUrl = changeUrl.replaceAll(notRegular + "^", "%5e");
				changeUrl = changeUrl.replaceAll(notRegular + "~", "%7e");
				changeUrl = changeUrl.replaceAll(notRegular + "[", "%5b");
				changeUrl = changeUrl.replaceAll(notRegular + "]", "%5d");
				changeUrl = changeUrl.replaceAll(notRegular + "`", "%60");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("转义url的特殊字符(对非安全字符进行转义)出错!");
			}
		}
		return changeUrl;
	}
}

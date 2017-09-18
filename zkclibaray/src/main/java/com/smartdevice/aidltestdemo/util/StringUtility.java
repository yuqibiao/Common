package com.smartdevice.aidltestdemo.util;

import android.text.TextUtils;
import android.util.Log;


public class StringUtility 
{
	/**@author xuxl
	 * @param strInput 传入String
	 * @return boolean 传入的String是否为空
	 * */
	static public boolean isEmpty(String strInput)
	{
	    return TextUtils.isEmpty(strInput);
	}
	
	public static String getStringFormat(byte[] bytes){
		String str = "";
		for(byte b : bytes){
			str += String.format("%02X ", b);
		}
		return str;
	}
	
	static protected boolean CheckByte(byte byteIn)
	{
		//'0' - '9'
		if(byteIn <= 0x39 && byteIn >= 0x30)
			return true;
		//'A' - 'F'
		if(byteIn <= 0x46 && byteIn >= 0x41)
			return true;
		//'a' - 'f'
		if(byteIn <= 0x66 && byteIn >= 0x61)
			return true;
		return false;
	}
	static protected boolean CheckString(String strInput)
	{
		strInput = strInput.trim();
		if(strInput.length() != 2)
			return false;
		byte[] byteArry = strInput.getBytes();
		for(int i = 0; i < 2; i++)
		{
			if(!CheckByte(byteArry[i]))
				return false;
		}
		return true;
	}
	
	static protected byte StringToByte(String strInput)
	{
		byte[] byteArry = strInput.getBytes();
		for(int i = 0; i < 2; i++)
		{
			
			if(byteArry[i] <= 0x39 && byteArry[i] >= 0x30)
			{
				byteArry[i] -= 0x30; 
			}
			else if(byteArry[i] <= 0x46 && byteArry[i] >= 0x41)
			{
				byteArry[i] -= 0x37;
			}
			else if(byteArry[i] <= 0x66 && byteArry[i] >= 0x61)
			{
				byteArry[i] -= 0x57;
			}
		}
		return (byte)((byteArry[0] << 4) | (byteArry[1] & 0x0F));
	}
	/** @author xuxl
	 *  功能：字符串转字节数�?
	 *  @param strInput 
	 *  @param arryByte 
	 *  @return int
	 * */
	static public byte[] StringToByteArray(String strInput)
	{
		int l = strInput.length() / 2;  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++) {  
            ret[i] = (byte) Integer  
                    .valueOf(strInput.substring(i * 2, i * 2 + 2), 16).byteValue();  
        }  
        return ret;  

	}
	static public String ByteArrayToString(byte[] arryByte, int nDataLength)
	{
		String strOut = new String();
		for(int i = 0; i < nDataLength; i++)
			strOut += String.format("%02X ", arryByte[i]);
		return strOut;
	}
	/** @author john.li
	 *  @param str 传入字符�?
	 *  @param reg 按照哪种方式或哪个字段拆�?
	 *  @return arrayStr 返回拆分后的数组�?
	 * */
	static public String[] spiltStrings(String str, String reg){
		String [] arrayStr = str.split(reg); 
		return arrayStr;
	}
	
	// 字符序列转换为16进制字符串  
    static public String bytesToHexString(byte[] src) {  
        return bytesToHexString(src, true);  
    }  
  
    static public String bytesToHexString(byte[] src, boolean isPrefix) {  
        StringBuilder stringBuilder = new StringBuilder();  
        if (isPrefix == true) {  
            stringBuilder.append("0x");  
        }  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        char[] buffer = new char[2];  
        for (int i = 0; i < src.length; i++) {  
            buffer[0] = Character.toUpperCase(Character.forDigit(  
                    (src[i] >>> 4) & 0x0F, 16));  
            buffer[1] = Character.toUpperCase(Character.forDigit(src[i] & 0x0F,  
                    16));  
            System.out.println(buffer);  
            stringBuilder.append(buffer);
            stringBuilder.append("  ");
        }  
        return stringBuilder.toString();  
    }  
}

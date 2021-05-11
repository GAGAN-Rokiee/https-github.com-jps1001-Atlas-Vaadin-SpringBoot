package it.besolution.utils;

public class CommonUtils {

	private CommonUtils(){}

	
	public static boolean isNullOrEmptyString(String string) 
	{
		try
		{
			if (null == string || "".equals(string.trim()) || "null".equals(string))
				return true;
			else
				return false;
		} 
		catch(Exception exp)
		{
			exp.printStackTrace();
			return true;
		}
	}
	
}

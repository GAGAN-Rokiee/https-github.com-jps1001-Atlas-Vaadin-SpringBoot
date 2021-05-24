package it.besolution.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class CommonUtils {

	private CommonUtils(){}


	public static Properties loadPropertyFile(String configurationFilePath) throws IOException
	{
		Properties prop = null;		
		FileInputStream fis = new FileInputStream (configurationFilePath);

		prop = new Properties();
		prop.load(fis);
		if (fis != null)
		{
			fis.close();
		}
		return prop;
	}

	public static String getValue(Properties prop, String keyName ) 
	{
		try
		{
			if (prop == null || prop.getProperty(keyName) == null)
			{
				return null;
			}

			return prop.getProperty(keyName).trim();
		} 
		catch(Exception exp)
		{
			exp.printStackTrace();
			return "";
		}
	}
	
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

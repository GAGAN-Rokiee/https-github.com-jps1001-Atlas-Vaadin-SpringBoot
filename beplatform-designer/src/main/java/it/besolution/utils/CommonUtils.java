package it.besolution.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonUtils {

	private CommonUtils(){}

	private static Logger logger = null;

	public static Logger getLogger(Class<?> clazz)
	{
		if(logger == null)
		{
			logger = LoggerFactory.getLogger(clazz);
		}
		return logger;
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

	public static void printStakeTrace(Exception exp,Class<?> clazz)
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("\n").append("START Exception ").append(" --->");
		buffer.append("\n").append(exp.getMessage()); StackTraceElement[]
				stackTraceElement = exp.getStackTrace(); for(int i = 0; i <
						stackTraceElement.length; i++) {
					buffer.append("\n");buffer.append(stackTraceElement[i]); }
				buffer.append("\n");buffer.append("<--- END Exception");
				getLogger(clazz).error(buffer.toString());

	}
	

}

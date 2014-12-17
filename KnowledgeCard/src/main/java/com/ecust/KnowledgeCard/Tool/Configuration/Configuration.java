package com.ecust.KnowledgeCard.Tool.Configuration;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
//∂¡»°…Ë÷√
public class Configuration {
	public final static String filePath = "config.properties";
	private static Properties props = null;
	public static void setValue(String key,String value)
	{
		try {
	    	 if(props==null)
	    	 {
	    		 props = new Properties();
	    		 InputStream in = new BufferedInputStream (new FileInputStream(filePath));
		         props.load(in);
	    	 }
	         props.setProperty (key , value);
	         } catch (Exception e) {
	         e.printStackTrace();
	         }
	}
	public static String readValue(String key)
	{
	     try {
	    	 if(props==null)
	    	 {
	    		 props = new Properties();
	    		 InputStream in = new BufferedInputStream (new FileInputStream(filePath));
		         props.load(in);
	    	 }
	         return props.getProperty (key);
	         } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	        }
	}
}
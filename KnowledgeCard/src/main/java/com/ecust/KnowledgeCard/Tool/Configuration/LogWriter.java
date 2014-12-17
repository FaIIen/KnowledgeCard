package com.ecust.KnowledgeCard.Tool.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
//дlog
public class LogWriter {
	private static Logger loger = null;
	private static String logFileName = Configuration.readValue("logFileName");
	public static void clear()
	{
		File file = new File(logFileName);
		file.delete();
	}
	public static void write(String str)
	{
		write(str,Level.ALL);
	}
	public static void setName(String name)
	{
		logFileName = name;
	}
	public static void write(String str,Level level)
	{
		if(loger==null)
		{
			loger =Logger.getLogger("KnowledgeCardFusion");
		    FileHandler fh;
		    try {
				fh = new FileHandler(logFileName,true);
				loger.addHandler(fh);
			    loger.setLevel(Level.ALL);
			    SimpleFormatter sf = new SimpleFormatter();
			    fh.setFormatter(sf);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		loger.log(level, str);
	    
	}
}

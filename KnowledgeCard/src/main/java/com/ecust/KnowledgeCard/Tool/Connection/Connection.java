package com.ecust.KnowledgeCard.Tool.Connection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ecust.KnowledgeCard.Tool.Configuration.Configuration;
 
public class Connection {
	public static Document connect(String url)
	{
		Document doc=null;
		if(url!=null && url.length()>0)
		{
			int times=Integer.parseInt(Configuration.readValue("RetryTimes"));
			while(times>0)
			{ 
				try {
					doc = getHTML(url);
				} catch (Exception e) {
					times--;
					if(times==0)
					{
						System.out.println("Connection Error when connect to "+url+" Message:"+e.getMessage());
					}
					continue;
				}
				break;
			}
		}
		return doc;
	}
	protected static Document getHTML(String url) throws IOException{
		Document doc=readFile(url);
		if(doc!=null)
			return doc;
		else{
			String txt=Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36")
					.cookie("_FS", "mkt=en-us&ui=#en-us")
					.execute()
					.body();
			saveFile(txt,url);
			doc=readFile(url);
			return doc;
		}
		
	}
	
	private static void saveFile(String txt,String url) throws IOException{
		int fileName=url.hashCode();
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File("./cache/"+Integer.toString(fileName)+".html")));
		bw.write(txt);
		bw.close();
		
	}
	
	private static Document readFile(String url) throws IOException{
		int fileName=url.hashCode();
		File file=new File("./cache/"+Integer.toString(fileName)+".html");
		if(file.exists())
			return Jsoup.parse(file, "UTF-8", ""); 
		else
			return  null;
	}
}
package com.ecust.KnowledgeCard.Tool.Fetcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ecust.KnowledgeCard.Bean.KnowledgeCard;
import com.ecust.KnowledgeCard.Main.KnowledgeCardFusion;


public class YahooFetcher extends CardFetcher{
	public YahooFetcher(int times){
		super(times);
		this.baseURL = "https://search.yahoo.com/search?p=";
	}

	protected KnowledgeCard cardAnalyze(Document doc)
	{
		KnowledgeCard card=new KnowledgeCard();
		
		//card name
		String name ="";
		if(null == doc || null == doc.select("div.cptn-ctnt > p.txt").first())
		{
			if(null == doc || null == doc.select("div.compImageProfile > p.txt").first()) return null;
			else name = doc.select("div.compImageProfile > p.txt").first().text();
		}
		else name = doc.select("div.cptn-ctnt > p.txt").first().text();
		card.setName(name);
		//abstract
		String abstr = "";
		if(null != doc.select("div.compText").first()) 
			abstr=doc.select("div.compText").first().text();
		card.setAbstractString(abstr);
		//attribute
		Elements lis = doc.select("ul.compInfo > li");
		for(Element li : lis)
		{
			String label = li.select("label").text();
			String content = li.text();
			int loc = content.indexOf(":");
			content = content.substring(loc+1);
			if(label.length() == 0) 
				continue;
			//识别日期
			Pattern p=Pattern.compile(".*,.*(age.*)*");
			Matcher m=p.matcher(content);
			String date="";
			if(m.find())
				date=m.group();
			

		}
		return card;
	}
	@Override
	protected KnowledgeCard[] multiCards(Document doc) {
		return null;
	}
}

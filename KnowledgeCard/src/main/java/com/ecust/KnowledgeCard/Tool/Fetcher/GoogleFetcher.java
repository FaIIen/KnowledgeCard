package com.ecust.KnowledgeCard.Tool.Fetcher;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ecust.KnowledgeCard.Bean.Attribute;
import com.ecust.KnowledgeCard.Bean.Entity;
import com.ecust.KnowledgeCard.Bean.Entity.entitySource;
import com.ecust.KnowledgeCard.Bean.Entity.entityType;
import com.ecust.KnowledgeCard.Bean.KnowledgeCard;
import com.ecust.KnowledgeCard.Tool.Connection.Connection;

public class GoogleFetcher extends CardFetcher{
	
	public GoogleFetcher(int times){
		super(times);
		this.baseURL = "https://www.google.com/#q=";
	}

	protected KnowledgeCard cardAnalyze(Document doc)
	{
		KnowledgeCard card=new KnowledgeCard();
		try {
			//=====卡片基本属性======
			if(null == doc || null == doc.select("div.kno-ecr-pt").first()) 
				return null;
			String name = doc.select("div.kno-ecr-pt").first().ownText();
			String abstr = "";
			if(null !=doc.select("div.kno-rdesc").first()) 
				abstr = doc.select("div.kno-rdesc").first().text();
			String wiki="";
			if(null !=doc.select("div.kno-rdesc > a[href]").first()) 
				wiki = doc.select("div.kno-rdesc > a[href]").attr("href");
			card.setAbstractString(abstr);
			//=====属性对爬取======
			Elements lis = doc.select("div.kp-blk ol > li");
			for(Element li : lis)
			{
				Attribute attr=new Attribute();
				String property = li.select("span._xdb").text(); //Property
				String value = li.select("span.kno-fv").text();
				//String[] items = value.split(",");
				String link = li.select("span.kno-fv > a[href]").attr("href");
				System.out.println(value);
				System.out.println(link);
				if(property.length() == 0) continue;
			}
		} catch(NullPointerException e) {
			return null;
		}
		return card;
	}

	protected KnowledgeCard[] multiCards(Document doc) {
		if(0 == doc.select("div.kno-sh").size()) return null;
		String head = doc.select("div.kno-sh").first().text();
		if(head.equals("See results about"))
		{
			Elements divs = doc.select("div#rhs_block li.mod a");
			List<KnowledgeCard> cards = new ArrayList<KnowledgeCard>();
			for(Element div : divs)
			{
				String href = div.attr("abs:href");
				Document subDoc = Connection.connect(href);
				KnowledgeCard card = cardAnalyze(subDoc);
				if(null != card) cards.add(card);
			}
			return cards.toArray(new KnowledgeCard[0]);
		}
		return null;
	}

}

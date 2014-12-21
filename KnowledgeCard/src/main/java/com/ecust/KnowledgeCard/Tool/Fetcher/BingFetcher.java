package com.ecust.KnowledgeCard.Tool.Fetcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ecust.KnowledgeCard.Bean.Attribute;
import com.ecust.KnowledgeCard.Bean.Entity;
import com.ecust.KnowledgeCard.Bean.Entity.entitySource;
import com.ecust.KnowledgeCard.Bean.Entity.entityType;
import com.ecust.KnowledgeCard.Bean.KnowledgeCard;
import com.ecust.KnowledgeCard.Tool.Connection.Connection;
import com.sun.jmx.snmp.Timestamp;



public class BingFetcher extends CardFetcher{
	int count;
	public BingFetcher(int times){
		super(times);
		this.count=times;
		this.baseURL = "http://global.bing.com/";
	}
//	@Override
//	protected Document getHTML(String url) throws IOException{
//		Document doc=Jsoup.connect(url)
//				.userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36")
//				.cookie("_FS", "mkt=en-us&ui=#en-us")
//				.timeout(5000)
//				.get();
//		return doc;
//	}

	protected KnowledgeCard cardAnalyze(Document doc)
	{
		KnowledgeCard card=new KnowledgeCard();
		try {
			Elements subModule  = doc.select("div.b_subModule ul.b_vList");
			if(subModule.size() == 0) 
				return null;
			//cardName
			String name = doc.select("h2.b_entityTitle").first().ownText();
			card.setName(name);
			//abstract
			String abstr = "";
			if(null!=subModule.first().select("div.b_hide").first())
				abstr=subModule.first().select("div.b_hide").first().text();
			else if(null!=subModule.first().select("span").first()) 
				abstr=subModule.first().select("span").first().text();
			card.setAbstractString(abstr);
			//wiki, dbpedia link
			String wiki="";
			if(null!=subModule.select("ul.b_vList").first().select("li>a[href]")) 
				wiki=subModule.select("ul.b_vList").first().select("li>a[href]").attr("href");
			card.setWikiUri(wiki);
			card.setDbUri(wiki.replace("http://en.wikipedia.org/wiki/", "http://dbpedia.org/page/"));
			//attr
			if(subModule.size()>=2)
			{
				Elements lis = subModule.get(1).select("li");
				for(Element li : lis)
				{
					if(null == li.select("span").first()) 
						continue;
					Attribute attr=new Attribute();
					card.setAttributes(attr);
					//property
					String property = li.select("span").first().html();
					property=property.substring(0,property.length()-1);
					attr.setProperty(property);
					//value
					String allValue = li.text().split(":")[1].trim();
					allValue += li.select("div.b_hide").text();
					String[] items=allValue.split("¡¤");
					//value link
					Elements href=li.select("a[href]");
					String[] link=null;
					String[] linktxt=null;
					if(href.size()!=0){
						link=new String[href.size()];
						linktxt=new String[href.size()];
						String[] hrefhtml=href.html().split("\n");
						for(int i=0;i<href.size();i++){
							link[i]=href.get(i).attr("href").trim();
							linktxt[i]=hrefhtml[i].trim();
						}
							
					}
					for(String value:items){
						value=value.trim();
						Entity entity=new Entity();
						entity.setSource(entitySource.Bing);
						entity.setTerm(value);
						entity.setCard(null);
						if(link!=null){
							for(int i=0;i<linktxt.length;i++){
								String txt=linktxt[i];
								if(value.contains(txt) && this.iteratorTimes!=0){
									CardFetcher bingFetcher=new BingFetcher(this.iteratorTimes-1);
									List<KnowledgeCard> entitycard=bingFetcher.fetch(link[i]);
									if(entitycard!=null)
										entity.setCard(entitycard.get(0));
									break;
								}
							}
						}
						attr.setValue(entity);
					}
					
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		return card;
	}

	protected KnowledgeCard[] multiCards(Document doc) {
		Elements ss=doc.select("ol#b_context > li.b_ans h2");
		if(ss.size()==0) 
			return null;
		String head = doc.select("ol#b_context > li.b_ans h2").first().text();
		if(head.equals("See results for"))
		{
			Element ul = doc.select("ol#b_context > li.b_ans > ul.b_vlist").first();
			Elements divs = ul.select("ul.b_vlist > li");
			List<KnowledgeCard> cards = new ArrayList<KnowledgeCard>();
			for(Element div : divs)
			{
				Element a=div.select("span.b_slyGridItem > a[href]").first();
				String href = a.attributes().get("href");
				CardFetcher fetcher=new BingFetcher(count);
				KnowledgeCard card = fetcher.fetch(href).get(0);
				if(null != card) cards.add(card);
			}
			return cards.toArray(new KnowledgeCard[0]);
		}
		return null;
	}
}

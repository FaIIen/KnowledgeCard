package com.ecust.KnowledgeCard.Tool.Fetcher;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ecust.KnowledgeCard.Bean.Attribute;
import com.ecust.KnowledgeCard.Bean.Entity;
import com.ecust.KnowledgeCard.Bean.Entity.entityType;
import com.ecust.KnowledgeCard.Bean.KnowledgeCard;
import com.ecust.KnowledgeCard.Bean.Entity.entitySource;
import com.ecust.KnowledgeCard.Main.KnowledgeCardFusion;
import com.ecust.KnowledgeCard.Tool.Connection.Connection;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;


public class YahooFetcher extends CardFetcher{
	
	AbstractSequenceClassifier<CoreLabel> classifier;
	public YahooFetcher(int times,AbstractSequenceClassifier<CoreLabel> classifier){
		super(times);
		this.classifier=classifier;
		this.baseURL = "https://search.yahoo.com/";
	}

	protected KnowledgeCard cardAnalyze(Document doc)
	{
		KnowledgeCard card=new KnowledgeCard();
		
		//card name
		Elements test=doc.select("div.compImageProfile");
		String name ="";
		if(doc==null)
			return null;
		if(null != doc.select("div.info > p.txt").first())
			name = doc.select("div.info > p.txt").first().text();
		else if(null != doc.select("div.compImageProfile > p.txt").first())
			name = doc.select("div.compImageProfile> p.txt").first().text();
		else if(null != doc.select("div.cptn-ctnt > p.txt").first())
			name = doc.select("div.cptn-ctnt > p.txt").first().text();
		else 
			return null;
		card.setName(name);
		//abstract
		String abstr = "";
		Elements abstra=doc.select("div.compText").select("p:has(a[href*=r.search.yahoo.com])");
		if(abstra.size()!=0){
			for(int i=0;i<abstra.size();i++){
				if(abstr.length()<abstra.get(i).text().length())
					abstr=abstra.get(i).text();
			}
		}
		card.setAbstractString(abstr);
		//attribute
		Elements lis = doc.select("ul.compInfo > li");
		//wiki db
		Elements d=doc.select("div[class^=compText ]").select("a[href*=r.search.yahoo.com]");
		String wiki="";
		for(int i=0;i<d.size();i++){
			wiki=d.get(i).attr("href");
			if(wiki.contains("wiki"))
				break;
		}
		Pattern p=Pattern.compile("(.*)en.wikipedia.org%2fwiki%2f(.*)/RK=0(.*)");
		Matcher m=p.matcher(wiki);
		if(m.find())
			wiki="http://en.wikipedia.org/wiki/"+m.group(2);
		card.setWikiUri(wiki);
		card.setDbUri(wiki.replace("http://en.wikipedia.org/wiki/", "http://dbpedia.org/page/"));
		for(Element li : lis)
		{
			Attribute attr=new Attribute();
			card.setAttributes(attr);
			//property
			String property = li.select("label").text();
			if(property.length() == 0) 
				continue;
			attr.setProperty(property);
			//value
			String content = li.ownText();
			if(content.endsWith(","))
				content=content.substring(0,content.length()-1);
			content=content.replace(", ", "");
			//识别日期
			boolean flag=false;
			List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(content);
			for(Triple<String,Integer,Integer> t:triples){
				if(t.first().equals("DATE") && content.contains("age")){
					flag=true;
					Entity entity=new Entity();
					entity.setSource(entitySource.Yahoo);
					entity.setTerm(content);
					entity.setType(entityType.Datetime);
					attr.setValue(entity);
				}
				else if(t.first().equals("DATE")){
					flag=true;
					Entity entity=new Entity();
					entity.setSource(entitySource.Yahoo);
					entity.setTerm(content.substring(t.second(),t.third()));
					entity.setType(entityType.Datetime);
					if(content.length()!=t.third()){
						content=content.substring(t.third()+1);
						flag=false;
					}
						
					attr.setValue(entity);
				}
				else if(t.first().equals("MONEY")){
					flag=true;
					Entity entity=new Entity();
					entity.setSource(entitySource.Yahoo);
					entity.setTerm(content);
					entity.setType(entityType.numerical);
					attr.setValue(entity);
				}
			}
			//普通字符串
			if(flag==false && !content.equals("")){
				String[] items=content.split(",");
				for(String term:items){
					Entity entity=new Entity();
					entity.setSource(entitySource.Yahoo);
					entity.setTerm(term);
					entity.setType(entityType.String);
					attr.setValue(entity);
				}
			}
			//有link的实体
			Elements ahref=li.select("a[href]");
			if(ahref.size()!=0){
				
				for(int i=0;i<ahref.size();i++){
					Entity entity=new Entity();
					String txt=ahref.get(i).text();
					String link=ahref.get(i).attr("href");
					if(!link.contains("search.yahoo.com") || link.contains("r.search.yahoo.com")){
						entity.setSource(entitySource.Yahoo);
						entity.setTerm(txt);
						entity.setType(entityType.String);
						attr.setValue(entity);
						continue;
					}
						
					link=link.replace("https://search.yahoo.com/", "");
					CardFetcher cardFetcher=new YahooFetcher(this.iteratorTimes-1,classifier);
					List<KnowledgeCard> cards=cardFetcher.fetch(link);
					
					if(cards!=null){
						KnowledgeCard entityCard=cards.get(0);
						entity.setCard(entityCard);
					}
					entity.setSource(entitySource.Yahoo);
					entity.setTerm(txt);
					entity.setType(entityType.ObjectProperty);
					attr.setValue(entity);
				}
			}
			

		}
		return card;
	}
	@Override
	protected KnowledgeCard[] multiCards(Document doc) {
		return null;
	}
}

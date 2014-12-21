package com.ecust.KnowledgeCard.Tool.Fetcher;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;

import com.ecust.KnowledgeCard.Bean.KnowledgeCard;
import com.ecust.KnowledgeCard.Tool.Connection.Connection;

public abstract class CardFetcher {
	protected int iteratorTimes;
	protected String baseURL = "";
	
	public CardFetcher(int times){
		this.iteratorTimes=times;
	}
	//同一个query下有多个card的情况
	protected abstract KnowledgeCard[] multiCards(Document doc);
	//只有一个card的情况
	protected abstract KnowledgeCard cardAnalyze(Document doc);
	//抓取
	public List<KnowledgeCard> fetch(String key)
	{
		String encodedName = key;
//		try {
//			if(!key.contains("%"))
//				encodedName = java.net.URLEncoder.encode(key,"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		Document doc = Connection.connect(baseURL+encodedName);
		KnowledgeCard[] cards = multiCards(doc);
		if(null == cards) 
		{
			KnowledgeCard card = cardAnalyze(doc);
			if(null != card)
			{
				cards = new KnowledgeCard[1];
				cards[0] = card;
			}
			else cards = null;
		}
		if(cards==null)
			return null;
		else
			return Arrays.asList(cards);
	}
}

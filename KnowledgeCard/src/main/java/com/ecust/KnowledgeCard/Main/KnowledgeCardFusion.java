package com.ecust.KnowledgeCard.Main;

import com.ecust.KnowledgeCard.Bean.KnowledgeCard;
import com.ecust.KnowledgeCard.Tool.Fetcher.BingFetcher;
import com.ecust.KnowledgeCard.Tool.Fetcher.CardFetcher;
import com.ecust.KnowledgeCard.Tool.Fetcher.GoogleFetcher;

public class KnowledgeCardFusion {

	public static void main(String[] args) {
		CardFetcher bing=new BingFetcher(1);
		KnowledgeCard[] cards=bing.fetch("YaoMing");
		for(KnowledgeCard card:cards){
			System.out.println(card.toString());
		}
		

	}

}

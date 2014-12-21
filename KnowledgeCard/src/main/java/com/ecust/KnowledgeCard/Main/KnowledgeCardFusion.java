package com.ecust.KnowledgeCard.Main;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ecust.KnowledgeCard.Bean.KnowledgeCard;
import com.ecust.KnowledgeCard.Tool.Fetcher.BingFetcher;
import com.ecust.KnowledgeCard.Tool.Fetcher.CardFetcher;
import com.ecust.KnowledgeCard.Tool.Fetcher.GoogleFetcher;
import com.ecust.KnowledgeCard.Tool.Fetcher.YahooFetcher;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class KnowledgeCardFusion {

	public static void main(String[] args) throws ClassCastException, ClassNotFoundException, IOException {
		//==============fetch card=================
//		String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";
//	    AbstractSequenceClassifier<CoreLabel> classifier= CRFClassifier.getClassifier(serializedClassifier);
//	    CardFetcher bing=new BingFetcher(1);
//		List<KnowledgeCard> bingCards=bing.fetch("search?q=swift");
//		List<KnowledgeCard> allCards=new ArrayList<>();
//		allCards.addAll(bingCards);
//		for(KnowledgeCard card:bingCards){
//			String yahooUrl="search?q="+card.getName();
//			CardFetcher yahoo=new YahooFetcher(1,classifier);
//			List<KnowledgeCard> yahooCards=yahoo.fetch(yahooUrl);
//			if(yahooCards!=null)
//				allCards.addAll(yahooCards);
//		}
//		for(KnowledgeCard card:allCards){ 
//			System.out.println(card);
//			System.out.println("=====");
//		}
		CardFetcher google=new GoogleFetcher(1);
		List<KnowledgeCard> googleCards=google.fetch("inception");
		//===============merge card=================
		

	}

}

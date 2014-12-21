package com.ecust.KnowledgeCard.Bean;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	public enum entityType{
		ObjectProperty,Datetime,String,numerical;
	}
	public enum entitySource{
		Google,Bing,Yahoo;
	}
	private String term;                    //实体描述
	private entityType type;                //实体类型
	private KnowledgeCard card;       	    //若实体有链接，则可能存在链到另一card
	private List<entitySource> source;      //数据来源
	
	//======function=======
	public String toString(){
		String uri="";
		if(card!=null){
			uri=card.getWikiUri();
			return term+"("+uri+")  "+source+"\n";
		}else{
			return term+"  "+source+"\n";
		}
		
	}
	//======get/set=======
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public entityType getType() {
		return type;
	}
	public void setType(entityType type) {
		this.type = type;
	}
	public KnowledgeCard getCard() {
		return card;
	}
	public void setCard(KnowledgeCard card) {
		this.card = card;
	}
	public List<entitySource> getSource() {
		return source;
	}
	public void setSource(entitySource source) {
		if(this.source==null)
			this.source=new ArrayList<>();
		this.source.add(source);
	}
	
	
}

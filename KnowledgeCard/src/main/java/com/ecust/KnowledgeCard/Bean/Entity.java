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
	private String term;                    //ʵ������
	private entityType type;                //ʵ������
	private KnowledgeCard card;       	    //��ʵ�������ӣ�����ܴ���������һcard
	private List<entitySource> source;      //������Դ
	
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

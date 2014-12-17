package com.ecust.KnowledgeCard.Bean;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeCard {
	private String name;                 //卡片名
	private List<Attribute> attributes;  //属性值串
	private String abstractString;       //摘要
	private String wikiUri;              //wikipedia 链接
	private String dbUri;                //dbpeida链接
	
	public KnowledgeCard(){
		attributes=new ArrayList<>();
	}
	//======function======
	public String toString(){
		return name+"\n"+
				abstractString+"\n"+
				attributes.toString()+"\n"+
				wikiUri+"\n"+
				dbUri;
	}
	//======get/set=======
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAttributes(Attribute attributes) {
		this.attributes.add(attributes);
	}
	public String getAbstractString() {
		return abstractString;
	}
	public void setAbstractString(String abstractString) {
		this.abstractString = abstractString;
	}
	public String getWikiUri() {
		return wikiUri;
	}
	public void setWikiUri(String wikiUri) {
		this.wikiUri = wikiUri;
	}
	public String getDbUri() {
		return dbUri;
	}
	public void setDbUri(String dbUri) {
		this.dbUri = dbUri;
	}
	
	
	
}

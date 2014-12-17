package com.ecust.KnowledgeCard.Bean;

import java.util.ArrayList;
import java.util.List;

public class Attribute {
	private String property;          
	private List<Entity> value;
	
	public Attribute(){
		value=new ArrayList<>();
	}
	//=======funtion=======
	public String toString(){
		return property+" : "+value.toString();
	}
	//======get/set=======
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public List<Entity> getValue() {
		return value;
	}
	public void setValue(Entity value) {
		this.value.add(value);
	}
}

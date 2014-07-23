package com.kwordz.text.formatter;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.kwordz.model.lyrics.Component;
import com.kwordz.model.lyrics.Line;
import com.kwordz.text.formatter.AbstractFormatterLayout.Layout;
import com.kwordz.text.parser.AbstractParser;

public class ComponentFormatter extends AbstractFormatter<Component> implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean showMetadata;
	
	private LineFormatter lineFormatter = new LineFormatter();
	
	public ComponentFormatter() {
		super();
		register(lineFormatter);
	}
	
	@Override
	public String format(Component component, Object... parameters) {
		StringBuilder stringBuilder = new StringBuilder();
		if(showMetadata && !edit && component.getType()!=null)
			stringBuilder.append(component.getType()+lineBreak());
		for(Line line : component.getLines())
			stringBuilder.append(lineFormatter.format(line)+lineBreak());
				
		if(edit){
			//append meta data
			stringBuilder = new StringBuilder( 
			AbstractParser.startTag(AbstractParser.COMPONENT)+lineBreak()+
				(component.getType()==null?"":AbstractParser.tag(AbstractParser.TYPE, component.getType().name())+lineBreak())+
				(component.getRepeatTimes()==null?"":AbstractParser.tag(AbstractParser.REPEAT, component.getRepeatTimes())+lineBreak())+
				stringBuilder+
			AbstractParser.endTag(AbstractParser.COMPONENT)
			+lineBreak()+lineBreak() );
		}
		
		if(html){
			//append meta data
			String repeateTimes = component.getRepeatTimes();
			if(repeateTimes==null)
				repeateTimes = "";
			else{
				repeateTimes = StringUtils.isNumeric(repeateTimes)?repeateTimes+" times":"---";
			}
			stringBuilder.insert(0,"<div class=\"lyricsComponent lyricsComponent"+component.getType()+"\"><p class=\"lyricsComponentTitle\">"+
					(component.getType()==null?"---":component.getType())+" "+repeateTimes+"</p> ").append("</div>");
		}
				
		return stringBuilder.toString();
	}
	
	public LineFormatter getLineFormatter() {
		return lineFormatter;
	}
	
	public void setLayout(Layout layout) {
		lineFormatter.setLayout(layout);
	}
		
}

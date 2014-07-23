package com.kwordz.text.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.logging.Level;

import com.kwordz.model.lyrics.Component;
import com.kwordz.model.lyrics.Component.Type;
import com.kwordz.model.lyrics.Line;

public class ComponentParser extends AbstractParser<Component> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private LineParser lineParser = new LineParser();
	
	@Override
	public Component parse(String text) throws Exception{
		text = text.trim();
		Component component = new Component();
		//extract meta data
		do{
			Object[] result = extractTagValue(text,TYPE);
			if(result==null)
				break;
			component.setType(Type.valueOf((String) result[0]));
			text = (String) result[2];
		}while(true);
		do{
			Object[] result = extractTagValue(text,REPEAT);
			if(result==null)
				break;
			component.setRepeatTimes((String) result[0]);
			text = (String) result[2];
		}while(true);
		
		BufferedReader bufferedReader = new BufferedReader(new StringReader(text));
		try {
			for(String line = bufferedReader.readLine();line!=null;line=bufferedReader.readLine()){
				Line l = lineParser.parse(line);
				if(l!=null)
					component.getLines().add(l);
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,e.toString(),e);
		}
		return component;
	}

}

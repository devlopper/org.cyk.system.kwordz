package com.kwordz.text.formatter;

import java.io.Serializable;

import com.kwordz.model.lyrics.Component;
import com.kwordz.model.lyrics.Lyrics;
import com.kwordz.text.formatter.AbstractFormatterLayout.Layout;

public class LyricsFormatter extends AbstractFormatter<Lyrics> implements Serializable{

	private static final long serialVersionUID = 1L;
	private ComponentFormatter componentFormatter = new ComponentFormatter();

	public LyricsFormatter() {
		super();
		register(componentFormatter);
	}
	
	@Override
	public String format(Lyrics lyrics,Object...parameters) {
		StringBuilder stringBuilder = new StringBuilder();
		for(Component component : lyrics.getComponents())
			stringBuilder.append(componentFormatter.format(component));
		return stringBuilder.toString();
	}
	
	public ComponentFormatter getComponentFormatter() {
		return componentFormatter;
	}
	
	public void setLayout(Layout layout) {
		componentFormatter.setLayout(layout);
	}
	
}

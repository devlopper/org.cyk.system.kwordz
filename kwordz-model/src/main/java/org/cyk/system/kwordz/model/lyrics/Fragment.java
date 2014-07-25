package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Fragment extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;

	private String text;
	private Chord chord;
	
	//TODO All following must be moved to business 
	
	/*
	public void transpose(float interval){
		if(chord!=null)
			chord.transpose(interval);
	}
	*/
	/*
	public boolean terminatesLine(){
		for(String endLine : StringUtils.END_LINE)
			if(text.endsWith(endLine))
				return true;
		return false;
	}
	
	public String getTextAsHtml(){
		return HTMLEncoder.encode(text,true,true);
	}*/
	
	
	@Override
	public String toString() {
		if(text==null)
			return chord.toString();
		else
			if(chord==null)
				return text;
		return "["+chord+"]"+text;
	}

}

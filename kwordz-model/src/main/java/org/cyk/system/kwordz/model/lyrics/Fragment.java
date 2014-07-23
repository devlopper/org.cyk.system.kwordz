package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.music.Chord;

@Getter @Setter
public class Fragment implements Serializable{

	private static final long serialVersionUID = 1L;

	private String text;
	private Chord chord;
	
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
		
	public Fragment() {}
	
	public Fragment(String text, Chord chord) {
		super();
		this.text = text;
		this.chord = chord;
	}
	
	public Fragment(String text) {
		this(text,null);
	}
	
	public Fragment(Chord chord) {
		this(null,chord);
	}
	
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

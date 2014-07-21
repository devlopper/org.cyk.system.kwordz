package org.cyk.system.kwordz.model.music;

import org.cyk.system.root.model.EnumHelper;

public enum NoteName {
	A,B,C,D,E,F,G;
		
	public static NoteName getValueOf(String text){
		for(NoteName noteName : values())
			if(noteName.toString().equals(text))
				return noteName;
		return null;
	}
	
	@Override
	public String toString() {
		return EnumHelper.getInstance().text(this);
	}
	
}

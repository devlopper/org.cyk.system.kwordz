package org.cyk.system.kwordz.model.music;

import org.cyk.system.root.model.EnumHelper;

public enum NoteAlteration {
	NONE,
	SHARP,
	FLAT;
	
	public static NoteAlteration getValueOf(String text){
		for(NoteAlteration noteAlteration : values())
			if(noteAlteration.toString().equals(text))
				return noteAlteration;
		return null;
	}
	
	@Override
	public String toString() {
		return EnumHelper.getInstance().text(this);
	}

}

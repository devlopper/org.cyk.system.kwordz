package org.cyk.system.kwordz.model.music;

import org.cyk.system.root.model.EnumHelper;

public enum NoteName {
	A,B,C,D,E,F,G;
		
	@Override
	public String toString() {
		return EnumHelper.getInstance().text(this);
	}
	
}

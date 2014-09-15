package org.cyk.system.kwordz.model.lyrics;

import org.cyk.system.root.model.EnumHelper;

public enum ChordLocation{
	FOLLOW_FRAGMENT,
	LEFT,
	TOP,
	RIGHT
	
	;
	
	@Override
	public String toString() {
		return EnumHelper.getInstance().text(this);
	}
}
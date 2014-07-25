package org.cyk.system.kwordz.model.lyrics;

import org.cyk.system.root.model.EnumHelper;

public enum Layout {
	CHORD_TOP_LINE,
	CHORD_LEFT_TEXT,
	CHORD_RIGHT_LINE,
	CHORD_ONLY,
	TEXT_ONLY;

	@Override
	public String toString() {
		return EnumHelper.getInstance().text(this);
	}
}
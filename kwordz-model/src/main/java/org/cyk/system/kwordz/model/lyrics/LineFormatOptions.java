package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;

@Getter @Setter
public class LineFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	public enum ChordLocation{FOLLOW_FRAGMENT,LEFT,TOP,RIGHT};
	
	private FragmentFormatOptions fragmentFormatOptions = new FragmentFormatOptions();
	
	private ChordLocation chordLocation = ChordLocation.FOLLOW_FRAGMENT;
	
}

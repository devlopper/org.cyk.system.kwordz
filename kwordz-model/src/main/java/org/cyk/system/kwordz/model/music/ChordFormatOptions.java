package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;

@Getter @Setter
public class ChordFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	private NoteFormatOptions noteFormatOptions = new NoteFormatOptions();
	
	private String separatorLeftAndRightHand,separatorNoteAndStructure;
	private Boolean showNoteName = Boolean.TRUE;
	private Boolean expand=Boolean.FALSE;
	private Boolean applyFormat=Boolean.FALSE;
	
}

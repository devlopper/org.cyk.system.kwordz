package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;

@Getter @Setter
public class LyricsFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	private ChordFormatOptions chordFormatOptions = new ChordFormatOptions();
	
	private String separatorLeftAndRightHand,separatorNoteAndStructure;
	private Boolean showNoteName = Boolean.TRUE;
	private Boolean expand=Boolean.FALSE;
	private Boolean applyFormat=Boolean.FALSE;
	
	private Boolean showMetadata;
	
	private String spanChordHtmlTag="<span class=\"chordClass\" title=\"%s\">%s</span>";
	private String spanTextHtmlTag="<span class=\"lyricClass\">%s</span>";
	
}

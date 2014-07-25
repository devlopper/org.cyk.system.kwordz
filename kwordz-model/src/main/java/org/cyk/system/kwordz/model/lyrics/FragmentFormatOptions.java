package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;

@Getter @Setter
public class FragmentFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	private ChordFormatOptions chordFormatOptions = new ChordFormatOptions();
	//private Layout layout=Layout.CHORD_LEFT_TEXT;
	private Boolean chordAtLeft = Boolean.TRUE;
	private Boolean showChord=Boolean.TRUE;
	private Boolean showText=Boolean.TRUE;
	
	private String chordHtmlTag="<span class=\"chordClass\" title=\"%s\">%s</span>";
	private String textHtmlTag="<span class=\"lyricClass\">%s</span>";	
	
}

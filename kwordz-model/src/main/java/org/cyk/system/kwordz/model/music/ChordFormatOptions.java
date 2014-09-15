package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;

@Getter @Setter
public class ChordFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	public static String DEFAULT_MARKER_START = "[";
	public static String DEFAULT_MARKER_END = "]";
	public static String DEFAULT_SEPARATOR_NOTE_AND_STRUCTURE = "";
	public static String DEFAULT_SEPARATOR_NOTE_AND_NOTE = " ";
	public static String DEFAULT_SEPARATOR_LEFT_AND_RIGHT_HAND = "/";
	
	public static String DEFAULT_MAJOR_CHORD_SYMBOL = "maj";
	public static String MAJOR_CHORD_CODE = "maj";
	
	public static Boolean SHOW_MAJOR_CHORD_SYMBOL = Boolean.FALSE;
	
	public enum ChordLayout{EXPAND,CONTRACT}
	
	private NoteFormatOptions noteFormatOptions = new NoteFormatOptions();
	
	private String separatorLeftAndRightHand=DEFAULT_SEPARATOR_LEFT_AND_RIGHT_HAND,separatorNoteAndStructure=DEFAULT_SEPARATOR_NOTE_AND_STRUCTURE,
			separatorNoteAndNote=DEFAULT_SEPARATOR_NOTE_AND_NOTE;
	private Boolean showNoteName = Boolean.TRUE,showMajorChordSymbol=SHOW_MAJOR_CHORD_SYMBOL;
	private ChordLayout layout=ChordLayout.CONTRACT;
	
	private Boolean showMarker;
	private String markerStart = DEFAULT_MARKER_START;
	private String markerEnd = DEFAULT_MARKER_END;
	private String markerStartReplacement = "{";
	private String markerEndReplacement = "}";
	
	/**/
	
	
}

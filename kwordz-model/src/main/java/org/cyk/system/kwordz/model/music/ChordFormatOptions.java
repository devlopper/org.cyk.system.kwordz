package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;

@Getter @Setter
public class ChordFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	public enum ChordLayout{EXPAND,CONTRACT}
	
	private NoteFormatOptions noteFormatOptions = new NoteFormatOptions();
	
	private String separatorLeftAndRightHand="/",separatorNoteAndStructure=" ",separatorNoteAndNote=" ";
	private Boolean showNoteName = Boolean.TRUE;
	private ChordLayout layout=ChordLayout.CONTRACT;
	
	private Boolean showMarker;
	private String markerStart = "[";
	private String markerEnd = "]";
	private String markerStartReplacement = "{";
	private String markerEndReplacement = "}";
	
	/**/
	
	
}

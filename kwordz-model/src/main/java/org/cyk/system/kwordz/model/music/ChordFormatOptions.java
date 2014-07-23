package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChordFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	public static final String NOTE_BASS_SEPARATOR = "/";
	
	private Boolean seperateNoteBass = Boolean.TRUE;
	
}

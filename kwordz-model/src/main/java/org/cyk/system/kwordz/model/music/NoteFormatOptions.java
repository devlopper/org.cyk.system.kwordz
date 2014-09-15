package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;

@Getter @Setter
public class NoteFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	public static String DEFAULT_SEPERATOR_NAME_ALTERATION = "";
	
	private String seperatorNameAndAlteration = DEFAULT_SEPERATOR_NAME_ALTERATION;
	
}

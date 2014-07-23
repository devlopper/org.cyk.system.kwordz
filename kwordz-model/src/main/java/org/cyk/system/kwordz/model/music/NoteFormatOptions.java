package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoteFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	public static final String NAME_ALTERATION_SEPARATOR = " ";
	
	private Boolean seperateNameAlterartion = Boolean.TRUE;
	
}

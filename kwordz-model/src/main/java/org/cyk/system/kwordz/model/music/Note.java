package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @EqualsAndHashCode(of={"name","alteration"},callSuper=false) @NoArgsConstructor
public class Note extends AbstractIdentifiable implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME_ALTERATION_SEPARATOR = " ";
	
	private NoteName name;
	private NoteAlteration alteration;
	
	public Note(NoteName name, NoteAlteration alteration) {
		super();
		this.name = name;
		this.alteration = alteration;
	}
	
	public Note(NoteName name) {
		this(name,NoteAlteration.NONE);
	}
	
	public Note(Note note){
		this(note.name,note.alteration);
	}
		
	@Override
	public String toString() {
		if(name==null)
			return "";
		if(alteration!=null && !alteration.toString().isEmpty())
			return name+NAME_ALTERATION_SEPARATOR+alteration;
		return name.toString();
	}
}

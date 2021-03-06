package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity @NoArgsConstructor
public class Note extends AbstractIdentifiable implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Enumerated
	private NoteName name;
	
	@Enumerated
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
			return name+" "+alteration;
		return name.toString();
	}
}

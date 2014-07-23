package org.cyk.system.kwordz.model.music;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter  @NoArgsConstructor 
public abstract class AbstractNoteCollection<STRUCTURE extends Structure> extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5823773737274637389L;
	
	protected STRUCTURE structure;
	
	protected transient List<Note> notes;

	public AbstractNoteCollection(STRUCTURE structure) {
		super();
		this.structure = structure;
	}
	
	
	
}

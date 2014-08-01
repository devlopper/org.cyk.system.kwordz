package org.cyk.system.kwordz.model.music;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @MappedSuperclass  @NoArgsConstructor 
public abstract class AbstractNoteCollection<STRUCTURE extends Structure> extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5823773737274637389L;
	
	@ManyToOne
	protected STRUCTURE structure;
	
	@Transient
	protected transient List<Note> notes;

	public AbstractNoteCollection(STRUCTURE structure) {
		super();
		this.structure = structure;
	}
	
	
	
}

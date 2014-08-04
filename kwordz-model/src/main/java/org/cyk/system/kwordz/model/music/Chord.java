package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class Chord extends AbstractNoteCollection<ChordStructure> implements Serializable{

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade=CascadeType.ALL) private Note bass;
	
	@OneToOne(cascade=CascadeType.ALL) private Note root;
	
	private Byte inversionOrder;
	
	public Chord(ChordStructure structure,Note bass) {
		super(structure);
		this.bass = bass;
		inversionOrder = 0;
	}

}

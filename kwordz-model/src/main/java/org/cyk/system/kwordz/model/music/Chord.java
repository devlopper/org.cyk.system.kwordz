package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Chord extends AbstractNoteCollection<ChordStructure> implements Serializable{

	private static final long serialVersionUID = 1L;

	private Note bass;
	private int inversionOrder;
	
	public Chord(ChordStructure structure,Note bass) {
		super(structure);
		this.bass = bass;
		inversionOrder = 0;
	}

}

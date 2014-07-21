package org.cyk.system.kwordz.model.music;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class Chord implements Serializable{

	private static final long serialVersionUID = 1L;

	private String structureId;
	private Note root;
	private Note bass;
	private int inversionOrder;
	/* computed from structure id */
	private transient ChordStructure structure;
	private transient List<Note> notes;
		
	public void transpose(float interval) {
		//root.transpose(interval);
		//bass.transpose(interval);
		notes = null;
	}
	
	public boolean isBassNoteEqualToRootChord(){
		return root.toString().equals(bass.toString());
	}
		
	public Chord() {}
	
	public Chord(ChordStructure chordStructure, Note base,Note bass) {
		this.structureId = chordStructure.getId();
		root = base;
		inversionOrder = 0;
		this.bass = bass;
	}

	public ChordStructure getStructure() {
		if(structure==null)
			structure = ChordStructure.getById(structureId);
		return structure;
	}
	public void setStructure(ChordStructure structure) {
		this.structure = structure;
		if(structure!=null)
			structureId = structure.getId();
	}
	
	public List<Note> getNotes() {
		if(notes==null)
			notes = getStructure().generateSequence(root);
		return notes;
	}
	
	@Override
	public String toString() {
		return root+" "+getStructure().getSymbol();	
	}

}

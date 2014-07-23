package org.cyk.system.kwordz.business.api.music;

import org.cyk.system.kwordz.model.music.AbstractNoteCollection;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.root.business.api.TypedBusiness;

public interface AbstractNoteCollectionBusiness<STRUCTURE extends Structure,COLLECTION extends AbstractNoteCollection<STRUCTURE>> extends TypedBusiness<COLLECTION> {
    
	void transpose(COLLECTION aCollection,Integer distance);
	
	void generateNotes(COLLECTION aCollection,STRUCTURE aStructure,Note base);
	
	Note findRoot(COLLECTION aCollection);
	
	
	
}

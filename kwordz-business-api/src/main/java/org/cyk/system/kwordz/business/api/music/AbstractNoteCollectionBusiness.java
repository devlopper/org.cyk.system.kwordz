package org.cyk.system.kwordz.business.api.music;

import org.cyk.system.kwordz.business.api.AbstractMusicBusiness;
import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.kwordz.model.music.AbstractNoteCollection;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.Structure;

public interface AbstractNoteCollectionBusiness<STRUCTURE extends Structure,COLLECTION extends AbstractNoteCollection<STRUCTURE>,OPTIONS extends AbstractFormatOptions> extends AbstractMusicBusiness<COLLECTION,OPTIONS> {
    
	void generateNotes(COLLECTION aCollection,STRUCTURE aStructure,Note base);
	
	Note findRoot(COLLECTION aCollection);
	
	
	
}

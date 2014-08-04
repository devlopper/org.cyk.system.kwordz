package org.cyk.system.kwordz.business.api.music;

import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.ChordStructure;

public interface ChordBusiness extends AbstractNoteCollectionBusiness<ChordStructure,Chord,ChordFormatOptions> {
    
	void generateNotes(Chord chord);
	
	Boolean equals(Chord chord1,Chord chord2,Boolean strict);
}

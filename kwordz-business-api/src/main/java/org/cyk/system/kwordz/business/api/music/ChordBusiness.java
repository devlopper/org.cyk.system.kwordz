package org.cyk.system.kwordz.business.api.music;

import java.util.Locale;

import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.ChordStructure;

public interface ChordBusiness extends AbstractNoteCollectionBusiness<ChordStructure,Chord> {
    
	Boolean bassEqualsToRoot(Chord chord);
	
	String format(Locale locale,Chord chord,ChordFormatOptions options);
	
	Chord parse(Locale locale,String text);
	
	Boolean equals(Chord chord1,Chord chord2,Boolean strict);
}

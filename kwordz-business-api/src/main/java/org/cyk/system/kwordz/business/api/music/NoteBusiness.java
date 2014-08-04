package org.cyk.system.kwordz.business.api.music;

import java.util.Collection;
import java.util.Locale;

import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteFormatOptions;
import org.cyk.system.root.business.api.TypedBusiness;

public interface NoteBusiness extends TypedBusiness<Note> {
    
	void useAlterartion(Note aNote,NoteAlteration anAlteration);
	
	void normalize(Note aNote,NoteAlteration preferredAlteration);
	void normalize(Collection<Note> aCollection,NoteAlteration preferredAlteration);
	
	void transpose(Note aNote,Integer distance);
	
	Integer distance(Note aNote1,Note aNote2);
	
	Boolean equals(Note aNote1,Note aNote2,Boolean strict);
	
	String degree(Integer distance,NoteAlteration preferredAlteration);
	
	String format(Locale locale,Note note,NoteFormatOptions options);
	
	String format(Locale locale,Note note);
	
	Note parse(Locale locale,String text);
	
	Collection<Note> findTones();
}

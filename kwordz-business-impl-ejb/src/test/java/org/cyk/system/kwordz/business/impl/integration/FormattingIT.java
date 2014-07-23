package org.cyk.system.kwordz.business.impl.integration;

import static org.cyk.system.kwordz.model.music.NoteAlteration.FLAT;
import static org.cyk.system.kwordz.model.music.NoteAlteration.NONE;
import static org.cyk.system.kwordz.model.music.NoteAlteration.SHARP;
import static org.cyk.system.kwordz.model.music.NoteName.A;
import static org.cyk.system.kwordz.model.music.NoteName.B;
import static org.cyk.system.kwordz.model.music.NoteName.C;
import static org.cyk.system.kwordz.model.music.NoteName.D;
import static org.cyk.system.kwordz.model.music.NoteName.E;
import static org.cyk.system.kwordz.model.music.NoteName.F;
import static org.cyk.system.kwordz.model.music.NoteName.G;

import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteFormatOptions;
import org.cyk.system.kwordz.model.music.NoteName;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class FormattingIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private NoteBusiness noteBusiness;
    @Inject private ChordStructureBusiness chordStructureBusiness;
    @Inject private ScaleStructureBusiness scaleStructureBusiness;
    @Inject private ChordBusiness chordBusiness;
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Override
    protected void populate() {
    	kwordzBusinessLayer.createChordStructures(chordStructureBusiness);
    	kwordzBusinessLayer.createScaleStructures(scaleStructureBusiness);
    }
    
    @Override
    protected void finds() {}

    @Override
    protected void businesses() {}

    @Override
    protected void create() {}

    @Override
    protected void delete() {}

    @Override
    protected void read() {}

    @Override
    protected void update() {}

    /**/

    @Test
	public void formatNote(){	
    	assertEqualsNoteAllLocale(C, NONE,"C","do");
    	assertEqualsNoteAllLocale(C, SHARP,"C #","do #");
    	assertEqualsNoteAllLocale(D, FLAT,"D b","re b");
    	assertEqualsNoteAllLocale(D, NONE,"D","re");
    	assertEqualsNoteAllLocale(D, SHARP,"D #","re #");
    	assertEqualsNoteAllLocale(E, NONE,"E","mi");
    	assertEqualsNoteAllLocale(F, NONE,"F","fa");
    	assertEqualsNoteAllLocale(G, NONE,"G","sol");
    	assertEqualsNoteAllLocale(A, NONE,"A","la");
    	assertEqualsNoteAllLocale(B, NONE,"B","si");
    	
    	assertEqualsNote("C #",Locale.ENGLISH,C, SHARP);
    	assertEqualsNote("do #",Locale.FRENCH,C, SHARP);
    	
    	NoteFormatOptions options = new NoteFormatOptions();
    	options.setSeperateNameAlterartion(Boolean.FALSE);
    	
    	assertEqualsNote("C#",Locale.ENGLISH,C, SHARP,options);
    	assertEqualsNote("do#",Locale.FRENCH,C, SHARP,options);
	}
      
    @Test
	public void formatChord(){	
    	assertEqualsChordAllLocale("maj", C, NONE, "C maj","do maj");
    }
    
    /**/
   
    private void assertEqualsNote(String expected,Locale locale,NoteName noteName,NoteAlteration noteAlteration,NoteFormatOptions options){
    	assertEquals(expected,noteBusiness.format(locale, new Note(noteName, noteAlteration),options));
    }
    private void assertEqualsNote(String expected,Locale locale,NoteName noteName,NoteAlteration noteAlteration){
    	assertEqualsNote(expected, locale, noteName, noteAlteration, kwordzBusinessLayer.getDefaultNoteFormatOptions());
    }
    private void assertEqualsNoteAllLocale(NoteName noteName,NoteAlteration noteAlteration,String...expected){
    	assertEqualsNote(expected[0], Locale.ENGLISH, noteName, noteAlteration);
    	assertEqualsNote(expected[1], Locale.FRENCH, noteName, noteAlteration);
    }
    
    private void assertEqualsChord(String expected,Locale locale,String structureCode,NoteName noteName,NoteAlteration noteAlteration,ChordFormatOptions options){
    	Chord chord = new Chord();
    	chordBusiness.generateNotes(chord, chordStructureBusiness.find(structureCode), new Note(noteName,noteAlteration));
    	assertEquals(expected,chordBusiness.format(locale, chord,options));
    }
    private void assertEqualsChord(String expected,Locale locale,String structureCode,NoteName noteName,NoteAlteration noteAlteration){
    	assertEqualsChord(expected, locale,structureCode, noteName, noteAlteration, kwordzBusinessLayer.getDefaultChordFormatOptions());
    }
    private void assertEqualsChordAllLocale(String structureCode,NoteName noteName,NoteAlteration noteAlteration,String...expected){
    	assertEqualsChord(expected[0], Locale.ENGLISH,structureCode, noteName, noteAlteration);
    	assertEqualsChord(expected[1], Locale.FRENCH,structureCode, noteName, noteAlteration);
    }
    
}

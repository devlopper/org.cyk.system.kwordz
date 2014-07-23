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
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.root.business.api.BusinessException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class ParsingIT extends AbstractBusinessIT {

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
    	kwordzBusinessLayer.buildPatterns();
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

    /* Note */
    
    @Test
	public void parsingNote(){	
    	assertParsingNote(C, NONE, Locale.ENGLISH, "C");
    	assertParsingNote(C, SHARP, Locale.ENGLISH, "C#");
    	assertParsingNote(C, FLAT, Locale.ENGLISH, "Cb");
    	
    	assertParsingNote(C, NONE, Locale.FRENCH, "do");
    	assertParsingNote(C, SHARP, Locale.FRENCH, "do#");
    	assertParsingNote(C, FLAT, Locale.FRENCH, "dob");
	}
    
    @Test(expected=BusinessException.class)
	public void parsingNoteUnsupportedLocale(){
    	assertEqualsNoteString(new Note(C,FLAT),noteBusiness.parse(Locale.CHINA, "Cbk"));
    }
    
    @Test(expected=BusinessException.class)
	public void parsingNoteNotFound(){
    	assertEqualsNoteString(new Note(C,FLAT),noteBusiness.parse(Locale.ENGLISH, ""));
    }
    
    /*
    @Test(expected=BusinessException.class)
	public void parsingNoteFormatLength(){
    	assertEqualsNoteString(new Note(C,FLAT),noteBusiness.parse(Locale.ENGLISH, "Cbk"));
    }*/
    
    @Test(expected=BusinessException.class)
	public void parsingNoteNameUnknown(){
    	assertEqualsNoteString(new Note(C,FLAT),noteBusiness.parse(Locale.ENGLISH, "Z"));
    }
    
    @Test(expected=BusinessException.class)
	public void parsingNoteAlterationUnknown(){
    	assertEqualsNoteString(new Note(C,FLAT),noteBusiness.parse(Locale.ENGLISH, "Cl"));
    }
    
    /* Chord */
    
    @Test
	public void parsingChord(){	
    	assertParsingChord("maj", C, NONE, Locale.ENGLISH, "C");
    	assertParsingChord("maj", C, SHARP, Locale.ENGLISH, "C#");
    	assertParsingChord("min", C, NONE, Locale.ENGLISH, "Cm");
    	assertParsingChord("maj7", C, NONE, Locale.ENGLISH, "Cmaj7");
    	
    	assertParsingChord("maj", D, FLAT, Locale.ENGLISH, "Db");
    	assertParsingChord("maj", D, NONE, Locale.ENGLISH, "D");
    	assertParsingChord("majDom7", D, NONE, Locale.ENGLISH, "D7");
    	assertParsingChord("majDom9", D, NONE, Locale.ENGLISH, "D9");
    	assertParsingChord("majDom11", D, NONE, Locale.ENGLISH, "D11");
    	assertParsingChord("majDom13", D, NONE, Locale.ENGLISH, "D13");
    	
    	
    	assertParsingChord("maj", E, NONE, Locale.ENGLISH, "E");
    	assertParsingChord("maj", E, NONE, Locale.ENGLISH, "Emaj");
    	assertParsingChord("maj", E, FLAT, Locale.ENGLISH, "Eb");
    	
    	assertParsingChord("maj", F, NONE, Locale.ENGLISH, "F");
    	assertParsingChord("maj", F, SHARP, Locale.ENGLISH, "F#");
    	
    	assertParsingChord("maj", G, NONE, Locale.ENGLISH, "G");
    	assertParsingChord("maj", A, NONE, Locale.ENGLISH, "A");
    	assertParsingChord("maj", B, NONE, Locale.ENGLISH, "B");
    }
    
    /**/
   
    protected void assertParsingNote(NoteName name,NoteAlteration alteration,Locale locale,String text){
    	assertEqualsNoteString(new Note(name,alteration),noteBusiness.parse(locale, text));
    }
    
    protected void assertParsingChord(String structureCode,NoteName name,NoteAlteration alteration,Locale locale,String text){
    	Chord chord = new Chord();
    	chordBusiness.generateNotes(chord, chordStructureBusiness.find(structureCode), new Note(name,alteration));
    	assertEquals(chord,chordBusiness.parse(locale, text));
    }
    
}

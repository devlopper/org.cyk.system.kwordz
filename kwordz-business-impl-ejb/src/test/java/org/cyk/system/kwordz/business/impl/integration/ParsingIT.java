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
    @Inject private ChordBusiness chordBusiness;
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Override
    protected void populate() {
    	kwordzBusinessLayer.createInitialData();
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
    	assertParsingNote(C, SHARP, Locale.ENGLISH, "c#");
    	assertParsingNote(C, FLAT, Locale.ENGLISH, "Cb");
    	assertParsingNote(C, SHARP, Locale.ENGLISH, "c #");
    	
    	assertParsingNote(C, NONE, Locale.FRENCH, "do");
    	assertParsingNote(C, SHARP, Locale.FRENCH, "do#");
    	assertParsingNote(C, FLAT, Locale.FRENCH, "dob");
    	
    	assertParsingNote(G, NONE, Locale.FRENCH, "sol");
    	assertParsingNote(G, NONE, Locale.FRENCH, "soL");
    	assertParsingNote(G, NONE, Locale.FRENCH, "sOl");
    	assertParsingNote(G, NONE, Locale.FRENCH, "sOL");
    	assertParsingNote(G, NONE, Locale.FRENCH, "Sol");
    	assertParsingNote(G, NONE, Locale.FRENCH, "SoL");
    	assertParsingNote(G, NONE, Locale.FRENCH, "SOl");
    	assertParsingNote(G, NONE, Locale.FRENCH, "SOL");
	}
    
    @Test(expected=BusinessException.class)
	public void parsingNoteUnsupportedLocale(){
    	assertEqualsNote(new Note(C,FLAT),noteBusiness.parse(Locale.CHINA, "Cbk"));
    }
    
    @Test(expected=BusinessException.class)
	public void parsingNoteNotFound(){
    	assertEqualsNote(new Note(C,FLAT),noteBusiness.parse(Locale.ENGLISH, ""));
    }
    
    @Test(expected=BusinessException.class)
	public void parsingNoteNameUnknown(){
    	assertEqualsNote(new Note(C,FLAT),noteBusiness.parse(Locale.ENGLISH, "Z"));
    }
    
    @Test(expected=BusinessException.class)
	public void parsingNoteAlterationUnknown(){
    	assertEqualsNote(new Note(C,FLAT),noteBusiness.parse(Locale.ENGLISH, "Cl"));
    }
    
    @Test(expected=BusinessException.class)
	public void parsingNoteUnsupportedCaseEnglishFlat(){
    	assertEqualsNote(new Note(B,FLAT),noteBusiness.parse(Locale.ENGLISH, "bB"));
    }
    
    /* Chord */
    
    @Test
	public void parsingChord(){	
    	assertParsingChord("maj", C, NONE, Locale.ENGLISH, "c");
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
    	
    	assertParsingChord("maj", F, NONE,G,NONE, Locale.ENGLISH, "G/F");
    	assertParsingChord("min", F, SHARP,B,FLAT, Locale.ENGLISH, "Bb/F#m");
    	
    	assertParsingChord("maj", G, NONE, Locale.ENGLISH, "G/G");
    	assertParsingChord("maj", G, SHARP,G,NONE, Locale.ENGLISH, "G/G#");
    	assertParsingChord("maj", G, NONE,G,SHARP, Locale.ENGLISH, "G#/G");
    	assertParsingChord("maj", G, SHARP, Locale.ENGLISH, "G#/G#");
    	assertParsingChord("maj", A, NONE, Locale.ENGLISH, "A");
    	assertParsingChord("maj", B, NONE, Locale.ENGLISH, "B");
    	
    	/***/
    	
    	assertParsingChord("maj", C, NONE, Locale.FRENCH, "do");
    	assertParsingChord("maj", C, SHARP, Locale.FRENCH, "Do#");
    	assertParsingChord("min7", C, NONE, Locale.FRENCH, "dOm7");
    	assertParsingChord("maj7", C, NONE, Locale.FRENCH, "domaj7");
    }
    
    @Test(expected=BusinessException.class)
	public void parsingChordUnsupportedLocale(){
    	assertParsingChord("maj", B, NONE, Locale.CHINESE, "B");
    }
    
    @Test(expected=BusinessException.class)
	public void parsingChordNotFound(){
    	assertParsingChord("maj", B, NONE, Locale.ENGLISH, "");
    }
    
    @Test(expected=BusinessException.class)
	public void parsingChordNoteNameUnknown(){
    	assertParsingChord("maj", B, NONE, Locale.ENGLISH, "V");
    }
    
    @Test(expected=BusinessException.class)
	public void parsingChordNoteAlterationUnknown(){
    	assertParsingChord("maj", B, NONE, Locale.ENGLISH, "Bx");
    }
    
    @Test(expected=BusinessException.class)
	public void parsingChordStrucutreUnknown(){
    	assertParsingChord("maj", B, NONE, Locale.ENGLISH, "Bp");
    }
    
    /**/
   
    private void assertParsingNote(NoteName name,NoteAlteration alteration,Locale locale,String text){
    	assertEqualsNote(new Note(name,alteration),noteBusiness.parse(locale, text));
    }
    
    private void assertParsingChord(String structureCode,NoteName rootName,NoteAlteration rootAlteration,NoteName bassName,NoteAlteration bassAlteration,Locale locale,String text){
    	Chord chord = new Chord();
    	chord.setStructure(chordStructureBusiness.find(structureCode));
    	chord.setRoot(new Note(rootName,rootAlteration));
    	chordBusiness.generateNotes(chord);
    	if(bassName!=null)
    		chord.setBass(new Note(bassName, bassAlteration));
    	assertEqualsChord(chord,chordBusiness.parse(locale, text));
    }
    
    private void assertParsingChord(String structureCode,NoteName rootName,NoteAlteration rootAlteration,Locale locale,String text){
    	assertParsingChord(structureCode, rootName, rootAlteration,null,null, locale, text);
    }
    
}

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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.business.api.music.StructureBusiness;
import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class MusicIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private NoteBusiness noteBusiness;
    @Inject private ChordStructureBusiness chordStructureBusiness;
    @Inject private ScaleStructureBusiness scaleStructureBusiness;
    @Inject private StructureBusiness structureBusiness;
    @Inject private ChordBusiness chordBusiness;
    @Inject private LanguageBusiness languageBusiness;
    
    private Structure structure; 
    
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
    public void generateSequence(){
    	//chords
    	structure = chordStructureBusiness.find("maj");
    	assertEqualsSequence(structure, new Note(C,NONE),Arrays.asList(new Note(C),new Note(E),new Note(G)));
    	assertEqualsSequence(structure, new Note(C,SHARP),
    			Arrays.asList(new Note(C,SHARP),new Note(F),new Note(G,SHARP)));
    	assertEqualsSequence(structure, new Note(D,NONE),Arrays.asList(new Note(D),new Note(F,SHARP),new Note(A)));
    	
    	//scales
    	structure = scaleStructureBusiness.find("maj");
    	debug(structure);
    	assertEqualsSequence(structure, new Note(C,NONE),Arrays.asList(new Note(C),new Note(D),new Note(E),new Note(F),new Note(G),new Note(A),new Note(B)));
    	assertEqualsSequence(structure, new Note(C,SHARP),
    			Arrays.asList(new Note(C,SHARP),new Note(D,SHARP),new Note(F),new Note(F,SHARP),new Note(G,SHARP),new Note(A,SHARP),new Note(C)));
    	assertEqualsSequence(structure, new Note(D,NONE),Arrays.asList(new Note(D),new Note(E),new Note(F,SHARP),new Note(G),new Note(A),new Note(B),new Note(C,SHARP)));
    	assertEqualsSequence(structure, new Note(B,FLAT),Arrays.asList(new Note(B,FLAT),new Note(C),new Note(D),new Note(E,FLAT),new Note(F),new Note(G),new Note(A)));
    	
    }
    
    @Test
    public void transposeNoteCollection(){
    	Chord chord = new Chord();
    	chord.setBass(new Note(C, NONE));
    	chordBusiness.generateNotes(chord, chordStructureBusiness.find("maj"), chord.getBass());
    	chordBusiness.transpose(chord, 2);
    	assertEqualsSequence(chord.getNotes(),Arrays.asList(new Note(D),new Note(F,SHARP),new Note(A)));
    }
    
    @Test
    public void noteEquals(){
    	assertEquals(Boolean.TRUE, noteBusiness.equals(new Note(C, SHARP), new Note(D, FLAT), Boolean.FALSE));
    	assertEquals(Boolean.TRUE, noteBusiness.equals(new Note(C, SHARP), new Note(C, SHARP), Boolean.TRUE));
    }
    
    //@Test
    public void bassEqualsToRoot(){
    	
    }
    
    //@Test
    public void i18n(){
    	assertEquals("maj", "Majeur");
    }
     
    /**/
    
    protected void assertEqualsSequence(Structure structure,Note base,List<Note> notes){
    	assertEqualsSequence(structureBusiness.generateSequence(structure, base), notes);
    }
    
    protected void assertEqualsSequence(List<Note> actual,List<Note> expected){
    	assertEquals(Boolean.TRUE, actual!=null);
    	assertEquals(expected.size(), actual.size());
    	//System.out.println(actual+" *--- "+expected);
    	for(int i=0;i<expected.size();i++)
    		assertEquals(Boolean.TRUE,noteBusiness.equals(expected.get(i), actual.get(i),Boolean.TRUE));
    }
    
    protected void assertI18nStructure(String structureId,String name){
    	Structure structure = structureBusiness.find(structureId);
    	assertEquals(Boolean.TRUE, structure!=null);
    	System.out.println(languageBusiness.findText(structure.getName()));
    	assertEquals(name,languageBusiness.findText(structure.getName()));
    }
  
    protected void assertParsingNote(NoteName name,NoteAlteration alteration,Locale locale,String text){
    	assertEquals(new Note(name,alteration).toString(),noteBusiness.parse(locale, text).toString());
    }
    
}

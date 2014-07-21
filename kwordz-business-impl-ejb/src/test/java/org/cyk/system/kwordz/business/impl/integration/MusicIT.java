package org.cyk.system.kwordz.business.impl.integration;

import java.util.Arrays;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.root.model.EnumHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import static org.cyk.system.kwordz.model.music.NoteName.*;
import static org.cyk.system.kwordz.model.music.NoteAlteration.*;

public class MusicIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private NoteBusiness noteBusiness;
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Override
    protected void populate() {}
    
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
	public void noteFrench(){
		EnumHelper.getInstance().setDefaultLocale(Locale.FRENCH);
	
		assertEqualsNote(NoteName.C, "si", "do", toString("do","#"));
		assertEqualsNote(NoteName.D, toString("re","b"), "re", toString("re","#"));
		assertEqualsNote(NoteName.E, toString("mi","b"), "mi", "fa");
		assertEqualsNote(NoteName.F, "mi", "fa", toString("fa","#"));
		assertEqualsNote(NoteName.G, toString("sol","b"), "sol", toString("sol","#"));
		assertEqualsNote(NoteName.A, toString("la","b"), "la", toString("la","#"));
		assertEqualsNote(NoteName.B, toString("si","b"), "si", "do");
	}
	
	@Test
	public void noteEnglish(){
		EnumHelper.getInstance().setDefaultLocale(Locale.ENGLISH);
		assertEqualsNote(NoteName.C, "B", "C", toString("C","#"));
		assertEqualsNote(NoteName.D, toString("D","b"), "D", toString("D","#"));
		assertEqualsNote(NoteName.E, toString("E","b"), "E", "F");
		assertEqualsNote(NoteName.F, "E", "F", toString("F","#"));
		assertEqualsNote(NoteName.G, toString("G","b"), "G", toString("G","#"));
		assertEqualsNote(NoteName.A, toString("A","b"), "A", toString("A","#"));
		assertEqualsNote(NoteName.B, toString("B","b"), "B", "C");
	}
	
	@Test
	public void useAlterartion(){
		assertUseAlteration(new Note(C,NONE), SHARP, new Note(C,NONE));
		assertUseAlteration(new Note(D,FLAT), SHARP, new Note(C,SHARP));
		assertUseAlteration(new Note(C,SHARP), FLAT, new Note(D,FLAT));
	}
	
	@Test
	public void transpose(){
		Note note = new Note(C);
        //Up
		assertTranspose(note,1,new Note(C,SHARP));
        assertTranspose(note,1,new Note(D,NONE));
        assertTranspose(note,1,new Note(D,SHARP));
        assertTranspose(note,1,new Note(E,NONE));
        assertTranspose(note,1,new Note(F,NONE));
        assertTranspose(note,1,new Note(F,SHARP));
        assertTranspose(note,1,new Note(G,NONE));
        assertTranspose(note,1,new Note(G,SHARP));
        assertTranspose(note,1,new Note(A,NONE));
        assertTranspose(note,1,new Note(A,SHARP));
        assertTranspose(note,1,new Note(B,NONE));
        assertTranspose(note,1,new Note(C,NONE));
        //Down
        note = new Note(C);
		assertTranspose(note,-1,new Note(B,NONE));
        assertTranspose(note,-1,new Note(B,FLAT));
        assertTranspose(note,-1,new Note(A,NONE));
        assertTranspose(note,-1,new Note(A,FLAT));
        assertTranspose(note,-1,new Note(G,NONE));
        assertTranspose(note,-1,new Note(G,FLAT));
        assertTranspose(note,-1,new Note(F,NONE));
        assertTranspose(note,-1,new Note(E,NONE));
        assertTranspose(note,-1,new Note(E,FLAT));
        assertTranspose(note,-1,new Note(D,NONE));
        assertTranspose(note,-1,new Note(D,FLAT));
        assertTranspose(note,-1,new Note(C,NONE));
        
		assertTranspose(new Note(D,FLAT),2,new Note(E,FLAT));
	}
	
	@Test
	public void distance(){
		
		assertDistance(new Note(C, NONE), new Note(C, SHARP), 1);
		
		assertDistance(new Note(C, NONE), new Note(D, NONE), 2);
		assertDistance(new Note(C, NONE), new Note(D, SHARP), 3);
		
		assertDistance(new Note(C, NONE), new Note(E, FLAT), 3);
		assertDistance(new Note(C, NONE), new Note(E, NONE), 4);
		assertDistance(new Note(C, NONE), new Note(F, NONE), 5);
		assertDistance(new Note(C, NONE), new Note(F, SHARP), 6);
		assertDistance(new Note(C, NONE), new Note(G, FLAT), 6);
		assertDistance(new Note(C, NONE), new Note(G, NONE), 7);
		assertDistance(new Note(C, NONE), new Note(G, SHARP), 8);
		assertDistance(new Note(C, NONE), new Note(A, FLAT), 8);
		assertDistance(new Note(C, NONE), new Note(A, NONE), 9);
		assertDistance(new Note(C, NONE), new Note(A, SHARP), 10);
		assertDistance(new Note(C, NONE), new Note(B, FLAT), 10);
		assertDistance(new Note(C, NONE), new Note(B, NONE), 11);
		
		
	}
	
	/**/
	
	protected void assertEqualsNoteName(String expected,NoteName actual){
		assertEquals(expected,actual.toString());
	}
	
	protected void assertEqualsNote(String expected,Note actual){
		assertEquals(expected,actual.toString());
	}
	
	protected void assertEqualsNote(Note note1,Note note2){
		assertEquals(note1.toString(), note2.toString());
	}
	
	protected void assertEqualsNote(NoteName noteName,String flat,String none,String sharp){
		Note flatNote = new Note(noteName,FLAT),noneNote = new Note(noteName,NONE),
				sharpNote = new Note(noteName,SHARP);
		noteBusiness.normalize(Arrays.asList(flatNote,noneNote,sharpNote),null);
		assertEqualsNote(flat,flatNote);
		assertEqualsNote(none,noneNote);
		assertEqualsNote(sharp,sharpNote);
	}
	
	protected void assertTranspose(Note note,Integer distance ,Note result){
		noteBusiness.transpose(note, distance);
		assertEqualsNote(result, note);
	}
	
	protected void assertUseAlteration(Note note,NoteAlteration alteration,Note result){
		noteBusiness.useAlterartion(note, alteration);
		assertEqualsNote(result, note);
	}
	
	protected void assertDistance(Note note1,Note note2,Integer result){
		assertEquals(result,noteBusiness.distance(note1, note2));
	}
	
	protected String toString(String name,String alteration){
		return name+(StringUtils.isEmpty(alteration)?"":Note.NAME_ALTERATION_SEPARATOR+alteration);
	}
	protected String toString(String name){
		return toString(name);
	}
}

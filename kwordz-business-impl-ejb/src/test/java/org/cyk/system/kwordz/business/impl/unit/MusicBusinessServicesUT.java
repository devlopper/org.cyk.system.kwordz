package org.cyk.system.kwordz.business.impl.unit;

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
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.impl.music.NoteBusinessImpl;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.root.model.EnumHelper;
import org.cyk.utility.common.test.AbstractUnitTest;
import org.junit.Test;

public class MusicBusinessServicesUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    private NoteBusiness noteBusiness = new NoteBusinessImpl(null);
    	
	@Test
	public void notei18n(){
		EnumHelper.getInstance().setDefaultLocale(Locale.FRENCH);
		assertEqualsNote(NoteName.C, "si", "do", toString("do","#"));
		assertEqualsNote(NoteName.D, toString("re","b"), "re", toString("re","#"));
		assertEqualsNote(NoteName.E, toString("mi","b"), "mi", "fa");
		assertEqualsNote(NoteName.F, "mi", "fa", toString("fa","#"));
		assertEqualsNote(NoteName.G, toString("sol","b"), "sol", toString("sol","#"));
		assertEqualsNote(NoteName.A, toString("la","b"), "la", toString("la","#"));
		assertEqualsNote(NoteName.B, toString("si","b"), "si", "do");
		
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
		assertDistance(new Note(C, NONE), new Note(C, NONE), 0);
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
	
	@Test
	public void degree(){	
		assertDegree(0,SHARP, "1");
		assertDegree(1,SHARP, "1#");
		assertDegree(1,FLAT, "2b");
		
		assertDegree(2,SHARP, "2");
		assertDegree(3,SHARP, "2#");
		assertDegree(3,FLAT, "3b");
		
		assertDegree(4,SHARP, "3");
		assertDegree(5,SHARP, "4");
		assertDegree(6,SHARP, "4#");
		assertDegree(6,FLAT, "5b");
		
		assertDegree(7,SHARP, "5");
		assertDegree(8,SHARP, "5#");
		assertDegree(8,FLAT, "6b");
		
		assertDegree(9,SHARP, "6");
		assertDegree(10,SHARP, "6#");
		assertDegree(10,FLAT, "7b");
		
		assertDegree(11,SHARP, "7");
		assertDegree(12,SHARP, "1");
	}

	//@Test
	public void noteParsing(){
		Set<String> n = new LinkedHashSet<>(Arrays.asList("do","re"));
		Set<String> a = new LinkedHashSet<>(Arrays.asList("#","b"));
		parseNote(n, a, "do");
		parseNote(n, a, "do#");
		parseNote(n, a, "dob");
		
		parseNote(n, a, "re");
		parseNote(n, a, "re#");
		parseNote(n, a, "reb");
		
		n = new LinkedHashSet<>(Arrays.asList("A","B","C","D","E","F","G"));
		a = new LinkedHashSet<>(Arrays.asList("#","b"));
		
		parseNote(n, a, "G");
		parseNote(n, a, "G#");
		parseNote(n, a, "Gb");
	}
	
	@Test
	public void chordParsing(){
		Pattern p = Pattern.compile("((((?i)A|B|C|D|E|F|G)(#|b)*)?\\s*[/]?\\s*)((?i)A|B|C|D|E|F|G)(#|b)*\\s*(maj|dim|dim7)?");
		Matcher m = p.matcher("C # dim");
		m.find();
		//debug(m);
		
		System.out.println(m.groupCount()+" groups");
		for(int i=1;i<=m.groupCount();i++)
			System.out.println("Groupe "+i+" : "+m.group(i));
			
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
	
	protected void assertDegree(Integer distance,NoteAlteration preferredAlteration,String result){
		assertEquals(result,noteBusiness.degree(distance,preferredAlteration));
	}
	
	protected String toString(String name,String alteration){
		return name+(StringUtils.isEmpty(alteration)?"":" "+alteration);
	}
	protected String toString(String name){
		return toString(name);
	}

	private void parseNote(Set<String> names,Set<String> alterations,String note){
		Pattern pattern = Pattern.compile("("+StringUtils.join(names,"|")+")("+StringUtils.join(alterations,"|")+")*");
		Matcher matcher = pattern.matcher(note);
		matcher.find();
		System.out.println("Name : "+ matcher.group(1)+" , Alteration : "+ matcher.group(2));
	}
	
	
	
}

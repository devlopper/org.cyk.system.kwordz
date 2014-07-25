package org.cyk.system.kwordz.model;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.root.model.EnumHelper;
import org.cyk.utility.common.test.AbstractUnitTest;
import org.junit.Test;

public class MusicUT extends AbstractUnitTest implements Serializable {

	private static final long serialVersionUID = 4424660747159697509L;

	@Test
	public void notenameI18n(){
		EnumHelper.getInstance().setDefaultLocale(Locale.FRENCH);
		assertEqualsNoteName("la",NoteName.A);
		assertEqualsNoteName("si",NoteName.B);
		assertEqualsNoteName("do",NoteName.C);
		assertEqualsNoteName("re",NoteName.D);
		assertEqualsNoteName("mi",NoteName.E);
		assertEqualsNoteName("fa",NoteName.F);
		assertEqualsNoteName("sol",NoteName.G);
		
		assertEqualsNoteName(NoteName.C ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.FRENCH, "do"));
		assertEqualsNoteName(NoteName.D ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.FRENCH, "re"));
		assertEqualsNoteName(NoteName.E ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.FRENCH, "mi"));
		assertEqualsNoteName(NoteName.F ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.FRENCH, "fa"));
		assertEqualsNoteName(NoteName.G ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.FRENCH, "sol"));
		assertEqualsNoteName(NoteName.A ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.FRENCH, "la"));
		assertEqualsNoteName(NoteName.B ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.FRENCH, "si"));
		
		assertEqualsNoteName(NoteName.C ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.ENGLISH, "C"));
		assertEqualsNoteName(NoteName.D ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.ENGLISH, "D"));
		assertEqualsNoteName(NoteName.E ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.ENGLISH, "E"));
		assertEqualsNoteName(NoteName.F ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.ENGLISH, "F"));
		assertEqualsNoteName(NoteName.G ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.ENGLISH, "G"));
		assertEqualsNoteName(NoteName.A ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.ENGLISH, "A"));
		assertEqualsNoteName(NoteName.B ,EnumHelper.getInstance().getValueOf(NoteName.class, Locale.ENGLISH, "B"));
		
	}
	
	@Test
	public void alteration(){
		Note note = new Note(NoteName.C, NoteAlteration.NONE);
		assertEquals(note.getAlteration(), NoteAlteration.NONE);
		EnumHelper.getInstance().setDefaultLocale(Locale.FRENCH);
		assertEquals("",NoteAlteration.NONE.toString());
		assertEquals("b",NoteAlteration.FLAT.toString());
		assertEquals("#",NoteAlteration.SHARP.toString());
	}
	
	@Test
	public void noteFrench(){
		EnumHelper.getInstance().setDefaultLocale(Locale.FRENCH);
		assertEqualsNote(NoteName.C, toString("do","b"), "do", toString("do","#"));
		assertEqualsNote(NoteName.D, toString("re","b"), "re", toString("re","#"));
		assertEqualsNote(NoteName.E, toString("mi","b"), "mi", toString("mi","#"));
		assertEqualsNote(NoteName.F, toString("fa","b"), "fa", toString("fa","#"));
		assertEqualsNote(NoteName.G, toString("sol","b"), "sol", toString("sol","#"));
		assertEqualsNote(NoteName.A, toString("la","b"), "la", toString("la","#"));
		assertEqualsNote(NoteName.B, toString("si","b"), "si", toString("si","#"));
	}
	
	@Test
	public void noteEnglish(){
		EnumHelper.getInstance().setDefaultLocale(Locale.ENGLISH);
		assertEqualsNote(NoteName.C, toString("C","b"), "C", toString("C","#"));
		assertEqualsNote(NoteName.D, toString("D","b"), "D", toString("D","#"));
		assertEqualsNote(NoteName.E, toString("E","b"), "E", toString("E","#"));
		assertEqualsNote(NoteName.F, toString("F","b"), "F", toString("F","#"));
		assertEqualsNote(NoteName.G, toString("G","b"), "G", toString("G","#"));
		assertEqualsNote(NoteName.A, toString("A","b"), "A", toString("A","#"));
		assertEqualsNote(NoteName.B, toString("B","b"), "B", toString("B","#"));
	}
	
	protected void assertEqualsNoteName(String expected,NoteName actual){
		assertEquals(expected,actual.toString());
	}
	
	protected void assertEqualsNoteName(NoteName expected,NoteName actual){
		assertEquals(expected.name(),actual.name());
	}
	
	protected void assertEqualsNote(String expected,Note actual){
		assertEquals(expected,actual.toString());
	}
	
	protected void assertEqualsNote(NoteName noteName,String bemol,String none,String sharp){
		assertEqualsNote(bemol,new Note(noteName,NoteAlteration.FLAT));
		assertEqualsNote(none,new Note(noteName,NoteAlteration.NONE));
		assertEqualsNote(sharp,new Note(noteName,NoteAlteration.SHARP));
	}
	
	protected String toString(String name,String alteration){
		return name+(StringUtils.isEmpty(alteration)?"":" "+alteration);
	}
	protected String toString(String name){
		return toString(name);
	}
	
}

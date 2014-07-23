package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteFormatOptions;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.kwordz.persistence.api.music.NoteDao;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.EnumHelper;

public class NoteBusinessImpl extends AbstractTypedBusinessService<Note, NoteDao> implements NoteBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	private static final String NAME_ALTERATION_SEPARATOR = " ";
	private static final Locale[] PARSING_SUPPORTED_LOCALE = {Locale.ENGLISH,Locale.FRENCH};
	private static final NoteName[] SENSIBLE_SHARP = {NoteName.E,NoteName.B};
	private static final NoteName[] SENSIBLE_FLAT = {NoteName.C,NoteName.F};
	
	@Inject private LanguageBusiness languageBusiness;
	
	@Inject
	public NoteBusinessImpl(NoteDao dao) { 
		super(dao);    
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void useAlterartion(Note aNote, NoteAlteration anAlteration) {
		if(anAlteration!=null && !NoteAlteration.NONE.equals(anAlteration) && !NoteAlteration.NONE.equals(aNote.getAlteration()) && !anAlteration.equals(aNote.getAlteration()) ){
			NoteName newNoteName = null;
			if(NoteAlteration.SHARP.equals(anAlteration)){
				// re b -> do (#)
				newNoteName = previousNoteName(aNote.getName());
			}else if(NoteAlteration.FLAT.equals(anAlteration)){
				//do # -> re (b) 
				newNoteName = nextNoteName(aNote.getName());
			}
			
			aNote.setName(newNoteName);
			aNote.setAlteration(anAlteration);
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void normalize(Note aNote,NoteAlteration preferredAlteration){
		if( ArrayUtils.contains(SENSIBLE_SHARP, aNote.getName()) && NoteAlteration.SHARP.equals(aNote.getAlteration()) ){
			aNote.setName(nextNoteName(aNote.getName()));
			aNote.setAlteration(NoteAlteration.NONE);
		}else if( ArrayUtils.contains(SENSIBLE_FLAT, aNote.getName()) && NoteAlteration.FLAT.equals(aNote.getAlteration()) ){
			aNote.setName(previousNoteName(aNote.getName()));
			aNote.setAlteration(NoteAlteration.NONE);
		}
		useAlterartion(aNote, preferredAlteration);
		
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void normalize(Collection<Note> aCollection,NoteAlteration preferredAlteration) {
		for(Note aNote : aCollection)
			normalize(aNote,preferredAlteration);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void transpose(Note aNote, Integer distance) {
		if(distance==0)
			return;
		NoteAlteration alteration = aNote.getAlteration();
		if(distance>0)
			for(int i=0;i<distance;i++)
				nextNote(aNote,alteration);
		else{
			distance = -distance;
			for(int i=0;i<distance;i++)
				previousNote(aNote,alteration);
		}
		useAlterartion(aNote, alteration);
		//System.out.println("NoteBusinessImpl.transpose() : "+aNote);
		//System.out.println(aNote);
	}
	
	@Override
	public Boolean equals(Note aNote1, Note aNote2,Boolean strict) {
		Note n1,n2;
		if(Boolean.TRUE.equals(strict)){
			n1 = aNote1;
			n2 = aNote2;
		}else{
			useAlterartion(n1 = new Note(aNote1), NoteAlteration.SHARP);
			useAlterartion(n2 = new Note(aNote2), NoteAlteration.SHARP);	
		}
		
		Boolean ok = n1.getName()!=null && n1.getName().equals(n2.getName());	
		if(Boolean.TRUE.equals(ok))
			if(n1.getAlteration()!=null)
				ok = n1.getAlteration().equals(n2.getAlteration());
			else
				ok = n2.getAlteration()==null;
		
		return ok;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Integer distance(Note aNote1, Note aNote2) {
		int halfToneCount = 0;
		for(Note index = new Note(aNote1);!equals(index, aNote2,Boolean.FALSE);transpose(index, 1))
			halfToneCount++;
		return halfToneCount;
	}
	
	@Override
	public String degree(Integer distance,NoteAlteration preferredAlteration) {
		distance = distance % 12;
		Integer index = null;
		Boolean altered = Boolean.FALSE;
		switch(distance){
		case 0:case 2:case 4: 
			index = distance/2 + 1;
			break;
		case 5: 
			index = 4;
			break;
		case 7:case 9:case 11: 
			index = distance/2 +2;
			break;
		case 1:	
			index = 1;	
			altered = Boolean.TRUE;
			break;
		case 3: 
			index = distance-1;	// String.valueOf((i-1)/2)+NoteAlteration.SHARP.toString();	
			altered = Boolean.TRUE;
			break;
		default:
			index = distance/2 + 1;
			altered = Boolean.TRUE;
			break;
		}
		if(Boolean.TRUE.equals(altered)){
			if(preferredAlteration==null)
				preferredAlteration = NoteAlteration.SHARP;
			if(NoteAlteration.FLAT.equals(preferredAlteration)){
				index++;
			}
			return index.toString()+preferredAlteration.toString();
		}else
			return index.toString();
	}
	
	@Override
	public String format(Locale locale, Note note,NoteFormatOptions options) {
		if(note.getName()==null)
			return "";
		if(options==null)
			options = KwordzBusinessLayer.getInstance().getDefaultNoteFormatOptions();
		if(note.getAlteration()!=null && !NoteAlteration.NONE.equals(note.getAlteration()))
			return languageBusiness.findText(locale, note.getName())+
					(Boolean.TRUE.equals(options.getSeperateNameAlterartion())?NAME_ALTERATION_SEPARATOR:"")+
					languageBusiness.findText(locale, note.getAlteration());
		return languageBusiness.findText(locale, note.getName());
	}
	
	@Override
	public String format(Locale locale, Note note) {
		return format(locale, note, KwordzBusinessLayer.getInstance().getDefaultNoteFormatOptions());
	}
	
	@Override
	public Note parse(Locale locale, String text) {
		exceptionUtils().exception(!ArrayUtils.contains(PARSING_SUPPORTED_LOCALE, locale),"kwordz.exception.parsing.localenotsupported",new Object[]{locale});
		text = clean(text);
		exceptionUtils().exception(StringUtils.isEmpty(text),"kwordz.exception.parsing.notenotfound");
		Note note = null;
		exceptionUtils().exception( Boolean.TRUE.equals(formatException(locale,text)),"kwordz.exception.parsing.noteformat",new Object[]{text});
		note = new Note();
		String noteNameSubString = noteNameSubString(locale, text);
		String noteAlterationSubString = StringUtils.substringAfter(text, noteNameSubString);
		note.setName(EnumHelper.getInstance().getValueOf(NoteName.class, locale, noteNameSubString));
		exceptionUtils().exception(note.getName()==null,"kwordz.exception.parsing.notename.unknown",new Object[]{noteNameSubString});
		if(StringUtils.isNotEmpty(noteAlterationSubString)){
			note.setAlteration(EnumHelper.getInstance().getValueOf(NoteAlteration.class, locale, noteAlterationSubString));
			exceptionUtils().exception(note.getAlteration()==null,"kwordz.exception.parsing.notealteration.unknown",new Object[]{noteAlterationSubString});
		}
		exceptionUtils().exception(note==null,"kwordz.exception.parsing.notenotfound");
		return note;
		
		/*
		if(Locale.FRENCH.equals(locale)){
			Boolean sol = text.equals("sol");
			// Letter | Letter+Alteration ex : do , dob , do#			
			exceptionUtils().exception( (sol && text.length()>4) || text.length()>3,"kwordz.exception.parsing.noteformat",new Object[]{text});
			note = new Note();
			String noteNameChar = text.substring(0, 2+(sol?1:0));
			text = StringUtils.substringAfter(text, noteNameChar);
			note.setName(EnumHelper.getInstance().getValueOf(NoteName.class, locale, noteNameChar));
			exceptionUtils().exception(note.getName()==null,"kwordz.exception.parsing.notename.unknown",new Object[]{noteNameChar});
			//note alteration constraint
			String noteAlterationChar = text;
			if(StringUtils.isNotEmpty(noteAlterationChar)){
				note.setAlteration(EnumHelper.getInstance().getValueOf(NoteAlteration.class, locale, noteAlterationChar));
				exceptionUtils().exception(note.getAlteration()==null,"kwordz.exception.parsing.notealteration.unknown",new Object[]{noteAlterationChar});
			}
		}else if(Locale.ENGLISH.equals(locale)){
			// Letter | Letter+Alteration ex : A , Ab , A#			
			exceptionUtils().exception(text.length()>2,"kwordz.exception.parsing.noteformat",new Object[]{text});
			note = new Note();
			Character noteNameChar = text.charAt(0);
			note.setName(EnumHelper.getInstance().getValueOf(NoteName.class, locale, noteNameChar.toString()));
			exceptionUtils().exception(note.getName()==null,"kwordz.exception.parsing.notename.unknown",new Object[]{noteNameChar});
			//note alteration constraint
			if(text.length()==2){
				Character noteAlterationChar = text.charAt(1);
				note.setAlteration(EnumHelper.getInstance().getValueOf(NoteAlteration.class, locale, noteAlterationChar.toString()));
				exceptionUtils().exception(note.getAlteration()==null,"kwordz.exception.parsing.notealteration.unknown",new Object[]{noteAlterationChar});
			}
		}
		*/
	
		
	}
	
	/**/
	
	private NoteName nextNoteName(NoteName noteName){
		return NoteName.values()[(noteName.ordinal()+1) % 7];
	}
	
	private NoteName previousNoteName(NoteName noteName){
		if(noteName.ordinal()==0)
			return NoteName.values()[NoteName.values().length-1];
		return NoteName.values()[noteName.ordinal()-1];
	}
    
	private void nextNote(Note aNote,NoteAlteration preferredAlterarion) {
		if(ArrayUtils.contains(SENSIBLE_SHARP, aNote.getName()))
			switch(aNote.getAlteration()){
			case NONE:aNote.setName(nextNoteName(aNote.getName()));break;
			case SHARP: exceptionUtils().exception("kwordz.exception.note.notnormalize");break;	
			case FLAT: aNote.setAlteration(NoteAlteration.NONE);break;
			}
		else
			switch(aNote.getAlteration()){
			case NONE:
				aNote.setAlteration(NoteAlteration.SHARP);
				break;
			case SHARP:
				aNote.setName(nextNoteName(aNote.getName()));
				aNote.setAlteration(NoteAlteration.NONE);
				break;
			case FLAT:aNote.setAlteration(NoteAlteration.NONE);break;
			}
	}
	
	private void previousNote(Note aNote,NoteAlteration preferredAlterarion) {
		if(ArrayUtils.contains(SENSIBLE_FLAT, aNote.getName()))
			switch(aNote.getAlteration()){
			case NONE:aNote.setName(previousNoteName(aNote.getName()));break;
			case FLAT: exceptionUtils().exception("kwordz.exception.note.notnormalize");break;	
			case SHARP: aNote.setAlteration(NoteAlteration.NONE);break;
			}
		else
			switch(aNote.getAlteration()){
			case NONE:aNote.setAlteration(NoteAlteration.FLAT);break;
			case SHARP:
				aNote.setAlteration(NoteAlteration.NONE);
				break;
			case FLAT:
				aNote.setName(previousNoteName(aNote.getName()));
				aNote.setAlteration(NoteAlteration.NONE);
				break;
			}
	}
	
	private String clean(String text){
		text = StringUtils.trim(text);
		text = StringUtils.replace(text, "  ", " ");
		return text;
	}
	
	private Boolean formatException(Locale locale, String text){
		if(Locale.FRENCH.equals(locale))
			return (isFrenchNoteSol(text) && text.length()>4) || text.length()>3;
		else if(Locale.ENGLISH.equals(locale))
			return text.length()>2;
		return Boolean.TRUE;
	}
	
	private String noteNameSubString(Locale locale, String text){
		if(Locale.FRENCH.equals(locale))
			return text.substring(0, 2+(isFrenchNoteSol(text)?1:0));
		else if(Locale.ENGLISH.equals(locale))
			return text.substring(0, 1);
		return null;
	}
	
	private Boolean isFrenchNoteSol(String text){
		return text.equals("sol");
	}
	
}

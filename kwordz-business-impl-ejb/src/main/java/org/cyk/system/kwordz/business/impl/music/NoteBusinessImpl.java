package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.kwordz.persistence.api.music.NoteDao;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;

public class NoteBusinessImpl extends AbstractTypedBusinessService<Note, NoteDao> implements NoteBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	private static final NoteName[] SENSIBLE_SHARP = {NoteName.E,NoteName.B};
	private static final NoteName[] SENSIBLE_FLAT = {NoteName.C,NoteName.F};
	
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
	public Boolean equals(Note aNote1, Note aNote2) {
		Note n1;
		useAlterartion(n1 = new Note(aNote1), NoteAlteration.SHARP);
		Note n2;
		useAlterartion(n2 = new Note(aNote2), NoteAlteration.SHARP);
		return n1.equals(n2);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Integer distance(Note aNote1, Note aNote2) {
		int halfToneCount = 0;
		for(Note index = new Note(aNote1);!equals(index, aNote2);transpose(index, 1))
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
}

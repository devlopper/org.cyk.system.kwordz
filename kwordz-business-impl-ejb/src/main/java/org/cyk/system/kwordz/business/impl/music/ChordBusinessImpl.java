package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.persistence.api.music.ChordDao;

public class ChordBusinessImpl extends AbstractNoteCollectionBusinessImpl<ChordStructure,Chord, ChordDao,ChordStructureBusiness> implements ChordBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	private static final String CHORD_BASS_NOTE_SEPARATOR = "/";
	
	@Inject
	public ChordBusinessImpl(ChordDao dao,ChordStructureBusiness chordStructureBusiness) { 
		super(dao,chordStructureBusiness);   
	}
	
	@Override
	public Boolean bassEqualsToRoot(Chord chord) {
		Note root = findRoot(chord);
		if(root==null)
			return Boolean.FALSE;
		return noteBusiness.equals(root, chord.getBass(),Boolean.FALSE);
	}
	
	@Override
	public void transpose(Chord aChord, Integer distance) {
		super.transpose(aChord, distance);
		noteBusiness.transpose(aChord.getBass(), distance);
	}
	
	@Override
	public Boolean equals(Chord chord1, Chord chord2,Boolean strict) {
		Boolean ok = Boolean.FALSE;
		if(ok = (chord1.getStructure().equals(chord2.getStructure()) && chord1.getNotes().size()==chord2.getNotes().size() )){
			for(int i=0;i<chord1.getNotes().size();i++)
				if(!noteBusiness.equals(chord1.getNotes().get(i), chord2.getNotes().get(i),strict))
					return Boolean.FALSE;
		}
		return ok;
	}
	
	@Override
	public String format(Locale locale, Chord chord, ChordFormatOptions options) {
		return noteBusiness.format(locale, findRoot(chord))+" "+chord.getStructure().getSymbols().iterator().next();
	}
	
	@Override
	public Chord parse(Locale locale, String text) {
		/*Letter[#b]ChordType - left hand = bass note = single note only*/
		String[] hands=  text.split(CHORD_BASS_NOTE_SEPARATOR);
		exceptionUtils().exception(hands.length>2,"kwordz.exception.parsing.numberofhands",new Object[]{text});
		Chord chord = new Chord();
		if(hands.length==2) 
			chord.setBass(noteBusiness.parse(locale,hands[1].trim()));
		String rightHand = hands[0].trim();
		exceptionUtils().exception(rightHand.isEmpty(),"kwordz.exception.parsing.chord.format",new Object[]{text});
		if(rightHand.length()==1){
			//chord.setBass(noteBusiness.parse(locale,rightHand));
			chord.setStructure(structureBusiness.findBySymbol("maj"));
		}else{ 
			int rootNoteLength;
			if(rightHand.charAt(1)=='#' || rightHand.charAt(1)=='b')
				rootNoteLength = 2;
			else
				rootNoteLength = 1;
			//chord.setRoot(noteBusiness.parse(locale,rightHand.substring(0, rootNoteLength)));
			chord.setStructure(structureBusiness.findBySymbol(rightHand.substring(rootNoteLength)));
		}		
		exceptionUtils().exception(chord.getStructure()==null,"kwordz.exception.parsing.chord.format",new Object[]{text});
		return chord;
	}
	
}

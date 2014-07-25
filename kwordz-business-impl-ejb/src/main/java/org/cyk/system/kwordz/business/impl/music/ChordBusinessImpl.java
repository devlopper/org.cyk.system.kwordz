package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.business.impl.ParserHelper;
import org.cyk.system.kwordz.business.impl.PatternMatcherType;
import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.persistence.api.music.ChordDao;

public class ChordBusinessImpl extends AbstractNoteCollectionBusinessImpl<ChordStructure,Chord, ChordDao,ChordStructureBusiness,ChordFormatOptions> implements ChordBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ParserHelper parserHelper;
	
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
		StringBuilder builder = new StringBuilder();
		if(Boolean.TRUE.equals(options.getShowMarker()))
			builder.append(options.getMarkerStart());
		String structureSymbol = chord.getStructure().getSymbols().iterator().next();
		if(StringUtils.isNotEmpty(structureSymbol))
			structureSymbol = options.getSeparatorNoteAndStructure()+structureSymbol;
		switch(options.getLayout()){
		case EXPAND:
			Collection<String> notes = new ArrayList<>();
			for(Note note : chord.getNotes())
				notes.add(noteBusiness.format(locale, note));
			builder.append(StringUtils.join(notes.iterator(),options.getSeparatorNoteAndNote())+structureSymbol);
			break;
		case CONTRACT:
			builder.append(noteBusiness.format(locale, findRoot(chord))+structureSymbol);
			break;
		}
		
		if(Boolean.TRUE.equals(options.getShowMarker()))
			builder.append(options.getMarkerEnd());
		return builder.toString();
	}
	
	@Override
	public Chord parse(Locale locale, String text) {
		/*Letter[#b]ChordType - left hand = bass note = single note only*/
		//System.out.println("ChordBusinessImpl.parse() : "+text);
		Matcher matcher = parserHelper.matcher(locale, PatternMatcherType.CHORD, text);
		
		Chord chord = new Chord();
		if(StringUtils.isNotEmpty(matcher.group(1))){
			text = parserHelper.stringAfter(text, matcher.group(1));
			chord.setBass(noteBusiness.parse(locale, parserHelper.getNoteString(matcher, 2)));
		}
		String noteString = parserHelper.getNoteString(matcher, 4);
		Note base = noteBusiness.parse(locale, noteString);
		if(chord.getBass()!=null && noteBusiness.equals(base, chord.getBass(), Boolean.FALSE))
			chord.setBass(null);
		text = parserHelper.stringAfter(text, noteString);
		if(StringUtils.isBlank(text)){
			text = "maj";
		}else{
			exceptionUtils().exception(StringUtils.isEmpty(matcher.group(6)),"kwordz.exception.parsing.chord.structure.unknown",new Object[]{text});
			text = matcher.group(6);
		}
		//System.out.println(text);
		chord.setStructure(structureBusiness.findBySymbol(text));
		generateNotes(chord, chord.getStructure(), base);
		return chord;
	}
	
}

package com.kwordz.text.parser;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.model.music.Note;

public class ChordParser extends AbstractParser<Chord> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public ChordParser() {
		addLocaleParser(new AbstractLocaleParser<Chord>(Locale.ENGLISH) {

			private static final long serialVersionUID = 1L;

			@Override
			public Chord parse(String text) throws Exception {
				/*Letter[#b]ChordType - left hand = bass note = single note only*/
				String[] hands=  text.split(CHORD_BASS_NOTE_SEPARATOR);
				//number of hands constraint
				if(hands.length>2)
					throw new ChordFormatException(text);
				NoteParser noteParser = new NoteParser();
				Chord chord = new Chord();
				if(hands.length==2) 
					chord.setBass(noteParser.parse(hands[1].trim(),locale));
				String rightHand = hands[0].trim();
				if(rightHand.isEmpty())
					throw new ChordFormatException(text);
				if(rightHand.length()==1){
					chord.setRoot(noteParser.parse(rightHand, locale));
					chord.setStructure(ChordStructure.getBySymbol(""));
				}else{ 
					int rootNoteLength;
					if(rightHand.charAt(1)=='#' || rightHand.charAt(1)=='b')
						rootNoteLength = 2;
					else
						rootNoteLength = 1;
					chord.setRoot(noteParser.parse(rightHand.substring(0, rootNoteLength), locale));
					chord.setStructure(ChordStructure.getBySymbol(rightHand.substring(rootNoteLength)));
					if(chord.getStructure()==null)
						throw new UnknownChordException(rightHand);
				}		
				if(chord.getBass()==null)
					chord.setBass(chord.getRoot());
				return chord;
			}
		});
	}

	@Override
	public Chord parse(String chordContractedForm) throws Exception{
		String temp = chordContractedForm;
		String[] hands = chordContractedForm.split(CHORD_BASS_NOTE_SEPARATOR);
		//number of hands constraint
		if(hands.length>2)
			throw new ChordFormatException(temp);
		String part[] = hands[0].split(CHORD_TYPE_SEPARATOR);
		//right hand format constraint
		if(part.length>2)
			throw new ChordFormatException(temp);

		NoteParser noteParser = new NoteParser();
		Note note = noteParser.parse(part[0].trim().replaceAll("  ", " "));
		Note bass;
		if(hands.length==1)
			bass = new Note(note);
		else
			bass = noteParser.parse(hands[1].trim());
		ChordStructure chordStructure = null;
		if(part.length==1)
			chordStructure = ChordStructure.getBySymbol("");
		else{
			chordStructure = ChordStructure.getBySymbol(part[1].trim());
			if(chordStructure==null)
				throw new UnknownChordException(temp);
		}
		return new Chord(chordStructure, note,bass);
	}

}

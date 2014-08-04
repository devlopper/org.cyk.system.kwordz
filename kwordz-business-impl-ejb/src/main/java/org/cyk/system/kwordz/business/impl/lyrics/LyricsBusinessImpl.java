package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.lyrics.PartBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.Line;
import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.persistence.api.lyrics.LyricsDao;
import org.cyk.system.root.model.ContentType;

public class LyricsBusinessImpl extends AbstractMusicBusinessImpl<Lyrics, LyricsDao,LyricsFormatOptions> implements LyricsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private PartBusiness partBusiness;
	@Inject private ChordStructureBusiness chordStructureBusiness;
	
	@Inject
	public LyricsBusinessImpl(LyricsDao dao) { 
		super(dao);    
	}

	@Override
	public void transpose(Lyrics lyrics, Integer distance) {
		for(Part part : lyrics.getParts())
			partBusiness.transpose(part, distance);
	}
	
	@Override
	public void generateNotes(Lyrics lyrics, Note root) {
		for(Chord chord : allChords(lyrics))
			chord.setNotes(chordStructureBusiness.generateSequence(chord.getStructure(), chord.getRoot()));
	}

	@Override
	protected LyricsFormatOptions defaultFormatOptions() {
		return KwordzBusinessLayer.getInstance().getDefaultLyricsFormatOptions();
	}
	
	@Override
	public String format(Locale locale, Lyrics lyrics,ContentType contentType,LyricsFormatOptions options) {
		StringBuilder stringBuilder = new StringBuilder();
		Collection<String> collection = new ArrayList<>();
		for(Part part : lyrics.getParts())
			collection.add(partBusiness.format(locale,part,contentType,options.getPartFormatOptions()));
		stringBuilder.append(StringUtils.join(collection.iterator(),contentType.getNewLineMarker()));
		return stringBuilder.toString();
	}

	@Override
	public Lyrics parse(Locale locale, String text) {
		text = StringUtils.strip(text);
		if(text==null || text.isEmpty())
			exceptionUtils().exception("null");//TODO move it to root .null(ValueName)
		Lyrics lyrics = new Lyrics();
		lyrics.getParts().add(partBusiness.parse(locale, text));
		
		return lyrics;
	}
	
	/**/
	
	private Collection<Chord> allChords(Lyrics lyrics) {
		Collection<Chord> collection = new ArrayList<>();
		for(Part part : lyrics.getParts())
			for(Line line : part.getLines())
				for(Fragment fragment : line.getFragments())
					if(fragment.getChord()!=null)
						collection.add(fragment.getChord());
		return collection;
	}
	
}

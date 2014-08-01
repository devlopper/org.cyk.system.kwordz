package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.lyrics.PartBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.persistence.api.lyrics.LyricsDao;
import org.cyk.system.root.model.ContentType;

public class LyricsBusinessImpl extends AbstractMusicBusinessImpl<Lyrics, LyricsDao,LyricsFormatOptions> implements LyricsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private PartBusiness partBusiness;
	
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
		Lyrics lyrics = new Lyrics();
		lyrics.getParts().add(partBusiness.parse(locale, text));
		return lyrics;
	}
	
}

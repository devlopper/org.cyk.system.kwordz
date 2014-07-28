package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.lyrics.PartBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.persistence.api.lyrics.LyricsDao;

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
	public String format(Locale locale, Lyrics lyrics,LyricsFormatOptions options) {
		StringBuilder stringBuilder = new StringBuilder();
		for(Part part : lyrics.getParts())
			stringBuilder.append(partBusiness.format(locale,part)+"\r\n");
		return stringBuilder.toString();
	}

	@Override
	public String format(Locale locale, Lyrics lyrics) {
		return format(locale, lyrics, KwordzBusinessLayer.getInstance().getDefaultLyricsFormatOptions());
	}

	@Override
	public Lyrics parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

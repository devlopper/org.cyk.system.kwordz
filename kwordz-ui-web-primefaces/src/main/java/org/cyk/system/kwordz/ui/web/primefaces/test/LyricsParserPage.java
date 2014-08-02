package org.cyk.system.kwordz.ui.web.primefaces.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions.ChordLocation;
import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.ContentType;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Named @ViewScoped @Getter @Setter
public class LyricsParserPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private LyricsBusiness lyricsBusiness;
	@Inject private LanguageBusiness languageBusiness;
	
	private List<SelectItem> locales = new ArrayList<>();
	private Locale locale = Locale.ENGLISH;
	
	private String lyricsToParse,lyricsParsed;
	private Lyrics lyrics;
	private LyricsFormatOptions lyricsFormatOptions = new LyricsFormatOptions();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		for(Locale locale : lyricsBusiness.findParsableLocales())
			locales.add(new SelectItem(locale, locale.toString()));
		lyricsFormatOptions.getPartFormatOptions().getLineFormatOptions().setChordLocation(ChordLocation.TOP);
	}
	
	public void parse(){
		lyrics = lyricsBusiness.parse(locale, lyricsToParse);
		lyricsParsed = lyricsBusiness.format(locale, lyrics,ContentType.HTML,lyricsFormatOptions);
	}

}

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
import org.cyk.system.kwordz.model.lyrics.ChordLocation;
import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.root.model.ContentType;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Named @ViewScoped @Getter @Setter
public class KwordzTestingBoard extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private LyricsBusiness lyricsBusiness;
	
	private List<SelectItem> locales = new ArrayList<>();
	private Locale locale = Locale.ENGLISH;
	
	private String inputLyrics,outputLyrics;
	private Lyrics lyrics;
	private LyricsFormatOptions lyricsFormatOptions = new LyricsFormatOptions();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		locales.add(new SelectItem(Locale.ENGLISH, "English"));
		locales.add(new SelectItem(Locale.FRENCH, "French"));
		locales.add(new SelectItem(Locale.GERMANY, "German"));
		lyricsFormatOptions.getPartFormatOptions().getLineFormatOptions().setChordLocation(ChordLocation.TOP);
	}
	
	public void parseInputLyrics(){
		lyrics = lyricsBusiness.parse(locale, inputLyrics);
		outputLyrics = lyricsBusiness.format(locale, lyrics,ContentType.HTML,lyricsFormatOptions);
	}

}

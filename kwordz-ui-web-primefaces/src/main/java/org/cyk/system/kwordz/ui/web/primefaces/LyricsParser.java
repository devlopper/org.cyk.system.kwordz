package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions.ChordLocation;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class LyricsParser extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2470538473226699152L;

	private Locale locale = Locale.ENGLISH;//TODO must be read from user session
	
	private String lyricsToParse,lyricsParsed;
	private Lyrics lyrics;
	private LyricsFormatOptions lyricsFormatOptions = new LyricsFormatOptions();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		lyricsFormatOptions.getPartFormatOptions().getLineFormatOptions().setChordLocation(ChordLocation.TOP);
	}
	
}

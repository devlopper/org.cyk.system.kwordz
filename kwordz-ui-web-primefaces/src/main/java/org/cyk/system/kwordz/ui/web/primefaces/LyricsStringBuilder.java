package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.net.URI;
import java.util.Locale;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions.ChordLocation;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.model.ContentType;
import org.cyk.ui.web.api.WebSession;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class LyricsStringBuilder extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2470538473226699152L;

	@Inject private WebSession session;
	@Inject private SongBusiness songBusiness;
	@Inject private LyricsBusiness lyricsBusiness;
	
	private Locale noteLocale;
	private String lyricsToParse,lyricsParsed;
	private Song song;
	private Note selectedTone;
	private URI embeddedMediaUri;
	private LyricsFormatOptions lyricsFormatOptions = new LyricsFormatOptions();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		lyricsFormatOptions.getPartFormatOptions().getLineFormatOptions().setChordLocation(ChordLocation.TOP);
	}
	
	public void init(Song song) {
		this.song = song;
		embeddedMediaUri = songBusiness.findMediaEmbeddedUri(song);
		build();
	}
	
	public void build(){
		songBusiness.transpose(song, selectedTone);
		lyricsParsed = lyricsBusiness.format(noteLocale, song.getLyrics(),ContentType.HTML, lyricsFormatOptions);
	}
	
}

package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.lyrics.ChordLocation;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.EnumHelper;
import org.cyk.ui.web.api.WebSession;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class LyricsStringBuilder extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2470538473226699152L;

	@Inject private WebSession session;
	@Inject private SongBusiness songBusiness;
	@Inject private LyricsBusiness lyricsBusiness;
	@Inject private NoteBusiness noteBusiness;
	
	private Locale noteLocale;
	private String lyricsToParse,lyricsParsed;
	private Song song;
	private Note selectedTone;
	private URI embeddedMediaUri;
	private LyricsFormatOptions lyricsFormatOptions = new LyricsFormatOptions(),__lyricsFormatOptions=new LyricsFormatOptions();
	private List<SelectItem> tonesItems = new ArrayList<>();
	
	private List<SelectItem> chordLocationItems = new ArrayList<>();
	private ChordLocation lyricsParsedChordLocation = ChordLocation.TOP,lyricsToParseChordLocation=ChordLocation.TOP,
			peviousLyricsToParseChordLocation;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		initTones(session.getLocale());
		noteLocale = session.getLocale();
		__lyricsFormatOptions.getPartFormatOptions().getLineFormatOptions().setChordLocation(ChordLocation.TOP);
		for(ChordLocation chordLocation : new ChordLocation[]{ChordLocation.TOP,ChordLocation.FOLLOW_FRAGMENT})
			chordLocationItems.add(new SelectItem(chordLocation, EnumHelper.getInstance().text(session.getLocale(), chordLocation)));
	}
	
	public void init(Song song) {
		this.song = song;
		embeddedMediaUri = songBusiness.findMediaEmbeddedUri(song);
		build();
	}
	
	public void initTones(Locale locale){
		tonesItems.clear();
		Long i=0l;
		for(Note note : noteBusiness.findTones()){
			note.setIdentifier(i++);
			tonesItems.add(new SelectItem(note, noteBusiness.format(locale, note)));
		}
	}
	
	public void build(){
		if(song.getLyrics()==null)
			return;
		songBusiness.transpose(song, selectedTone);
		lyricsFormatOptions.getPartFormatOptions().getLineFormatOptions().setChordLocation(lyricsParsedChordLocation);
		lyricsParsed = lyricsBusiness.format(noteLocale, song.getLyrics(),ContentType.HTML, lyricsFormatOptions);
	}
	
	public void buildAndApplySongLyrics(){
		song.setLyrics(lyricsBusiness.parse(noteLocale,lyricsToParse));
	}
	
	public void applyLyricsToParseChange(){
		if(lyricsToParse==null)
			return;
		if(!lyricsToParseChordLocation.equals(peviousLyricsToParseChordLocation)){
			if(ChordLocation.FOLLOW_FRAGMENT.equals(lyricsToParseChordLocation)){
				lyricsToParse = lyricsBusiness.parseableForm(lyricsToParse);
				peviousLyricsToParseChordLocation = lyricsToParseChordLocation;
				//lyricsToParseChordLocation = ChordLocation.TOP;
			}else if(ChordLocation.TOP.equals(lyricsToParseChordLocation)){
				lyricsToParse = lyricsBusiness.format(session.getLocale(), lyricsBusiness.parse(session.getLocale(), lyricsToParse), ContentType.TEXT,__lyricsFormatOptions);
				peviousLyricsToParseChordLocation = lyricsToParseChordLocation;
				//lyricsToParseChordLocation = ChordLocation.FOLLOW_FRAGMENT;
			}
		}
	}
	
}

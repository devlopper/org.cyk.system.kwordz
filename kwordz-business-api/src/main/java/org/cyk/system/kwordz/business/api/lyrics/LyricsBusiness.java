package org.cyk.system.kwordz.business.api.lyrics;

import org.cyk.system.kwordz.business.api.AbstractMusicBusiness;
import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.music.Note;

public interface LyricsBusiness extends AbstractMusicBusiness<Lyrics,LyricsFormatOptions> {
   
	void generateNotes(Lyrics lyrics,Note root);
	
	String parseableForm(String text);
}

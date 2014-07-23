package org.cyk.system.kwordz.model.song;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;

@Getter @Setter @Entity @NoArgsConstructor
public class Song extends AbstractEnumeration implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int LYRICS_LENGTH = 1024 * 4;
	
	@ManyToOne private Album album;
	private Note tone;
	@ManyToOne private File media;
	
	/* lyrics */
	private String lyricsLocaleCode;//language code (fr,en,...)
	@Basic(fetch=FetchType.LAZY) private Lyrics lyrics;
	@Basic(fetch=FetchType.LAZY) @Column(length=LYRICS_LENGTH) private String lyricsCompleteTextOnly;
	
	/*
	public void transpose(float interval){
		tone.transpose(interval);
		lyrics.transpose(interval);
	}
	*/
	/*
	public String getThumbnailURI(){
		if(media==null)
			return "";
		if(media.getThumbnailURI()==null)
			return super.getThumbnailURI();
		return media.getThumbnailURI().toString();
	}
	*/

}

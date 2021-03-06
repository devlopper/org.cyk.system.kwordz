package org.cyk.system.kwordz.model.song;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.music.MusicKind;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.information.InformationCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,uiIconName="music-note")
public class Song extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne private Album album;
	
	private String name;
	
	@OneToOne(cascade=CascadeType.ALL) private Note tone;
	
	@OneToOne(cascade=CascadeType.ALL) private File media;
	
	@OneToOne(cascade=CascadeType.ALL) private Lyrics lyrics;
	
	@OneToOne(cascade=CascadeType.ALL) private InformationCollection informationCollection;
	
	@OneToOne(cascade=CascadeType.ALL) private MusicKind musicKind;

	public Song(Album album,String name, Note tone, File media, Lyrics lyrics) {
		super();
		this.album = album;
		this.name = name;
		this.tone = tone;
		this.media = media;
		this.lyrics = lyrics;
	}
	
	@Override
	public String getUiString() {
		return name;
	}
	
}

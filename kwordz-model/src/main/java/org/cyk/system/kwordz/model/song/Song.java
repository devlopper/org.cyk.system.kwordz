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
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.information.InformationCollection;

@Getter @Setter @Entity @NoArgsConstructor
public class Song extends AbstractEnumeration implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne private Album album;
	
	@OneToOne(cascade=CascadeType.ALL) private Note tone;
	
	@OneToOne(cascade=CascadeType.ALL) private File media;
	
	@OneToOne(cascade=CascadeType.ALL) private Lyrics lyrics;
	
	@OneToOne(cascade=CascadeType.ALL) private InformationCollection informationCollection;
	
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

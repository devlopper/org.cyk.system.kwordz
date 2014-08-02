package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.song.Song;

@Getter @Setter
public abstract class AbstractSongInfos implements Serializable {

	private static final long serialVersionUID = 5367546224286620639L;

	protected Song song;

	public AbstractSongInfos(Song song) {
		super();
		this.song = song;
	}
	
	
	
}

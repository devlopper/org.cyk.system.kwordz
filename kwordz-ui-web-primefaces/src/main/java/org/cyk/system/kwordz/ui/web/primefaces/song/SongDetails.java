package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.song.Song;

@Getter @Setter
public class SongDetails extends AbstractSongInfos implements Serializable {

	private static final long serialVersionUID = 5367546224286620639L;

	public SongDetails(Song song) {
		super(song);
	}
}

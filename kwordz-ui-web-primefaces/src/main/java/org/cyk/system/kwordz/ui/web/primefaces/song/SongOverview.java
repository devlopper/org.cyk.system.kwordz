package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import org.cyk.system.kwordz.model.song.Song;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class SongOverview extends AbstractSongInfos implements Serializable {

	private static final long serialVersionUID = 5367546224286620639L;

	private Boolean infosAtBottom=Boolean.TRUE;
	
	public SongOverview(Song song,Boolean infosAtBottom) {
		super(song);
		this.infosAtBottom = infosAtBottom;
	}
}

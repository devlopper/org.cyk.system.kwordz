package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.song.Song;

@Getter @Setter
public class SongInfosList extends AbstractInfosList<Song, SongInfos> implements Serializable {

	private static final long serialVersionUID = -6356004259181731404L;
	
	public SongInfosList(String name, Collection<Song> list,Boolean infosAtBottom) {
		super(list);
		columns = Boolean.TRUE.equals(infosAtBottom)?5:1;
		this.name = name;
	}

	
}

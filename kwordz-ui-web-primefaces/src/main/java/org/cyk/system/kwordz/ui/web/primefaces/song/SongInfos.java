package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import org.cyk.system.kwordz.model.song.Song;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class SongInfos extends AbstractInfos<Song> implements Serializable {

	private static final long serialVersionUID = 5367546224286620639L;

	private Boolean showSong=Boolean.TRUE,showAlbum=Boolean.TRUE,showSinger=Boolean.TRUE;
	
	public Song getSong(){
		return entity;
	}

}

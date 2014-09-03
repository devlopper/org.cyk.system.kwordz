package org.cyk.system.kwordz.persistence.api.song;

import java.util.Collection;

import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.persistence.api.TypedDao;

public interface SongDao extends TypedDao<Song> {

	Collection<Song> readByAlbum(Album album);
	
	Long countByAlbum(Album album);


}

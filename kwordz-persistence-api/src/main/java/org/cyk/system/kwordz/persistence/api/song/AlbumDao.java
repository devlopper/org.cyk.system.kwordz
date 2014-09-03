package org.cyk.system.kwordz.persistence.api.song;

import java.util.Collection;

import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AlbumDao extends TypedDao<Album> {

	Collection<Album> readBySinger(Singer singer);
	
	Long countBySinger(Singer singer);


}

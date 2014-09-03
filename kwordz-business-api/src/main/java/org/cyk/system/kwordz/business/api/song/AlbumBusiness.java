package org.cyk.system.kwordz.business.api.song;

import java.util.Collection;

import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.root.business.api.TypedBusiness;

public interface AlbumBusiness extends TypedBusiness<Album> {
    
	void loadSongs(Album album);
	
	void findHierarchy(Album album);
	
	Collection<Album> findBySinger(Singer singer);
} 

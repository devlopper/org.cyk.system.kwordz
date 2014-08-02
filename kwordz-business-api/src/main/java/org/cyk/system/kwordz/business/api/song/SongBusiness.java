package org.cyk.system.kwordz.business.api.song;

import java.util.Collection;

import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.business.api.TypedBusiness;

public interface SongBusiness extends TypedBusiness<Song> {
    
	Collection<Song> findRelated(Song song);
	 
} 

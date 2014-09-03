package org.cyk.system.kwordz.business.api.song;

import java.net.URI;
import java.util.Collection;

import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness.ThumnailSize;
import org.cyk.system.root.model.party.Party;

public interface SongBusiness extends TypedBusiness<Song> {
    
	void transpose(Song song,Note tone);
	
	Collection<Song> findRelated(Song song);
	
	Collection<Song> findWelcome();
	
	Collection<Song> find(Party party);
	
	URI findMediaThumbnailUri(Song song,ThumnailSize size);
    
    URI findMediaEmbeddedUri(Song song);
    
    Collection<Song> findByAlbum(Album album);
} 

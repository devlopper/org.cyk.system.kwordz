package org.cyk.system.kwordz.business.impl.song;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.persistence.api.song.SongDao;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;

public class SongBusinessImpl extends AbstractTypedBusinessService<Song, SongDao> implements SongBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private NoteBusiness noteBusiness;
	@Inject private LyricsBusiness lyricsBusiness;
	
	@Inject
	public SongBusinessImpl(SongDao dao) { 
		super(dao);    
	}
	
	@Override
	public void transpose(Song song, Note tone) {
		if(tone==null || noteBusiness.equals(song.getTone(), tone, Boolean.FALSE))
			return;
		lyricsBusiness.transpose(song.getLyrics(), noteBusiness.distance(song.getTone(), tone));	
		song.setTone(tone);
	}
  
	@Override
	public Collection<Song> findRelated(Song song) {
		// TODO Auto-generated method stub
		return find().all();
	}
	
	
}

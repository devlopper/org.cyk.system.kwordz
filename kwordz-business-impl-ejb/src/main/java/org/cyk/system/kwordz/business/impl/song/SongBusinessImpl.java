package org.cyk.system.kwordz.business.impl.song;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.persistence.api.song.SongDao;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;

public class SongBusinessImpl extends AbstractTypedBusinessService<Song, SongDao> implements SongBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public SongBusinessImpl(SongDao dao) { 
		super(dao);    
	}
  
	@Override
	public Collection<Song> findRelated(Song song) {
		// TODO Auto-generated method stub
		return find().all();
	}
	
	
}

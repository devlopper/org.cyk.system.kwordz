package org.cyk.system.kwordz.business.impl.song;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.song.AlbumBusiness;
import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.persistence.api.song.AlbumDao;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;

public class AlbumBusinessImpl extends AbstractTypedBusinessService<Album, AlbumDao> implements AlbumBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private SongBusiness songBusiness;
	
	@Inject
	public AlbumBusinessImpl(AlbumDao dao) { 
		super(dao);    
	}
	
	@Override
	public void loadSongs(Album album) {
		// TODO Auto-generated method stub
		album.setSongs(songBusiness.find().all());
	}
	
	@Override
	public void findHierarchy(Album album) {
		loadSongs(album);
	}
	
}

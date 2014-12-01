package org.cyk.system.kwordz.business.impl.song;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.song.AlbumBusiness;
import org.cyk.system.kwordz.business.api.song.SingerBusiness;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.SingerSearchCriteria;
import org.cyk.system.kwordz.persistence.api.song.SingerDao;
import org.cyk.system.root.business.impl.party.AbstractPartyBusinessImpl;

public class SingerBusinessImpl extends AbstractPartyBusinessImpl<Singer, SingerDao,SingerSearchCriteria> implements SingerBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private AlbumBusiness albumBusiness;
	
	@Inject
	public SingerBusinessImpl(SingerDao dao) { 
		super(dao);    
	}
	
	@Override
	public void loadAlbums(Singer singer) {
		singer.setAlbums(albumBusiness.findBySinger(singer));
	}

	@Override
	public void findHierarchy(Singer singer) {
		loadAlbums(singer);
		for(Album album : singer.getAlbums())
			albumBusiness.findHierarchy(album);
	}
	
	@Override
	public Collection<Singer> findSuggestions(String name) {
		return dao.readWhereNameContains(name);
	}
	
}

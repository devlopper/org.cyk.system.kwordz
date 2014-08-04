package org.cyk.system.kwordz.business.api.song;

import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.root.business.api.party.AbstractPartyBusiness;

public interface SingerBusiness extends AbstractPartyBusiness<Singer> {
    
	void findHierarchy(Singer singer);
	
	void loadAlbums(Singer singer);
} 

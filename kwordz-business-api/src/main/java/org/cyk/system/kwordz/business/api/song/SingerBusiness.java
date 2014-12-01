package org.cyk.system.kwordz.business.api.song;

import java.util.Collection;

import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.SingerSearchCriteria;
import org.cyk.system.root.business.api.party.AbstractPartyBusiness;

public interface SingerBusiness extends AbstractPartyBusiness<Singer,SingerSearchCriteria> {
    
	void findHierarchy(Singer singer);
	
	void loadAlbums(Singer singer);

	Collection<Singer> findSuggestions(String name);
} 

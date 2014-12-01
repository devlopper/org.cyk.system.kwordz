package org.cyk.system.kwordz.persistence.api.song;

import java.util.Collection;

import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.SingerSearchCriteria;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;

public interface SingerDao extends AbstractPartyDao<Singer,SingerSearchCriteria> {

	Collection<Singer> readWhereNameContains(String name);

	Long countWhereNameContains(String name);


}

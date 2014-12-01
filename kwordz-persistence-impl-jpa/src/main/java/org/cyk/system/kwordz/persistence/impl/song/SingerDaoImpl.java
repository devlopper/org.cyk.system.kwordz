package org.cyk.system.kwordz.persistence.impl.song;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.SingerSearchCriteria;
import org.cyk.system.kwordz.persistence.api.song.SingerDao;
import org.cyk.system.root.persistence.impl.party.AbstractPartyDaoImpl;

public class SingerDaoImpl extends AbstractPartyDaoImpl<Singer,SingerSearchCriteria> implements SingerDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	 
    private String readWhereNameContains,countWhereNameContains;
     
    @Override 
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readWhereNameContains, "SELECT singer FROM Singer singer WHERE LOCATE(:name,singer.name) > 0");
        
    }
     
    @Override
    public Collection<Singer> readWhereNameContains(String name) {
        return namedQuery(readWhereNameContains).parameter("name", name)
                .resultMany();
    }

    @Override
    public Long countWhereNameContains(String name) {
        return countNamedQuery(countWhereNameContains).parameter("name", name)
                .resultOne();
    }

    

}
 
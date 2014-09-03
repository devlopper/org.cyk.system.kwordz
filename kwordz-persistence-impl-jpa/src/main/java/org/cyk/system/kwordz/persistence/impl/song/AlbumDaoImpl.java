package org.cyk.system.kwordz.persistence.impl.song;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.persistence.api.song.AlbumDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.utility.common.computation.ArithmeticOperator;

public class AlbumDaoImpl extends AbstractTypedDao<Album> implements AlbumDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readBySinger,countBySinger;
    	
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readBySinger, _select().where("singer",ArithmeticOperator.EQ));
    }
     
    @Override
    public Collection<Album> readBySinger(Singer singer) {
        return namedQuery(readBySinger).parameter("singer", singer)
                .resultMany();
    }

    @Override
    public Long countBySinger(Singer singer) {
        return countNamedQuery(countBySinger).parameter("singer", singer)
                .resultOne();
    }

}
 
package org.cyk.system.kwordz.persistence.impl.song;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.persistence.api.song.SongDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.utility.common.computation.ArithmeticOperator;

public class SongDaoImpl extends AbstractTypedDao<Song> implements SongDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
 
    private String readByAlbum,countByAlbum;
     
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByAlbum, _select().where("album",ArithmeticOperator.EQ));
    }
     
    @Override
    public Collection<Song> readByAlbum(Album album) {
        return namedQuery(readByAlbum).parameter("album", album)
                .resultMany();
    }

    @Override
    public Long countByAlbum(Album album) {
        return countNamedQuery(countByAlbum).parameter("album", album)
                .resultOne();
    }

    

}
 
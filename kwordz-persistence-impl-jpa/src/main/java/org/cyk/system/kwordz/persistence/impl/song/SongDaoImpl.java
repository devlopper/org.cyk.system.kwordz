package org.cyk.system.kwordz.persistence.impl.song;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.model.song.SongSearchCriteria;
import org.cyk.system.kwordz.persistence.api.song.SongDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.utility.common.computation.ArithmeticOperator;

public class SongDaoImpl extends AbstractTypedDao<Song> implements SongDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
 
	private static final String ORDER_BY_FORMAT = "ORDER BY %s";
	
	//private static final String READ_BY_NO_CRITERIA_FORMAT = "SELECT song FROM Song song "+ORDER_BY_FORMAT;
	
	private static final String READ_BY_CRITERIA_FORMAT = "SELECT song FROM Song song WHERE "
    		+ "    (LENGTH(:songName) > 0   AND (LOCATE(LOWER(:songName),LOWER(song.name))                > 0))"
    		+ " OR (LENGTH(:albumName) > 0  AND (LOCATE(LOWER(:albumName),LOWER(song.album.name))         > 0))"
    		+ " OR (LENGTH(:singerName) > 0 AND (LOCATE(LOWER(:singerName),LOWER(song.album.singer.name)) > 0))";
	
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_FORMAT+" "+ORDER_BY_FORMAT;
	
    private String readByAlbum,countByAlbum,readByCriteria,countByCriteria,readByCriteriaSongAscendingOrder,readByCriteriaSongDescendingOrder,
    	readByCriteriaAlbumAscendingOrder,readByCriteriaAlbumDescendingOrder,readByCriteriaSingerAscendingOrder,readByCriteriaSingerDescendingOrder;
     
    @Override
    protected void namedQueriesInitialisation() {
        
    	super.namedQueriesInitialisation();
        registerNamedQuery(readByAlbum, _select().where("album",ArithmeticOperator.EQ));
        registerNamedQuery(readByCriteria,READ_BY_CRITERIA_FORMAT);
        
        registerNamedQuery(readByCriteriaSongAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "song.name ASC") );
        registerNamedQuery(readByCriteriaSongDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "song.name DESC") );
        
        registerNamedQuery(readByCriteriaAlbumAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "song.album.name ASC") );
        registerNamedQuery(readByCriteriaAlbumDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "song.album.name DESC") );
        
        registerNamedQuery(readByCriteriaSingerAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "song.album.singer.name ASC") );
        registerNamedQuery(readByCriteriaSingerDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "song.album.singer.name DESC") );
    }
    
    @Override
    protected String readAllOrderByString() {
    	return String.format(ORDER_BY_FORMAT, QueryStringBuilder.VAR+".name");
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

	@Override
	public Collection<Song> readByCriteria(SongSearchCriteria searchCriteria) {
		String queryName = null;
		if(searchCriteria.getSongNameSearchCriteria().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getSongNameSearchCriteria().getAscendingOrdered())?
					readByCriteriaSongAscendingOrder:readByCriteriaSongDescendingOrder;
		}else if(searchCriteria.getAlbumNameSearchCriteria().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getAlbumNameSearchCriteria().getAscendingOrdered())?
					readByCriteriaAlbumAscendingOrder:readByCriteriaAlbumDescendingOrder;
		}else if(searchCriteria.getSingerNameSearchCriteria().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getSingerNameSearchCriteria().getAscendingOrdered())?
					readByCriteriaSingerAscendingOrder:readByCriteriaSingerDescendingOrder;
		}else
			queryName = readByCriteriaSongAscendingOrder;
		return namedQuery(queryName)
				.parameter("songName",searchCriteria.getSongNameSearchCriteria().getPreparedValue())
				.parameter("albumName",searchCriteria.getAlbumNameSearchCriteria().getPreparedValue())
				.parameter("singerName",searchCriteria.getSingerNameSearchCriteria().getPreparedValue())
                .resultMany();
	}

	@Override
	public Long countByCriteria(SongSearchCriteria searchCriteria) {
		return countNamedQuery(countByCriteria)
				.parameter("songName",searchCriteria.getSongNameSearchCriteria().getPreparedValue())
				.parameter("albumName",searchCriteria.getAlbumNameSearchCriteria().getPreparedValue())
				.parameter("singerName",searchCriteria.getSingerNameSearchCriteria().getPreparedValue())
                .resultOne();
	}

    

}
 
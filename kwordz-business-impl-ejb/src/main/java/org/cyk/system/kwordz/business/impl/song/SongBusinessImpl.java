package org.cyk.system.kwordz.business.impl.song;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.model.song.SongSearchCriteria;
import org.cyk.system.kwordz.persistence.api.song.SongDao;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness.ThumnailSize;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.party.Party;

public class SongBusinessImpl extends AbstractTypedBusinessService<Song, SongDao> implements SongBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	//private static final URI EMBEDDED_VIDEO_URI_NULL = URI.create("");
	
	@Inject private NoteBusiness noteBusiness;
	@Inject private LyricsBusiness lyricsBusiness;
	@Inject private FileBusiness fileBusiness;
	
	@Inject
	public SongBusinessImpl(SongDao dao) { 
		super(dao);    
	}
	
	@Override
	public void transpose(Song song, Note tone) {
		if(tone==null || noteBusiness.equals(song.getTone(), tone, Boolean.FALSE))
			return;
		lyricsBusiness.transpose(song.getLyrics(), noteBusiness.distance(song.getTone(), tone));	
		song.setTone(tone);
	}
  
	@Override
	public Collection<Song> findRelated(Song song) {
		// TODO Auto-generated method stub
		return find().all();
	}

	@Override
	public Collection<Song> findWelcome() {
		// TODO Auto-generated method stub
		return find().all();
	}

	@Override
	public Collection<Song> find(Party party) {
		// TODO Auto-generated method stub
		if(party==null){
			return find().all();
		}else{
			return find().all();
		}
	}

	@Override
	public URI findMediaThumbnailUri(Song song, ThumnailSize size) {
		if(song.getMedia()==null)
			return null;// TODO Default thumbnail
		return fileBusiness.findThumbnailUri(song.getMedia(), size);
	}

	@Override
	public URI findMediaEmbeddedUri(Song song) {
		if(song.getMedia()==null)
			return null;// TODO Embedded
		return fileBusiness.findEmbeddedUri(song.getMedia());
	}

	@Override
	public Collection<Song> findByAlbum(Album album) {
		return dao.readByAlbum(album);
	}

	@Override
	public Collection<Song> findByCriteria(SongSearchCriteria searchCriteria) {
		applyDataReadConfigToDao(getDataReadConfig());
		if(criteriaFound(searchCriteria))
			return dao.readByCriteria(searchCriteria);
		else
			return dao.readAll();
	}

	@Override
	public Long countByCriteria(SongSearchCriteria searchCriteria) {
		if(criteriaFound(searchCriteria))
			return dao.countByCriteria(searchCriteria);
		else
			return dao.countAll();
	}
	
	private Boolean criteriaFound(SongSearchCriteria searchCriteria){
		return StringUtils.isNotBlank(searchCriteria.getSingerNameSearchCriteria().getValue())
				&& StringUtils.isNotBlank(searchCriteria.getAlbumNameSearchCriteria().getValue())
				&& StringUtils.isNotBlank(searchCriteria.getSongNameSearchCriteria().getValue());
	}
}

package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.business.api.file.MediaBusiness.ThumnailSize;

@Getter @Setter
public class SongInfosList extends AbstractInfosList<Song, SongInfos> implements Serializable {

	private static final long serialVersionUID = -6356004259181731404L;
	
	public SongInfosList(String name, Collection<Song> list,Boolean infosAtBottom,SongBusiness songBusiness) {
		super(list);
		columns = Boolean.TRUE.equals(infosAtBottom)?5:1;
		this.name = name;
		for(SongInfos songInfos : this.list){
			songInfos.setInfosAtBottom(infosAtBottom);
			songInfos.setThumbnailUri(songBusiness.findMediaThumbnailUri(songInfos.getEntity(), ThumnailSize._1));
		}
	}
	
	public SongInfosList(Collection<Song> list,SongBusiness songBusiness) {
		this(null,list,Boolean.TRUE,songBusiness);
	}

	
}

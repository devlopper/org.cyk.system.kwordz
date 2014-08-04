package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.song.Album;

@Getter @Setter
public class AlbumInfosList extends AbstractInfosList<Album, AlbumInfos> implements Serializable {

	private static final long serialVersionUID = -6356004259181731404L;

	public AlbumInfosList(Collection<Album> list) {
		super(list);
		columns = 5;
	}

}

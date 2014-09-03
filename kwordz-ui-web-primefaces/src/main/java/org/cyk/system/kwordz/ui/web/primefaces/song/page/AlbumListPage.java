package org.cyk.system.kwordz.ui.web.primefaces.song.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.system.kwordz.business.api.song.AlbumBusiness;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.ui.web.primefaces.song.AlbumInfos;
import org.cyk.system.kwordz.ui.web.primefaces.song.AlbumInfosList;

@Named @ViewScoped
public class AlbumListPage extends AbstractListPage<Album,AlbumInfos,AlbumInfosList> implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private AlbumBusiness albumBusiness;
	
	@Override
	protected AlbumInfosList createInfosList() {
		return new AlbumInfosList(albumBusiness.findAll());
	}
	
}

package org.cyk.system.kwordz.ui.web.primefaces.song.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.song.AlbumBusiness;
import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.ui.web.primefaces.song.SongInfosList;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named @ViewScoped
public class AlbumConsultPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private AlbumBusiness albumBusiness;
	@Inject private SongBusiness songBusiness;
	
	@Getter private Album album;
	@Getter private SongInfosList songInfosList;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		album = identifiableFromRequestParameter(Album.class);
		albumBusiness.loadSongs(album); 
		songInfosList = new SongInfosList(null,album.getSongs(),Boolean.TRUE,songBusiness);
		
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}
}

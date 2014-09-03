package org.cyk.system.kwordz.ui.web.primefaces.song.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.song.SingerBusiness;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.ui.web.primefaces.song.AlbumInfosList;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Named @ViewScoped
public class SingerConsultPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private SingerBusiness singerBusiness;
	
	@Getter private Singer singer;
	@Getter private AlbumInfosList albumInfosList;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		singer = identifiableFromRequestParameter(Singer.class);
		singerBusiness.loadAlbums(singer);
		albumInfosList = new AlbumInfosList(singer.getAlbums());
		
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}
	
}

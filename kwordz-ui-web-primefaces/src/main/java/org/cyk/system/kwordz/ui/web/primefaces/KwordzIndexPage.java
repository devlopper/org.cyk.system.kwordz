package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.ui.web.primefaces.song.SongInfosList;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Named @RequestScoped
public class KwordzIndexPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 2069491746669275213L;

	@Inject private SongBusiness songBusiness;
	
	@Getter private List<SongInfosList> songInfosLists = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		songInfosLists.add(new SongInfosList(songBusiness.find().all()) );
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}
}

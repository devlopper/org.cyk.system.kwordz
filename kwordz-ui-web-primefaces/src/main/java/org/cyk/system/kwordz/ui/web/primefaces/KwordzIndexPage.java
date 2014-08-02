package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.ui.web.primefaces.song.SongOverviewList;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Named @RequestScoped
public class KwordzIndexPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 2069491746669275213L;

	@Inject private SongBusiness songBusiness;
	
	@Getter private List<SongOverviewList> songOverviewLists = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		songOverviewLists.add(new SongOverviewList("L1", songBusiness.find().all(),5,Boolean.TRUE) );
		songOverviewLists.add(new SongOverviewList("L2", songBusiness.find().all(),5,Boolean.TRUE) );
	}
	
}

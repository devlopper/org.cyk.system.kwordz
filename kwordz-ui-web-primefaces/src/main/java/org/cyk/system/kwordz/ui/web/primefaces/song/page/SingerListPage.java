package org.cyk.system.kwordz.ui.web.primefaces.song.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.system.kwordz.business.api.song.SingerBusiness;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.ui.web.primefaces.song.SingerInfos;
import org.cyk.system.kwordz.ui.web.primefaces.song.SingerInfosList;

@Named @ViewScoped
public class SingerListPage extends AbstractListPage<Singer,SingerInfos,SingerInfosList> implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private SingerBusiness singerBusiness;
	
	@Override
	protected SingerInfosList createInfosList() {
		return new SingerInfosList(singerBusiness.findAll());
	}
	
}

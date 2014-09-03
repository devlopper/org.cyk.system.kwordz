package org.cyk.system.kwordz.ui.web.primefaces.song.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.ui.web.primefaces.song.SongInfos;
import org.cyk.system.kwordz.ui.web.primefaces.song.SongInfosList;

@Named @ViewScoped
public class SongListPage extends AbstractListPage<Song,SongInfos,SongInfosList> implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private SongBusiness songBusiness;
	
	@Override
	protected SongInfosList createInfosList() {
		return new SongInfosList(songBusiness.findAll(),songBusiness);
	}
	
}

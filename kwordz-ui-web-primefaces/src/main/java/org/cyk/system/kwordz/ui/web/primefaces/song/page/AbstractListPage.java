package org.cyk.system.kwordz.ui.web.primefaces.song.page;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.kwordz.ui.web.primefaces.song.AbstractInfos;
import org.cyk.system.kwordz.ui.web.primefaces.song.AbstractInfosList;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

public abstract class AbstractListPage<ENTITY,INFOS extends AbstractInfos<ENTITY>,INFOS_LIST extends AbstractInfosList<ENTITY, INFOS>> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Getter protected INFOS_LIST infosList;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		infosList = createInfosList();	
	}
	
	protected abstract INFOS_LIST createInfosList();
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}
	
}

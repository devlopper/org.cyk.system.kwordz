package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.song.SingerBusiness;
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.music.MusicKind;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.ui.web.primefaces.song.SingerFormData;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.PrimefacesDefaultDesktopLayoutManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named @Deployment(initialisationType=InitialisationType.EAGER,order=1)
public class KwordzWebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3659075703567481942L;

	@Inject private KwordzBusinessLayer kwordzBusinessLayer;
	@Inject private GenericBusiness genericBusiness;
	@Inject private SingerBusiness singerBusiness;
	
	@Inject private UIManager uiManager;
	@Inject private WebManager webManager;
	@Inject private PrimefacesDefaultDesktopLayoutManager primefacesDefaultDesktopLayoutManager;
	
	@Getter
	private BusinessEntityInfos singerBusinessEntityInfos,albumBusinessEntityInfos,songBusinessEntityInfos;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		uiManager.setBusinessSystemName(kwordzBusinessLayer.getSystemName());
		webManager.setDecoratedTemplateInclude("/org.cyk.ui.web.primefaces.kwordz/include/template.xhtml");
		primefacesDefaultDesktopLayoutManager.setNorthInclude("/org.cyk.ui.web.primefaces.kwordz/include/layout/default/north.xhtml");
		primefacesDefaultDesktopLayoutManager.setWestInclude("/org.cyk.ui.web.primefaces.kwordz/include/layout/default/west.xhtml");
		
		singerBusinessEntityInfos = uiManager.businessEntityInfos(Singer.class);
		albumBusinessEntityInfos = uiManager.businessEntityInfos(Album.class);
		songBusinessEntityInfos = uiManager.businessEntityInfos(Song.class);
		
		uiManager.registerFormData(Singer.class, SingerFormData.class);
		//uiManager.registerFormData(Song.class, SongFormData.class);
		
		
	}
	
	/**/
	
	public List<Singer> singerSuggestions(String name){
		return new ArrayList<>(singerBusiness.findSuggestions(name));
	}
	
	@Produces @Named @ApplicationScoped
	public List<SelectItem> getMusicKindItems(){
		List<SelectItem> list = new ArrayList<>();
		for(AbstractIdentifiable musicKind : genericBusiness.use(MusicKind.class).find().all())
			list.add(new SelectItem(musicKind, ((MusicKind)musicKind).getName()));
		return list;
	}

}

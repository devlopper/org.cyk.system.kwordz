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
import org.cyk.system.kwordz.model.music.MusicKind;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Named @Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=1)
public class KwordzWebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3659075703567481942L;

	@Inject private GenericBusiness genericBusiness;
	@Inject private UIManager uiManager;
	@Inject private SingerBusiness singerBusiness;
		
	@Getter
	private BusinessEntityInfos singerBusinessEntityInfos,albumBusinessEntityInfos,songBusinessEntityInfos;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		
		singerBusinessEntityInfos = uiManager.businessEntityInfos(Singer.class);
		albumBusinessEntityInfos = uiManager.businessEntityInfos(Album.class);
		songBusinessEntityInfos = uiManager.businessEntityInfos(Song.class);
		
	}
	
	
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

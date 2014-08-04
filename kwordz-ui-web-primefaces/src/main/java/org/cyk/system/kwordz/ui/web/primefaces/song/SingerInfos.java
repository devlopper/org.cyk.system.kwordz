package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.song.SingerBusiness;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.WebHierarchyNode;
import org.cyk.ui.web.primefaces.PrimefacesTree;
import org.cyk.utility.common.AbstractMethod;

@Getter 
public class SingerInfos implements Serializable {

	private static final long serialVersionUID = 3119663959677343932L;

	private Singer singer;
	
	private PrimefacesTree albumsTree;

	public SingerInfos(Singer singer,SingerBusiness singerBusiness) {
		super();
		this.singer = singer;
		singerBusiness.findHierarchy(singer);
		WebHierarchyNode hierarchyNode = new WebHierarchyNode(null);
		hierarchyNode.setLabel(UIManager.getInstance().text("word.albums"));
		albumsTree = new PrimefacesTree(hierarchyNode);
		albumsTree.setChildrenMethod(new AbstractMethod<Collection<Object>, Object>() {
			private static final long serialVersionUID = -6016777253522636220L;
			@SuppressWarnings("rawtypes")
			@Override
			protected Collection __execute__(Object parameter) {
				if(parameter instanceof Singer){
					return ((Singer)parameter).getAlbums();
				}else if(parameter instanceof Album){
					return ((Album)parameter).getSongs();
				}
				return null;
			}
		});
		for(Album album : singer.getAlbums())
			albumsTree.populate(album);	
	}
	
	
}

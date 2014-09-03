package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.kwordz.model.song.Singer;

@Getter 
public class SingerInfos extends AbstractInfos<Singer> implements Serializable {

	private static final long serialVersionUID = 3119663959677343932L;

	/*
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
	}*/
	
	
}

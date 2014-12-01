package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.song.SongSearchCriteria;
import org.cyk.system.kwordz.ui.web.primefaces.song.SongInfos;
import org.cyk.system.kwordz.ui.web.primefaces.song.SongInfosList;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@Named @RequestScoped
public class LibraryPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 2069491746669275213L;

	@Inject private SongBusiness songBusiness;
	
	@Getter private SongInfosList songInfosList;
	private LazyDataModel<SongInfos> dataModel;
	private Integer songResultCount=2;
	@Getter private UICommandable searchCommandable;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contentTitle = text("library");
		songBusiness.getDataReadConfig().setMaximumResultCount(2l);
		
		searchCommandable =  UIProvider.getInstance().createCommandable(null, "command.search", IconType.ACTION_SEARCH, null, null);
		
		((Commandable)searchCommandable).setShowLabel(Boolean.FALSE);
		((Commandable)searchCommandable).getButton().setType("button");
		((Commandable)searchCommandable).getButton().setOnclick("PF('songDataTable').filter();");
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}
	
	public LazyDataModel<SongInfos> getDataModel() {
		if(dataModel==null){
			dataModel = new LazyDataModel<SongInfos>() {
				private static final long serialVersionUID = -5029807106028522292L;
				@Override
				public List<SongInfos> load(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String, Object> filters) {
					songBusiness.getDataReadConfig().setFirstResultIndex((long) first);	
					SongSearchCriteria searchCriteria = new SongSearchCriteria((String) filters.get("globalFilter"));
					
					searchCriteria.getSongNameSearchCriteria().setAscendingOrdered("song.name".equals(sortField)?(SortOrder.ASCENDING.equals(sortOrder)?Boolean.TRUE:Boolean.FALSE):null);
					searchCriteria.getAlbumNameSearchCriteria().setAscendingOrdered("song.album.name".equals(sortField)?(SortOrder.ASCENDING.equals(sortOrder)?Boolean.TRUE:Boolean.FALSE):null);
					searchCriteria.getSingerNameSearchCriteria().setAscendingOrdered("song.album.singer.name".equals(sortField)?(SortOrder.ASCENDING.equals(sortOrder)?Boolean.TRUE:Boolean.FALSE):null);
					
					songInfosList = new SongInfosList(songBusiness.findByCriteria(searchCriteria),songBusiness);
					
					for(SongInfos songInfos : songInfosList.getList()){
						songInfos.setInfosAtBottom(Boolean.FALSE);
						songInfos.setShowSinger(Boolean.FALSE);
						songInfos.setShowAlbum(Boolean.FALSE);
					}
					songResultCount = songBusiness.countByCriteria(searchCriteria).intValue();
					return songInfosList.getList();
				}
				
				@Override
				public int getRowCount() {
					return songResultCount;
				}
			};
		}
		return dataModel;
	}
	
	public Integer getMaximumSongResultCount() {
		return songBusiness.getDataReadConfig().getMaximumResultCount().intValue();
	}
	
}

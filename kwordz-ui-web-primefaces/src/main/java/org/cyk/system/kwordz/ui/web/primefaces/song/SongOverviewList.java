package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.song.Song;

@Getter @Setter
public class SongOverviewList implements Serializable {

	private static final long serialVersionUID = -6356004259181731404L;
	
	private String name;
	private Integer columns;
	private List<SongOverview> list = new ArrayList<>();
	
	public SongOverviewList(String name, Collection<Song> list,Integer columns,Boolean infosAtBottom) {
		super();
		this.name = name;
		this.columns = columns==null?1:columns;
		if(list!=null)
			for(Song song : list)
				this.list.add(new SongOverview(song,infosAtBottom));
	}
	
	

}

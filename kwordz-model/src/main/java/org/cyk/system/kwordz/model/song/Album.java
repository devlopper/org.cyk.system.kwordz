package org.cyk.system.kwordz.model.song;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,uiIconName="music-disc")
public class Album extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne private Singer singer;
	private String name;

	@Transient private Collection<Song> songs;
	
	public Album(Singer singer,String name) {
		super();
		this.singer = singer;
		this.name = name;
	}
	
	@Override
	public String getUiString() {
		return name;
	}
}

package org.cyk.system.kwordz.model.song;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity @NoArgsConstructor
public class Album extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne private Singer singer;
	private String name;

	public Album(Singer singer,String name) {
		super();
		this.singer = singer;
		this.name = name;
	}
	
	
}

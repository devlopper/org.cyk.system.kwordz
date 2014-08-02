package org.cyk.system.kwordz.model.song;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.party.Party;

@Getter @Setter @Entity @NoArgsConstructor
public class Singer extends Party implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Singer(String name){
		this.name = name;
	}
}

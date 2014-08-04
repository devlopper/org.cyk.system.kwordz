package org.cyk.system.kwordz.model.song;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,uiIconName="person")
public class Singer extends Party implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Transient private Collection<Album> albums;
	
	public Singer(String name){
		this.name = name;
	}
}

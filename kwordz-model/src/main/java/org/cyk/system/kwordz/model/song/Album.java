package org.cyk.system.kwordz.model.song;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,uiIconName="music-disc")
public class Album extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Input 
	@InputChoice
	@InputOneChoice
	@InputOneCombo
	@NotNull
	@ManyToOne 
	private Singer singer;
	
	@Input 
	@InputText
	@NotNull
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
	
	@Override
	public String toString() {
		return name;
	}
}

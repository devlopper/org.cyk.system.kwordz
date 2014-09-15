package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity @NoArgsConstructor @AllArgsConstructor
public class Fragment extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(length=1024 * 4)
	private String text;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Chord chord;
	
	//private String seperator;
	
}

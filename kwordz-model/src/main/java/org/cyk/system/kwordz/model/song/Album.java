package org.cyk.system.kwordz.model.song;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @Entity @NoArgsConstructor
public class Album extends AbstractEnumeration implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne private Singer singer;
	
	
}

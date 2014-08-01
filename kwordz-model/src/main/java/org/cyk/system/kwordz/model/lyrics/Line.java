package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class Line extends AbstractIdentifiable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Fragment> fragments = new LinkedList<Fragment>();
	
}

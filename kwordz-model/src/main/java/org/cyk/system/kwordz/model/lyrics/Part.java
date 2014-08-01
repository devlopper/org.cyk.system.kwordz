package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class Part extends AbstractIdentifiable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public enum Type {
		INTRO,VERSE,PRE_CHORUS,CHORUS,BRIDGE,INSTRUMENTAL,OUTTRO,SOLO;
	}
	
	@Enumerated
	private Type type;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Line> lines = new LinkedList<Line>();
	
	private String repeatTimes;

}

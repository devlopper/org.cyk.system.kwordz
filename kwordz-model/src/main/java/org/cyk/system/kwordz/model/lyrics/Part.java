package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Part extends AbstractIdentifiable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public enum Type {
		INTRO,VERSE,PRE_CHORUS,CHORUS,BRIDGE,INSTRUMENTAL,OUTTRO,SOLO;
	}
	
	private Type type;
	private List<Line> lines = new LinkedList<Line>();
	private String repeatTimes;

	//TODO All following must be moved to business 

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Line line : lines)
			s.append(line);
		return s.toString();
	}
	
}

package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Component implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public enum Type {
		INTRO,VERSE,PRE_CHORUS,CHORUS,BRIDGE,INSTRUMENTAL,OUTTRO,SOLO;
	}
	
	private Type type;
	private List<Line> lines = new LinkedList<Line>();
	private String repeatTimes;

	public Component(String text) {
		super();
		this.lines.add(new Line(text));
	}
	public Component(Type type, List<Line> lines) {
		super();
		this.type = type;
		this.lines = lines;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Line line : lines)
			s.append(line);
		return s.toString();
	}
	
}

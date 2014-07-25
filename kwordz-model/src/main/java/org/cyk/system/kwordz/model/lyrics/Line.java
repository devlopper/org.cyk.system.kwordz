package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Line extends AbstractIdentifiable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<Fragment> fragments = new LinkedList<Fragment>();
	private Boolean merged = Boolean.TRUE;//TODO what is the use of this property ?
	
	//TODO All following must be moved to business 
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Fragment fragment : fragments)
			s.append(fragment);
		return s.toString()+"\r\n";
	}
	
}

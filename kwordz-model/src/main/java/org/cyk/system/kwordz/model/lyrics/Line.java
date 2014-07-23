package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Line implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<Fragment> fragments = new LinkedList<Fragment>();
	private boolean merged = true;
	
	public Line() {}
	
	public Line(List<Fragment> fragments, boolean merged) {
		super();
		this.fragments = fragments;
		this.merged = merged;
	}
	public Line(String text) {
		fragments.add(new Fragment(text));
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Fragment fragment : fragments)
			s.append(fragment);
		return s.toString()+"\r\n";
	}
	
}

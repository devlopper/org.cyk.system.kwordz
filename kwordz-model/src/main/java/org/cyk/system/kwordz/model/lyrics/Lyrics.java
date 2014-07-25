package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Lyrics extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Part> parts = new LinkedList<Part>();
	
	//TODO All following must be moved to business 
	
	/**/
	/*
	public void transpose(float interval){
		for(Fragment fragment : getFragments())
			fragment.transpose(interval);
	}
	*/
	/**/
	
	
	//public List<Fragment> getFragments(){
		/*
		if(fragments==null){
			fragments = new LinkedList<Fragment>();
			for(Component c : components)
				for(Line line : c.getLines())
					fragments.addAll(line.getFragments());
		}
		*/
		//return null;//fragments;
	//}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Part part : parts)
			s.append(part);
		return s.toString().trim();
	}
	
}

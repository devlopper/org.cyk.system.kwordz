package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Lyrics implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Component> components = new LinkedList<Component>();
	
	private transient List<Fragment> fragments;
	
	/**/
	/*
	public void transpose(float interval){
		for(Fragment fragment : getFragments())
			fragment.transpose(interval);
	}
	*/
	/**/
	
	public Lyrics(String text) {
		super();
		this.components.add(new Component(text));
	}
	public Lyrics(List<Component> components) {
		super();
		this.components = components;
	}
	
	
	public List<Fragment> getFragments(){
		/*
		if(fragments==null){
			fragments = new LinkedList<Fragment>();
			for(Component c : components)
				for(Line line : c.getLines())
					fragments.addAll(line.getFragments());
		}
		*/
		return fragments;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Component component : components)
			s.append(component);
		return s.toString().trim();
	}
	
}

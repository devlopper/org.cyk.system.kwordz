package org.cyk.system.kwordz.model.song;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;

@Getter @Setter
public class AlbumSearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	private StringSearchCriteria nameSearchCriteria = new StringSearchCriteria();
	
	public AlbumSearchCriteria(){
		this(null);
	}
	
	public AlbumSearchCriteria(String name) {
		setStringSearchCriteria(nameSearchCriteria, name);
	}
	
}

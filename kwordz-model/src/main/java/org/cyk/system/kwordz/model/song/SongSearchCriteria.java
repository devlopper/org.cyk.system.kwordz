package org.cyk.system.kwordz.model.song;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;

@Getter @Setter
public class SongSearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	private StringSearchCriteria singerNameSearchCriteria = new StringSearchCriteria();
	private StringSearchCriteria albumNameSearchCriteria = new StringSearchCriteria();
	private StringSearchCriteria songNameSearchCriteria = new StringSearchCriteria();
	
	public SongSearchCriteria(){
		this(null);
	}
	
	public SongSearchCriteria(String name) {
		singerNameSearchCriteria.setValue(name);
		albumNameSearchCriteria.setValue(name);
		songNameSearchCriteria.setValue(name);
		
		criterias.add(singerNameSearchCriteria);
		criterias.add(albumNameSearchCriteria);
		criterias.add(songNameSearchCriteria);
	}
	
}

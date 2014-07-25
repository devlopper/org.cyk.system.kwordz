package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.music.AbstractStructureBusiness;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.kwordz.persistence.api.music.AbstractStructureDao;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;

public abstract class AbstractStructureBusinessImpl<STRUCTURE extends Structure,DAO extends AbstractStructureDao<STRUCTURE>> extends AbstractEnumerationBusinessImpl<STRUCTURE, DAO> implements AbstractStructureBusiness<STRUCTURE>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private NoteBusiness noteBusiness;
	 
	public AbstractStructureBusinessImpl(DAO dao) {  
		super(dao);   
	}  

	@Override
	public List<Note> generateSequence(STRUCTURE structure,Note base) {
		List<Note> notes = new ArrayList<Note>();
		Note next = new Note(base);
		notes.add(new Note(next));
		for(Integer interval : structure.getIntervals()){
			noteBusiness.transpose(next, interval);
			noteBusiness.useAlterartion(next, base.getAlteration());
			notes.add(new Note(next));
		}
		return notes;
	}
	
	
}

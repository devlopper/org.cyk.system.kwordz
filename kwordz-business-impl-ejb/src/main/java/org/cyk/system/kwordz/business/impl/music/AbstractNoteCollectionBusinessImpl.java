package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.AbstractNoteCollectionBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.music.AbstractStructureBusiness;
import org.cyk.system.kwordz.model.music.AbstractNoteCollection;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.kwordz.persistence.api.music.AbstractNoteCollectionDao;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;

public abstract class AbstractNoteCollectionBusinessImpl<STRUCTURE extends Structure ,COLLECTION extends AbstractNoteCollection<STRUCTURE>,DAO extends AbstractNoteCollectionDao<STRUCTURE,COLLECTION>,STRUCTURE_BUSINESS extends AbstractStructureBusiness<STRUCTURE>> extends AbstractTypedBusinessService<COLLECTION, DAO> implements AbstractNoteCollectionBusiness<STRUCTURE,COLLECTION>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	protected STRUCTURE_BUSINESS structureBusiness;
	@Inject protected NoteBusiness noteBusiness;
	
	public AbstractNoteCollectionBusinessImpl(DAO dao,STRUCTURE_BUSINESS structureBusiness) { 
		super(dao);   
		this.structureBusiness = structureBusiness;
	}
	
	@Override
	public void generateNotes(COLLECTION aCollection,STRUCTURE aStructure,Note base) {
		aCollection.setStructure(aStructure);
		aCollection.setNotes(structureBusiness.generateSequence(aStructure, base));
	}
	
	@Override
	public void transpose(COLLECTION aCollection, Integer distance) {
		if(aCollection.getNotes()==null)
			return;
		for(Note note : aCollection.getNotes())
			noteBusiness.transpose(note, distance);
	}
	
	@Override
	public Note findRoot(COLLECTION aCollection) {
		if(aCollection.getNotes()==null || aCollection.getNotes().isEmpty())
			return null;
		return aCollection.getNotes().get(0);
	}
	
}

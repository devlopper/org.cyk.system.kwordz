package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.AbstractNoteCollectionBusiness;
import org.cyk.system.kwordz.business.api.music.AbstractStructureBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.kwordz.model.music.AbstractNoteCollection;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.kwordz.persistence.api.music.AbstractNoteCollectionDao;

public abstract class AbstractNoteCollectionBusinessImpl<STRUCTURE extends Structure ,COLLECTION extends AbstractNoteCollection<STRUCTURE>,DAO extends AbstractNoteCollectionDao<STRUCTURE,COLLECTION>,
	STRUCTURE_BUSINESS extends AbstractStructureBusiness<STRUCTURE>,OPTIONS extends AbstractFormatOptions> 
	extends AbstractMusicBusinessImpl<COLLECTION, DAO,OPTIONS> implements AbstractNoteCollectionBusiness<STRUCTURE,COLLECTION,OPTIONS>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	protected STRUCTURE_BUSINESS structureBusiness;
	@Inject protected NoteBusiness noteBusiness;
	
	public AbstractNoteCollectionBusinessImpl(DAO dao,STRUCTURE_BUSINESS structureBusiness) { 
		super(dao);   
		this.structureBusiness = structureBusiness;
	}
	
	@Override
	public void transpose(COLLECTION aCollection, Integer distance) {
		if(aCollection.getNotes()==null)
			return;
		for(Note note : aCollection.getNotes())
			noteBusiness.transpose(note, distance);
	}
	
}

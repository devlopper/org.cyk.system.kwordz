package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.persistence.api.music.ChordStructureDao;

public class ChordStructureBusinessImpl extends AbstractStructureBusinessImpl<ChordStructure, ChordStructureDao> implements ChordStructureBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject 
	public ChordStructureBusinessImpl(ChordStructureDao dao) { 
		super(dao);    
	}

	@Override
	public ChordStructure findBySymbol(String symbol) {
		return dao.readBySymbol(symbol);
	}
	
} 

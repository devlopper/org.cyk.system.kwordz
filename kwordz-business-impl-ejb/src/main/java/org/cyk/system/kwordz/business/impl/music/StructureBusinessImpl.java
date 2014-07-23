package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.StructureBusiness;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.kwordz.persistence.api.music.StructureDao;

public class StructureBusinessImpl extends AbstractStructureBusinessImpl<Structure, StructureDao> implements StructureBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject 
	public StructureBusinessImpl(StructureDao dao) { 
		super(dao);   
	}

	
	
}

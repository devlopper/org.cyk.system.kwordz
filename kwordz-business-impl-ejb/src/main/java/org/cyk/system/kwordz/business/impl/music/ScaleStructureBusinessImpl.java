package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.model.music.ScaleStructure;
import org.cyk.system.kwordz.persistence.api.music.ScaleStructureDao;

public class ScaleStructureBusinessImpl extends AbstractStructureBusinessImpl<ScaleStructure, ScaleStructureDao> implements ScaleStructureBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject 
	public ScaleStructureBusinessImpl(ScaleStructureDao dao) { 
		super(dao);    
	}

	
	
} 

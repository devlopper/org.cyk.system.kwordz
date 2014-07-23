package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.ScaleBusiness;
import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.model.music.Scale;
import org.cyk.system.kwordz.model.music.ScaleStructure;
import org.cyk.system.kwordz.persistence.api.music.ScaleDao;

public class ScaleBusinessImpl extends AbstractNoteCollectionBusinessImpl<ScaleStructure,Scale, ScaleDao,ScaleStructureBusiness> implements ScaleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ScaleBusinessImpl(ScaleDao dao,ScaleStructureBusiness structureBusiness) { 
		super(dao,structureBusiness);   
	}

	
	
}

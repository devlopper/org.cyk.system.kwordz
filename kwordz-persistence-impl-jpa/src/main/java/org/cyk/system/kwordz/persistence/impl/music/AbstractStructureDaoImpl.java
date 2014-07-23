package org.cyk.system.kwordz.persistence.impl.music;

import java.io.Serializable;

import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.kwordz.persistence.api.music.AbstractStructureDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public abstract class AbstractStructureDaoImpl<STRUCTURE extends Structure> extends AbstractEnumerationDaoImpl<STRUCTURE> implements AbstractStructureDao<STRUCTURE>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
} 
 
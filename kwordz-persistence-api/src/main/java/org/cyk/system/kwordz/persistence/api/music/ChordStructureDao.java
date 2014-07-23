package org.cyk.system.kwordz.persistence.api.music;

import org.cyk.system.kwordz.model.music.ChordStructure;

public interface ChordStructureDao extends AbstractStructureDao<ChordStructure> {


	ChordStructure readBySymbol(String symbol);
	
}

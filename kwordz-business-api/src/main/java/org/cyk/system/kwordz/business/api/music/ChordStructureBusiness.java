package org.cyk.system.kwordz.business.api.music;

import org.cyk.system.kwordz.model.music.ChordStructure;

public interface ChordStructureBusiness extends AbstractStructureBusiness<ChordStructure> {
    
	ChordStructure findBySymbol(String symbol);
	
}

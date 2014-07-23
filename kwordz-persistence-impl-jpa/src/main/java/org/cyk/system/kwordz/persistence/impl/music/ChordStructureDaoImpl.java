package org.cyk.system.kwordz.persistence.impl.music;

import java.io.Serializable;

import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.persistence.api.music.ChordStructureDao;

public class ChordStructureDaoImpl extends AbstractStructureDaoImpl<ChordStructure> implements ChordStructureDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readBySymbol;
	
	@Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readBySymbol, "SELECT structure FROM ChordStructure structure WHERE :symbol MEMBER OF structure.symbols");
    }
	
	@Override
	public ChordStructure readBySymbol(String symbol) {
		return namedQuery(readBySymbol).parameter("symbol", symbol).resultOne();
	}
	
}
  
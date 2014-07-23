package org.cyk.system.kwordz.business.api.music;

import java.util.List;

import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.root.business.api.AbstractEnumerationBusiness;

public interface AbstractStructureBusiness<STRUCTURE extends Structure> extends AbstractEnumerationBusiness<STRUCTURE> {
    
	List<Note> generateSequence(STRUCTURE structure,Note base);
	
}

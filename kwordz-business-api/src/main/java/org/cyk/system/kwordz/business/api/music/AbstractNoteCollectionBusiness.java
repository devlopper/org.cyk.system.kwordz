package org.cyk.system.kwordz.business.api.music;

import org.cyk.system.kwordz.business.api.AbstractMusicBusiness;
import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.kwordz.model.music.AbstractNoteCollection;
import org.cyk.system.kwordz.model.music.Structure;

public interface AbstractNoteCollectionBusiness<STRUCTURE extends Structure,COLLECTION extends AbstractNoteCollection<STRUCTURE>,OPTIONS extends AbstractFormatOptions> extends AbstractMusicBusiness<COLLECTION,OPTIONS> {
    
	
}

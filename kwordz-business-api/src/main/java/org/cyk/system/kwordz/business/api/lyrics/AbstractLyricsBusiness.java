package org.cyk.system.kwordz.business.api.lyrics;

import org.cyk.system.kwordz.business.api.AbstractMusicBusiness;
import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.root.model.AbstractIdentifiable;

public interface AbstractLyricsBusiness <TYPE extends AbstractIdentifiable,OPTIONS extends AbstractFormatOptions> extends AbstractMusicBusiness<TYPE,OPTIONS> {
    
	String parseableForm(String text,Boolean chordAtTop);
	
}

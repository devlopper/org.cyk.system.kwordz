package org.cyk.system.kwordz.business.api.lyrics;

import java.util.Locale;

import org.cyk.system.kwordz.business.api.AbstractMusicBusiness;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;

public interface FragmentBusiness extends AbstractMusicBusiness<Fragment,FragmentFormatOptions> {
    
	void format(Locale locale, Fragment fragment,FragmentFormatOptions options,StringBuilder chordLine,StringBuilder textLine);
	
}

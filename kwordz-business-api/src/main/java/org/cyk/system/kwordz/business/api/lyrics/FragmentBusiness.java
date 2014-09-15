package org.cyk.system.kwordz.business.api.lyrics;

import java.util.Locale;

import org.cyk.system.kwordz.business.api.AbstractMusicBusiness;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.cyk.system.root.model.ContentType;

public interface FragmentBusiness extends AbstractMusicBusiness<Fragment,FragmentFormatOptions> {
    
	void format(Locale locale, Fragment fragment,ContentType contentType,FragmentFormatOptions options,StringBuilder chords,StringBuilder texts);
	
	String parseableForm(String chord,String text);
	
}

package org.cyk.system.kwordz.business.api;

import java.util.Locale;

import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.ContentType;

public interface AbstractMusicBusiness<TYPE extends AbstractIdentifiable,OPTIONS extends AbstractFormatOptions> extends TypedBusiness<TYPE> {
    
	void transpose(TYPE type,Integer distance);
	
	String format(Locale locale,TYPE object,ContentType contentType,OPTIONS options);
	
	String format(Locale locale,TYPE object,ContentType contentType);
	
	String format(Locale locale,TYPE object,OPTIONS options);
	
	String format(Locale locale,TYPE object);
	
	TYPE parse(Locale locale,String text);
	
}

package org.cyk.system.kwordz.business.impl;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.system.kwordz.business.api.AbstractMusicBusiness;
import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.persistence.api.TypedDao;

public abstract class AbstractMusicBusinessImpl<TYPE extends AbstractIdentifiable,DAO extends TypedDao<TYPE>,OPTIONS extends AbstractFormatOptions> extends AbstractTypedBusinessService<TYPE, DAO> implements AbstractMusicBusiness<TYPE,OPTIONS>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractMusicBusinessImpl(DAO dao) { 
		super(dao);    
	}
	
	@Override
	public String format(Locale locale, TYPE object,ContentType contentType) {
		return format(locale, object,contentType, defaultFormatOptions());
	}
	
	@Override
	public String format(Locale locale, TYPE object, OPTIONS options) {
		return format(locale,object,ContentType.TEXT,options);
	}
	
	@Override
	public String format(Locale locale, TYPE object) {
		return format(locale,object,ContentType.TEXT);
	}
	
	protected abstract OPTIONS defaultFormatOptions();

}

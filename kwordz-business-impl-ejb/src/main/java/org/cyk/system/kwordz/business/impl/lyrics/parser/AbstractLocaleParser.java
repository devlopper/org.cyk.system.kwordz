package org.cyk.system.kwordz.business.impl.lyrics.parser;

import java.io.Serializable;
import java.util.Locale;

public abstract class AbstractLocaleParser<T> extends AbstractParser<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected Locale locale;
	
	public AbstractLocaleParser(Locale locale) {
		super();
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

}
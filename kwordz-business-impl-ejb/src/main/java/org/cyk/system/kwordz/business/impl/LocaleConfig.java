package org.cyk.system.kwordz.business.impl;

import java.util.Locale;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

 
public enum LocaleConfig {

	FRENCH(Locale.FRENCH),
	ENGLISH(Locale.ENGLISH),
	
	;
	
	@Getter
	private Locale locale;
	@Getter @Setter
	private Pattern notePattern,chordPattern;
	
	private LocaleConfig(Locale locale) {
		this.locale = locale;
	}
	
	
	
}

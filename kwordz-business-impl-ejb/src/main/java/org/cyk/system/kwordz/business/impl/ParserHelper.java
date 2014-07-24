package org.cyk.system.kwordz.business.impl;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class ParserHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6518187499895981817L;

	private static final String[] LEFT_HAND_AND_RIGHT_HAND_SEPERATORS = {"/"};
	
	private static ParserHelper INSTANCE;
	
	@Inject private LanguageBusiness languageBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
    public void prepare(ChordStructureBusiness chordStructureBusiness){
    	Set<String> names,alterations,symbols=new LinkedHashSet<>();
    	names = new LinkedHashSet<>();
    	alterations = new LinkedHashSet<>();
    	for(LocaleConfig localeConfig : LocaleConfig.values()){
    		names.clear();
    		alterations.clear();
    		for(NoteName name : NoteName.values())
        		names.add(languageBusiness.findText(localeConfig.getLocale(),name));
    		for(NoteAlteration alteration : NoteAlteration.values())
    			addString(alterations,languageBusiness.findText(localeConfig.getLocale(),alteration));
    		localeConfig.setNotePattern(patternNote(names, alterations));
    		for(ChordStructure structure : chordStructureBusiness.find().all())
    			for(String symbol : structure.getSymbols())
    				addString(symbols,symbol);
    		localeConfig.setChordPattern(patternChord(names, alterations, symbols));
    		//debug(localeConfig);
    	}
    }
    
    private void addString(Set<String> set,String string){
    	if(string==null)
    		return;
    	if(string.isEmpty())
    		return;//"\\s";
    	set.add(string);
    }
    
    private Pattern patternNote(Set<String> names,Set<String> alterations){
    	return Pattern.compile(notePatternAsString(names, alterations));
    }
    
    private Pattern patternChord(Set<String> names,Set<String> alterations,Set<String> symbols){
    	return Pattern.compile(
    			//"("+StringUtils.join(names,"|")+"\\s*[\\\\]){0,1}\\s*"
    			"("+notePatternAsString(names, alterations)+")?\\s*["+StringUtils.join(LEFT_HAND_AND_RIGHT_HAND_SEPERATORS,"|")+"]?\\s*"
    			+ notePatternAsString(names, alterations)
    			+ "\\s*("+StringUtils.join(symbols,"|")+")?"
    			);
    }
    
    private String notePatternAsString(Set<String> names,Set<String> alterations){
    	return "("+StringUtils.join(names,"|")+")("+StringUtils.join(alterations,"|")+")*";
    }
    
    public Matcher matcher(Locale locale,PatternMatcherType type,String text){
    	LocaleConfig localeConfig = LocaleConfig.valueOfLocale(locale);
		ExceptionUtils.getInstance().exception(localeConfig==null,"kwordz.exception.parsing.localenotsupported",new Object[]{locale});
		
		text = StringUtils.trim(text);
		text = StringUtils.replace(text, "  ", " ");
		
		ExceptionUtils.getInstance().exception(StringUtils.isEmpty(text),"kwordz.exception.parsing."+type+".notfound");
		Matcher matcher;
		switch(type){
		case NOTE:matcher = localeConfig.getNotePattern().matcher(text);break;
		case CHORD:matcher = localeConfig.getChordPattern().matcher(text);break;
		default : matcher = null;
		}
		if(matcher==null)
			return null;
		ExceptionUtils.getInstance().exception(!matcher.find(),"kwordz.exception.parsing."+type+".notfound",new Object[]{text});
		return matcher;
    }
    
    public String stringAfter(String text,String seperator){
    	return StringUtils.substringAfter(text, seperator).trim();
    }
    
    public String getNoteString(Matcher matcher,Integer nameGroupIndex){
    	return matcher.group(nameGroupIndex)+StringUtils.defaultString(matcher.group(nameGroupIndex+1));
    }
    
    public static ParserHelper getInstance() {
		return INSTANCE;
	}
	
}

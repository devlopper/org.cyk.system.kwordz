package org.cyk.system.kwordz.business.impl;

import java.io.Serializable;
import java.util.Collection;
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

	/**
	 * Note name case in insensitive. Alteration case is sensitive. Zero or many alteration are allowed<br/>
	 * Examples : C , c , Cm, cm , Bb , bb , Bbb , B#b
	 */
	public static final String NOTE_PATTERN_FORMAT = "((?i)%1$s)%2$s(%3$s)*";
	/**
	 * Can have a structure symbol. Can contain bass note. Bass note must be at left
	 * Examples : C , F/C , C sus2 , F/C sus2
	 */
	public static final String CHORD_PATTERN_FORMAT = "("+NOTE_PATTERN_FORMAT+"%4$s)?"+NOTE_PATTERN_FORMAT+"(%5$s)?";
	
	private static final String[] LEFT_HAND_AND_RIGHT_HAND_SEPERATORS = {"/"};
	private static final String[] NOTENAME_AND_NOTEALTERATION_SEPERATORS = {" "};
	
	private static final String[] PATTERN_NOTENAME_AND_NOTEALTERATION_SEPERATORS = {"\\s*"};
	
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
    	return Pattern.compile(String.format(NOTE_PATTERN_FORMAT,or(names),or(PATTERN_NOTENAME_AND_NOTEALTERATION_SEPERATORS),or(alterations)));
    }
    
    private Pattern patternChord(Set<String> names,Set<String> alterations,Set<String> symbols){
    	return Pattern.compile(String.format(CHORD_PATTERN_FORMAT,or(names),or(PATTERN_NOTENAME_AND_NOTEALTERATION_SEPERATORS),or(alterations),
    			or(LEFT_HAND_AND_RIGHT_HAND_SEPERATORS),or(symbols)));
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
		ExceptionUtils.getInstance().exception(!matcher.find(),"kwordz.exception.parsing."+type.name().toLowerCase()+".notfound",new Object[]{text});
		return matcher;
    }
    
    public String stringAfter(String text,String seperator){
    	return StringUtils.substringAfter(text, seperator).trim();
    }
    
    /* pattern builder shortcuts */
    
    private String or(Collection<String> collection){
    	return or(collection.toArray(new String[]{}));
    }
    private String or(String[] array){
    	return StringUtils.join(array,"|");
    }
    
    public String getNoteString(Matcher matcher,Integer nameGroupIndex){
    	return matcher.group(nameGroupIndex)+StringUtils.defaultString(matcher.group(nameGroupIndex+1));
    }
    
    public String getLeftAndRightHandSeperator(){
    	return LEFT_HAND_AND_RIGHT_HAND_SEPERATORS[0];
    }
    
    public String getNoteNameAndNoteAlterationSeperator(){
    	return NOTENAME_AND_NOTEALTERATION_SEPERATORS[0];
    }
    
    public static ParserHelper getInstance() {
		return INSTANCE;
	}
	
}

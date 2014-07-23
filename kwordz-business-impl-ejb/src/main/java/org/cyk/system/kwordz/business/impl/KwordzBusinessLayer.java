package org.cyk.system.kwordz.business.impl;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.music.AbstractStructureBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteFormatOptions;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.kwordz.model.music.ScaleStructure;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class KwordzBusinessLayer extends AbstractBusinessLayer implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private static final String I18N_PREFIX = "kwordz.";
	private static KwordzBusinessLayer INSTANCE;
	
	@Getter private Pattern patternNoteFrench;
	@Getter private Pattern patternChordFrench;
	@Getter private Pattern patternNoteEnglish;
	@Getter private Pattern patternChordEnglish;
	
	@Getter private final ChordFormatOptions defaultChordFormatOptions = new ChordFormatOptions();
	@Getter private final NoteFormatOptions defaultNoteFormatOptions = new NoteFormatOptions();
	
	@Inject private ChordStructureBusiness chordStructureBusiness;
	@Inject private ScaleStructureBusiness scaleStructureBusiness;
	@Inject private LanguageBusiness languageBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		//buildPatterns();
	}
	
    @Override
    public void createInitialData() {
        createChordStructures(chordStructureBusiness);
        createScaleStructures(scaleStructureBusiness);
        buildPatterns();
    }
    
    public void createChordStructures(ChordStructureBusiness structureBusiness){
    	createChordStructure(structureBusiness,"maj","maj","majeur", 4,3);
    	createChordStructure(structureBusiness,"sus2",2,5);
    	createChordStructure(structureBusiness,"sus4",5,2);
    	createChordStructure(structureBusiness,"6", 4,3,2);
    	createChordStructure(structureBusiness,"6sus4",5,2,2);
    	createChordStructure(structureBusiness,"7sus4", 5,2,3);
    	createChordStructure(structureBusiness,"9sus4", 5,2,3,4);
    	createChordStructure(structureBusiness,"majDom7","7", 4,3);
    	createChordStructure(structureBusiness,"majDom9","9", 4,3,3,4);
    	createChordStructure(structureBusiness,"majDom11","11", 4,3,3,4,3);
    	createChordStructure(structureBusiness,"majDom13","13", 4,3,3,4,3,4);
    	createChordStructure(structureBusiness,"maj7", 4,3,4);
    	createChordStructure(structureBusiness,"maj9", 4,3,4,3);
    	createChordStructure(structureBusiness,"maj11", 4,3,4,3,3);
    	createChordStructure(structureBusiness,"maj13", 4,3,4,3,3,4);
    	createChordStructure(structureBusiness,"min","m", 3,4);
    	createChordStructure(structureBusiness,"min6","m6", 3,4,2);
    	createChordStructure(structureBusiness,"min9","m9", 3,4,3,4);
    	createChordStructure(structureBusiness,"min11","m11", 3,4,3,4,3);
    	createChordStructure(structureBusiness,"min13","m13", 3,4,3,4,3,4);
    	createChordStructure(structureBusiness,"dim", 3,3);
    	createChordStructure(structureBusiness,"dim7", 3,3,3);
    }
    
    public void createScaleStructures(ScaleStructureBusiness structureBusiness){
    	createScaleStructure(structureBusiness,"maj", 2,2,1,2,2,2);
    }
    
    //@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap) {
        //beansMap.put((Class)Event.class, (TypedBusiness)eventBusiness);
    }
    
    public void buildPatterns(){
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
    	return Pattern.compile("("+StringUtils.join(names,"|")+")("+StringUtils.join(alterations,"|")+")*");
    }
    
    private Pattern patternChord(Set<String> names,Set<String> alterations,Set<String> symbols){
    	return Pattern.compile("("+StringUtils.join(names,"|")+")("+StringUtils.join(alterations,"|")+")*\\s*("+StringUtils.join(symbols,"|")+"){0,1}"
    			//+ "\\s*[\\]\\s*"+StringUtils.join(names,"|")
    			);
    }
    
    /**/
    
    private void createChordStructure(ChordStructureBusiness structureBusiness,String code,String symbol,String name,Integer...intervals){
    	ChordStructure structure = new ChordStructure();
    	structure.getSymbols().add(symbol);
    	createStructure(structureBusiness, structure, code,name, intervals);
    }
    private void createChordStructure(ChordStructureBusiness structureBusiness,String code,String symbol,Integer...intervals){
    	createChordStructure(structureBusiness, code, symbol,code, intervals);
    }
    private void createChordStructure(ChordStructureBusiness structureBusiness,String code,Integer...intervals){
    	createChordStructure(structureBusiness, code, code, intervals);
    }
    
    private void createScaleStructure(ScaleStructureBusiness structureBusiness,String code,String name,Integer...intervals){
    	ScaleStructure structure = new ScaleStructure();
    	createStructure(structureBusiness, structure, code,name, intervals);
    }
    
    private void createScaleStructure(ScaleStructureBusiness structureBusiness,String code,Integer...intervals){
    	createScaleStructure(structureBusiness, code, code, intervals);
    }
    
    
    private <T extends Structure> void createStructure(AbstractStructureBusiness<T> structureBusiness,T structure,String code,String name,Integer...intervals){
    	structure.setCode(code);
    	structure.setName(name);
    	structure.setNameI18nId(I18N_PREFIX+structure.getClass().getSimpleName().toLowerCase()+"."+code);
    	for(Integer interval : intervals)
    		structure.getIntervals().add(interval);
    	structureBusiness.create(structure);
    }
    
    public static KwordzBusinessLayer getInstance() {
		return INSTANCE;
	}

}

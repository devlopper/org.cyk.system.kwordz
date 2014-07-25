package org.cyk.system.kwordz.business.impl;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.music.AbstractStructureBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.model.music.NoteFormatOptions;
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
	
	@Getter private final ChordFormatOptions defaultChordFormatOptions = new ChordFormatOptions();
	@Getter private final NoteFormatOptions defaultNoteFormatOptions = new NoteFormatOptions();
	@Getter private final FragmentFormatOptions defaultFragmentFormatOptions = new FragmentFormatOptions();
	@Getter private final LyricsFormatOptions defaultLyricsFormatOptions = new LyricsFormatOptions();
	
	@Inject private ChordStructureBusiness chordStructureBusiness;
	@Inject private ScaleStructureBusiness scaleStructureBusiness;
	@Inject private ParserHelper parserHelper;
	@Inject private LanguageBusiness languageBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		configFormatOptions(defaultNoteFormatOptions);
		configFormatOptions(defaultChordFormatOptions);
		configFormatOptions(defaultFragmentFormatOptions);
		parserHelper.prepare(chordStructureBusiness);
		languageBusiness.registerResourceBundle("org.cyk.system.kwordz.business.impl.resources.exception", getClass().getClassLoader());
	}
	
    @Override
    public void createInitialData() {
        createChordStructures(chordStructureBusiness);
        createScaleStructures(scaleStructureBusiness);
        parserHelper.prepare(chordStructureBusiness);
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
    
    private void configFormatOptions(NoteFormatOptions noteFormatOptions){
    	noteFormatOptions.setSeperatorNameAndAlteration(parserHelper.getNoteNameAndNoteAlterationSeperator());
    }
    
    private void configFormatOptions(ChordFormatOptions chordFormatOptions){
    	configFormatOptions(chordFormatOptions.getNoteFormatOptions());
    }
    
    private void configFormatOptions(FragmentFormatOptions fragmentFormatOptions){
    	configFormatOptions(fragmentFormatOptions.getChordFormatOptions());
    }
    
    public static KwordzBusinessLayer getInstance() {
		return INSTANCE;
	}

}

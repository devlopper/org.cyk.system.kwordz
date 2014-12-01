package org.cyk.system.kwordz.business.impl;

import java.io.Serializable;
import java.net.URI;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.music.AbstractStructureBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.business.api.song.SingerBusiness;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.lyrics.PartFormatOptions;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.model.music.MusicKind;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteFormatOptions;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.kwordz.model.music.ScaleStructure;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
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
	@Getter private final LineFormatOptions defaultLineFormatOptions = new LineFormatOptions();
	@Getter private final PartFormatOptions defaultPartFormatOptions = new PartFormatOptions();
	@Getter private final LyricsFormatOptions defaultLyricsFormatOptions = new LyricsFormatOptions();
	
	@Inject private ChordStructureBusiness chordStructureBusiness;
	@Inject private ScaleStructureBusiness scaleStructureBusiness;
	@Inject private ParserHelper parserHelper;
	@Inject private LanguageBusiness languageBusiness;
	@Inject private LyricsBusiness lyricsBusiness;
	@Inject private SingerBusiness singerBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		configFormatOptions(defaultNoteFormatOptions);
		configFormatOptions(defaultChordFormatOptions);
		configFormatOptions(defaultFragmentFormatOptions);
		configFormatOptions(defaultLineFormatOptions);
		configFormatOptions(defaultPartFormatOptions);
		configFormatOptions(defaultLyricsFormatOptions);
		parserHelper.prepare(chordStructureBusiness);
		languageBusiness.registerResourceBundle("org.cyk.system.kwordz.model.resources.entity", getClass().getClassLoader());
		languageBusiness.registerResourceBundle("org.cyk.system.kwordz.model.music.music", getClass().getClassLoader());
		
		languageBusiness.registerResourceBundle("org.cyk.system.kwordz.business.impl.exception", getClass().getClassLoader());
		languageBusiness.registerResourceBundle("org.cyk.system.kwordz.business.impl.ui", getClass().getClassLoader());
		
	}
	
    @Override
    public void createInitialData() {
        createChordStructures(chordStructureBusiness);
        createScaleStructures(scaleStructureBusiness);
        parserHelper.prepare(chordStructureBusiness);
        createMusicKind();
        fakeData();
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
    	createChordStructure(structureBusiness,"min7","m7", 3,4,3);
    	createChordStructure(structureBusiness,"min9","m9", 3,4,3,4);
    	createChordStructure(structureBusiness,"min11","m11", 3,4,3,4,3);
    	createChordStructure(structureBusiness,"min13","m13", 3,4,3,4,3,4);
    	createChordStructure(structureBusiness,"dim", 3,3);
    	createChordStructure(structureBusiness,"dim7", 3,3,3);
    }
    
    public void createScaleStructures(ScaleStructureBusiness structureBusiness){
    	createScaleStructure(structureBusiness,"maj", 2,2,1,2,2,2);
    }
    
    public void createMusicKind(){
    	create(new MusicKind(null, "ROCK", "Rock"));
    	create(new MusicKind(null, "SOUL", "Soul"));
    	create(new MusicKind(null, "REGGEA", "Reggea"));
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap) {
        beansMap.put((Class)Singer.class, (TypedBusiness)singerBusiness);
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
    
    private void configFormatOptions(LineFormatOptions lineFormatOptions){
    	configFormatOptions(lineFormatOptions.getFragmentFormatOptions());
    }
    
    private void configFormatOptions(PartFormatOptions partFormatOptions){
    	configFormatOptions(partFormatOptions.getLineFormatOptions());
    }
    
    private void configFormatOptions(LyricsFormatOptions lyricsFormatOptions){
    	configFormatOptions(lyricsFormatOptions.getPartFormatOptions());
    }
    
    public static KwordzBusinessLayer getInstance() {
		return INSTANCE;
	}
    
    /**/
    
    private void fakeData(){
    	
    	Singer singer1,singer2,singer3;
    	singer1 = create(new Singer("Chanteur one"));
    	singer2 = create(new Singer("Chanteur two"));
    	singer3 = create(new Singer("Chanteur three"));
    	
    	Album s1Album1,s1Album2,s1Album3,s2Album1,s3Album1,s3Album2;
    	s1Album1 = create(new Album(singer1,"Album 1")); 
    	s1Album2 = create(new Album(singer1,"Album 2")); 
    	s1Album3 = create(new Album(singer1,"Album 3")); 
    	
    	s2Album1 = create(new Album(singer2,"Album 1"));
    	
    	s3Album1 = create(new Album(singer3,"Album 1")); 
    	s3Album2 = create(new Album(singer3,"Album 2")); 
    	
    	create(new Song(s1Album1,"Chant 1",new Note(NoteName.C),createYoutubeVideo("nQ8MFn4yikA"),lyricsBusiness.parse(Locale.ENGLISH, "[C]Je[G]sus [Am]You [Dm7]Are")));
    	
    	create(new Song(s1Album2,"Chant 2",new Note(NoteName.G),createYoutubeVideo("FBgWEzKCfUQ"),lyricsBusiness.parse(Locale.ENGLISH, "[C]Je[G]sus [Am]You [Dm7]Are")));
    	
    	create(new Song(s1Album3,"Chant 3",new Note(NoteName.E),createYoutubeVideo("n2iTo0SVktc"),lyricsBusiness.parse(Locale.ENGLISH, "Jesus")));
    	
    	create(new Song(s2Album1,"Chant 1",new Note(NoteName.G),createYoutubeVideo("hCBgm1orMfo"),lyricsBusiness.parse(Locale.ENGLISH, "Jesus")));
    	
    	create(new Song(s3Album1,"Chant 1",new Note(NoteName.A),createYoutubeVideo("NQr54a6KaUc"),lyricsBusiness.parse(Locale.ENGLISH, "Jesus")));
    	
    	create(new Song(s3Album2,"Chant 2",new Note(NoteName.B),null,lyricsBusiness.parse(Locale.ENGLISH, "Jesus")));
    }
    
    private File createYoutubeVideo(String videoId){
    	File file = new File();
    	file.setUri(URI.create("https://www.youtube.com/watch?v="+videoId));
    	return file;
    }
    
}

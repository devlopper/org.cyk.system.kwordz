package org.cyk.system.kwordz.business.impl.integration;

import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.FragmentBusiness;
import org.cyk.system.kwordz.business.api.lyrics.LineBusiness;
import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.lyrics.PartBusiness;
import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.Line;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions.ChordLocation;
import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.model.lyrics.PartFormatOptions;
import org.cyk.system.root.model.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class LyricsParsingIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Inject private ChordBusiness chordBusiness;
    @Inject private FragmentBusiness fragmentBusiness;
    @Inject private LineBusiness lineBusiness;
    @Inject private PartBusiness partBusiness;
    @Inject private LyricsBusiness lyricsBusiness;
    
    @Override
    protected void populate() {
    	kwordzBusinessLayer.createInitialData();
    }
    
    @Override
    protected void finds() {}

    @Override
    protected void businesses() {}

    @Override
    protected void create() {}

    @Override
    protected void delete() {}

    @Override
    protected void read() {}

    @Override
    protected void update() {}

    /**/
          
    @Test
	public void parseFragment(){	
    	assertFragment(Locale.ENGLISH,"[C m7]Jesus is","C m7", "Jesus is");
    	assertFragment(Locale.ENGLISH,"[G]Alive","G maj", "Alive");
    	assertFragment(Locale.ENGLISH,"Jesus",null, "Jesus");
    }
    
    @Test
   	public void parseLine(){
    	assertLine(Locale.ENGLISH, "[C sus2]Jesus is [G]Alive", 
    			"C sus2   G maj"+ContentType.TEXT.getNewLineMarker()+
    			"Jesus is Alive");
    	
    	assertLine(Locale.ENGLISH, "Jesus is the lord", 
    			"Jesus is the lord");
    }
    
    @Test
   	public void parsePart(){
    	assertPart(Locale.ENGLISH, "[C sus2]Jesus is [G]Alive\r\nHe [D m]is [A m]ri[E m7]sen", 
    			"C sus2   G maj"+ContentType.TEXT.getNewLineMarker()+
    			"Jesus is Alive"+ContentType.TEXT.getNewLineMarker()+
    			"   D m A m E m7"+ContentType.TEXT.getNewLineMarker()+
    			"He is  ri  sen");
    }
    
    @Test
   	public void parseLyrics(){
    	assertLyrics(Locale.ENGLISH, "[C sus2]Jesus is [G]Alive\r\nHe [D m]is [A m]ri[E m7]sen", 
    			"C sus2   G maj"+ContentType.TEXT.getNewLineMarker()+
    			"Jesus is Alive"+ContentType.TEXT.getNewLineMarker()+
    			"   D m A m E m7"+ContentType.TEXT.getNewLineMarker()+
    			"He is  ri  sen");
    }
    
    /**/

    private void assertFragment(Locale locale,String fragmentString,String chord,String text){
    	Fragment fragment = fragmentBusiness.parse(locale, fragmentString);
    	assertEquals(chord, chordBusiness.format(locale, fragment.getChord()));
    	assertEquals(text, fragment.getText());
    }
    
    private void assertLine(Locale locale,String inputChordAtLeft,String expectedResult){
    	Line line = lineBusiness.parse(locale, inputChordAtLeft);
    	LineFormatOptions lineFormatOptions = new LineFormatOptions();
    	lineFormatOptions.setChordLocation(ChordLocation.TOP);
    	String result = lineBusiness.format(locale, line,lineFormatOptions);
    	assertEquals(expectedResult, result);
    }
    
    private void assertPart(Locale locale,String inputChordAtLeft,String expectedResult){
    	Part part = partBusiness.parse(locale, inputChordAtLeft);
    	PartFormatOptions partFormatOptions = new PartFormatOptions();
    	partFormatOptions.getLineFormatOptions().setChordLocation(ChordLocation.TOP);
    	String result = partBusiness.format(locale, part,partFormatOptions);
    	assertEquals(expectedResult, result);
    }
    
    private void assertLyrics(Locale locale,String inputChordAtLeft,String expectedResult){
    	Lyrics lyrics = lyricsBusiness.parse(locale, inputChordAtLeft);
    	LyricsFormatOptions lyricsFormatOptions = new LyricsFormatOptions();
    	lyricsFormatOptions.getPartFormatOptions().getLineFormatOptions().setChordLocation(ChordLocation.TOP);
    	String result = lyricsBusiness.format(locale, lyrics,lyricsFormatOptions);
    	assertEquals(expectedResult, result);
    }
    
}

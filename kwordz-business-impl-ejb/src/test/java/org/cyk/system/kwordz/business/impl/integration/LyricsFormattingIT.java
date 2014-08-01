package org.cyk.system.kwordz.business.impl.integration;

import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.FragmentBusiness;
import org.cyk.system.kwordz.business.api.lyrics.LineBusiness;
import org.cyk.system.kwordz.business.api.lyrics.PartBusiness;
import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.cyk.system.kwordz.model.lyrics.Line;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions.ChordLocation;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.model.lyrics.PartFormatOptions;
import org.cyk.system.root.model.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class LyricsFormattingIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    private static final String 
    LINE_FRAGMENTS[][] = {
    	{"Jesus","[C maj]","save","[G maj]"}
    },
    /* Chords at left - Chords inside - Chords at right - Chords at top - Chords only - Text only */
    LINES[][] = {
    	{"[C maj][G maj]Jesus save","[C maj]Jesus [G maj]save","Jesus save[C maj][G maj]","C maj G maj\r\nJesus save ","[C maj][G maj]","Jesus save"}
    };
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Inject private ChordBusiness chordBusiness;
    @Inject private FragmentBusiness fragmentBusiness;
    @Inject private LineBusiness lineBusiness;
    @Inject private PartBusiness partBusiness;
    //@Inject private LyricsBusiness lyricsBusiness;
    
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
	public void formatFragment(){	
    	assertEqualsFragment(Locale.ENGLISH, "Jesus is good", "C","[C maj]Jesus is good","Jesus is good[C maj]","[C maj]","Jesus is good","Jesus is good", "C maj        ");
    	assertEqualsFragment(Locale.ENGLISH, "he", "C sus2","[C sus2]he","he[C sus2]","[C sus2]","he", "he    ", "C sus2");
    	
    	assertEqualsFragment(Locale.FRENCH, "Jesus is good", "do","[do maj]Jesus is good","Jesus is good[do maj]","[do maj]","Jesus is good","Jesus is good", "do maj       ");
    	assertEqualsFragment(Locale.FRENCH, "he", "do sus2","[do sus2]he","he[do sus2]","[do sus2]","he", "he     ", "do sus2");
    	
    	//assertEqualsFragment(Locale.FRENCH, "he", "","he","he","","he", "he", "");
    }
    
    @Test
   	public void formatLine(){
    	assertEqualsLine(Locale.ENGLISH, 0);
    }
    
    @Test
   	public void formatPart(){
    	Locale locale = Locale.ENGLISH;
    	assertEqualsPart(locale, createPart(locale), 0);
    }
    
    @Test
   	public void formatLyrics(){
    	
    }
    
    /**/

    private void assertEqualsFragment(Locale locale,String inputText,String inputChord,String chordAtLeft,String chordAtRight,String chordOnly,String textOnly,
    		String outputLine,String outputChord){
    	StringBuilder cl=new StringBuilder(),tl = new StringBuilder();
    	Fragment fragment = new Fragment(inputText, chordBusiness.parse(locale, inputChord));
    	FragmentFormatOptions options = new FragmentFormatOptions();
    	
    	options.setChordAtLeft(Boolean.TRUE);
    	assertEquals(chordAtLeft, fragmentBusiness.format(locale, fragment, options));
    	options.setChordAtLeft(Boolean.FALSE);
    	assertEquals(chordAtRight, fragmentBusiness.format(locale, fragment, options));
    	
    	options.setShowChord(Boolean.FALSE);options.setShowText(Boolean.TRUE);
    	assertEquals(textOnly, fragmentBusiness.format(locale, fragment, options));
    	options.setShowChord(Boolean.TRUE);options.setShowText(Boolean.FALSE);
    	assertEquals(chordOnly, fragmentBusiness.format(locale, fragment, options));
    	options.setShowChord(Boolean.FALSE);options.setShowText(Boolean.FALSE);
    	assertEquals("", fragmentBusiness.format(locale, fragment, options));
    	
    	options.setPadding(FragmentFormatOptions.PADDING);
    	options.getChordFormatOptions().setShowMarker(Boolean.FALSE);
    	fragmentBusiness.format(locale, fragment ,ContentType.TEXT,options, cl, tl);
    	assertEquals(outputChord, cl.toString());
    	assertEquals(outputLine, tl.toString());
    	assertEquals(tl.length(), cl.length());
    }
    
    private void assertEqualsLine(Locale locale,Integer lineIndex){
    	Line line = createLine(locale, lineIndex);
    	LineFormatOptions options = kwordzBusinessLayer.getDefaultLineFormatOptions();
    	
    	options.setChordLocation(ChordLocation.LEFT);
    	assertEquals(LINES[lineIndex][0], lineBusiness.format(locale, line,options));
    	
    	options.setChordLocation(ChordLocation.FOLLOW_FRAGMENT);
    	assertEquals(LINES[lineIndex][1], lineBusiness.format(locale, line,options));
    	
    	options.setChordLocation(ChordLocation.RIGHT);
    	assertEquals(LINES[lineIndex][2], lineBusiness.format(locale, line,options));
    	
    	options.setChordLocation(ChordLocation.TOP);
    	options.getFragmentFormatOptions().setShowChord(Boolean.TRUE);options.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(LINES[lineIndex][3], lineBusiness.format(locale, line,options));
    	
    	options.setChordLocation(ChordLocation.LEFT);
    	options.getFragmentFormatOptions().setShowChord(Boolean.TRUE);options.getFragmentFormatOptions().setShowText(Boolean.FALSE);
    	assertEquals(LINES[lineIndex][4], lineBusiness.format(locale, line,options));
    	
    	options.getFragmentFormatOptions().setShowChord(Boolean.FALSE);options.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(LINES[lineIndex][5], lineBusiness.format(locale, line,options));
    	
    }
    
    private void assertEqualsPart(Locale locale,Part part,Integer lineIndex){
    	PartFormatOptions options = kwordzBusinessLayer.getDefaultPartFormatOptions();
    	LineFormatOptions lineFormatOptions = options.getLineFormatOptions();
    	
    	lineFormatOptions.setChordLocation(ChordLocation.LEFT);
    	assertEquals(LINES[lineIndex][0], partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.FOLLOW_FRAGMENT);
    	assertEquals(LINES[lineIndex][1], partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.RIGHT);
    	assertEquals(LINES[lineIndex][2], partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.TOP);
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.TRUE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(LINES[lineIndex][3], partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.LEFT);
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.TRUE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.FALSE);
    	assertEquals(LINES[lineIndex][4], partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.FALSE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(LINES[lineIndex][5], partBusiness.format(locale, part,options));
    }
    
    /*
    private void assertEqualsLyrics(Locale locale,Lyrics lyrics){
    	LyricsFormatOptions options = kwordzBusinessLayer.getDefaultLyricsFormatOptions();
    	LineFormatOptions lineFormatOptions = options.getPartFormatOptions().getLineFormatOptions();
    	
    	lineFormatOptions.setChordLocation(ChordLocation.LEFT);
    	assertEquals(LINES[lineIndex][0], lyricsBusiness.format(locale, lyrics,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.FOLLOW_FRAGMENT);
    	assertEquals(LINES[lineIndex][1], lyricsBusiness.format(locale, lyrics,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.RIGHT);
    	assertEquals(LINES[lineIndex][2], lyricsBusiness.format(locale, lyrics,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.TOP);
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.TRUE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(LINES[lineIndex][3], lyricsBusiness.format(locale, lyrics,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.LEFT);
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.TRUE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.FALSE);
    	assertEquals(LINES[lineIndex][4], lyricsBusiness.format(locale, lyrics,options));
    	
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.FALSE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(LINES[lineIndex][5], lyricsBusiness.format(locale, lyrics,options));
    }
    */
    
    /**/
    
    private Line createLine(Locale locale,Integer lineIndex){
    	Line line = new Line();
    	for(int i=0;i<LINE_FRAGMENTS[lineIndex].length;i=i+2)
    		line.getFragments().add(new Fragment(LINE_FRAGMENTS[lineIndex][i], chordBusiness.parse(locale, LINE_FRAGMENTS[lineIndex][i+1])));
    	return line;
    }
    
    private Part createPart(Locale locale){
    	Part part = new Part();
    	for(int i=0;i<LINES.length;i++)
    		part.getLines().add(createLine(locale, i));
    	return part;
    }
    /*
    private Lyrics createLyrics(Locale locale){
    	Lyrics lyrics = new Lyrics();
    	lyrics.getParts().add(createPart(locale));
    	return lyrics;
    }*/
    
    
    
}

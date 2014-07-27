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
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.model.lyrics.PartFormatOptions;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class LyricsFormattingIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private ChordBusiness chordBusiness;
    @Inject private FragmentBusiness fragmentBusiness;
    @Inject private LineBusiness lineBusiness;
    @Inject private PartBusiness partBusiness;
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    private String chordsAtLeft="[C maj][G maj]Jesus save",
    		chordsInside="[C maj]Jesus [G maj]save",
    		chordsAtRight="Jesus save[C maj][G maj]",
    		chordsAtTop="C maj G maj\r\nJesus save ",
    		chordsOnly="[C maj][G maj]",
    		textOnly="Jesus save";
    
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
    }
    
    @Test
   	public void formatLine(){
    	Line line = new Line();
    	Locale locale = Locale.ENGLISH;
    	line.getFragments().add(new Fragment("Jesus", chordBusiness.parse(locale, "C")));
    	line.getFragments().add(new Fragment("save", chordBusiness.parse(locale, "G")));
    	assertEqualsLine(locale, line, chordsAtLeft, chordsInside, chordsAtRight, chordsAtTop, chordsOnly, textOnly);
    }
    
    @Test
   	public void formatPart(){
    	Locale locale = Locale.ENGLISH;
    	Part part = new Part();
    	Line line = new Line();
    	line.getFragments().add(new Fragment("Jesus", chordBusiness.parse(locale, "C")));
    	line.getFragments().add(new Fragment("save", chordBusiness.parse(locale, "G")));
    	
    	part.getLines().add(line);
    	
    	assertEqualsPart(locale, part, chordsAtLeft, chordsInside, chordsAtRight, chordsAtTop, chordsOnly, textOnly);
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
    	fragmentBusiness.format(locale, fragment ,options, cl, tl);
    	assertEquals(outputChord, cl.toString());
    	assertEquals(outputLine, tl.toString());
    	assertEquals(tl.length(), cl.length());
    }
    
    private void assertEqualsLine(Locale locale,Line line,String chordAtLeft,String inside,String chordAtRight,String chordAtTop,String chordOnly,String textOnly){
    	LineFormatOptions options = kwordzBusinessLayer.getDefaultLineFormatOptions();
    	
    	options.setChordLocation(ChordLocation.LEFT);
    	assertEquals(chordAtLeft, lineBusiness.format(locale, line,options));
    	
    	options.setChordLocation(ChordLocation.FOLLOW_FRAGMENT);
    	assertEquals(inside, lineBusiness.format(locale, line,options));
    	
    	options.setChordLocation(ChordLocation.RIGHT);
    	assertEquals(chordAtRight, lineBusiness.format(locale, line,options));
    	
    	options.setChordLocation(ChordLocation.TOP);
    	options.getFragmentFormatOptions().setShowChord(Boolean.TRUE);options.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(chordAtTop, lineBusiness.format(locale, line,options));
    	
    	options.setChordLocation(ChordLocation.LEFT);
    	options.getFragmentFormatOptions().setShowChord(Boolean.TRUE);options.getFragmentFormatOptions().setShowText(Boolean.FALSE);
    	assertEquals(chordOnly, lineBusiness.format(locale, line,options));
    	
    	options.getFragmentFormatOptions().setShowChord(Boolean.FALSE);options.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(textOnly, lineBusiness.format(locale, line,options));
    	
    }
    
    private void assertEqualsPart(Locale locale,Part part,String chordAtLeft,String inside,String chordAtRight,String chordAtTop,String chordOnly,String textOnly){
    	PartFormatOptions options = kwordzBusinessLayer.getDefaultPartFormatOptions();
    	LineFormatOptions lineFormatOptions = options.getLineFormatOptions();
    	
    	lineFormatOptions.setChordLocation(ChordLocation.LEFT);
    	assertEquals(chordAtLeft, partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.FOLLOW_FRAGMENT);
    	assertEquals(inside, partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.RIGHT);
    	assertEquals(chordAtRight, partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.TOP);
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.TRUE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(chordAtTop, partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.setChordLocation(ChordLocation.LEFT);
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.TRUE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.FALSE);
    	assertEquals(chordOnly, partBusiness.format(locale, part,options));
    	
    	lineFormatOptions.getFragmentFormatOptions().setShowChord(Boolean.FALSE);lineFormatOptions.getFragmentFormatOptions().setShowText(Boolean.TRUE);
    	assertEquals(textOnly, partBusiness.format(locale, part,options));
    }
    
    private void assertEqualsLyrics(Locale locale,Part part,String chordAtLeft,String inside,String chordAtRight,String chordAtTop,String chordOnly,String textOnly){
    	LyricsFormatOptions options = kwordzBusinessLayer.getDefaultLyricsFormatOptions();
    	
    }
    
}

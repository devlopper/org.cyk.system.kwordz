package org.cyk.system.kwordz.business.impl.integration;

import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.FragmentBusiness;
import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class LyricsFormattingIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private ChordBusiness chordBusiness;
    @Inject private FragmentBusiness fragmentBusiness;
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
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
    	
    	fragmentBusiness.format(locale, fragment ,options, cl, tl);
    	assertEquals(outputChord, cl.toString());
    	assertEquals(outputLine, tl.toString());
    	assertEquals(tl.length(), cl.length());
    }
    
}

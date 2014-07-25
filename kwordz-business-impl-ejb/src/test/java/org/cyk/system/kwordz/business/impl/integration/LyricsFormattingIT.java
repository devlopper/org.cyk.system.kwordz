package org.cyk.system.kwordz.business.impl.integration;

import static org.cyk.system.kwordz.model.music.NoteAlteration.FLAT;
import static org.cyk.system.kwordz.model.music.NoteAlteration.NONE;
import static org.cyk.system.kwordz.model.music.NoteAlteration.SHARP;
import static org.cyk.system.kwordz.model.music.NoteName.A;
import static org.cyk.system.kwordz.model.music.NoteName.B;
import static org.cyk.system.kwordz.model.music.NoteName.C;
import static org.cyk.system.kwordz.model.music.NoteName.D;
import static org.cyk.system.kwordz.model.music.NoteName.E;
import static org.cyk.system.kwordz.model.music.NoteName.F;
import static org.cyk.system.kwordz.model.music.NoteName.G;

import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.FragmentBusiness;
import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.business.api.music.ChordStructureBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.business.impl.ParserHelper;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteAlteration;
import org.cyk.system.kwordz.model.music.NoteFormatOptions;
import org.cyk.system.kwordz.model.music.NoteName;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class LyricsFormattingIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Inject private ChordStructureBusiness chordStructureBusiness;
    @Inject private ScaleStructureBusiness scaleStructureBusiness;
    @Inject private ChordBusiness chordBusiness;
    @Inject private FragmentBusiness fragmentBusiness;
    
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Override
    protected void populate() {
    	//kwordzBusinessLayer.createChordStructures(chordStructureBusiness);
    	//kwordzBusinessLayer.createScaleStructures(scaleStructureBusiness);
    	//ParserHelper.getInstance().prepare(chordStructureBusiness);
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
    	assertEqualsFragment(Locale.ENGLISH, "Jesus is good", "C", kwordzBusinessLayer.getDefaultFragmentFormatOptions(), "C maj        ","Jesus is good");
    	assertEqualsFragment(Locale.ENGLISH, "he", "C dim", kwordzBusinessLayer.getDefaultFragmentFormatOptions(),  "C dim","he   ");
    }
    
    /**/

    private void assertEqualsFragment(Locale locale,String text,String chord,FragmentFormatOptions options,String chordLine,String textLine){
    	StringBuilder cl=new StringBuilder(),tl = new StringBuilder();
    	fragmentBusiness.format(locale, new Fragment(text, chordBusiness.parse(locale, chord)), options, cl, tl);
    	assertEquals(chordLine, cl.toString());
    	assertEquals(textLine, tl.toString());
    	assertEquals(tl.length(), cl.length());
    }
    
}

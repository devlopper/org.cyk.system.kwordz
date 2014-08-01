package org.cyk.system.kwordz.business.impl.unit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cyk.system.kwordz.business.impl.ParserHelper;
import org.cyk.utility.common.test.AbstractUnitTest;

public class ParserHelperUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;
	
    private static String NOTE_NAMES = "A|B|C|D|E|F|G";
    private static String NOTE_NAME_ALTERATION_SEPERATOR = "";
    private static String NOTE_ALTERATIONS = "#|b";
    private static Pattern NOTE_PATTERN;
    private static Pattern CHORD_PATTERN;
    private static Pattern FRAGMENT_PATTERN;
    
    private static final String[] ASSERT_NOTES_TRUE_1 = {"C","c","B","b","Bb","bb","cb","c#"};
    private static final String[] ASSERT_NOTES_TRUE_2 = {"b p","c b","c #"};
    private static final String[] ASSERT_NOTES_FALSE_1 = {"s"};
    
    private static final String[] ASSERT_CHORDS_TRUE_1 = {"Cmaj","cmaj","Bdim","bdim","bbdim","cb7","c#dim7"};
    private static final String[] ASSERT_CHORDS_TRUE_2 = {"b bdim","c b","c #"};
    private static final String[] ASSERT_CHORDS_FALSE_1 = {"h"};
    
    {
    	NOTE_PATTERN = Pattern.compile(String.format(ParserHelper.PatternType.NOTE.getValue(),NOTE_NAMES,NOTE_NAME_ALTERATION_SEPERATOR,NOTE_ALTERATIONS));
    	CHORD_PATTERN = Pattern.compile(String.format(ParserHelper.PatternType.CHORD.getValue(),NOTE_NAMES,NOTE_NAME_ALTERATION_SEPERATOR,NOTE_ALTERATIONS,"/","maj|dim|dim7"));
    	//FRAGMENT_PATTERN = Pattern.compile(String.format(ParserHelper.PatternType.FRAGMENT.getValue(),NOTE_NAMES,NOTE_NAME_ALTERATION_SEPERATOR,NOTE_ALTERATIONS,"/","maj|dim|dim7"));
    }
    

    //@Test
	public void notePattern(){
    	assertNotePattern(Boolean.TRUE,ASSERT_NOTES_TRUE_1);
    	assertNotePattern(Boolean.TRUE,ASSERT_NOTES_TRUE_2);
    	assertNotePattern(Boolean.FALSE,ASSERT_NOTES_FALSE_1);
    }
    
    //@Test
	public void chordPattern(){
    	assertChordPattern(Boolean.TRUE,ASSERT_NOTES_TRUE_1);
    	assertChordPattern(Boolean.TRUE,ASSERT_NOTES_TRUE_2);
    	assertChordPattern(Boolean.FALSE,ASSERT_NOTES_FALSE_1);
    	
    	assertChordPattern(Boolean.TRUE,ASSERT_CHORDS_TRUE_1);
    	assertChordPattern(Boolean.TRUE,ASSERT_CHORDS_TRUE_2);
    	assertChordPattern(Boolean.FALSE,ASSERT_CHORDS_FALSE_1);
    }
    
    //@Test
	public void fragmentPattern(){
    	assertFragmentPattern(Boolean.TRUE, new String[]{"[C]Text","Text","[C]"});
    }
    	
	/**/
	
	private void assertNotePattern(Boolean found,String...notes){
		assertPattern(NOTE_PATTERN, found, notes);
	}
	
	private void assertChordPattern(Boolean found,String...chords){
		assertPattern(CHORD_PATTERN, found, chords);
	}
	
	private void assertFragmentPattern(Boolean found,String...fragments){
		assertPattern(FRAGMENT_PATTERN, found, fragments);
	}
	
	private void assertPattern(Pattern pattern,Boolean found,String...expected){
		Matcher matcher = null;
		for(String value : expected){
			matcher = pattern.matcher(value);
			Boolean find = matcher.find();
			
			System.out.println(value);
			for(int i=1;i<=matcher.groupCount();i++)
				System.out.println("\tGroupe "+i+" : "+matcher.group(i));
			
			assertEquals(Boolean.TRUE.equals(found),find);
		}
	}
	
	
	
}

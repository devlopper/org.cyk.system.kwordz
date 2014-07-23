package org.cyk.system.kwordz.business.impl.lyrics.parser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import org.cyk.system.root.business.impl.validation.ExceptionUtils;


public abstract class AbstractParser<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected static final Logger LOGGER = Logger.getLogger(AbstractParser.class.getName());
	
	public static final String NOTE_SEPARATOR = " ";
	public static final String CHORD_TYPE_SEPARATOR = "-";
	public static final String CHORD_BASS_NOTE_SEPARATOR = "/";
	
	public static final String CHORD_START = "[";
	public static final String CHORD_END = "]";
	public static final String CHORD_START_REPLACEMENT = "{";
	public static final String CHORD_END_REPLACEMENT = "}";
	public static final Set<Character> CHORD_SEPARATORS = new HashSet<Character>();
	
	static{
		CHORD_SEPARATORS.add(' ');
		CHORD_SEPARATORS.add('\t');
		CHORD_SEPARATORS.add('-');
	}
	
	public static final String TAG_MARKER = "::";
	public static final String COMPONENT = "KWZ_COMP";
	public static final String TYPE = "KWZ_TYPE";
	public static final String REPEAT = "KWZ_REPEAT";
	public static final String LINE = "KWZ_LC";
			
	private Set<AbstractLocaleParser<T>> localeParsers;
	
	protected boolean addLocaleParser(AbstractLocaleParser<T> localeParser){
		if(localeParsers==null)
			localeParsers = new HashSet<AbstractLocaleParser<T>>();
		return localeParsers.add(localeParser);
	}
	
	public abstract T parse(String text) throws Exception;
	
	private AbstractLocaleParser<T> getLocaleParser(Locale locale){
		if(localeParsers!=null)
			for(AbstractLocaleParser<T> lp : localeParsers)
				if(lp.getLocale().equals(locale))
					return lp;
		return null;
	}
	
	public T parse(String text,Locale locale) throws Exception{
		AbstractLocaleParser<T> localeParser = getLocaleParser(locale);
		if(localeParser==null)
			ExceptionUtils.getInstance().exception("LocaleParserNotYetImplemented");
		return localeParser.parse(text);
	}
	
	/*
	 * Text can contains various informations
	 * an information is given as TAGNAME::VALUE::TAGNAME
	 * 
	 * */
	
	public static String startTag(String name){
		return name+TAG_MARKER;
	}
	public static String endTag(String name){
		return TAG_MARKER+name;
	}
	public static String tag(String name,String value){
		return startTag(name)+value+endTag(name);
	}
	
	protected Object[] extractTagValue(String text,String tagName){
		String startTag = startTag(tagName),endTag = endTag(tagName);
		
		int i = text.indexOf(startTag);
		if(i<0) return null;
		int j = text.indexOf(endTag,i);
		if(j<=i) return null;
		
		int tagLength=startTag.length();
		String value = text.substring(i+tagLength, j);
		
		text = text.substring(0, i)+text.substring(j+tagLength);
		return new Object[]{value,i,text};
	}
	
	protected class ExtractedTagInfos{
		String name;
		int startTagIndex,endTagIndex;
	}

}

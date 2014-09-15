package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.lyrics.FragmentBusiness;
import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.persistence.api.lyrics.FragmentDao;
import org.cyk.system.root.business.api.HtmlBusiness;
import org.cyk.system.root.model.ContentType;

public class FragmentBusinessImpl extends AbstractMusicBusinessImpl<Fragment, FragmentDao,FragmentFormatOptions> implements FragmentBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ChordBusiness chordBusiness;
	@Inject private HtmlBusiness htmlBusiness;
	
	@Inject
	public FragmentBusinessImpl(FragmentDao dao) { 
		super(dao);    
	}

	@Override
	public void transpose(Fragment fragment, Integer distance) {
		if(fragment.getChord()!=null)
			chordBusiness.transpose(fragment.getChord(), distance);
	}
	
	@Override
	protected FragmentFormatOptions defaultFormatOptions() {
		return KwordzBusinessLayer.getInstance().getDefaultFragmentFormatOptions();
	}

	@Override
	public String format(Locale locale, Fragment fragment,ContentType contentType,FragmentFormatOptions options) {
		StringBuilder line = new StringBuilder();
		String text = StringUtils.defaultString(Boolean.TRUE.equals(options.getShowText())?fragment.getText():null);
		options.getChordFormatOptions().setShowMarker(Boolean.TRUE);
		String chord = StringUtils.defaultString(Boolean.TRUE.equals(options.getShowChord())?
				chordBusiness.format(locale, fragment.getChord(), contentType ,options.getChordFormatOptions()):null);
		String result;
		if(Boolean.TRUE.equals(options.getChordAtLeft()))
			result = chord+text;
		else
			result = text+chord;
		if(result!=null && !result.trim().isEmpty())
			line.append(result);
		
		return line.toString();
	}

	@Override
	public void format(Locale locale, Fragment fragment,ContentType contentType,FragmentFormatOptions options, StringBuilder chords,StringBuilder texts) {
		FragmentPartBuilder chordBuilder=new FragmentPartBuilder();
		FragmentPartBuilder textBuilder;
		String textString = StringUtils.defaultString(fragment.getText());
		//Clean text string
		switch(contentType){
		case HTML: 
			textString = StringEscapeUtils.escapeHtml4(textString);
			break;
		default:
			break;
		}
		textBuilder = new FragmentPartBuilder(textString);
		
		if(StringUtils.isNotEmpty(textBuilder.value))
			addSpaceSeperator(texts,contentType.getSpaceMarker());
		
		if(fragment.getChord()!=null){
			if(Boolean.FALSE.equals(options.getChordFormatOptions().getShowMarker()))
				addSpaceSeperator(chords,contentType.getSpaceMarker());
			chordBuilder.value = chordBusiness.format(locale, fragment.getChord(), contentType,options.getChordFormatOptions());
		}
		
		pad(locale, fragment, contentType, options, chordBuilder, textBuilder);
		style(locale, fragment, contentType, options, chordBuilder, textBuilder);
		
		if(StringUtils.isNotEmpty(chords) && StringUtils.isNotEmpty(chordBuilder.value) && !StringUtils.endsWith(chords, contentType.getSpaceMarker()) && !StringUtils.startsWith(chordBuilder.value, contentType.getSpaceMarker())){
			chords.append(contentType.getSpaceMarker());
			texts.append(contentType.getSpaceMarker());
		}
		//if(chordBuilder.part().length()!=textBuilder.part().length())
			//System.out.println("FragmentBusinessImpl.format() : \r\n"+chordBuilder.value+"\r\n"+textBuilder.value);
		chords.append(chordBuilder.part());
		texts.append(textBuilder.part());
	}
	
	//TODO should not be called - to be removed so : space must be kept while parsing
	private void addSpaceSeperator(StringBuilder stringBuilder,String seperator){
		/*
		if(StringUtils.isNotEmpty(stringBuilder))
			stringBuilder.append(seperator);
		*/
	}
	
	private void pad(Locale locale, Fragment fragment,ContentType contentType,FragmentFormatOptions options, FragmentPartBuilder chords,FragmentPartBuilder texts){
		if(StringUtils.isNotEmpty(options.getPadding())){
			if(texts.value.length()>chords.value.length())
				chords.padding = StringUtils.repeat(options.getPadding(),texts.value.length()-chords.value.length());
			else
				texts.padding = StringUtils.repeat(options.getPadding(),chords.value.length()-texts.value.length());
		}
	}
	
	private void style(Locale locale, Fragment fragment,ContentType contentType,FragmentFormatOptions options, FragmentPartBuilder chords,FragmentPartBuilder texts){
		switch (contentType) {
		case TEXT:
			// Nothing to do
			break;
		case HTML:
			//Chord
			options.getChordTag().getAttributes().put("title", chords.value);
			chords.value = htmlBusiness.format(options.getChordTag(),chords.value);
			//Text
			texts.value = htmlBusiness.format(options.getTextTag(),texts.value);
			break;
		}
	}
	
	@Override
	public Fragment parse(Locale locale, String text) {
		ChordFormatOptions chordFormatOptions = KwordzBusinessLayer.getInstance().getDefaultChordFormatOptions();
		text = StringUtils.stripStart(text, " ");
		String noSpaces = StringUtils.strip(text);
		text = noSpaces+(StringUtils.endsWith(text,ContentType.TEXT.getSpaceMarker())? ContentType.TEXT.getSpaceMarker():"");
		String chordString = StringUtils.substringBetween(text, chordFormatOptions.getMarkerStart(), chordFormatOptions.getMarkerEnd());
		if(StringUtils.isNotEmpty(chordString))
			text = StringUtils.substringAfter(text, chordFormatOptions.getMarkerEnd());
		return new Fragment(text, StringUtils.isEmpty(chordString)?null:chordBusiness.parse(locale, chordString)
				//,StringUtils.endsWithAny(text, ContentType.TEXT.getNewLineMarker())?ContentType.TEXT.getNewLineMarker():" "
					);
	}
	
	@Override
	public String parseableForm(String chord, String text) {
		StringBuilder parseable = new StringBuilder();
		if(StringUtils.isNotBlank(chord))
			parseable.append(ChordFormatOptions.DEFAULT_MARKER_START+chord+ChordFormatOptions.DEFAULT_MARKER_END);
		parseable.append(StringUtils.stripStart(text, " "));
		return parseable.toString();
	}
	
	/**/
	@NoArgsConstructor
	private class FragmentPartBuilder {
		String value="",padding="";

		public FragmentPartBuilder(String value) {
			super();
			this.value = value;
		}
		
		public String part(){
			return value+padding;
		}
	}
	
}

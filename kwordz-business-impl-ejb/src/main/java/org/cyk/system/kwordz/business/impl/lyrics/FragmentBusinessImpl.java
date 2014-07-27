package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.lyrics.FragmentBusiness;
import org.cyk.system.kwordz.business.api.music.ChordBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.model.AbstractFormatOptions.ContentType;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.cyk.system.kwordz.persistence.api.lyrics.FragmentDao;

public class FragmentBusinessImpl extends AbstractMusicBusinessImpl<Fragment, FragmentDao,FragmentFormatOptions> implements FragmentBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ChordBusiness chordBusiness;
	
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
	public String format(Locale locale, Fragment fragment,FragmentFormatOptions options) {
		StringBuilder line = new StringBuilder();
		String text = StringUtils.defaultString(Boolean.TRUE.equals(options.getShowText())?fragment.getText():null);
		options.getChordFormatOptions().setShowMarker(Boolean.TRUE);
		String chord = StringUtils.defaultString(Boolean.TRUE.equals(options.getShowChord())?
				chordBusiness.format(locale, fragment.getChord(), options.getChordFormatOptions()):null);
		String result;
		if(Boolean.TRUE.equals(options.getChordAtLeft()))
			result = chord+text;
		else
			result = text+chord;
		if(result!=null && !result.trim().isEmpty())
			line.append(result);
		return line.toString();
		/*
		switch(options.getLayout()){
		case CHORD_LEFT_TEXT:
			options.getChordFormatOptions().setShowMarker(Boolean.TRUE);
			if(fragment.getChord()!=null)
				line.append(chordBusiness.format(locale, fragment.getChord(), options.getChordFormatOptions()));
			if(StringUtils.isNotEmpty(fragment.getText()))
				line.append(fragment.getText());
			break;
		case CHORD_RIGHT_LINE:
			
			break;
		case TEXT_ONLY:
			if(StringUtils.isNotEmpty(fragment.getText()))
				line.append(fragment.getText());
			break;
		case CHORD_ONLY:
			if(fragment.getChord()!=null)
				line.append(chordBusiness.format(locale,fragment.getChord(),options.getChordFormatOptions()));
			break;
		case CHORD_TOP_LINE:
			
			break;
		}
		return null;
		*/
	}

	@Override
	public void format(Locale locale, Fragment fragment,FragmentFormatOptions options, StringBuilder chords,StringBuilder texts) {
		String chord="";
		String text = StringUtils.defaultString(fragment.getText());
		int cl=0,tl = text.length();
		boolean html = ContentType.HTML.equals(options.getContentType());
		
		if(StringUtils.isNotEmpty(text))
			addSpaceSeperator(texts);
		
		if(fragment.getChord()!=null){
			if(Boolean.FALSE.equals(options.getChordFormatOptions().getShowMarker()))
				addSpaceSeperator(chords);
				//spaceChordLine(chords,texts," ");
			//options.getChordFormatOptions().setShowMarker(Boolean.FALSE);
			chord = chordBusiness.format(locale, fragment.getChord(), options.getChordFormatOptions());//contract
			cl = chord.length();
			/*
			chord = html?String.format(options.getChordHtmlTag(),
					chordBusiness.format(locale, fragment.getChord(), options.getChordFormatOptions())expand,chord):chord;
			*/
		}
		if(html)
			text =  String.format(options.getTextHtmlTag(),text);
		if(StringUtils.isNotEmpty(options.getPadding())){
			if(tl>cl)
				chord += StringUtils.repeat(options.getPadding(),tl-cl);
			else
				text += StringUtils.repeat(options.getPadding(),cl-tl);
		}
		chords.append(chord);
		texts.append(text);
	}
	
	private void addSpaceSeperator(StringBuilder stringBuilder){
		if(StringUtils.isNotEmpty(stringBuilder))
			stringBuilder.append(" ");
	}
	
	/*
	private void spaceChordLine(StringBuilder chordLine,StringBuilder textLine,String spaces){
		if(chordLine.length()>0 && chordLine.charAt(chordLine.length()-1)!=' '){
			chordLine.append(spaces);
			if(textLine!=null)
				textLine.append(spaces);
		}
	}
	*/
	
	@Override
	public Fragment parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
	}

	


	

	
}

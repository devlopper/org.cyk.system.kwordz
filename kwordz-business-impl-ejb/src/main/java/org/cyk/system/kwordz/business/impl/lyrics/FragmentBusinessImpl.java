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
		switch(options.getLayout()){
		case CHORD_LEFT_TEXT:
			
			break;
		case CHORD_RIGHT_LINE:
			
			break;
		case TEXT_ONLY:
			
			break;
		case CHORD_ONLY:
			
			break;
		case CHORD_TOP_LINE:
			
			break;
		}
		return null;
	}

	@Override
	public void format(Locale locale, Fragment fragment,FragmentFormatOptions options, StringBuilder chordLine,StringBuilder textLine) {
		String chord="";
		String text = StringUtils.defaultString(fragment.getText());
		int cl=0,tl = text.length();
		boolean html = ContentType.HTML.equals(options.getContentType());
		if(fragment.getChord()!=null){
			spaceChordLine(chordLine,textLine," ");
			chord = chordBusiness.format(locale, fragment.getChord(), options.getChordFormatOptions());//contract
			cl = chord.length();
			chord = html?String.format(options.getChordHtmlTag(),
					chordBusiness.format(locale, fragment.getChord(), options.getChordFormatOptions())/*expand*/,chord):chord;
		}
		if(html)
			text =  String.format(options.getTextHtmlTag(),text);
		if(tl>cl)
			chord += StringUtils.repeat(" ",tl-cl);
		else
			text += StringUtils.repeat(" ",cl-tl);

		chordLine.append(chord);
		textLine.append(text);
	}
	
	private void spaceChordLine(StringBuilder chordLine,StringBuilder textLine,String spaces){
		if(chordLine.length()>0 && chordLine.charAt(chordLine.length()-1)!=' '){
			chordLine.append(spaces);
			if(textLine!=null)
				textLine.append(spaces);
		}
	}
	
	@Override
	public Fragment parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
	}

	


	

	
}

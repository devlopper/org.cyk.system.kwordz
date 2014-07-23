package com.kwordz.text.formatter;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.kwordz.model.lyrics.Fragment;
import com.kwordz.text.parser.AbstractParser;

public class FragmentFormatter extends AbstractFormatterLayout<Fragment> implements Serializable{

	private static final long serialVersionUID = 1L;

	private ChordFormatter chordFormatter = new ChordFormatter();

	private String spanChordHtmlTag="<span class=\"chordClass\" title=\"%s\">%s</span>";
	private String spanTextHtmlTag="<span class=\"lyricClass\">%s</span>";
		
	@Override
	public void chordAtLeftOfLyrics(Fragment fragment, StringBuilder line) {
		if(fragment.getText()==null)
			line.append(AbstractParser.CHORD_START+chordFormatter.format(fragment.getChord())+AbstractParser.CHORD_END);
		else
			if(fragment.getChord()==null)
				line.append(fragment.getText());
			else
				line.append(AbstractParser.CHORD_START+chordFormatter.format(fragment.getChord()) +AbstractParser.CHORD_END+fragment.getText());
	}
	
	@Override
	public void chordAtRightOfLine(Fragment fragment,StringBuilder line) {
		
	}
	
	@Override
	public void chordAtTopOfLine(Fragment fragment, StringBuilder chordLine,StringBuilder textLine, boolean merged) {
		String chord="";
		if(!merged){
			spaceChordLine(chordLine, null,"   ");
			chord = chordFormatter.contract(fragment.getChord());
			chordLine.append( html?String.format(spanChordHtmlTag,chordFormatter.expand(fragment.getChord()),chord):chord );
			return;
		}
		
		String text = fragment.getText()==null?"":fragment.getText();

		int cl=0,tl = text.length();
		if(fragment.getChord()!=null){
			spaceChordLine(chordLine,textLine," ");
			chord = chordFormatter.contract(fragment.getChord());
			cl = chord.length();
			chord = html?String.format(spanChordHtmlTag,chordFormatter.expand(fragment.getChord()),chord):chord;
		}
		if(html)
			text =  String.format(spanTextHtmlTag,text);
		if(tl>cl)
			chord += StringUtils.repeat(" ",tl-cl);
		else
			text += StringUtils.repeat(" ",cl-tl);

		chordLine.append(chord);
		textLine.append(text);
	}
	
	@Override
	public void chordOnly(Fragment fragment, StringBuilder line) {
		if(fragment.getChord()!=null){
			String chord = chordFormatter.contract(fragment.getChord());
			line.append(html?String.format(spanChordHtmlTag,chordFormatter.expand(fragment.getChord()),chord):chord);
		}
	}
	
	@Override
	public void lyricsOnly(Fragment fragment, StringBuilder line) {
		if(fragment.getText()!=null)
			line.append(html?String.format(spanTextHtmlTag,fragment.getText()):fragment.getText());
	}
	
	private void spaceChordLine(StringBuilder chordLine,StringBuilder textLine,String spaces){
		if(chordLine.length()>0 && chordLine.charAt(chordLine.length()-1)!=' '){
			chordLine.append(spaces);
			if(textLine!=null)
				textLine.append(spaces);
		}
	}
	public Layout getLayout() {
		return layout;
	}
	public ChordFormatter getChordFormatter() {
		return chordFormatter;
	}
	public void setChordFormatter(ChordFormatter chordFormatter) {
		this.chordFormatter = chordFormatter;
	}
	
}

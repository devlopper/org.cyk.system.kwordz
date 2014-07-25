package org.cyk.system.kwordz.business.impl.lyrics.formatter;

/*
public class FragmentFormatter extends AbstractFormatterLayout<Fragment> implements Serializable{

	private static final long serialVersionUID = 1L;

	private ChordFormatter chordFormatter = new ChordFormatter();

	private String spanChordHtmlTag="<span class=\"chordClass\" title=\"%s\">%s</span>";
	private String spanTextHtmlTag="<span class=\"lyricClass\">%s</span>";
		
	@Override
	public void chordAtRightOfLine(Fragment fragment,StringBuilder line) {
		
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
*/

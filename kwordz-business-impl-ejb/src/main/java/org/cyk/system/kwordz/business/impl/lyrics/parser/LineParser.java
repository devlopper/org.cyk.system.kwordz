package org.cyk.system.kwordz.business.impl.lyrics.parser;

/*
public class LineParser extends AbstractParser<Line>{
	
	private static final long serialVersionUID = 1L;
	private ChordParser chordParser = new ChordParser();
	
	public Line parse(String lyricsLine) throws Exception{
		if(StringUtils.trimToNull(lyricsLine)==null)
			return null;
		Line line = new Line();
		String st = startTag(LINE),et = endTag(LINE);
		if(lyricsLine.startsWith(st) && lyricsLine.endsWith(et)){
			lyricsLine = StringUtils.stripStart(lyricsLine, st); 
			lyricsLine = StringUtils.stripEnd(lyricsLine, et);
			line.setMerged(false);
		}
		chordParser = new ChordParser();
		int chordStartIndex,chordEndIndex;
		String chord,text;
		while(!lyricsLine.isEmpty()){
			chord = null;
			text = null;
			get the chord
			if(lyricsLine.startsWith(CHORD_START)){
				chordEndIndex = lyricsLine.indexOf(CHORD_END);
				if(chordEndIndex<0)
					throw new ParseException("No chord found", 0);
				chord = lyricsLine.substring(1,chordEndIndex);
				lyricsLine = lyricsLine.substring(chordEndIndex+1);
			}
			get the text
			chordStartIndex = lyricsLine.indexOf(CHORD_START);
			if(chordStartIndex<0){
				text = lyricsLine;
				lyricsLine = "";
			}else{
				text = lyricsLine.substring(0,chordStartIndex);
				lyricsLine = lyricsLine.substring(chordStartIndex);
			}
			if(chord==null || chord.isEmpty())
				line.getFragments().add(new Fragment(text));
			else
				line.getFragments().add(new Fragment(text, chordParser.parse(chord)));	
		}
		return line;
	}
	
}
*/

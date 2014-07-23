package org.cyk.system.kwordz.business.impl.lyrics.formatter;

/*
public class LineFormatter extends AbstractFormatterLayout<Line> implements Serializable{

	private static final long serialVersionUID = 1L;
	private FragmentFormatter fragmentFormatter = new FragmentFormatter();
	
	public LineFormatter() {
		register(fragmentFormatter);
	}
	
	@Override
	public String format(Line line, Object... parameters) {
		StringBuilder chordLine = new StringBuilder();
		StringBuilder textLine = new StringBuilder();
		super.format(line,chordLine,textLine,line.isMerged());
		String result;
		switch(layout){
		case CHORD_TOP_LINE:
			if(line.isMerged())
				if(chordLine.toString().trim().isEmpty())
					result = textLine.toString();
				else
					result = chordLine+lineBreak()+textLine;	
			else
				result = chordLine.toString();
			break;
		default:
			result = chordLine.toString();
		}
		if(edit)
			if(!line.isMerged())
				result = AbstractParser.tag(AbstractParser.LINE,result);
		return result;
	}
	
	@Override
	public void chordAtLeftOfLyrics(Line line, StringBuilder l) {
		for(Fragment fragment : line.getFragments())
			fragmentFormatter.format(fragment, l);
	}
	
	@Override
	public void chordAtRightOfLine(Line data, StringBuilder line) {
		
	}
	
	@Override
	public void chordAtTopOfLine(Line line, StringBuilder chordLine,StringBuilder textLine, boolean merged) {
		for(Fragment fragment : line.getFragments())
			fragmentFormatter.format(fragment, chordLine,textLine,line.isMerged());
		
	}
	
	@Override
	public void chordOnly(Line line, StringBuilder l) {
		for(Fragment fragment : line.getFragments())
			fragmentFormatter.format(fragment, l);
	}
	
	@Override
	public void lyricsOnly(Line line, StringBuilder l) {
		for(Fragment fragment : line.getFragments())
			fragmentFormatter.format(fragment, l);
	}
	
	public FragmentFormatter getFragmentFormatter() {
		return fragmentFormatter;
	}

}
*/

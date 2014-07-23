package org.cyk.system.kwordz.business.impl.lyrics.formatter;

/*
public abstract class AbstractFormatterLayout<T> extends AbstractFormatter<T>  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public enum Layout {
		CHORD_TOP_LINE("lyrics.layout.chord.top.line"),
		CHORD_LEFT_TEXT("lyrics.layout.chord.left.text"),
		CHORD_RIGHT_LINE("lyrics.layout.chord.right.line"),
		CHORD_ONLY("lyrics.layout.chord.only"),
		TEXT_ONLY("lyrics.layout.text.only");
		
		private String i18nLabelId;
		private Layout(String i18nLabelId) {
			this.i18nLabelId = i18nLabelId;
		}

		@Override
		public String toString() {
			return KwordZModelLayer.getInstance().getI18n().get(i18nLabelId);
		}
	}
	
	protected Layout layout=Layout.CHORD_TOP_LINE; 
	
	@Override
	public String format(T object, Object... parameters) {
		switch(layout){
		case CHORD_LEFT_TEXT: 
			chordAtLeftOfLyrics(object, (StringBuilder) parameters[0]);
			break;
		case CHORD_RIGHT_LINE:
			chordAtRightOfLine(object, (StringBuilder) parameters[0]);
			break;
		case CHORD_TOP_LINE:
			chordAtTopOfLine(object, (StringBuilder)parameters[0], (StringBuilder)parameters[1], (Boolean)parameters[2]);
			break;
		case CHORD_ONLY: 
			chordOnly(object, (StringBuilder) parameters[0]);
			break;
		case TEXT_ONLY: 
			lyricsOnly(object, (StringBuilder) parameters[0]);
			break;
		}
		return null;
	}
	
	public abstract void chordAtTopOfLine(T data,StringBuilder chordLine,StringBuilder textLine,boolean merged);
	
	public abstract void chordAtLeftOfLyrics(T data,StringBuilder line);
	
	public abstract void chordAtRightOfLine(T data,StringBuilder line);
	
	public abstract void lyricsOnly(T data,StringBuilder line);
	
	public abstract void chordOnly(T data,StringBuilder line);
	
	public void setLayout(Layout layout) {
		this.layout = layout;
		if(formatters!=null)
			for(AbstractFormatter<?> formatter : formatters)
				if(formatter instanceof AbstractFormatterLayout)
					((AbstractFormatterLayout<?>)formatter).setLayout(layout);
	}
	public Layout getLayout() {
		return layout;
	}
}
*/

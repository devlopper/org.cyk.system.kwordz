package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.lyrics.PartBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.lyrics.Lyrics;
import org.cyk.system.kwordz.model.lyrics.LyricsFormatOptions;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.persistence.api.lyrics.LyricsDao;

public class LyricsBusinessImpl extends AbstractMusicBusinessImpl<Lyrics, LyricsDao,LyricsFormatOptions> implements LyricsBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private PartBusiness partBusiness;
	
	@Inject
	public LyricsBusinessImpl(LyricsDao dao) { 
		super(dao);    
	}

	@Override
	public void transpose(Lyrics lyrics, Integer distance) {
		for(Part part : lyrics.getParts())
			partBusiness.transpose(part, distance);
	}

	@Override
	public String format(Locale locale, Lyrics lyrics,LyricsFormatOptions options) {
		StringBuilder stringBuilder = new StringBuilder();
		for(Part part : lyrics.getParts())
			stringBuilder.append(partBusiness.format(locale,part)+"\r\n");
		return stringBuilder.toString();
	}

	@Override
	public String format(Locale locale, Lyrics lyrics) {
		return format(locale, lyrics, KwordzBusinessLayer.getInstance().getDefaultLyricsFormatOptions());
	}

	@Override
	public Lyrics parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**/
	/*
	private String formatPart(Locale locale,Part part,LyricsFormatOptions options){
		StringBuilder stringBuilder = new StringBuilder();
		if(Boolean.TRUE.equals(options.getShowMetadata()) && !Boolean.TRUE.equals(options.getEditable()) && part.getType()!=null)
			stringBuilder.append(part.getType()+lineBreak());
		for(Line line : part.getLines())
			stringBuilder.append(formatLine(locale, line, options)+lineBreak());
				
		if(Boolean.TRUE.equals(options.getEditable())){
			//append meta data
			stringBuilder = new StringBuilder( 
			AbstractParser.startTag(AbstractParser.COMPONENT)+lineBreak()+
				(part.getType()==null?"":AbstractParser.tag(AbstractParser.TYPE, part.getType().name())+lineBreak())+
				(part.getRepeatTimes()==null?"":AbstractParser.tag(AbstractParser.REPEAT, part.getRepeatTimes())+lineBreak())+
				stringBuilder+
			AbstractParser.endTag(AbstractParser.COMPONENT)
			+lineBreak()+lineBreak() );
		}
		
		if(ContentType.HTML.equals(options.getContentType())){
			//append meta data
			String repeateTimes = part.getRepeatTimes();
			if(repeateTimes==null)
				repeateTimes = "";
			else{
				repeateTimes = StringUtils.isNumeric(repeateTimes)?repeateTimes+" times":"---";
			}
			stringBuilder.insert(0,"<div class=\"lyricsComponent lyricsComponent"+part.getType()+"\"><p class=\"lyricsComponentTitle\">"+
					(part.getType()==null?"---":part.getType())+" "+repeateTimes+"</p> ").append("</div>");
		}
				
		return stringBuilder.toString();
	}
	
	private String formatLine(Locale locale,Line line,LyricsFormatOptions options){
		StringBuilder chordLine = new StringBuilder();
		StringBuilder textLine = new StringBuilder();
		super.format(line,chordLine,textLine,Boolean.TRUE.equals(options.get) line.isMerged());
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
	*/

}

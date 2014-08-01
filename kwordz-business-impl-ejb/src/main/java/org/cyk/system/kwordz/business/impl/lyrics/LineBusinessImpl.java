package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.lyrics.FragmentBusiness;
import org.cyk.system.kwordz.business.api.lyrics.LineBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.Line;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions.ChordLocation;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.persistence.api.lyrics.LineDao;
import org.cyk.system.root.model.ContentType;

public class LineBusinessImpl extends AbstractMusicBusinessImpl<Line, LineDao,LineFormatOptions> implements LineBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private FragmentBusiness fragmentBusiness;
	
	@Inject
	public LineBusinessImpl(LineDao dao) { 
		super(dao);    
	}

	@Override
	public void transpose(Line line, Integer distance) {
		for(Fragment fragment : line.getFragments())
			fragmentBusiness.transpose(fragment, distance);
	}
	
	@Override
	protected LineFormatOptions defaultFormatOptions() {
		return KwordzBusinessLayer.getInstance().getDefaultLineFormatOptions();
	}

	@Override
	public String format(Locale locale, Line line,ContentType contentType, LineFormatOptions options) {
		if(line==null)
			return null;
		StringBuilder builder = new StringBuilder();
		StringBuilder chords;
		switch(options.getChordLocation()){
		case FOLLOW_FRAGMENT:
			Collection<String> collection = new ArrayList<>();
			for(Fragment fragment : line.getFragments())
				collection.add(fragmentBusiness.format(locale, fragment,contentType, options.getFragmentFormatOptions()));
			builder.append(StringUtils.join(collection.iterator(),contentType.getSpaceMarker()));
			break;
		case TOP:case LEFT:case RIGHT:
			chords = new StringBuilder();
			
			options.getFragmentFormatOptions().setPadding(ChordLocation.TOP.equals(options.getChordLocation())?contentType.getSpaceMarker():null);
			options.getFragmentFormatOptions().getChordFormatOptions().setShowMarker(!ChordLocation.TOP.equals(options.getChordLocation()));
			for(Fragment fragment : line.getFragments())
				fragmentBusiness.format(locale, fragment, contentType,options.getFragmentFormatOptions(), chords, builder);
			if(ChordLocation.LEFT.equals(options.getChordLocation()))
				builder = join(chords,builder, options.getFragmentFormatOptions().getShowChord(), options.getFragmentFormatOptions().getShowText());
			else if(ChordLocation.RIGHT.equals(options.getChordLocation()))
				builder = join(builder,chords, options.getFragmentFormatOptions().getShowText(), options.getFragmentFormatOptions().getShowChord());
				//builder.append(chords);
			else if(ChordLocation.TOP.equals(options.getChordLocation()))
				if(Boolean.TRUE.equals(options.getShowEmptyLineChord()) || !StringUtils.isBlank(chords))
					builder.insert(0,chords+contentType.getNewLineMarker());
			break;
		}
		return StringUtils.stripEnd(builder.toString(),null);
	}
	
	@Override
	public Line parse(Locale locale, String text) {
		Line line = new Line();
		ChordFormatOptions chordFormatOptions = KwordzBusinessLayer.getInstance().getDefaultChordFormatOptions();
		Integer markerChordStartIndex =  StringUtils.indexOf(text, chordFormatOptions.getMarkerStart());
		if(markerChordStartIndex>0){
			line.getFragments().add(fragmentBusiness.parse(locale, StringUtils.substring(text, 0, markerChordStartIndex)));
			text = StringUtils.substring(text, markerChordStartIndex);
		}
		
		String fragmentString;
		while(StringUtils.isNotEmpty(text)){
			Integer fragmentEndIndex = StringUtils.indexOf(text, chordFormatOptions.getMarkerStart());
			if(fragmentEndIndex>=0)
				fragmentEndIndex = StringUtils.indexOf(text, chordFormatOptions.getMarkerStart(),fragmentEndIndex+1);
			if(fragmentEndIndex==-1)
				fragmentEndIndex = text.length();
			fragmentString = StringUtils.substring(text, 0,fragmentEndIndex);
			line.getFragments().add(fragmentBusiness.parse(locale, fragmentString));
			text = StringUtils.substring(text, fragmentString.length());
		}
		return line;
	}
	
	/**/
	
	private StringBuilder join(StringBuilder sb1,StringBuilder sb2,Boolean showSb1,Boolean showSb2){
		if(Boolean.TRUE.equals(showSb1))
			if(Boolean.TRUE.equals(showSb2))
				return sb1.append(sb2);
			else
				return sb1;
		else if(Boolean.TRUE.equals(showSb2))
			return sb2;
		else
			return new StringBuilder();
	}

	
}

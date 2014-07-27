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
import org.cyk.system.kwordz.model.lyrics.Fragment;
import org.cyk.system.kwordz.model.lyrics.FragmentFormatOptions;
import org.cyk.system.kwordz.model.lyrics.Line;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions.ChordLocation;
import org.cyk.system.kwordz.persistence.api.lyrics.LineDao;

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
	public String format(Locale locale, Line line, LineFormatOptions options) {
		StringBuilder builder = new StringBuilder();
		StringBuilder chords;
		switch(options.getChordLocation()){
		case FOLLOW_FRAGMENT:
			Collection<String> collection = new ArrayList<>();
			for(Fragment fragment : line.getFragments())
				collection.add(fragmentBusiness.format(locale, fragment, options.getFragmentFormatOptions()));
			builder.append(StringUtils.join(collection.iterator()," "));
			break;
		case TOP:case LEFT:case RIGHT:
			chords = new StringBuilder();
			options.getFragmentFormatOptions().setPadding(ChordLocation.TOP.equals(options.getChordLocation())?FragmentFormatOptions.PADDING:null);
			options.getFragmentFormatOptions().getChordFormatOptions().setShowMarker(!ChordLocation.TOP.equals(options.getChordLocation()));
			for(Fragment fragment : line.getFragments())
				fragmentBusiness.format(locale, fragment, options.getFragmentFormatOptions(), chords, builder);
			if(ChordLocation.LEFT.equals(options.getChordLocation()))
				builder = join(chords,builder, options.getFragmentFormatOptions().getShowChord(), options.getFragmentFormatOptions().getShowText());
			else if(ChordLocation.RIGHT.equals(options.getChordLocation()))
				builder = join(builder,chords, options.getFragmentFormatOptions().getShowText(), options.getFragmentFormatOptions().getShowChord());
				//builder.append(chords);
			else if(ChordLocation.TOP.equals(options.getChordLocation()))
				builder.insert(0,chords+"\r\n");
			break;
		}
		return builder.toString();
	}
	
	@Override
	public Line parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
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

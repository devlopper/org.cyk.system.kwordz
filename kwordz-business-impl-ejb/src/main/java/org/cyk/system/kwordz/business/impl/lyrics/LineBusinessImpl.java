package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.LineBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.model.lyrics.Line;
import org.cyk.system.kwordz.model.lyrics.LineFormatOptions;
import org.cyk.system.kwordz.persistence.api.lyrics.LineDao;

public class LineBusinessImpl extends AbstractMusicBusinessImpl<Line, LineDao,LineFormatOptions> implements LineBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
		
	@Inject
	public LineBusinessImpl(LineDao dao) { 
		super(dao);    
	}

	@Override
	public void transpose(Line type, Integer distance) {
		// TODO Auto-generated method stub		
	}

	@Override
	public String format(Locale locale, Line object, LineFormatOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Line parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

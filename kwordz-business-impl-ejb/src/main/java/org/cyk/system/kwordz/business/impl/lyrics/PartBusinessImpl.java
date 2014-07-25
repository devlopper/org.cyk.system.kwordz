package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.lyrics.PartBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.model.lyrics.PartFormatOptions;
import org.cyk.system.kwordz.persistence.api.lyrics.PartDao;

public class PartBusinessImpl extends AbstractMusicBusinessImpl<Part, PartDao,PartFormatOptions> implements PartBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public PartBusinessImpl(PartDao dao) { 
		super(dao);    
	}


	@Override
	public void transpose(Part type, Integer distance) {
		// TODO Auto-generated method stub		
	}


	@Override
	public String format(Locale locale, Part object, PartFormatOptions options) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Part parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

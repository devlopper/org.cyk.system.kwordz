package org.cyk.system.kwordz.business.impl.lyrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.business.api.lyrics.LineBusiness;
import org.cyk.system.kwordz.business.api.lyrics.PartBusiness;
import org.cyk.system.kwordz.business.impl.AbstractMusicBusinessImpl;
import org.cyk.system.kwordz.model.lyrics.Line;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.model.lyrics.PartFormatOptions;
import org.cyk.system.kwordz.persistence.api.lyrics.PartDao;

public class PartBusinessImpl extends AbstractMusicBusinessImpl<Part, PartDao,PartFormatOptions> implements PartBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject private LineBusiness lineBusiness;
	
	@Inject
	public PartBusinessImpl(PartDao dao) { 
		super(dao);    
	}

	@Override
	public void transpose(Part part, Integer distance) {
		for(Line line : part.getLines())
			lineBusiness.transpose(line, distance);
	}

	@Override
	public String format(Locale locale, Part part, PartFormatOptions options) {
		StringBuilder builder = new StringBuilder();
		Collection<String> collection = new ArrayList<>();
		for(Line line : part.getLines())
			collection.add(lineBusiness.format(locale, line, options.getLineFormatOptions()));
		
		builder.append(StringUtils.join(collection.iterator(),"\r\n"));
		return builder.toString();
	}

	@Override
	public Part parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
	}	
}

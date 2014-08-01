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
import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.lyrics.Line;
import org.cyk.system.kwordz.model.lyrics.Part;
import org.cyk.system.kwordz.model.lyrics.PartFormatOptions;
import org.cyk.system.kwordz.persistence.api.lyrics.PartDao;
import org.cyk.system.root.model.ContentType;

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
	protected PartFormatOptions defaultFormatOptions() {
		return KwordzBusinessLayer.getInstance().getDefaultPartFormatOptions();
	}

	@Override
	public String format(Locale locale, Part part,ContentType contentType, PartFormatOptions options) {
		StringBuilder builder = new StringBuilder();
		Collection<String> collection = new ArrayList<>();
		for(Line line : part.getLines())
			collection.add(lineBusiness.format(locale, line,contentType, options.getLineFormatOptions()));
		
		builder.append(StringUtils.join(collection.iterator(),contentType.getNewLineMarker()));
		return builder.toString();
	}

	@Override
	public Part parse(Locale locale, String text) {
		Part part = new Part();
		String[] lines = StringUtils.split(text, "\r\n");
		for(String line : lines)
			part.getLines().add(lineBusiness.parse(locale, line));
		return part;
	}	
}

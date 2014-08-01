package org.cyk.system.kwordz.business.impl.music;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.music.ScaleBusiness;
import org.cyk.system.kwordz.business.api.music.ScaleStructureBusiness;
import org.cyk.system.kwordz.model.music.Scale;
import org.cyk.system.kwordz.model.music.ScaleFormatOptions;
import org.cyk.system.kwordz.model.music.ScaleStructure;
import org.cyk.system.kwordz.persistence.api.music.ScaleDao;
import org.cyk.system.root.model.ContentType;

public class ScaleBusinessImpl extends AbstractNoteCollectionBusinessImpl<ScaleStructure,Scale, ScaleDao,ScaleStructureBusiness,ScaleFormatOptions> implements ScaleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ScaleBusinessImpl(ScaleDao dao,ScaleStructureBusiness structureBusiness) { 
		super(dao,structureBusiness);   
	}
	
	@Override
	protected ScaleFormatOptions defaultFormatOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String format(Locale locale, Scale object,ContentType contentType ,ScaleFormatOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scale parse(Locale locale, String text) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}

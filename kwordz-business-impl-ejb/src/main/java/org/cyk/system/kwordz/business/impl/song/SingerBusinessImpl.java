package org.cyk.system.kwordz.business.impl.song;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.kwordz.business.api.song.SingerBusiness;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.persistence.api.song.SingerDao;
import org.cyk.system.root.business.impl.party.AbstractPartyBusinessImpl;

public class SingerBusinessImpl extends AbstractPartyBusinessImpl<Singer, SingerDao> implements SingerBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public SingerBusinessImpl(SingerDao dao) { 
		super(dao);    
	} 
	
}

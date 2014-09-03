package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.net.URI;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInfos<ENTITY> implements Serializable {

	private static final long serialVersionUID = 5367546224286620639L;

	protected ENTITY entity;
	protected Boolean infosAtBottom=Boolean.TRUE;
	protected URI thumbnailUri;
	
}

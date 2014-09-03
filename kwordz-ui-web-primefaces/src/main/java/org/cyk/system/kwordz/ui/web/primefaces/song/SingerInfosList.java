package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.song.Singer;

@Getter @Setter
public class SingerInfosList extends AbstractInfosList<Singer, SingerInfos> implements Serializable {

	private static final long serialVersionUID = -6356004259181731404L;

	public SingerInfosList(Collection<Singer> list) {
		super(list);
		columns = 5;
	}

}

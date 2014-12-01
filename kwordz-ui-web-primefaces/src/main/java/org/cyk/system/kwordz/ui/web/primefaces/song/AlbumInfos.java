package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.kwordz.model.song.Album;
import org.cyk.ui.web.primefaces.Tree;

@Getter 
public class AlbumInfos extends AbstractInfos<Album> implements Serializable {

	private static final long serialVersionUID = 3119663959677343932L;

	private Tree songsTree;

}

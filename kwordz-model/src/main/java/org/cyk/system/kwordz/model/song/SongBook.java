package org.cyk.system.kwordz.model.song;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @Entity @NoArgsConstructor
public class SongBook extends AbstractEnumeration implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(fetch=FetchType.LAZY) @OrderColumn private List<Song> songs = new LinkedList<Song>();
	
}

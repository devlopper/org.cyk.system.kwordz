package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.language.Language;

@Getter @Setter @Entity @NoArgsConstructor
public class Lyrics extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final int LYRICS_LENGTH = 1024 * 4;
	
	@ManyToOne
	private Language language;
	
	/**
	 * Unstructured text
	 */
	@Basic(fetch=FetchType.LAZY) @Column(length=LYRICS_LENGTH) 
	private String textOnly;
	
	/**
	 * Structured text
	 */
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<Part> parts = new LinkedList<Part>();
	
	
}

package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity @NoArgsConstructor
public class Lyrics extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final int LYRICS_LENGTH = 1024 * 4;
	
	/**
	 * language code (fr,en,...)
	 */
	private String localeCode;
	
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

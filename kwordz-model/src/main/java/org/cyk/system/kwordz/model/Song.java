package org.cyk.system.kwordz.model;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity
public class Song extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -2704316497891411538L;

}

package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class Scale extends AbstractNoteCollection<ScaleStructure> implements Serializable{

	private static final long serialVersionUID = 1L;

	

}

package org.cyk.system.kwordz.model.music;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class ChordStructure extends Structure implements Serializable{
		
	private static final long serialVersionUID = 1L;
	
	@ElementCollection
	private Set<String> symbols = new HashSet<>();
	
}

package org.cyk.system.kwordz.model.music;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

/**
 * Set of interval
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity @NoArgsConstructor
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Structure extends AbstractEnumeration implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected List<Integer> intervals = new LinkedList<Integer>();
	
	
}

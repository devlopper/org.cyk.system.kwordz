package org.cyk.system.kwordz.model.music;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.DataTreeType;

@Getter @Setter @NoArgsConstructor @Entity 
public class MusicKind extends DataTreeType implements Serializable  {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4876159772208660975L;

	public MusicKind(DataTreeType parent, String code,String label) {
        super(parent, code,label);
    }    
    
}


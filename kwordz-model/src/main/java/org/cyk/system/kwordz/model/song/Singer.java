package org.cyk.system.kwordz.model.song;

import java.io.Serializable;
import java.net.URL;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @Entity @NoArgsConstructor @AllArgsConstructor
public class Singer extends AbstractEnumeration implements Serializable{

	private static final long serialVersionUID = 1L;

	private URL officialWebSite;
	
	
}

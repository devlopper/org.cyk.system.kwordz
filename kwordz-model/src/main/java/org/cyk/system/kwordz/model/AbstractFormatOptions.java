package org.cyk.system.kwordz.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	public enum ContentType {TEXT,HTML}
	
	protected ContentType contentType;
	protected Boolean editable;
	
}

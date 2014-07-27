package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;

@Getter @Setter
public class PartFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	private LineFormatOptions lineFormatOptions = new LineFormatOptions();
	
}

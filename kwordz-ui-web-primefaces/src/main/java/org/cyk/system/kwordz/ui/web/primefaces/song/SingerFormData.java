package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.ui.api.editor.AbstractFormData;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter
public class SingerFormData extends AbstractFormData<Singer> implements Serializable {

	private static final long serialVersionUID = 235306190360023398L;

	@UIField
	@NotNull
	private String name;

}

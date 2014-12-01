package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class SingerFormData extends AbstractFormModel<Singer> implements Serializable {

	private static final long serialVersionUID = 235306190360023398L;

	@Input
	@InputText
	@NotNull
	private String name;

}

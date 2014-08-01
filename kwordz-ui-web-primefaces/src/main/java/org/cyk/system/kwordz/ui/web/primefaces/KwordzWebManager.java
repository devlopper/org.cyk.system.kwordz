package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.ui.web.api.WebManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=1)
public class KwordzWebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3659075703567481942L;

	@Inject private WebManager webManager;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		webManager.setDecoratedTemplateInclude("/org.cyk.ui.web.primefaces.kwordz/include/template.xhtml");
	}
	
}

package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.PrimefacesDefaultDesktopLayoutManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=1)
public class KwordzWebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3659075703567481942L;

	@Inject private WebManager webManager;
	@Inject private PrimefacesDefaultDesktopLayoutManager primefacesDefaultDesktopLayoutManager;
	@Inject private LyricsBusiness lyricsBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		webManager.setDecoratedTemplateInclude("/org.cyk.ui.web.primefaces.kwordz/include/template.xhtml");
		primefacesDefaultDesktopLayoutManager.setNorthInclude("/org.cyk.ui.web.primefaces.kwordz/include/layout/default/north.xhtml");
	}
	
	@Named @Produces
    public List<SelectItem> getParsableLyricsLocales(){
		List<SelectItem> locales = new ArrayList<>();
		for(Locale locale : lyricsBusiness.findParsableLocales())
			locales.add(new SelectItem(locale, locale.toString()));
    	return locales;
    }
}

package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.ui.web.primefaces.song.SingerFormData;
import org.cyk.ui.api.MenuManager.Type;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.web.primefaces.AbstractContextListener;

@WebListener
public class ContextListener extends AbstractContextListener implements Serializable {

	private static final long serialVersionUID = 3659075703567481942L;

	@Override
	protected void initialisation() {
		super.initialisation();
		webManager.setDecoratedTemplateInclude("/org.cyk.ui.web.primefaces.kwordz/include/template.xhtml");
		defaultDesktopLayoutManager.setNorthInclude("/org.cyk.ui.web.primefaces.kwordz/include/layout/default/north.xhtml");
		defaultDesktopLayoutManager.setWestInclude("/org.cyk.ui.web.primefaces.kwordz/include/layout/default/west.xhtml");
	
	}
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		IdentifiableConfiguration config = new IdentifiableConfiguration(Singer.class, SingerFormData.class);
		uiManager.registerConfiguration(config);
	}
	
	/**/
	
	@Override
	public void menu(UserSession userSession, UIMenu menu, Type type) {
		// TODO Auto-generated method stub
		
	}

}

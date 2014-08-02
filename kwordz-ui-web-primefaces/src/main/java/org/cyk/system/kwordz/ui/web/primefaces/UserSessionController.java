package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.web.primefaces.Command;
import org.cyk.ui.web.primefaces.PrimefacesMessageManager;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;

@SessionScoped @Named
public class UserSessionController extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3480243608840186805L;

	@Getter private String searchInput;
	@Getter private Command searchCommand;
	@Inject transient private PrimefacesMessageManager messageManager;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		DefaultCommandable primefacesEditCommandable = new DefaultCommandable();
		primefacesEditCommandable.setLabel(UIManager.getInstance().text("search"));
		primefacesEditCommandable.setCommand(new DefaultCommand());
		primefacesEditCommandable.getCommand().setMessageManager(messageManager);
		primefacesEditCommandable.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 3913474940359268490L;
			@Override
			protected Object __execute__(Object parameter) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		searchCommand =  new Command(primefacesEditCommandable);
	}
}

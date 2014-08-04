package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
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
		DefaultCommandable commandable = new DefaultCommandable();
		commandable.setLabel(UIManager.getInstance().text("command.search"));
		commandable.setCommand(new DefaultCommand());
		commandable.getCommand().setMessageManager(messageManager);
		commandable.setShowLabel(Boolean.FALSE);
		commandable.setIconType(IconType.ACTION_SEARCH);
		commandable.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 3913474940359268490L;
			@Override
			protected Object __execute__(Object parameter) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		searchCommand =  new Command(commandable);
	}
}

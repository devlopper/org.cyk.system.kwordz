package org.cyk.system.kwordz.ui.web.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.api.WebSession;
import org.cyk.ui.web.primefaces.Command;
import org.cyk.ui.web.primefaces.PrimefacesMessageManager;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@SessionScoped @Named
public class UserSessionController extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3480243608840186805L;

	@Inject private LyricsBusiness lyricsBusiness;
	@Inject transient private LanguageBusiness languageBusiness;
	//@Inject private NoteBusiness noteBusiness;
	
	@Getter private String searchInput;
	@Getter private Command searchCommand;
	@Inject transient private PrimefacesMessageManager messageManager;
	@Inject transient private WebSession session;
	
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
	
	@Produces @Named @SessionScoped
	public List<SelectItem> getParsableLocalesItems() {
		List<SelectItem> list = new ArrayList<>();
		for(Locale locale : lyricsBusiness.findParsableLocales())
			list.add(new SelectItem(locale, languageBusiness.findText(session.getLocale(),locale)));
		return list;
	}
	
	/*@Produces @Named @SessionScoped
	public List<SelectItem> getTonesItems() {
		List<SelectItem> list = new ArrayList<>();
		Long i=0l;
		for(Note note : noteBusiness.findTones()){
			note.setIdentifier(i++);
			list.add(new SelectItem(note, noteBusiness.format(session.getLocale(), note)));
		}
		return list;
	}*/
}

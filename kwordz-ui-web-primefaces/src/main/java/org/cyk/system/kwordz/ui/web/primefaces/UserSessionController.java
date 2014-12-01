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
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.api.WebSession;
import org.cyk.ui.web.primefaces.PrimefacesMessageManager;
import org.cyk.utility.common.cdi.AbstractBean;

@SessionScoped @Named
public class UserSessionController extends AbstractBean implements Serializable,CommandListener {

	private static final long serialVersionUID = -3480243608840186805L;

	@Inject private LyricsBusiness lyricsBusiness;
	@Inject transient private LanguageBusiness languageBusiness;
	//@Inject private NoteBusiness noteBusiness;
	
	@Getter private String searchInput;
	@Getter private UICommandable searchCommandable;
	@Inject transient private PrimefacesMessageManager messageManager;
	@Inject transient private WebSession session;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		searchCommandable = UIProvider.getInstance().createCommandable(this, "command.search", IconType.ACTION_SEARCH, null, null);
		searchCommandable.getCommand().setMessageManager(messageManager);
		searchCommandable.setShowLabel(Boolean.FALSE);
		searchCommandable.setIconType(IconType.ACTION_SEARCH);
	}
	
	@Produces @Named @SessionScoped
	public List<SelectItem> getParsableLocalesItems() {
		List<SelectItem> list = new ArrayList<>();
		for(Locale locale : lyricsBusiness.findParsableLocales())
			list.add(new SelectItem(locale, languageBusiness.findText(session.getLocale(),locale)));
		return list;
	}

	/**/
	
	@Override
	public Object fail(UICommand arg0, Object arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notificationMessageIdAfterServe(UICommand arg0, Object arg1,
			AfterServeState arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean notifyAfterServe(UICommand arg0, AfterServeState arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serve(UICommand arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object succeed(UICommand arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transfer(UICommand arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean validate(UICommand arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
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

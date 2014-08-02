package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.ui.web.primefaces.LyricsParser;
import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.Command;
import org.cyk.utility.common.AbstractMethod;

@Named @ViewScoped
public class SongEditPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private LyricsBusiness lyricsBusiness;

	@Inject @Getter private LyricsParser parser;
	private Crud crud;
	@Getter private Command primefacesEditCommand;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		DefaultCommandable primefacesEditCommandable = new DefaultCommandable();
		primefacesEditCommandable.setLabel(uiManager.text("ok"));
		primefacesEditCommandable.setCommand(new DefaultCommand());
		primefacesEditCommandable.getCommand().setMessageManager(getMessageManager());
		primefacesEditCommandable.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 3913474940359268490L;
			@Override
			protected Object __execute__(Object parameter) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		primefacesEditCommand =  new Command(primefacesEditCommandable);
		
	}
	
}

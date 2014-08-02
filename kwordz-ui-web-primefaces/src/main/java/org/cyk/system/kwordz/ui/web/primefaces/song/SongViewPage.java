package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.ui.web.primefaces.LyricsParser;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.Command;
import org.cyk.utility.common.AbstractMethod;

@Named @ViewScoped
public class SongViewPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private LyricsBusiness lyricsBusiness;
	@Inject private SongBusiness songBusiness;
  
	@Getter private Song song;
	@Getter private SongOverviewList relatedSongList;
	@Inject @Getter private LyricsParser parser;
	
	@Getter private Command primefacesApplyCommand;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		song = identifiableFromRequestParameter(Song.class);
		relatedSongList = new SongOverviewList("Related", songBusiness.findRelated(song),1,Boolean.FALSE);
		
		DefaultCommandable commandable = new DefaultCommandable();
		commandable.setLabel(uiManager.text("ok"));
		commandable.setCommand(new DefaultCommand());
		commandable.getCommand().setMessageManager(getMessageManager());
		commandable.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 3913474940359268490L;
			@Override
			protected Object __execute__(Object parameter) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		primefacesApplyCommand =  new Command(commandable);
		
	}
	
}

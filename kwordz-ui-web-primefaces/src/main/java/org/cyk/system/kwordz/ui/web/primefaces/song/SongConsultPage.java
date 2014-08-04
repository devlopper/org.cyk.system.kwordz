package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.kwordz.business.api.lyrics.LyricsBusiness;
import org.cyk.system.kwordz.business.api.music.NoteBusiness;
import org.cyk.system.kwordz.business.api.song.SongBusiness;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.ui.web.primefaces.SongBuilder;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.Command;
import org.cyk.utility.common.AbstractMethod;

@Named @ViewScoped
public class SongConsultPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	@Inject private LyricsBusiness lyricsBusiness;
	@Inject private SongBusiness songBusiness;
	@Inject private LanguageBusiness languageBusiness;
	@Inject private NoteBusiness noteBusiness;
	//@Inject private SingerBusiness singerBusiness;
	@Getter @Inject private SongBuilder builder;
	
	@Getter private Song song;
	@Getter private SongInfosList relatedSongList;
	@Getter private SingerInfos singerInfos;
	
	@Getter private List<SelectItem> parsableLocalesItems = new ArrayList<>();
	@Getter private List<SelectItem> tonesItems = new ArrayList<>();
	
	@Getter private String embeddedMediaUrl;
	
	@Getter private Command applyCommand;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		song = identifiableFromRequestParameter(Song.class);
		
		for(Locale locale : lyricsBusiness.findParsableLocales()){
			parsableLocalesItems.add(new SelectItem(locale, languageBusiness.findText(session.getLocale(),locale)));
			if(locale.equals(session.getLocale()))
				builder.setNoteLocale(locale);
		}
		
		Long i=0l;
		for(Note note : noteBusiness.findTones()){
			note.setIdentifier(i++);
			tonesItems.add(new SelectItem(note, noteBusiness.format(session.getLocale(), note)));
			if(noteBusiness.equals(note, song.getTone(), Boolean.TRUE)){
				builder.setSelectedTone(note);
			}
		}
		//singerInfos = new SingerInfos(song.getAlbum().getSinger(), singerBusiness);
		relatedSongList = new SongInfosList("Related", songBusiness.findRelated(song),Boolean.FALSE);
		builder.init(song);
		DefaultCommandable commandable = new DefaultCommandable();
		commandable.setLabel(uiManager.text("command.apply"));
		commandable.setCommand(new DefaultCommand());
		commandable.getCommand().setMessageManager(getMessageManager());
		commandable.setIconType(IconType.ACTION_APPLY);
		commandable.setShowLabel(Boolean.FALSE);
		commandable.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 3913474940359268490L;
			@Override
			protected Object __execute__(Object parameter) {
				builder.build();
				return null;
			}
		});
		applyCommand =  new Command(commandable);
		
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}
	
}

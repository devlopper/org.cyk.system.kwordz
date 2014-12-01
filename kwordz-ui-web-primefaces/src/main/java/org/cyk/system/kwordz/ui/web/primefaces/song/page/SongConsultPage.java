package org.cyk.system.kwordz.ui.web.primefaces.song.page;

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
import org.cyk.system.kwordz.ui.web.primefaces.LyricsStringBuilder;
import org.cyk.system.kwordz.ui.web.primefaces.song.SingerInfos;
import org.cyk.system.kwordz.ui.web.primefaces.song.SongInfosList;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.api.annotation.RequestParameter;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named @ViewScoped
public class SongConsultPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;
	
	/*
	 * Services
	 */
	@Inject private LyricsBusiness lyricsBusiness;
	@Inject private SongBusiness songBusiness;
	@Inject private LanguageBusiness languageBusiness;
	@Inject private NoteBusiness noteBusiness;
	//@Inject private SingerBusiness singerBusiness;
	
	/*
	 * Request parameters
	 */
	@Getter @RequestParameter private Song song;
	
	/*
	 * DTOs
	 */
	@Getter @Inject private LyricsStringBuilder builder;
	@Getter private SongInfosList relatedSongList;
	@Getter private SingerInfos singerInfos;
	@Getter private List<SelectItem> parsableLocalesItems = new ArrayList<>();
	@Getter private List<SelectItem> tonesItems = new ArrayList<>();
	
	@Getter private UICommandable applyCommandable;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
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
		relatedSongList = new SongInfosList("Related", songBusiness.findRelated(song),Boolean.FALSE,songBusiness);
		builder.init(song);
		applyCommandable = UIProvider.getInstance().createCommandable(null, "command.apply", IconType.ACTION_APPLY, null, null);
		
		applyCommandable.getCommand().setMessageManager(getMessageManager());
		applyCommandable.setShowLabel(Boolean.FALSE);
		applyCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = 1L;
			public void serve(org.cyk.ui.api.command.UICommand command, Object parameter) {builder.build();};
		});
		
		
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}
	
}

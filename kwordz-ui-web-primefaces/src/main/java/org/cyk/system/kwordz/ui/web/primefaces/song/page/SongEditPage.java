package org.cyk.system.kwordz.ui.web.primefaces.song.page;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.business.api.song.AlbumBusiness;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.ui.web.primefaces.LyricsStringBuilder;
import org.cyk.system.root.business.api.file.MediaBusiness;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.language.Language;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.primefaces.Command;
import org.cyk.ui.web.primefaces.dynamic.AbstractEditorPage;
import org.cyk.utility.common.AbstractMethod;
import org.primefaces.event.SelectEvent;

@Named @ViewScoped
public class SongEditPage extends AbstractEditorPage<Song> implements Serializable {

	private static final long serialVersionUID = 479730074989365192L;

	@Inject private AlbumBusiness albumBusiness;
	@Inject private MediaBusiness mediaBusiness;
	
	@Getter @Setter private Singer singer;
	@Getter @Setter private Language lyricsLanguage;
	@Getter private List<SelectItem> albums = new ArrayList<>();;	
	
	@Getter @Inject private LyricsStringBuilder builder;
	@Getter private Command previewCommand,applyLyricsInputOptionsCommand/*,loadMediaCommand*/;
	@Getter @Setter private URL mediaUrl;
	@Getter @Setter private URL mediaEmbeddedUrl;
	
	private AbstractMethod<Object, Object> submitMethod;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		submitMethod = editor.getSubmitMethodMain();
		editor.setSubmitMethodMain(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4484402099766301780L;
			@Override
			protected Object __execute__(Object parameter) {
				builder.buildAndApplySongLyrics();
				submitMethod.execute(parameter);
				return null;
			}
		});
		builder.init(getSong());
				
		previewCommand =  new Command(createCommandable("command.preview", IconType.ACTION_PREVIEW, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 3913474940359268490L;
			@Override
			protected Object __execute__(Object parameter) {
				builder.buildAndApplySongLyrics();
				builder.build();
				return null;
			}
		}, null, null));
		previewCommand.getCommandButton().setUpdate(":form:parsedLyrics");
		
		applyLyricsInputOptionsCommand =  new Command(createCommandable("command.apply", IconType.ACTION_APPLY, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 3913474940359268490L;
			@Override
			protected Object __execute__(Object parameter) {
				builder.applyLyricsToParseChange();
				return null;
			}
		}, null, null));
		applyLyricsInputOptionsCommand.getCommandButton().setUpdate(":form:lyrics");
		
	}
	
	public void singerSelect(SelectEvent selectEvent){
		Singer singer = (Singer) selectEvent.getObject();
		albums.clear();
		for(Album album : albumBusiness.findBySinger(singer))
			albums.add(new SelectItem(album, album.getUiString()));
	}
	
	public void notationSelect(ValueChangeEvent valueChangeEvent){
		Locale locale = (Locale) valueChangeEvent.getNewValue();
		builder.initTones(locale);
	}
	
	public void mediaUriSelect(ValueChangeEvent valueChangeEvent){
		URL url = (URL) valueChangeEvent.getNewValue();
		File file = new File();
		file.setUri(mediaBusiness.findEmbeddedUri(URI.create(url.toString())));
		getSong().setMedia(file);
		try {
			mediaEmbeddedUrl = file.getUri().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public Song getSong(){
		return (Song) editor.getObjectModel();
	}
}

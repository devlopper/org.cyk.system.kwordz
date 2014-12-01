package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.net.URL;
import java.util.Locale;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.music.MusicKind;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.root.model.language.Language;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;

@Getter @Setter
public class SongFormData extends AbstractFormModel<Song> implements Serializable {

	private static final long serialVersionUID = 235306190360023398L;

	@Input
	@ManyToOne @NotNull
	private Singer singer;
	
	@ManyToOne @NotNull
	private Album album;
	
	@NotNull
	private String name;
	
	@ManyToOne @NotNull
	private Note tone;
	
	@ManyToOne @NotNull
	private MusicKind musicKind;
	
	@NotNull
	private URL mediaUrl;
	
	@NotNull
	private String lyrics;
	
	private Language language;

	private Locale locale;

}

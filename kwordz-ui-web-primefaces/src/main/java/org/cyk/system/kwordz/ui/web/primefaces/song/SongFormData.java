package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.net.URL;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.music.MusicKind;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.ui.api.editor.AbstractFormData;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter
public class SongFormData extends AbstractFormData<Song> implements Serializable {

	private static final long serialVersionUID = 235306190360023398L;

	@ManyToOne @UIField @NotNull
	private Singer singer;
	
	@ManyToOne @UIField @NotNull
	private Album album;
	
	@UIField @NotNull
	private String name;
	
	@ManyToOne @UIField @NotNull
	private Note tone;
	
	@ManyToOne @UIField @NotNull
	private MusicKind musicKind;
	
	@UIField @NotNull
	private URL mediaUrl;
	
	@UIField(textArea=true) @NotNull
	private String lyrics;

}

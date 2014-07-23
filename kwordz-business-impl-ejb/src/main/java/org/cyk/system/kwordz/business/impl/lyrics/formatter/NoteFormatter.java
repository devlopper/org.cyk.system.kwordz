package com.kwordz.text.formatter;

import java.io.Serializable;

import com.kwordz.model.note.Note;
import com.kwordz.text.parser.AbstractParser;

public class NoteFormatter extends AbstractFormatter<Note> implements Serializable{

	private static final long serialVersionUID = 1L;
	private boolean showAlterationSeparator = false;
	
	@Override
	public String format(Note note,Object...parameters) {
		return showAlterationSeparator?note.toString():note.toString().replaceAll(AbstractParser.NOTE_SEPARATOR, "");
	}
	
	public boolean isShowAlterationSeparator() {
		return showAlterationSeparator;
	}
	public void setShowAlterationSeparator(boolean showAlterationSeparator) {
		this.showAlterationSeparator = showAlterationSeparator;
	}

}

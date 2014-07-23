package org.cyk.system.kwordz.business.impl.lyrics.formatter;

/*
public class ChordFormatter extends AbstractFormatter<Chord> implements Serializable{

	private static final long serialVersionUID = 1L;
	private NoteFormatter noteFormatter;
	private String noteSeparator = " ";
	
	//private String chordText = "kyc";// I don't know why it is there
	private boolean showTypeSeparator;
	private boolean showName = true;
	private boolean expanded;
	private boolean applyFormat=true;
	private Note root;
	
	public ChordFormatter() {
		noteFormatter = new NoteFormatter();
	}
	
	public ChordFormatter(NoteFormatter noteFormatter) {
		super();
		this.noteFormatter = noteFormatter;
	}
	
	public ChordFormatter(NoteFormatter noteFormatter, String noteSeparator,
			boolean showTypeSeparator, boolean showName, boolean expanded,
			boolean applyFormat, Note root) {
		super();
		this.noteFormatter = noteFormatter;
		this.noteSeparator = noteSeparator;
		this.showTypeSeparator = showTypeSeparator;
		this.showName = showName;
		this.expanded = expanded;
		this.applyFormat = applyFormat;
		this.root = root;
	}

	@Override
	public String format(Chord chord,Object...parameters) {
		if(chord==null)
			return "";
		return showName?(expanded?expand(chord):contract(chord)):degree(chord);
	}
	
	public String degree(Chord chord) {
		String d = root.degree(chord.getRoot());
		return d+chord.getStructure().getSymbol()+getChordAugmentWithDegreeBaseNoteIfNeeded(chord);
	}
		
	private String augmentWithbaseNoteIfNeeded(Chord chord){
		if(chord.isBassNoteEqualToRootChord())
			return "";
		return  AbstractParser.CHORD_BASS_NOTE_SEPARATOR+(applyFormat?noteFormatter.format(chord.getBass()):chord.getBass());
	}
	
	private String getChordAugmentWithDegreeBaseNoteIfNeeded(Chord chord){
		if(chord.isBassNoteEqualToRootChord())
			return "";
		return AbstractParser.CHORD_BASS_NOTE_SEPARATOR+root.degree(chord.getBass());
	}
		
	public String contract(Chord chord){
		String type = chord.getStructure().getSymbol();
		if(!type.isEmpty())
			if(showTypeSeparator)
				type = AbstractParser.CHORD_TYPE_SEPARATOR +type;
		type = type+augmentWithbaseNoteIfNeeded(chord);
		StringBuilder out = new StringBuilder();
		if(applyFormat)
			if(showName)
				out.append(noteFormatter.format(chord.getRoot()));
			else
				out.append(root.degree(chord.getRoot()));
		else
			if(showName)
				out.append(chord.getRoot());
			else
				out.append(root.degree(chord.getRoot()));
	
		return out+type;
	}
		
	public String expand(Chord chord){
		StringBuilder expanded = new StringBuilder();
		for(Note note : chord.getNotes())
			expanded.append(noteFormatter.format(note)+" ");
		expanded.append(augmentWithbaseNoteIfNeeded(chord));
		return expanded.toString().trim();
	}
	
	public NoteFormatter getNoteFormatter() {
		return noteFormatter;
	}
	public void setNoteFormatter(NoteFormatter noteFormatter) {
		this.noteFormatter = noteFormatter;
	}
	public void setNoteSeparator(String noteSeparator) {
		this.noteSeparator = noteSeparator;
	}
	public String getNoteSeparator() {
		return noteSeparator;
	}
	public boolean isShowTypeSeparator() {
		return showTypeSeparator;
	}
	public void setShowTypeSeparator(boolean showTypeSeparator) {
		this.showTypeSeparator = showTypeSeparator;
	}
	public boolean isShowName() {
		return showName;
	}
	public void setShowName(boolean showName) {
		this.showName = showName;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isApplyFormat() {
		return applyFormat;
	}
	public void setApplyFormat(boolean applyFormat) {
		this.applyFormat = applyFormat;
	}
	public Note getRoot() {
		return root;
	}
	public void setRoot(Note root) {
		this.root = root;
	}
	
}
*/

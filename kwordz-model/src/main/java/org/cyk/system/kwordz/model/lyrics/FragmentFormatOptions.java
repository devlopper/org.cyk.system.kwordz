package org.cyk.system.kwordz.model.lyrics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.kwordz.model.AbstractFormatOptions;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.root.model.html.HtmlTag;

@Getter @Setter
public class FragmentFormatOptions extends AbstractFormatOptions implements Serializable {

	private static final long serialVersionUID = 5281169944501545966L;

	public static final String PADDING = " ";
		
	public static final String CHORD_HTML_TAG_NAME = "span";
	public static final String CHORD_HTML_TAG_CLASS = "cyk-kwordz-chord";
	
	public static final String TEXT_HTML_TAG_NAME = "span";
	public static final String TEXT_HTML_TAG_CLASS = "cyk-kwordz-lyrics";
	
	private ChordFormatOptions chordFormatOptions = new ChordFormatOptions();

	private Boolean chordAtLeft = Boolean.TRUE;
	private Boolean showChord=Boolean.TRUE;
	private Boolean showText=Boolean.TRUE;
	
	private String padding;
	
	private HtmlTag chordTag = new HtmlTag();
	private HtmlTag textTag = new HtmlTag();
	
	public FragmentFormatOptions() {
		configTag(chordTag, CHORD_HTML_TAG_NAME, "class","cyk-kwordz-chord");
		configTag(textTag, TEXT_HTML_TAG_NAME, "class","cyk-kwordz-lyrics");
	}
	
	private void configTag(HtmlTag tag,String name,String...attributes){
		tag.setName(name);
		if(attributes!=null)
			for(int i=0;i<attributes.length-1;i=i+2)
				tag.getAttributes().put(attributes[i], attributes[i+1]);
	}
	
}

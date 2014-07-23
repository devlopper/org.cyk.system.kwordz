package org.cyk.system.kwordz.business.impl.lyrics.formatter;

/*
public abstract class AbstractFormatter<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected Set<AbstractFormatter<?>> formatters;
	protected boolean html;
	protected boolean edit;
	
	public abstract String format(T object,Object...parameters);
	
	public void register(AbstractFormatter<?> formatter){
		if(formatters==null)
			formatters = new HashSet<AbstractFormatter<?>>();
		formatters.add(formatter);
	}
	
	protected String lineBreak(){
		return html?"<br/>":"\r\n";
	}
	
	public boolean isHtml() {
		return html;
	}
	public void setHtml(boolean html) {
		this.html = html;
		if(formatters!=null)
			for(AbstractFormatter<?> formatter : formatters)
				formatter.setHtml(html);
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
		if(formatters!=null)
			for(AbstractFormatter<?> formatter : formatters)
				formatter.setEdit(edit);
	}

}
*/

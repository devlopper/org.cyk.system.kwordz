package org.cyk.system.kwordz.ui.web.primefaces.song;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInfosList<ENTITY,INFOS extends AbstractInfos<ENTITY>> implements Serializable {

	private static final long serialVersionUID = -6356004259181731404L;
	
	public enum RenderType{TABLE,GRID}
	
	protected String name;
	protected Integer columns;
	protected List<INFOS> list = new ArrayList<>();
	protected Class<ENTITY> infosClass;
	protected RenderType renderType = RenderType.GRID;
	
	@SuppressWarnings("unchecked")
	public AbstractInfosList(Collection<ENTITY> list) {
		super();
		infosClass = (Class<ENTITY>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		if(list!=null)
			for(ENTITY entity : list){
				INFOS infos = null;
				try {
					infos = (INFOS) infosClass.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				infos.setEntity(entity);
				this.list.add(infos);
			}
	}
	
	public Boolean getIsRenderedAsGrid(){
		return RenderType.GRID.equals(renderType);
	}
	
}

package org.cyk.system.kwordz.ui.web.primefaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.system.kwordz.ui.web.primefaces.song.SongOverviewList;

@FacesComponent(value="org.cyk.ui.primefaces.kwordz.SongOverviewList")
public class SongOverviewListCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public SongOverviewList getValue() {
        return (SongOverviewList) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(SongOverviewList songOverviewList) {
        getStateHelper().put(PropertyKeys.value, songOverviewList);
    } 

}
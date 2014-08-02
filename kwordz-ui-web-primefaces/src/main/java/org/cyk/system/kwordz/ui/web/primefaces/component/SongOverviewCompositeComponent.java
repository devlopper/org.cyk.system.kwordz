package org.cyk.system.kwordz.ui.web.primefaces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.system.kwordz.ui.web.primefaces.song.SongOverview;

@FacesComponent(value="org.cyk.ui.primefaces.kwordz.SongOverview")
public class SongOverviewCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public SongOverview getValue() {
        return (SongOverview) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(SongOverview songOverview) {
        getStateHelper().put(PropertyKeys.value, songOverview);
    } 

}
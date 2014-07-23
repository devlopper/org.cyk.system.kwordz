package org.cyk.system.kwordz.persistence.api.music;

import org.cyk.system.kwordz.model.music.AbstractNoteCollection;
import org.cyk.system.kwordz.model.music.Structure;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractNoteCollectionDao<STRUCTURE extends Structure,COLLECTION extends AbstractNoteCollection<STRUCTURE>> extends TypedDao<COLLECTION> {


}

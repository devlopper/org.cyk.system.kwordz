package org.cyk.system.kwordz.persistence.impl.music;

import java.io.Serializable;

import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordStructure;
import org.cyk.system.kwordz.persistence.api.music.ChordDao;

public class ChordDaoImpl extends AbstractNoteCollectionDaoImpl<ChordStructure,Chord> implements ChordDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	/* 
   private String readWhereFromDateGreaterThanByDate,countWhereFromDateGreaterThanByDate,
    readWhereFromDateBetweenByStartDateByEndDate,countWhereFromDateBetweenByStartDateByEndDate;
       
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readWhereFromDateGreaterThanByDate, _select().where("period.fromDate", "fromDate",GT));
        registerNamedQuery(readWhereFromDateBetweenByStartDateByEndDate, _select().where("period.fromDate", "startDate",GTE)
                .where(AND,"period.fromDate", "endDate",LTE));
    }
     
    @Override
    public Collection<Event> readWhereFromDateGreaterThanByDate(Date date) {
        return namedQuery(readWhereFromDateGreaterThanByDate).parameter("fromDate", date)
                .resultMany();
    }

    @Override
    public Long countWhereFromDateGreaterThanByDate(Date date) {
        return countNamedQuery(countWhereFromDateGreaterThanByDate).parameter("fromDate", date)
                .resultOne();
    }

    @Override
    public Collection<Event> readWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return namedQuery(readWhereFromDateBetweenByStartDateByEndDate).parameter("startDate", startDate).parameter("endDate", endDate)
                .resultMany();
    }

    @Override
    public Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return countNamedQuery(countWhereFromDateBetweenByStartDateByEndDate).parameter("startDate", startDate).parameter("endDate", endDate)
                .resultOne();
    }
    */

}
 
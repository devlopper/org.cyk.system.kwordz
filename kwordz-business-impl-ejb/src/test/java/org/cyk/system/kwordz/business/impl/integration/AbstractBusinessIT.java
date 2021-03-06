package org.cyk.system.kwordz.business.impl.integration;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.system.kwordz.business.impl.KwordzBusinessLayer;
import org.cyk.system.kwordz.model.music.Chord;
import org.cyk.system.kwordz.model.music.ChordFormatOptions;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.impl.BusinessIntegrationTestHelper;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.DefaultValidator;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.utility.common.test.AbstractIntegrationTestJpaBased;
import org.cyk.utility.common.test.ArchiveBuilder;
import org.jboss.shrinkwrap.api.Archive;

public abstract class AbstractBusinessIT extends AbstractIntegrationTestJpaBased {

	private static final long serialVersionUID = -5752455124275831171L;
	@Inject protected ExceptionUtils exceptionUtils;
	@Inject protected DefaultValidator defaultValidator;
	@Inject private GenericDaoImpl g;
	@Inject protected GenericBusiness genericBusiness;
	@Inject protected KwordzBusinessLayer kwordzBusinessLayer;
	
	@Inject protected ValidatorMap validatorMap;// = ValidatorMap.getInstance();
    
	{
		ChordFormatOptions.DEFAULT_SEPARATOR_NOTE_AND_STRUCTURE = " ";
		ChordFormatOptions.SHOW_MAJOR_CHORD_SYMBOL = Boolean.TRUE;
	}
	
    @Override
    public EntityManager getEntityManager() {
        return g.getEntityManager();
    }
	
    @Override
    protected void _execute_() {
        super._execute_();
        create();    
        read(); 
        update();    
        delete();    
        finds();
        businesses();
    }
    
	protected abstract void finds();
	
	protected abstract void businesses();
	
	/* Shortcut */
    
    protected AbstractIdentifiable create(AbstractIdentifiable object){
        return genericBusiness.create(object);
    }
    
    protected AbstractIdentifiable update(AbstractIdentifiable object){
        return genericBusiness.update(object);
    }
    
    protected void validate(Object object){
        if(object==null)
            return;
        @SuppressWarnings("unchecked")
        AbstractValidator<Object> validator = (AbstractValidator<Object>) validatorMap.validatorOf(object.getClass());
        if(validator==null){
            //log.warning("No validator has been found. The default one will be used");
            //validator = defaultValidator;
            return;
        }
        try {
            validator.validate(object);
        } catch (Exception e) {}
        
        if(!Boolean.TRUE.equals(validator.isSucces()))
            System.out.println(validator.getMessagesAsString());
        
    }
    /*
    protected void assertEqualsNoteString(Note expected,Note actual){
		assertEquals(expected.toString(),actual.toString());
	}*/
    
    protected void assertEqualsNote(Note expected,Note actual){
		assertEquals("Note equals",Boolean.TRUE,
			expected==null?actual==null:
			(
				( expected.getName()==null?actual.getName()==null:expected.getName().equals(actual.getName()) ) &&
				( expected.getAlteration()==null?actual.getAlteration()==null:expected.getAlteration().equals(actual.getAlteration()) )
			)
		);
	}
    
    protected void assertEqualsChord(Chord expected,Chord actual){
    	if(expected!=null && actual!=null){
    		assertEquals("Structure", expected.getStructure().getCode(), actual.getStructure().getCode());
    		assertEquals("Lenght", expected.getNotes().size(), actual.getNotes().size());
    		for(int i=0;i<expected.getNotes().size();i++)
    			assertEqualsNote(expected.getNotes().get(i), actual.getNotes().get(i));
    		assertEqualsNote(expected.getBass(), actual.getBass());
    	}
    }
    
    public static Archive<?> createRootDeployment() {
        return  
                new ArchiveBuilder().create().getArchive().
                    addClasses(BusinessIntegrationTestHelper.classes()).
                    addPackages(Boolean.FALSE, BusinessIntegrationTestHelper.packages()).
                    addPackages(Boolean.TRUE,"org.cyk.system.kwordz") 
                //_deploymentOfPackages("org.cyk.system.root").getArchive()
              
                //.addPackages(Boolean.FALSE,BusinessIntegrationTestHelper.PACKAGES)
                //.addClasses(BusinessIntegrationTestHelper.CLASSES)
                //.addPackage(ExceptionUtils.class.getPackage())
                //.addPackage(PersonValidator.class.getPackage())
                ;
    } 
}

package org.cyk.system.kwordz.persistence.impl.integration;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.kwordz.model.music.Note;
import org.cyk.system.kwordz.model.music.NoteName;
import org.cyk.system.kwordz.model.song.Album;
import org.cyk.system.kwordz.model.song.Singer;
import org.cyk.system.kwordz.model.song.Song;
import org.cyk.system.kwordz.model.song.SongSearchCriteria;
import org.cyk.system.kwordz.persistence.api.song.SongDao;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class SongPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	} 
	
	@Inject private SongDao songDao;
	
	@Override
	protected void populate() {
		Singer singer1,singer2,singer3;
    	singer1 = create(new Singer("Chanteur one"));
    	singer1.setContactCollection(null);
    	singer2 = create(new Singer("Chanteur two"));
    	singer2.setContactCollection(null);
    	singer3 = create(new Singer("Chanteur three"));
    	singer3.setContactCollection(null);
    	
    	Album s1Album1,s1Album2,s1Album3,s2Album1,s3Album1,s3Album2;
    	s1Album1 = create(new Album(singer1,"Album 1")); 
    	s1Album2 = create(new Album(singer1,"Album 2")); 
    	s1Album3 = create(new Album(singer1,"Album 3")); 
    	
    	s2Album1 = create(new Album(singer2,"Album 1"));
    	
    	s3Album1 = create(new Album(singer3,"Album 1")); 
    	s3Album2 = create(new Album(singer3,"Album 2")); 
    	
    	create(song(s1Album1,"Chant 1"));
    	
    	create(song(s1Album2,"Chant 2"));
    	
    	create(song(s1Album3,"Chant 3"));
    	
    	create(song(s2Album1,"Chant 1"));
    	
    	create(song(s3Album1,"Chant 1"));
    	
    	create(song(s3Album2,"Chant 2"));
		
	}
	
	private Song song(Album album,String name){
	    return create(new Song(album,name,new Note(NoteName.C),null,null));
	}
					
	// CRUD 
	
	@Override
	protected void create() {}

	@Override
	protected void read() {}

	@Override
	protected void update() {}

	@Override
	protected void delete() {}
	
	@Override
	protected void queries() {
	   
		Assert.assertEquals(6,songDao.select().all().size());
		
		assertSongSearch(null,null,"one", 3);
		assertSongSearch(null,null,"ote", 0);
		assertSongSearch(null,"album",null, 6);
		assertSongSearch(null,"Album 1",null, 3);
		
		assertSongSearch("a", 6);
		assertSongSearch("an", 6);
		assertSongSearch("one", 3);
		assertSongSearch("Album 2", 2);
		assertSongSearch("hello", 0);
	}
	
	private void assertSongSearch(SongSearchCriteria searchCriteria,Integer expected){
		Collection<String> c = new ArrayList<>();
		if(StringUtils.isNotEmpty(searchCriteria.getSingerNameSearchCriteria().getValue()))
			c.add("Singer : "+searchCriteria.getSingerNameSearchCriteria().getValue());
		if(StringUtils.isNotEmpty(searchCriteria.getAlbumNameSearchCriteria().getValue()))
			c.add("Album : "+searchCriteria.getAlbumNameSearchCriteria().getValue());
		if(StringUtils.isNotEmpty(searchCriteria.getSongNameSearchCriteria().getValue()))
			c.add("Song : "+searchCriteria.getSongNameSearchCriteria().getValue());
		
		Assert.assertEquals("Search "+StringUtils.join(c,","),expected.intValue(),songDao.countByCriteria(searchCriteria).intValue());
	}
	
	private void assertSongSearch(String songName,String albumName,String singerName,Integer expected){
		SongSearchCriteria searchCriteria = new SongSearchCriteria();
		searchCriteria.getSongNameSearchCriteria().setValue(songName);
		searchCriteria.getAlbumNameSearchCriteria().setValue(albumName);
		searchCriteria.getSingerNameSearchCriteria().setValue(singerName);
		assertSongSearch(searchCriteria, expected);
	}
	
	private void assertSongSearch(String name,Integer expected){
		assertSongSearch(name, name, name, expected);
	}
	
	
}

package org.cloudfoundry.samples.music.repositories.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.cloudfoundry.samples.music.domain.Album;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaAlbumRepositoryTest {
	
	private static final Logger logger = LoggerFactory.getLogger(JpaAlbumRepositoryTest.class);
	
	@Rule public TestName name = new TestName();

	
    @Autowired
    JpaAlbumRepository repository;
    
    Album album;

    @Before
    public void setUp() throws Exception{
    	//Test Data 생성
    	album = new Album("Over the Rainbow", "Eric Clapton", "1994", "Modern Rock");
    	album.setAlbumId("eric");
    	repository.save(album);
    }
    
// ========================== 2-2-2. JUnit 테스트 ==========================   
    	@Test
	public void testFindById() throws Exception{
		//when
		Album actual = repository.findById(album.getId()).orElse(null);
		
		//then
		printAlbum(name.getMethodName(),actual);
		
		//TODO 2-2-2. 1)JUnit 단위테스트 실패 코드
		//assertThat(actual.getAlbumId()).isEqualTo(null);
		//TODO 2-2-2. 2)JUnit 단위테스트 성공 코드
		//assertThat(actual.getAlbumId()).isEqualTo(album.getAlbumId());

	}
// =========================================================================    
    
    
    
	@Test
	public void testFindAll() throws Exception{
		//when
		List<Album> actual = repository.findAll();
		
		//then
		printAlbumList(name.getMethodName(),actual);
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0)).isEqualTo(album);
        assertThat(actual.get(0).getAlbumId()).isEqualTo("eric");
        assertThat(actual.get(0).getId()).isEqualTo(album.getId());
		
	}
	
	@Test
	public void testSave() throws Exception{
		//add another Album
		//when
		Album another = new Album("Signe","Eric Clapton","1992","Country");
		another.setAlbumId("eric2");
		repository.save(another);
		List<Album> actual = repository.findAll();

		//then
		printAlbumList(name.getMethodName(),actual);
		
        assertThat(actual.size()).isEqualTo(2);
        assertTrue(actual.contains(another));
        assertThat(actual.get(actual.indexOf(another))).isEqualTo(another);
        assertThat(actual.get(actual.indexOf(another)).getAlbumId()).isEqualTo(another.getAlbumId());
        assertThat(actual.get(actual.indexOf(another)).getId()).isEqualTo(another.getId());
	}
	
	@Test
	public void testUpdate() throws Exception{
		//when
		printAlbum(name.getMethodName(),album);
		album.setReleaseYear("1894"); 
		repository.save(album);//setter만으로도 JPA에서는 update효과가 있으나 controller에서의 사용방법대로 test
		Album actual = repository.findById(album.getId()).orElse(null);
		
		//then
		printAlbum(name.getMethodName(),actual);
		assertThat(actual.getReleaseYear()).isEqualTo("1894");
	}
	
	@Test
	public void testDelete() throws Exception{
		//when
		Album another = new Album("Signe","Eric Clapton","1992","Country");
		another.setAlbumId("eric2");
		repository.save(another);
		
		List<Album> albumList = repository.findAll();
		printAlbumList(name.getMethodName(),albumList);
        assertThat(albumList.size()).isEqualTo(2);
        
        repository.delete(album);
        
        //then
		List<Album> actual = repository.findAll();
		printAlbumList(name.getMethodName(),actual);
        assertThat(actual.size()).isEqualTo(1);
	}
	
	@Test
	public void testDeleteById() throws Exception{
		//when
		Album another = new Album("Signe","Eric Clapton","1992","Country");
		another.setAlbumId("eric2");
		repository.save(another);
		
		List<Album> albumList = repository.findAll();
		printAlbumList(name.getMethodName(),albumList);
        assertThat(albumList.size()).isEqualTo(2);
        
        repository.deleteById(another.getId());
        
        //then
        List<Album> actual = repository.findAll();
		printAlbumList(name.getMethodName(),actual);
        assertThat(actual.size()).isEqualTo(1);
	}
	

	@Test
	public void testFindById_NotFound() throws Exception{
		//when
		Album actual = repository.findById("12345").orElse(null);
		
		//then
		printAlbum(name.getMethodName(),actual);
		assertThat(actual).isEqualTo(null);
	}

	private void printAlbumList(String testName, List<Album> albumList) {
		logger.info("["+testName+"]");
        for(Album temp:albumList) {
        	logger.info("---------------------");
        	logger.info("Album id :" + temp.getAlbumId());
        	logger.info("Album artist :" + temp.getArtist());
        	logger.info("Album title :" + temp.getTitle());        	
        	logger.info("Album genre :" + temp.getGenre());        	
        	logger.info("Album release Year :" + temp.getReleaseYear());        	
        	logger.info("ID :" + temp.getId());        	
        	logger.info("track count :" + temp.getTrackCount());        	
        }
	}
	
	private void printAlbum(String testName, Album temp) {
		logger.info("["+testName+"]");
		if (temp == null) {
			logger.info("Album : NULL");
			return;
		}
    	logger.info("---------------------");
    	logger.info("Album id :" + temp.getAlbumId());
    	logger.info("Album artist :" + temp.getArtist());
    	logger.info("Album title :" + temp.getTitle());        	
    	logger.info("Album genre :" + temp.getGenre());        	
    	logger.info("Album release Year :" + temp.getReleaseYear());        	
    	logger.info("ID :" + temp.getId());        	
    	logger.info("track count :" + temp.getTrackCount());        	
	}

}

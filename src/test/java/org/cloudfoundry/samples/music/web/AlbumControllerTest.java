package org.cloudfoundry.samples.music.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;

import java.util.Iterator;
import java.util.List;

import org.cloudfoundry.samples.music.domain.Album;
import org.cloudfoundry.samples.music.repositories.jpa.JpaAlbumRepository;
import org.cloudfoundry.samples.music.repositories.jpa.JpaAlbumRepositoryTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(AlbumControllerTest.class);
	
	@Rule public TestName name = new TestName();


	@Autowired
	MockMvc mvc;
	
	@MockBean
	JpaAlbumRepository repository;

    private Jackson2ResourceReader resourceReader;
    private Resource sourceData;
    private List<Album> baseAlbumList;
	
    @SuppressWarnings("unchecked")
	@Before
    public void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        resourceReader = new Jackson2ResourceReader(mapper);
        sourceData = new ClassPathResource("albums.json");
        baseAlbumList = (List<Album>)getEntityFromResource(sourceData);
    }
    
    @Test
    public void findAllTest() throws Exception{
    	//목적 : json <-> Object : serialization<->deserialization
    	//given
    	printAlbumList(name.getMethodName(), baseAlbumList);
        given(repository.findAll()).willReturn(baseAlbumList);
        
        //when
		mvc.perform(get("/albums"))
		.andDo(print())
		//then
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Marvin Gaye")))
		.andExpect(jsonPath("$", hasSize(baseAlbumList.size())))
		.andExpect(jsonPath("$[0].artist", is(baseAlbumList.get(0).getArtist())));
		
		then(repository).should().findAll();
    }
    
    @Test
    public void addAlbumTest() throws Exception{
    	//목적 : json <-> Object : serialization<->deserialization ,and request method파악된 정확한 call
    	//update, delete, findById는 repository테스트로 대체(repository를 mockup하게 되어 테스트 의미 약화)
    	//given
    	String addJson = " {\r\n" + 
    			"        \"artist\": \"Eric Clapton\",\r\n" + 
    			"        \"title\": \"Signe\",\r\n" + 
    			"        \"releaseYear\": \"1992\",\r\n" + 
    			"        \"genre\": \"Blues\"\r\n" + 
    			"    }";
    	
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Album addAlbum = mapper.readValue(addJson, Album.class);
        printAlbum(name.getMethodName(), addAlbum);
        given(repository.save(ArgumentMatchers.any(Album.class))).willReturn(addAlbum);
    	
        //when
		mvc.perform(put("/albums")
				.contentType("application/json;charset=UTF-8")
				.content(addJson))
        .andDo(print())
		//then
		.andExpect(status().isOk())
		.andExpect(content().string(containsString(addAlbum.getTitle())))
		.andExpect(jsonPath("artist", is(addAlbum.getArtist())));
		
		then(repository).should().save(ArgumentMatchers.any(Album.class));
    }
    private Object getEntityFromResource(Resource resource) {
        try {
            return resourceReader.readFrom(resource, this.getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

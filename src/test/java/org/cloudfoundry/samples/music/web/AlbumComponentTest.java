package org.cloudfoundry.samples.music.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.cloudfoundry.samples.music.domain.Album;
import org.cloudfoundry.samples.music.repositories.jpa.JpaAlbumRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * mongo, radis를 setup하는 과정에서 connection error가 나지만,
 * config문제지 test에는 문제가 없음
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AlbumComponentTest {

	@Autowired
	MockMvc mvc;
	
	@Autowired
	JpaAlbumRepository repository;

    private Jackson2ResourceReader resourceReader;
    private Resource sourceData;
    private List<Album> expectedAlbumList;

    @Before
    public void setUp() {
    	//초기 데이터 29건 DB에 테스트 메소드 수행시 마다 등록(tearDown에서 수행 후 바로 삭제)
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        resourceReader = new Jackson2ResourceReader(mapper);
        sourceData = new ClassPathResource("albums.json");
        expectedAlbumList = (List<Album>)getEntityFromResource(sourceData);
        for(Album testData:expectedAlbumList) {
        	repository.save(testData);
        }
    }
    
    @Test
    public void findAllTest() throws Exception{
        //when
		mvc.perform(get("/albums"))
		.andDo(print())
		//then
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Marvin Gaye")))
		.andExpect(jsonPath("$", hasSize(expectedAlbumList.size())))
		.andExpect(jsonPath("$[0].artist", is(expectedAlbumList.get(0).getArtist())));
    }
    

    @Test
    public void addAlbumTest() throws Exception{
    	//given
    	String addJson = " {\r\n" + 
    			"        \"artist\": \"Eric Clapton\",\r\n" + 
    			"        \"title\": \"Signe\",\r\n" + 
    			"        \"releaseYear\": \"1992\",\r\n" + 
    			"        \"genre\": \"Blues\"\r\n" + 
    			"    }";
    	
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Album expectedAlbum = mapper.readValue(addJson, Album.class);
    	
        //when
		mvc.perform(put("/albums")
				.contentType("application/json;charset=UTF-8")
				.content(addJson))
        .andDo(print())
		.andExpect(status().isOk());
		
		//then
		mvc.perform(get("/albums"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(expectedAlbumList.size()+1)))
		.andExpect(content().string(containsString(expectedAlbum.getTitle())));

    }
    
    @Test
    public void updateAlbumTest() throws Exception{
    	//given
    	String addJson = " {\r\n" + 
    			"        \"artist\": \"Eric Clapton\",\r\n" + 
    			"        \"title\": \"Signe\",\r\n" + 
    			"        \"releaseYear\": \"1992\",\r\n" + 
    			"        \"genre\": \"Blues\"\r\n" + 
    			"    }";
    	
    	
		MvcResult addedAlbum = mvc.perform(put("/albums")
				.contentType("application/json;charset=UTF-8")
				.content(addJson))
        .andDo(print())
		.andExpect(status().isOk())
		.andReturn();
		
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Album expectedAlbum = mapper.readValue(addedAlbum.getResponse().getContentAsString(), Album.class);
        expectedAlbum.setReleaseYear("1982");
		
        //when
		mvc.perform(post("/albums")
		.contentType("application/json;charset=UTF-8")
		.content(mapper.writeValueAsString(expectedAlbum)))
		.andDo(print())
		//then
		.andExpect(status().isOk())
		.andExpect(content().string(containsString(expectedAlbum.getReleaseYear())));
    }
    
    @Test
    public void getByIdTest() throws Exception{
    	//given
    	String addJson = " {\r\n" + 
    			"        \"artist\": \"Eric Clapton\",\r\n" + 
    			"        \"title\": \"Signe\",\r\n" + 
    			"        \"releaseYear\": \"1992\",\r\n" + 
    			"        \"genre\": \"Blues\"\r\n" + 
    			"    }";
    	
		MvcResult addedAlbum = mvc.perform(put("/albums")
				.contentType("application/json;charset=UTF-8")
				.content(addJson))
        .andDo(print())
		.andExpect(status().isOk())
		.andReturn();
		
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Album expectedAlbum = mapper.readValue(addedAlbum.getResponse().getContentAsString(), Album.class);
    	
        //when
		mvc.perform(get("/albums/"+expectedAlbum.getId()))
        .andDo(print())
        //then
		.andExpect(status().isOk())
		.andExpect(content().string(containsString(expectedAlbum.getTitle())));

    }
    
    @Test
    public void deleteByIdTest() throws Exception{
    	//given
    	String addJson = " {\r\n" + 
    			"        \"artist\": \"Eric Clapton\",\r\n" + 
    			"        \"title\": \"Signe\",\r\n" + 
    			"        \"releaseYear\": \"1992\",\r\n" + 
    			"        \"genre\": \"Blues\"\r\n" + 
    			"    }";
    	
		MvcResult addedAlbum = mvc.perform(put("/albums")
				.contentType("application/json;charset=UTF-8")
				.content(addJson))
        .andDo(print())
		.andExpect(status().isOk())
		.andReturn();
		
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Album expectedAlbum = mapper.readValue(addedAlbum.getResponse().getContentAsString(), Album.class);
    	
        //when
		mvc.perform(delete("/albums/"+expectedAlbum.getId()))
        .andDo(print())
        //then
		.andExpect(status().isOk());
		
		mvc.perform(get("/albums/"+expectedAlbum.getId()))
        .andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(""));
    }

    @After
    public void tearDown() throws Exception{
    	repository.deleteAll();
    }
    
    private Object getEntityFromResource(Resource resource) {
        try {
            return resourceReader.readFrom(resource, this.getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	

}

package com.example.movie;

import com.example.movie.model.Movie;
import com.example.movie.repository.MovieJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashMap;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/schema.sql", "/data.sql"})
public class MovieControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieJpaRepository movieJpaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private HashMap<Integer, String[]> data =new HashMap<>();

    {
        data.put(1,new String[]{"Avengers: Endgame", "Robert Downey Jr."});
        data.put(2,new String[]{"Avatar", "Sam Worthington"});
        data.put(3,new String[]{"Titanic", "Leonardo DiCaprio"});
        data.put(4,new String[]{"Star Wars: The Force Awakens", "Daisy Ridley"});
        data.put(5,new String[]{"Jurassic World", "Chris Pratt"});
        data.put(6,new String[]{"The Dark Knight", "Christian Bale"});//post
        data.put(7,new String[]{"Avatar 2", "Sam Worthington"});//put

    }

    @BeforeAll
    public  static void first(){

    }



    @Test
    @Order(1)
    public void testGetMovies() throws Exception {


        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(5)))
                .andExpect(jsonPath("$[0].movieId", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].movieName",
                        Matchers.equalToIgnoringCase(data.get(1)[0])))
                .andExpect(jsonPath("$[0].leadActor",
                        Matchers.equalToIgnoringCase(data.get(1)[1])))
                .andExpect(jsonPath("$[1].movieId", Matchers.equalTo(2)))
                .andExpect(jsonPath("$[1].movieName",
                        Matchers.equalToIgnoringCase(data.get(2)[0])))
                .andExpect(jsonPath("$[1].leadActor",
                        Matchers.equalToIgnoringCase(data.get(2)[1])))
                .andExpect(jsonPath("$[2].movieId", Matchers.equalTo(3)))
                .andExpect(jsonPath("$[2].movieName",
                        Matchers.equalToIgnoringCase(data.get(3)[0])))
                .andExpect(jsonPath("$[2].leadActor",
                        Matchers.equalToIgnoringCase(data.get(3)[1])))
                .andExpect(jsonPath("$[3].movieId", Matchers.equalTo(4)))
                .andExpect(jsonPath("$[3].movieName",
                        Matchers.equalToIgnoringCase(data.get(4)[0])))
                .andExpect(jsonPath("$[3].leadActor",
                        Matchers.equalToIgnoringCase(data.get(4)[1])))
                .andExpect(jsonPath("$[4].movieId", Matchers.equalTo(5)))
                .andExpect(jsonPath("$[4].movieName",
                        Matchers.equalToIgnoringCase(data.get(5)[0])))
                .andExpect(jsonPath("$[4].leadActor",
                        Matchers.equalToIgnoringCase(data.get(5)[1])));
    }

    @Test
    @Order(2)
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get("/movies/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    public void testGetMovieById() throws Exception {
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.movieName", Matchers.equalToIgnoringCase(data.get(1)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(1)[1])));

        mockMvc.perform(get("/movies/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.movieName", Matchers.equalToIgnoringCase(data.get(2)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(2)[1])));

        mockMvc.perform(get("/movies/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.movieName", Matchers.equalToIgnoringCase(data.get(3)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(3)[1])));

        mockMvc.perform(get("/movies/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(4)))
                .andExpect(jsonPath("$.movieName", Matchers.equalToIgnoringCase(data.get(4)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(4)[1])));

        mockMvc.perform(get("/movies/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.movieName", Matchers.equalToIgnoringCase(data.get(5)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(5)[1])));
    }

    @Test
    @Order(4)
    public void testPost() throws Exception {

        String content = "{\"movieName\":\""+data.get(6)[0]+"\",\"leadActor\":\""+data.get(6)[1]+"\"}";

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/movies")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(6)))
                .andExpect(jsonPath("$.movieName",
                        Matchers.equalToIgnoringCase(data.get(6)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(6)[1])));
    }

    @Test
    @Order(5)
    public void testAfterPost() throws Exception {
        mockMvc.perform(get("/movies/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(6)))
                .andExpect(jsonPath("$.movieName",
                        Matchers.equalToIgnoringCase(data.get(6)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(6)[1])));
    }

    @Test
    @Order(6)
    public void testDbAfterPost() throws Exception {

        Movie movie = movieJpaRepository.findById(6).get();
        assertEquals(movie.getMovieName(), data.get(6)[0]);
        assertEquals(movie.getLeadActor(), data.get(6)[1]);

    }

    @Test
    @Order(7)
    public void testPutNotFound() throws Exception {
        String content = "{\"movieName\":\""+data.get(6)[0]+"\",\"leadActor\":\""+data.get(6)[1]+"\"}";

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/movies/33")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(8)
    public void testPut() throws Exception {

        String content = "{\"movieName\":\""+data.get(7)[0]+"\",\"leadActor\":\""+data.get(7)[1]+"\"}";


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/movies/2")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.movieName",
                        Matchers.equalToIgnoringCase(data.get(7)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(7)[1])));
    }

    @Test
    @Order(9)
    public void testAfterPut() throws Exception {

        mockMvc.perform(get("/movies/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.movieName",
                        Matchers.equalToIgnoringCase(data.get(7)[0])))
                .andExpect(jsonPath("$.leadActor",
                        Matchers.equalToIgnoringCase(data.get(7)[1])));

    }

    @Test
    @Order(10)
    public void testDbAfterPut() throws Exception {

        Movie movie = movieJpaRepository.findById(2).get();
        assertEquals(data.get(7)[0], movie.getMovieName());
        assertEquals(data.get(7)[1], movie.getLeadActor());
    }

    @Test
    @Order(11)
    public void testDeleteNotFound() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/movies/90");
        mockMvc.perform(mockRequest).andExpect(status().isNotFound());

    }

    @Test
    @Order(12)
    public void testDelete() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/movies/6");

        mockMvc.perform(mockRequest).andExpect(status().isOk());

    }

    @Test
    @Order(13)
    public void testAfterDelete() throws Exception {

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(5)))
                .andExpect(jsonPath("$[0].movieId", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].movieName",
                        Matchers.equalToIgnoringCase(data.get(1)[0])))
                .andExpect(jsonPath("$[0].leadActor",
                        Matchers.equalToIgnoringCase(data.get(1)[1])))

                .andExpect(jsonPath("$[1].movieId", Matchers.equalTo(2)))
                .andExpect(jsonPath("$[1].movieName",
                        Matchers.equalToIgnoringCase(data.get(7)[0])))
                .andExpect(jsonPath("$[1].leadActor",
                        Matchers.equalToIgnoringCase(data.get(7)[1])))

                .andExpect(jsonPath("$[2].movieId", Matchers.equalTo(3)))
                .andExpect(jsonPath("$[2].movieName",
                        Matchers.equalToIgnoringCase(data.get(3)[0])))
                .andExpect(jsonPath("$[2].leadActor",
                        Matchers.equalToIgnoringCase(data.get(3)[1])))

                .andExpect(jsonPath("$[3].movieId", Matchers.equalTo(4)))
                .andExpect(jsonPath("$[3].movieName",
                        Matchers.equalToIgnoringCase(data.get(4)[0])))
                .andExpect(jsonPath("$[3].leadActor",
                        Matchers.equalToIgnoringCase(data.get(4)[1])))

                .andExpect(jsonPath("$[4].movieId", Matchers.equalTo(5)))
                .andExpect(jsonPath("$[4].movieName",
                        Matchers.equalToIgnoringCase(data.get(5)[0])))
                .andExpect(jsonPath("$[4].leadActor",
                        Matchers.equalToIgnoringCase(data.get(5)[1])));

        mockMvc.perform(get("/movies/6")).andExpect(status().isNotFound());
    }

    @AfterAll
    public void cleanup() {

        jdbcTemplate.execute("drop table movieList");
    }

}
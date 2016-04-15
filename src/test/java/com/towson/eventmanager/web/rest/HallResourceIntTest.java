package com.towson.eventmanager.web.rest;

import com.towson.eventmanager.Application;
import com.towson.eventmanager.domain.Hall;
import com.towson.eventmanager.repository.HallRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HallResource REST controller.
 *
 * @see HallResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HallResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    @Inject
    private HallRepository hallRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHallMockMvc;

    private Hall hall;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HallResource hallResource = new HallResource();
        ReflectionTestUtils.setField(hallResource, "hallRepository", hallRepository);
        this.restHallMockMvc = MockMvcBuilders.standaloneSetup(hallResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hallRepository.deleteAll();
        hall = new Hall();
        hall.setName(DEFAULT_NAME);
        hall.setAddress(DEFAULT_ADDRESS);
        hall.setCapacity(DEFAULT_CAPACITY);
    }

    @Test
    public void createHall() throws Exception {
        int databaseSizeBeforeCreate = hallRepository.findAll().size();

        // Create the Hall

        restHallMockMvc.perform(post("/api/halls")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hall)))
                .andExpect(status().isCreated());

        // Validate the Hall in the database
        List<Hall> halls = hallRepository.findAll();
        assertThat(halls).hasSize(databaseSizeBeforeCreate + 1);
        Hall testHall = halls.get(halls.size() - 1);
        assertThat(testHall.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHall.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHall.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    public void getAllHalls() throws Exception {
        // Initialize the database
        hallRepository.save(hall);

        // Get all the halls
        restHallMockMvc.perform(get("/api/halls?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hall.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)));
    }

    @Test
    public void getHall() throws Exception {
        // Initialize the database
        hallRepository.save(hall);

        // Get the hall
        restHallMockMvc.perform(get("/api/halls/{id}", hall.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hall.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY));
    }

    @Test
    public void getNonExistingHall() throws Exception {
        // Get the hall
        restHallMockMvc.perform(get("/api/halls/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateHall() throws Exception {
        // Initialize the database
        hallRepository.save(hall);

		int databaseSizeBeforeUpdate = hallRepository.findAll().size();

        // Update the hall
        hall.setName(UPDATED_NAME);
        hall.setAddress(UPDATED_ADDRESS);
        hall.setCapacity(UPDATED_CAPACITY);

        restHallMockMvc.perform(put("/api/halls")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hall)))
                .andExpect(status().isOk());

        // Validate the Hall in the database
        List<Hall> halls = hallRepository.findAll();
        assertThat(halls).hasSize(databaseSizeBeforeUpdate);
        Hall testHall = halls.get(halls.size() - 1);
        assertThat(testHall.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHall.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHall.getCapacity()).isEqualTo(UPDATED_CAPACITY);
    }

    @Test
    public void deleteHall() throws Exception {
        // Initialize the database
        hallRepository.save(hall);

		int databaseSizeBeforeDelete = hallRepository.findAll().size();

        // Get the hall
        restHallMockMvc.perform(delete("/api/halls/{id}", hall.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Hall> halls = hallRepository.findAll();
        assertThat(halls).hasSize(databaseSizeBeforeDelete - 1);
    }
}

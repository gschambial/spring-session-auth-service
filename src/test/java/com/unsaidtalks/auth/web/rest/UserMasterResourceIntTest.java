package com.unsaidtalks.auth.web.rest;

import static com.unsaidtalks.auth.web.rest.TestUtil.createFormattingConversionService;
import static com.unsaidtalks.auth.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.unsaidtalks.auth.AuthServiceApp;
import com.unsaidtalks.auth.domain.UserMaster;
import com.unsaidtalks.auth.repository.UserMasterRepository;
import com.unsaidtalks.auth.service.UserMasterService;
import com.unsaidtalks.auth.service.dto.UserMasterDTO;
import com.unsaidtalks.auth.service.mapper.UserMasterMapper;
import com.unsaidtalks.auth.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the UserMasterResource REST controller.
 *
 * @see UserMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApp.class)
public class UserMasterResourceIntTest {

	private static final String DEFAULT_USER_ID = "AAAAAAAAAA";

	private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
	private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

	private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
	private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
	private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_USER_EMAIL = "AAAAAAAAAA";
	private static final String UPDATED_USER_EMAIL = "BBBBBBBBBB";

	private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
	private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

	private static final ZonedDateTime DEFAULT_DOB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
	private static final ZonedDateTime UPDATED_DOB = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

	private static final String DEFAULT_GENDER = "AAAAAAAAAA";
	private static final String UPDATED_GENDER = "BBBBBBBBBB";

	private static final Integer DEFAULT_STATUS = 1;
	private static final Integer UPDATED_STATUS = 2;

	private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneOffset.UTC);
	private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

	private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneOffset.UTC);
	private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

	private static final String DEFAULT_USER_PHONE = "AAAAAAAAAA";
	private static final String UPDATED_USER_PHONE = "BBBBBBBBBB";

	private static final Boolean DEFAULT_EMAIL_AUTH = false;
	private static final Boolean UPDATED_EMAIL_AUTH = true;

	private static final Boolean DEFAULT_PHONE_AUTH = false;
	private static final Boolean UPDATED_PHONE_AUTH = true;

	@Autowired
	private UserMasterRepository userMasterRepository;

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restUserMasterMockMvc;

	private UserMaster userMaster;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final UserMasterResource userMasterResource = new UserMasterResource(userMasterService);
		this.restUserMasterMockMvc = MockMvcBuilders.standaloneSetup(userMasterResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
				.setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
				.build();
	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static UserMaster createEntity(EntityManager em) {
		UserMaster userMaster = new UserMaster().password(DEFAULT_PASSWORD).firstName(DEFAULT_FIRST_NAME)
				.lastName(DEFAULT_LAST_NAME).userEmail(DEFAULT_USER_EMAIL).countryCode(DEFAULT_COUNTRY_CODE)
				.dob(DEFAULT_DOB).gender(DEFAULT_GENDER).status(DEFAULT_STATUS).createDate(DEFAULT_CREATE_DATE)
				.updateDate(DEFAULT_UPDATE_DATE).userPhone(DEFAULT_USER_PHONE).emailAuth(DEFAULT_EMAIL_AUTH)
				.phoneAuth(DEFAULT_PHONE_AUTH);
		return userMaster;
	}

	@Before
	public void initTest() {
		userMaster = createEntity(em);
	}

	@Test
	@Transactional
	public void createUserMaster() throws Exception {
		int databaseSizeBeforeCreate = userMasterRepository.findAll().size();

		// Create the UserMaster
		userMaster.setId("gschambial");
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);
		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isCreated());

		// Validate the UserMaster in the database
		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeCreate + 1);
		UserMaster testUserMaster = userMasterList.get(userMasterList.size() - 1);
		assertThat(testUserMaster.getPassword()).isEqualTo(DEFAULT_PASSWORD);
		assertThat(testUserMaster.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
		assertThat(testUserMaster.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
		assertThat(testUserMaster.getUserEmail()).isEqualTo(DEFAULT_USER_EMAIL);
		assertThat(testUserMaster.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
		assertThat(testUserMaster.getDob()).isEqualTo(DEFAULT_DOB);
		assertThat(testUserMaster.getGender()).isEqualTo(DEFAULT_GENDER);
		assertThat(testUserMaster.getStatus()).isEqualTo(DEFAULT_STATUS);
		assertThat(testUserMaster.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
		assertThat(testUserMaster.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
		assertThat(testUserMaster.getUserPhone()).isEqualTo(DEFAULT_USER_PHONE);
		assertThat(testUserMaster.isEmailAuth()).isEqualTo(DEFAULT_EMAIL_AUTH);
		assertThat(testUserMaster.isPhoneAuth()).isEqualTo(DEFAULT_PHONE_AUTH);
	}

	@Test
	@Transactional
	public void checkPasswordIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setPassword(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkFirstNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setFirstName(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkLastNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setLastName(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkUserEmailIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setUserEmail(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkCountryCodeIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setCountryCode(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkDobIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setDob(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkGenderIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setGender(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkCreateDateIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setCreateDate(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkUpdateDateIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setUpdateDate(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkUserPhoneIsRequired() throws Exception {
		int databaseSizeBeforeTest = userMasterRepository.findAll().size();
		// set the field null
		userMaster.setUserPhone(null);

		// Create the UserMaster, which fails.
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		restUserMasterMockMvc.perform(post("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllUserMasters() throws Exception {
		userMaster.setId("gschambial");
		// Initialize the database
		userMasterRepository.saveAndFlush(userMaster);

		// Get all the userMasterList
		restUserMasterMockMvc.perform(get("/api/user-masters?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(userMaster.getId().toString())))
				.andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
				.andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
				.andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
				.andExpect(jsonPath("$.[*].userEmail").value(hasItem(DEFAULT_USER_EMAIL.toString())))
				.andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
				.andExpect(jsonPath("$.[*].dob").value(hasItem(sameInstant(DEFAULT_DOB))))
				.andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
				.andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
				.andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
				.andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))))
				.andExpect(jsonPath("$.[*].userPhone").value(hasItem(DEFAULT_USER_PHONE.toString())))
				.andExpect(jsonPath("$.[*].emailAuth").value(hasItem(DEFAULT_EMAIL_AUTH.booleanValue())))
				.andExpect(jsonPath("$.[*].phoneAuth").value(hasItem(DEFAULT_PHONE_AUTH.booleanValue())));
	}

	@Test
	@Transactional
	public void getUserMaster() throws Exception {
		userMaster.setId("gschambial");
		// Initialize the database
		userMasterRepository.saveAndFlush(userMaster);

		// Get the userMaster
		restUserMasterMockMvc.perform(get("/api/user-masters/{id}", userMaster.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(userMaster.getId().toString()))
				.andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
				.andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
				.andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
				.andExpect(jsonPath("$.userEmail").value(DEFAULT_USER_EMAIL.toString()))
				.andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
				.andExpect(jsonPath("$.dob").value(sameInstant(DEFAULT_DOB)))
				.andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
				.andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
				.andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
				.andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)))
				.andExpect(jsonPath("$.userPhone").value(DEFAULT_USER_PHONE.toString()))
				.andExpect(jsonPath("$.emailAuth").value(DEFAULT_EMAIL_AUTH.booleanValue()))
				.andExpect(jsonPath("$.phoneAuth").value(DEFAULT_PHONE_AUTH.booleanValue()));
	}

	@Test
	@Transactional
	public void getNonExistingUserMaster() throws Exception {
		// Get the userMaster
		restUserMasterMockMvc.perform(get("/api/user-masters/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateUserMaster() throws Exception {
		userMaster.setId("gschambial");
		// Initialize the database
		userMasterRepository.saveAndFlush(userMaster);

		int databaseSizeBeforeUpdate = userMasterRepository.findAll().size();

		// Update the userMaster
		UserMaster updatedUserMaster = userMasterRepository.findById(userMaster.getId()).get();
		// Disconnect from session so that the updates on updatedUserMaster are not
		// directly saved in db
		em.detach(updatedUserMaster);
		updatedUserMaster.password(UPDATED_PASSWORD).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME)
				.userEmail(UPDATED_USER_EMAIL).countryCode(UPDATED_COUNTRY_CODE).dob(UPDATED_DOB).gender(UPDATED_GENDER)
				.status(UPDATED_STATUS).createDate(UPDATED_CREATE_DATE).updateDate(UPDATED_UPDATE_DATE)
				.userPhone(UPDATED_USER_PHONE).emailAuth(UPDATED_EMAIL_AUTH).phoneAuth(UPDATED_PHONE_AUTH);
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(updatedUserMaster);

		restUserMasterMockMvc.perform(put("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isOk());

		// Validate the UserMaster in the database
		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeUpdate);
		UserMaster testUserMaster = userMasterList.get(userMasterList.size() - 1);
		assertThat(testUserMaster.getPassword()).isEqualTo(UPDATED_PASSWORD);
		assertThat(testUserMaster.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
		assertThat(testUserMaster.getLastName()).isEqualTo(UPDATED_LAST_NAME);
		assertThat(testUserMaster.getUserEmail()).isEqualTo(UPDATED_USER_EMAIL);
		assertThat(testUserMaster.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
		assertThat(testUserMaster.getDob()).isEqualTo(UPDATED_DOB);
		assertThat(testUserMaster.getGender()).isEqualTo(UPDATED_GENDER);
		assertThat(testUserMaster.getStatus()).isEqualTo(UPDATED_STATUS);
		assertThat(testUserMaster.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
		assertThat(testUserMaster.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
		assertThat(testUserMaster.getUserPhone()).isEqualTo(UPDATED_USER_PHONE);
		assertThat(testUserMaster.isEmailAuth()).isEqualTo(UPDATED_EMAIL_AUTH);
		assertThat(testUserMaster.isPhoneAuth()).isEqualTo(UPDATED_PHONE_AUTH);
	}

	@Test
	@Transactional
	public void updateNonExistingUserMaster() throws Exception {
		int databaseSizeBeforeUpdate = userMasterRepository.findAll().size();

		// Create the UserMaster
		UserMasterDTO userMasterDTO = userMasterMapper.toDto(userMaster);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restUserMasterMockMvc.perform(put("/api/user-masters").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userMasterDTO))).andExpect(status().isBadRequest());

		// Validate the UserMaster in the database
		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteUserMaster() throws Exception {
		userMaster.setId("gschambial");
		// Initialize the database
		userMasterRepository.saveAndFlush(userMaster);

		int databaseSizeBeforeDelete = userMasterRepository.findAll().size();

		// Get the userMaster
		userMaster.setId("gschambial");
		restUserMasterMockMvc
				.perform(delete("/api/user-masters/{id}", "gschambial").accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<UserMaster> userMasterList = userMasterRepository.findAll();
		assertThat(userMasterList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(UserMaster.class);
		UserMaster userMaster1 = new UserMaster();
		userMaster1.setId("gschambial");
		UserMaster userMaster2 = new UserMaster();
		userMaster2.setId(userMaster1.getId());
		assertThat(userMaster1).isEqualTo(userMaster2);
		userMaster2.setId("gschambial1");
		assertThat(userMaster1).isNotEqualTo(userMaster2);
		userMaster1.setId(null);
		assertThat(userMaster1).isNotEqualTo(userMaster2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(UserMasterDTO.class);
		UserMasterDTO userMasterDTO1 = new UserMasterDTO();
		userMasterDTO1.setId("gschambial");
		UserMasterDTO userMasterDTO2 = new UserMasterDTO();
		assertThat(userMasterDTO1).isNotEqualTo(userMasterDTO2);
		userMasterDTO2.setId(userMasterDTO1.getId());
		assertThat(userMasterDTO1).isEqualTo(userMasterDTO2);
		userMasterDTO2.setId("gschambial1");
		assertThat(userMasterDTO1).isNotEqualTo(userMasterDTO2);
		userMasterDTO1.setId(null);
		assertThat(userMasterDTO1).isNotEqualTo(userMasterDTO2);
	}

	@Test
	@Transactional
	public void testEntityFromId() {
		assertThat(userMasterMapper.fromId("gschambial").getId()).isEqualTo("gschambial");
		assertThat(userMasterMapper.fromId(null)).isNull();
	}
}

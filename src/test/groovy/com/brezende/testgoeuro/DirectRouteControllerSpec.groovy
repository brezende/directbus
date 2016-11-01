package com.brezende.testgoeuro

import com.brezende.testgoeuro.service.RouteService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

import static org.mockito.BDDMockito.given
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@RunWith(SpringRunner)
@SpringBootTest(webEnvironment=MOCK, classes=Main)
public class DirectRouteControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext

	private MockMvc mockMvc

	@MockBean
	private RouteService service

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build()
	}

	@Test
	public void "Service should output a response body compatible with requirements"() {
		given(service.directRoute(1,2)).willReturn(true)

		mockMvc.perform(get("/api/direct?dep_sid=1&arr_sid=2"))
			.andExpect(status().isOk())
			.andExpect(content().json(
				"""
				{
					"dep_sid":1,
					"arr_sid":2,
					"direct_bus_route":true
				}
				"""
			))
	}

	@Test
	public void "Requests missing required params should return an error"() {
		mockMvc.perform(get("/api/direct?dep_sid=1"))
				.andExpect(status().is4xxClientError())

		mockMvc.perform(get("/api/direct?arr_sid=1"))
				.andExpect(status().is4xxClientError())

		mockMvc.perform(get("/api/direct"))
				.andExpect(status().is4xxClientError())

		given(service.directRoute(1,2)).willReturn(true)
		mockMvc.perform(get("/api/direct?dep_sid=1&arr_sid=2&unknown_par=x"))
				.andExpect(status().isOk())
	}

	@Test
	public void "Station ids should be integers"() {
		given(service.directRoute(1,2)).willReturn(true)
		mockMvc.perform(get("/api/direct?dep_sid=1&arr_sid=2"))
				.andExpect(status().isOk())

		mockMvc.perform(get("/api/direct?dep_sid=A&arr_sid=B"))
				.andExpect(status().is4xxClientError())

		mockMvc.perform(get("/api/direct?dep_sid=1.0&arr_sid=2.0"))
				.andExpect(status().is4xxClientError())
	}
}
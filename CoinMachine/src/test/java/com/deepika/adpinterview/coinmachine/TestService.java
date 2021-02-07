package com.deepika.adpinterview.coinmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.deepika.adpinterview.coinmachine.db.CoinCount;
import com.deepika.adpinterview.coinmachine.db.CoinMachineRepo;
import com.deepika.adpinterview.coinmachine.exception.CustomException;
import com.deepika.adpinterview.coinmachine.service.CoinCountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class TestService {

	@Autowired
	CoinCountService ccService;

	@MockBean
	private CoinMachineRepo coinMachineRepo;

	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	protected void setUpMVC() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@BeforeEach
	public void init() {
		List<CoinCount> dbList = new ArrayList<CoinCount>();
		dbList.add(new CoinCount(1, 100));
		dbList.add(new CoinCount(5, 100));
		dbList.add(new CoinCount(10, 100));
		dbList.add(new CoinCount(25, 100));

		when(coinMachineRepo.findAll()).thenReturn(dbList);

		when(coinMachineRepo.saveAll(any(dbList.getClass()))).thenReturn(new ArrayList<CoinCount>());

		// when(coinMachineRepo.save(any(CoinCount.class))).thenReturn(new CoinCount());
		
		setUpMVC
		();
	}

	@Test
	public void testMaxCoins() throws CustomException {

		HashMap<String, Integer> inputMap = new HashMap<String, Integer>();
		inputMap.put("10", 3);
		HashMap<String, Integer> outputMap = ccService.getChange(inputMap, true);
		assertEquals(4, outputMap.size());
		assertEquals(outputMap.get("1"), 100);
		assertEquals(outputMap.get("5"), 100);
		assertEquals(outputMap.get("25"), 56);
		assertEquals(outputMap.get("10"), 100);
	}

	@Test
	public void testMinCoins() throws CustomException {

		HashMap<String, Integer> inputMap = new HashMap<String, Integer>();
		inputMap.put("10", 3);
		HashMap<String, Integer> outputMap = ccService.getChange(inputMap, false);
		assertEquals(2, outputMap.size());
		assertEquals(outputMap.get("25"), 100);
		assertEquals(outputMap.get("10"), 50);

	}

	@Test
	public void testMaxCoinsAPI() throws Exception{

		HashMap<String, Integer> inputMap = new HashMap<String, Integer>();
		inputMap.put("10", 3);
		
		String inputJson = new ObjectMapper().writeValueAsString(inputMap);
		
		String uri = "/v1/change/maxCoins";
			
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		HashMap<String, Integer>  outputMap = new ObjectMapper().readValue(content, HashMap.class);

		assertEquals(4, outputMap.size());
		assertEquals(outputMap.get("1"), 100);
		assertEquals(outputMap.get("5"), 100);
		assertEquals(outputMap.get("25"), 56);
		assertEquals(outputMap.get("10"), 100);
	}
	
	@Test
	public void testMinCoinsAPI() throws Exception {

		HashMap<String, Integer> inputMap = new HashMap<String, Integer>();
		inputMap.put("10", 3);

		String inputJson = new ObjectMapper().writeValueAsString(inputMap);
		String uri = "/v1/change/minCoins";
			
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		HashMap<String, Integer>  outputMap = new ObjectMapper().readValue(content, HashMap.class);
		assertEquals(2, outputMap.size());
		assertEquals(outputMap.get("25"), 100);
		assertEquals(outputMap.get("10"), 50);

	}
	
	@Test
	public void testInvalidURI() throws Exception {

		HashMap<String, Integer> inputMap = new HashMap<String, Integer>();
		inputMap.put("10", 3);

		String inputJson = new ObjectMapper().writeValueAsString(inputMap);
		String uri = "/v1/change/mnCoins";
			
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		

	}

}

package com.deepika.adpinterview.coinmachine.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepika.adpinterview.coinmachine.exception.CustomException;
import com.deepika.adpinterview.coinmachine.service.CoinCountService;

@RestController
public class APIController {
	
	@Autowired
	CoinCountService ccService;
	
	@PostMapping(
			value="/v1/change/{minCoinsORmaxCoins}", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String,Integer> getMinCoinChange(@RequestBody HashMap<String, Integer> billsToChange, @PathVariable("minCoinsORmaxCoins") String minORmax ) throws CustomException{
		HashMap<String,Integer> retMap = new HashMap<String,Integer>();
		
		if(minORmax.equalsIgnoreCase("minCoins"))
			retMap = ccService.getChange(billsToChange,false);
		else if(minORmax.equalsIgnoreCase("maxCoins"))
			retMap = ccService.getChange(billsToChange, true);
		else
			throw new CustomException("1003","Invalid path parameter should be either minCoins or maxCoins ");
		
		return retMap;
	}
	

}

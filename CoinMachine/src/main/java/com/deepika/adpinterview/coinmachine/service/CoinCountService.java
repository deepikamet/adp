package com.deepika.adpinterview.coinmachine.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deepika.adpinterview.coinmachine.db.CoinCount;
import com.deepika.adpinterview.coinmachine.db.CoinMachineRepo;
import com.deepika.adpinterview.coinmachine.exception.CustomException;

@Component
public class CoinCountService {

	@Autowired
	CoinMachineRepo coinMachineRepo;

	public synchronized HashMap<String, Integer> getChange(HashMap<String, Integer> billsToChange, boolean isMaxCoins)
			throws CustomException {

		HashMap<String, Integer> returnMap = new HashMap<String, Integer>();
		int total = 0;
		// 1. To retrieve the total bill value
		for (String key : billsToChange.keySet()) {
			try {
				total += (billsToChange.get(key) * Integer.parseInt(key));
			} catch (NullPointerException npe) {
				throw new CustomException("1002", "Invalid Data");
			}
		}
		System.out.println("Total : " + total);
		int balanceInCents = total * 100;

		// 2. To retrieve the data from repo and put it in map
		Map<Integer, Integer> dbCoinCountsMap = coinMachineRepo.findAll().stream()
				.collect(Collectors.toMap(CoinCount::getCoinVal, CoinCount::getAvlCt));
		
		List<CoinCount> dbUpdateList = new ArrayList<CoinCount>();
		List<Integer> dbCoinCountsMapKeys = new ArrayList<Integer>();

		// 3. retrieve the keys from map based on the mincoins or maxcoins
		if (isMaxCoins)
			dbCoinCountsMapKeys = dbCoinCountsMap.keySet().stream().sorted().collect(Collectors.toList());
		else
			dbCoinCountsMapKeys = dbCoinCountsMap.keySet().stream().sorted(Comparator.reverseOrder())
					.collect(Collectors.toList());

		for (int coinValue : dbCoinCountsMapKeys) {
			if (balanceInCents == 0)
				break;
			int avlCoinCount = dbCoinCountsMap.get(coinValue);
			int returnCoinCount = balanceInCents / coinValue;
			if (avlCoinCount >= returnCoinCount) {
				// deduct from DB
				dbUpdateList.add(new CoinCount(coinValue, avlCoinCount - returnCoinCount));
				// add to return map
				returnMap.put(String.valueOf(coinValue), returnCoinCount);
				// update balance
				balanceInCents = 0;
			} else if(avlCoinCount != 0){
				// deduct from DB
				dbUpdateList.add(new CoinCount(coinValue, 0));
				// add to return map
				returnMap.put(String.valueOf(coinValue), avlCoinCount);
				// update balance
				balanceInCents -= avlCoinCount * coinValue;
			}
		}
		if (balanceInCents == 0)
			coinMachineRepo.saveAll(dbUpdateList);
		else
			throw new CustomException("1001", "Not Enough Coins.");
		
		return returnMap;
	}
}

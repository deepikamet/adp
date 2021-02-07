package com.deepika.adpinterview.coinmachine.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
	
	@Value("${app.initial.coin.count}")
	int initialCointCount;
	
	@Autowired
	CoinMachineRepo coinMachineRepo;
	
	@Autowired
	void initializeDB() {
		List<CoinCount> coinCtList = new ArrayList<CoinCount>();
		
		coinCtList.add(new CoinCount(1,initialCointCount));
		coinCtList.add(new CoinCount(5,initialCointCount));
		coinCtList.add(new CoinCount(10,initialCointCount));
		coinCtList.add(new CoinCount(25,initialCointCount));
			
		coinMachineRepo.saveAll(coinCtList);
	}

}

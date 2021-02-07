package com.deepika.adpinterview.coinmachine.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinMachineRepo extends JpaRepository<CoinCount, Integer>{
	
	//CoinCount findByCoinVal(int coinVal) ;

}

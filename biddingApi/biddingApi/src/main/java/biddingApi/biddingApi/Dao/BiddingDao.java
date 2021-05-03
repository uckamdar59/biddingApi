package biddingApi.biddingApi.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biddingApi.biddingApi.Entities.BiddingData;

@Repository
public interface BiddingDao extends JpaRepository<BiddingData,String>{

	
	
}

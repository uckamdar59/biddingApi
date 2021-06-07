package biddingApi.biddingApi.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biddingApi.biddingApi.Entities.BiddingData;

@Repository
public interface BiddingDao extends JpaRepository<BiddingData,String>{

	public List<BiddingData> findByLoadId(String loadId);
    public BiddingData findByLoadIdAndTransporterId(String loadId,String transporterId);
	public List<BiddingData> findByLoadId(String loadId, Pageable p);
	
}
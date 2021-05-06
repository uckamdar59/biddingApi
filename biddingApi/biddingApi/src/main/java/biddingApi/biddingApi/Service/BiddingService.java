package biddingApi.biddingApi.Service;

import java.util.List;

import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;

public interface BiddingService {

	public BidPostResponse addBid(BidPostRequest bidPostRequest);
	public List<BiddingData> getBid(String loadId);
	public void deleteBid(String id);
	public BiddingData getBidById(String id);
	
}

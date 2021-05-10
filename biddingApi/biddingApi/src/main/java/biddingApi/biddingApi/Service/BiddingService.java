package biddingApi.biddingApi.Service;

import java.util.List;

import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;
import biddingApi.biddingApi.Model.BidPutRequest;
import biddingApi.biddingApi.Model.BidPutResponse;

public interface BiddingService {

	public BidPostResponse addBid(BidPostRequest bidPostRequest);
	public List<BiddingData> getBid(Integer pageNo, String loadId);
	public void deleteBid(String id);
	public BiddingData getBidById(String id);
	public BidPutResponse updateBid(String id, BidPutRequest bidPutRequest);
	
}

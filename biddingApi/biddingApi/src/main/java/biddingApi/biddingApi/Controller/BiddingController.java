package biddingApi.biddingApi.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;
import biddingApi.biddingApi.Service.BiddingService;

@RestController
public class BiddingController {

	@Autowired
	private BiddingService biddingService;
	
	@GetMapping("/bidding")
	public List<BiddingData> getBid(@RequestParam(value="loadId",required=false) String loadId)
	{
		return biddingService.getBid(loadId);
	}
	
	@GetMapping("/bidding/{Id}")
	public BiddingData getBidById(@PathVariable String Id)
	{
		return biddingService.getBidById(Id);
	}
	
	
	@PostMapping("/bidding")
	public BidPostResponse addBid(@RequestBody BidPostRequest bidPostRequest)
	{
		return biddingService.addBid(bidPostRequest);
	}
	
	@DeleteMapping("/bidding/{Id}")
	public void deleteBid(@PathVariable String Id)
	{
	biddingService.deleteBid(Id);
	}
	
	
}

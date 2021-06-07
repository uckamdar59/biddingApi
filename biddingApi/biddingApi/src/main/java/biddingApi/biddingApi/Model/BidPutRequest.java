package biddingApi.biddingApi.Model;

import java.util.List;

import lombok.Data;

public @Data class BidPutRequest {
	
	private Long rate;

	private List<String> truckId;
	private Boolean transporterApproval;
	private Boolean shipperApproval;
}

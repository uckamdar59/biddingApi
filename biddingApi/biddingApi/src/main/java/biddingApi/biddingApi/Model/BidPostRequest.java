package biddingApi.biddingApi.Model;

import java.util.List;

import lombok.Data;

public @Data class BidPostRequest {

	private String transporterId;
	private Long rate;
	private String loadId;
	private List<String> truckId;
	private Boolean transporterApproval;
	private Boolean shipperApproval;
	
}

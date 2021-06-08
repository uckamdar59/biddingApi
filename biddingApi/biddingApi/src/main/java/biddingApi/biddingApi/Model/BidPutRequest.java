package biddingApi.biddingApi.Model;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

public @Data class BidPutRequest {
	
	private Long rate;
	@Enumerated(EnumType.STRING)
	private UnitValue unitValue;
	public enum UnitValue{
		PER_TON, PER_TRUCK
	}

	private List<String> truckId;
	private Boolean transporterApproval;
	private Boolean shipperApproval;
}

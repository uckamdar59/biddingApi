package biddingApi.biddingApi.Model;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

public @Data class BidPostRequest {

	private String transporterId;
	private Long rate;
	private String loadId;
	private List<String> truckId;
	
	@Enumerated(EnumType.STRING)
	private UnitValue unitValue;

	public enum UnitValue{
		PER_TON, PER_TRUCK
	}

}

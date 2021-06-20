package biddingApi.biddingApi.Model;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import biddingApi.biddingApi.Entities.BiddingData.UnitValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public @Data class BidPostRequest {

	private String transporterId;
	private String loadId;
	private Long rate;
	private UnitValue unitValue;
	private List<String> truckId;
	private String biddingDate;
}

package biddingApi.biddingApi.ErrorConstants;

import lombok.Data;

public @Data class Constants {

	private String success = "Success";
	private String uLoadIdIsNullAndTransPorterIdIsNull = "Failed: TransporterId and LoadId both cannot be null simulataneously";
	
}

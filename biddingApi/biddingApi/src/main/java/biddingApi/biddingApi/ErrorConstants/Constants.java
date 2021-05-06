package biddingApi.biddingApi.ErrorConstants;

import lombok.Data;

public @Data class Constants {

	private String success = "Success";
	private String pLoadIdAndTransPorterIdAreNull = "Failed: TransporterId and LoadId both cannot be null simulataneously";
	private String pLoadIdAndTransporterIdAreNotNull = "Failed: TransporterId and LoadId both cannot be assigned simulataneously";
}

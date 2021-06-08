package biddingApi.biddingApi.ErrorConstants;

public class Constants {

	public static String success = "Success";
	public static String pLoadIdAndTransPorterIdAreNull = "Failed: TransporterId and LoadId both cannot be null simulataneously";
	public static String pLoadIdIsNull = "Failed: LoadId cannot be null";
	public static String pTransporterRateIsNull = "Failed: Transporter Rate Cannot be null";
	public static String pBidExists = "Failed: LoadId already exists, where transporterId may be null or not";
	public static String pLoadIdAndTransporterIdExists = "Failed: LoadId is already associated with TransporterId";
	public static String dataSavingExcetion = "Failed: Problem with data integration";
	public static String uTransportIdIsNullTruckUpdate = "Failed: Cannot update TruckId's where TransporterId is Null";
	public static String uShipperIdNotNullTruckUpdate = "Failed: Cannot update TruckId by shipper";
	public static String uDataNotExists = "Failed: Data doesn't exists for given Id";
	public static String uBothApproval = "Failed: Cannot update both Shipper and Transporter Approval";
	public static String uTApprovalWhereTidIsNull = "Failed: Cannot update Transporter Approval where TransporterId is Null";
	public static String uRateBothApprovalNull = "Failed: both Shipper or Transporter Approval cannot be null when rate is not Null";
	public static String dSuccess = "bid-Account successfully deleted";
	public static String dDataNotExists = "Failed: Data doesn't exists for given Id";
	public static String unitValueisNull = "Failed: unitValue cannot be null when rate is not Null";
}

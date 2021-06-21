
package biddingApi.biddingApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import biddingApi.biddingApi.Dao.BiddingDao;
import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.ErrorConstants.Constants;
import biddingApi.biddingApi.Model.BidDeleteResponse;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;
import biddingApi.biddingApi.Model.BidPutRequest;
import biddingApi.biddingApi.Model.BidPutResponse;
import biddingApi.biddingApi.Service.BiddingServiceImpl;

@SpringBootTest
public class TestBiddingService {

	@Autowired
	private BiddingServiceImpl biddingService;

	@MockBean
	private BiddingDao biddingDao;

	@Test
	public void addDataSuccess() {

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:123", (long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.save(listBiddingData.get(0))).thenReturn(listBiddingData.get(0));

		BidPostResponse bidPostResponse = new BidPostResponse(Constants.success, Constants.ID,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:123"), false, true, null);

		assertEquals(bidPostResponse.getStatus(), biddingService.addBid(bidPostRequest).getStatus());

	}

//	@Test
//	public void addDataFailed_invalidTransporterId() {
//
//		BidPostRequest bidPostRequest = new BidPostRequest(null, "AP 32 AD 2220", null, (long) 0, null, null, null, null);
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		when(biddingDao.save(listBiddingData.get(1))).thenReturn(listBiddingData.get(1));
//
//		BidPostResponse response = new BidPostResponse(TruckConstants.IN_CORRECT_TRANSPORTER_ID, null, null,
//				null, null, null, 0, null, null, null, null);
//
//		assertEquals(response, biddingService.addData(bidPostRequest));
//
//	}

	@Test
	public void addDataFailed_invalidLoadId_null() {

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null,
				(long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));

		BidPostResponse response = new BidPostResponse(Constants.pLoadIdIsNull, null, null, null, null, null, null,
				null, null, null);

		assertEquals(response, biddingService.addBid(bidPostRequest));

	}

	@Test
	public void addDataFailed_invalidRate_null() {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));

		BidPostResponse response = new BidPostResponse(Constants.pTransporterRateIsNull, null, null, null, null, null,
				null, null, null, null);

		assertEquals(response, biddingService.addBid(bidPostRequest));
	}

	@Test
	public void addDataFailed_invalidUnitValue_null() {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", (long) 23, null, Arrays.asList("truck:123"), null);

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));

		BidPostResponse response = new BidPostResponse(Constants.unitValueisNull, null, null, null, null, null, null,
				null, null, null);

		assertEquals(response, biddingService.addBid(bidPostRequest));
	}

	@Test
	public void addDataFailed_invalidUnitValue_null_Rate_null() {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", null, null, Arrays.asList("truck:123"), null);

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.save(listBiddingData.get(2))).thenReturn(listBiddingData.get(2));

		BidPostResponse response = new BidPostResponse(Constants.pTransporterRateIsNull, null, null, null, null, null,
				null, null, null, null);

		assertEquals(response, biddingService.addBid(bidPostRequest));
	}

	@Test
	public void getBiddingDataWithIdSuccess() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

//		
		assertEquals(listBiddingData.get(0), biddingService.getBidById(Constants.ID));

	}

	@Test
	public void updateSuccess() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abc"), null, false, null);

		BidPutResponse response = new BidPutResponse(Constants.uSuccess, Constants.ID,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123", (long) 20, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:123"), null, false, null);

	}

	@Test
	public void updateDataFailed_AccountNotFound() {

		String wrongBidId = "bid:62cc8557-52cd-4742-a11e-276cc7abcde";

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abc"), true, false, wrongBidId);

		BidPutResponse response = new BidPutResponse(Constants.uDataNotExists, null, null, null, null, null, null, null,
				null, null);

		assertEquals(response, biddingService.updateBid(wrongBidId, bidPutRequest));

	}

	@Test
	public void getTruckDataPagableSuccess_LoadId() {

		Pageable currentPage;
		Integer pageNo;

		List<BiddingData> listBiddingData = createBiddingData();

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) Constants.pageSize);

		when(biddingDao.findByLoadId(Constants.LOAD_ID, currentPage)).thenReturn(listBiddingData.subList(2, 4));

		assertEquals(listBiddingData.subList(2, 4), biddingService.getBid(pageNo, Constants.LOAD_ID,null));

	}

	@Test
	public void deleteDataSuccess() {

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidDeleteResponse response = new BidDeleteResponse(Constants.dSuccess);

		assertEquals(response, biddingService.deleteBid(Constants.ID));

	}

	@Test
	public void deleteDataFailed_AccountNotFound() {

		String wrongBidId = "bid:xyz";

		List<BiddingData> listBiddingData = createBiddingData();

		when(biddingDao.findById(Constants.ID)).thenReturn(Optional.ofNullable(listBiddingData.get(0)));

		BidDeleteResponse response = new BidDeleteResponse(Constants.dDataNotExists);

		assertEquals(response, biddingService.deleteBid(wrongBidId));

	}

	public List<BiddingData> createBiddingData() {
		List<BiddingData> biddingList = Arrays.asList(
				new BiddingData(Constants.ID, "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:1234",
						(long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id1", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, (long) 20,
						BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb61", "load:123", null,
						BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id3", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb63", "load:123", null,
						BiddingData.Unit.PER_TON, Arrays.asList("truck:123", "truck:456"), false, true, null)
//				new BiddingData("id1", null, "AP 32 AD 2226", true, null, (long) 0, null, null, null, null),
//				new BiddingData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, true, null, (long) 0,
//						null, null, null, null),
//				new BiddingData("id3", Constants.TRANSPORTER_ID, "AP 32 AD 2220", true, null, (long) 0, null, null,
//						null, null),
//				new BiddingData("id4", Constants.TRANSPORTER_ID, "Ap32ad2219", true, null, (long) 0, null, null,
//						null, null),
//				new BiddingData("id5", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "A32ad2219", false, null,
//						(long) 0, null, null, (long) 30, null),
//				new BiddingData("id6", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "Ap32ad221", false, null,
//						(long) 0, null, null, (long) 40, null)
		);

		return biddingList;
	}

}
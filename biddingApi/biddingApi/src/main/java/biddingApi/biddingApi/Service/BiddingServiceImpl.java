package biddingApi.biddingApi.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biddingApi.biddingApi.Dao.BiddingDao;
import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.ErrorConstants.Constants;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;

@Service
public class BiddingServiceImpl implements BiddingService{

	@Autowired
	private BiddingDao biddingDao;
	
	private Constants constants = new Constants();
	
	@Override
	public BidPostResponse addBid(BidPostRequest request) {
		// TODO Auto-generated method stub
		BiddingData data = new BiddingData();
		BidPostResponse response = new BidPostResponse();
		  
		data.setTransporterId(request.getTransporterId());
		data.setLoadId(request.getLoadId());
		data.setRate(request.getRate());
		data.setTruckId(request.getTruckId());
		data.setTransporterApproval(request.getTransporterApproval());
		data.setShipperApproval(request.getShipperApproval());
		data.setId("bid:"+UUID.randomUUID());
		
		if(data.getTransporterId()!=null)
		{
			data.setTransporterApproval(true);
		}
		
		if(data.getLoadId()!=null)
		{
			data.setShipperApproval(true);
		}
		
		if(data.getLoadId()==null&&data.getTransporterId()==null)
		{
			 response.setStatus(constants.getULoadIdIsNullAndTransPorterIdIsNull());
			 return response;
		}
		
		biddingDao.save(data);
		
      
        response.setStatus(constants.getSuccess());
		return response;
	}

	@Override
	public List<BiddingData> getBid() {
		// TODO Auto-generated method stub
		return biddingDao.findAll();
	
	}

	@Override
	public void deleteBid(String id) {
		// TODO Auto-generated method stub
		biddingDao.deleteById(id);
	}

	@Override
	public BiddingData getBidById(String id) {
		// TODO Auto-generated method stub
		return biddingDao.findById(id).get();
	}

}

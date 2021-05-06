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
		
		if(request.getTransporterId()!=null&&request.getLoadId()!=null)
		{
			 response.setStatus(constants.getPLoadIdAndTransporterIdAreNotNull());
			 return response;
		}
		else if(request.getTransporterId()!=null)
		{
			data.setTransporterId(request.getTransporterId());
			data.setTransporterApproval(true);
			data.setShipperApproval(false);
		}
		else if(request.getLoadId()!=null)
		{
			data.setLoadId(request.getLoadId());
			data.setShipperApproval(true);
			data.setTransporterApproval(false);
		}
		else if(request.getTransporterId()==null&&request.getLoadId()==null)
		{
			 response.setStatus(constants.getPLoadIdAndTransPorterIdAreNull());
			 return response;
		}
		
	
		data.setRate(request.getRate());
		data.setTruckId(request.getTruckId());
		data.setId("bid:"+UUID.randomUUID());
		
		
		biddingDao.save(data);
		
      
        response.setStatus(constants.getSuccess());
		return response;
	}

	@Override
	public List<BiddingData> getBid(String loadId) {
		// TODO Auto-generated method stub
		if(loadId!=null)
		{
			return biddingDao.findByLoadId(loadId);
		}
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

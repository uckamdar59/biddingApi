package biddingApi.biddingApi.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import biddingApi.biddingApi.Dao.BiddingDao;
import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.ErrorConstants.Constants;
import biddingApi.biddingApi.Model.BidDeleteResponse;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;
import biddingApi.biddingApi.Model.BidPutRequest;
import biddingApi.biddingApi.Model.BidPutResponse;

@Service
public class BiddingServiceImpl implements BiddingService{

	@Autowired
	private BiddingDao biddingDao;
	
	@Override
	public BidPostResponse addBid(BidPostRequest request) {
		// TODO Auto-generated method stub
		BiddingData data = new BiddingData();
		BidPostResponse response = new BidPostResponse();
		
		if(request.getTransporterId()==null&&request.getLoadId()==null){
			//Case: where both LoadId and TransporterIs are Null
			 response.setStatus(Constants.pLoadIdAndTransPorterIdAreNull);
			 return response;
		}else if(request.getLoadId()==null){
			// Case: where LoadIs Null
			 response.setStatus(Constants.pLoadIdIsNull);
			 return response;
		}else if(request.getTransporterId()==null){
			// Case: where at least one bid exists where it contains the same loadId
			List<BiddingData> temp = biddingDao.findByLoadId(request.getLoadId());
			if(temp.size()!=0){
				response.setStatus(Constants.pBidExists);
				return response;
			}
			// Case: where transportId id null and LoadId is not Nulls
			data.setLoadId(request.getLoadId());
			data.setShipperApproval(true);
			data.setRate(request.getRate());
		}else {
			// Case: Bid where tranposrterId is not Null and LoadId is not Null
			data.setTransporterId(request.getTransporterId());
			data.setTruckId(request.getTruckId());
			//Case: checking Rate is provided or not, Rate should be provided in this case
			if(request.getRate()==null) {
				response.setStatus(Constants.pTransporterRateIsNull);
				return response;
			}
			data.setLoadId(request.getLoadId());
			data.setRate(request.getRate());
			data.setTransporterApproval(true);
			data.setShipperApproval(false);
		}
		
		data.setId("bid:"+UUID.randomUUID());
		// handeling unique constraint exception
		try {
		    biddingDao.save(data);
		}catch(DataIntegrityViolationException e){
			response.setStatus(Constants.pLoadIdAndTransporterIdExists);
			return response;
		}catch(Exception e){
			response.setStatus(Constants.dataSavingExcetion);
			return response;
		}
        response.setStatus(Constants.success);
		return response;
	}

	@Override
	public List<BiddingData> getBid(Integer pageNo,String loadId) {
		// TODO Auto-generated method stub
		if(pageNo==null)
			pageNo=0;
		if(loadId!=null){
			Pageable p = PageRequest.of(pageNo,2);
			return biddingDao.findByLoadId(loadId,p);
		}else {
			Pageable p = PageRequest.of(pageNo,2);
			return biddingDao.findAll(p).getContent();
		}
	}

	@Override
	public BidDeleteResponse deleteBid(String id) {
		// TODO Auto-generated method stub
		BidDeleteResponse response = new BidDeleteResponse();
		if(biddingDao.findById(id).orElse(null)!=null)
		{
		biddingDao.deleteById(id);
		response.setStatus(Constants.dSuccess);
		return response;
		}
		response.setStatus(Constants.dDataNotExists);
		return response;
	}

	@Override
	public BiddingData getBidById(String id) {
		// TODO Auto-generated method stub
		return biddingDao.findById(id).orElse(null);
	}

	@Override
	public BidPutResponse updateBid(String id, BidPutRequest bidPutRequest) {
		// TODO Auto-generated method stub
		BidPutResponse response = new BidPutResponse();
		BiddingData dataById = biddingDao.findById(id).orElse(null);
		
		if(dataById==null){
			response.setStatus(Constants.uDataNotExists);
			return response;
		}
		
		
		if(bidPutRequest.getShipperApproval()!=null&&bidPutRequest.getTransporterApproval()!=null){
			response.setStatus(Constants.uBothApproval);
			return response;
		}
		
		if(dataById.getTransporterId()==null&&bidPutRequest.getTransporterApproval()!=null) {
			response.setStatus(Constants.uTApprovalWhereTidIsNull);
			return response;
		}
		
		if(dataById.getTransporterApproval()==null&&bidPutRequest.getTruckId()!=null){
    		response.setStatus(Constants.uTransportIdIsNullTruckUpdate);
    		return response;
    	}else if(bidPutRequest.getShipperApproval()!=null&&bidPutRequest.getTruckId()!=null){
    		response.setStatus(Constants.uShipperIdNotNullTruckUpdate);
    		return response;
    	}else if(bidPutRequest.getTruckId()!=null){
    		dataById.setTruckId(bidPutRequest.getTruckId());
    	}
		
		
		if(bidPutRequest.getRate()==null&&bidPutRequest.getShipperApproval()!=null){
			dataById.setShipperApproval(true);
		}else if(bidPutRequest.getRate()==null&&bidPutRequest.getTransporterApproval()!=null){
			if(dataById.getTransporterId()!=null)
			dataById.setTransporterApproval(true);
		}
	
		
		if(bidPutRequest.getRate()!=null&&(bidPutRequest.getShipperApproval()==null)&&(bidPutRequest.getTransporterApproval()==null)) {
			response.setStatus(Constants.uRateBothApprovalNull);
			return response;
		}else if((bidPutRequest.getRate()!=null)&&(bidPutRequest.getShipperApproval()!=null)) {
			if(bidPutRequest.getRate()==dataById.getRate()) {
				dataById.setShipperApproval(true);
			}else {
				dataById.setRate(bidPutRequest.getRate());
				dataById.setShipperApproval(true);
				if(dataById.getTransporterId()!=null)
				dataById.setTransporterApproval(false);
			}
		}else if((bidPutRequest.getRate()!=null)&&(bidPutRequest.getTransporterApproval()!=null)) {
			if(bidPutRequest.getRate()==dataById.getRate()) {
				dataById.setTransporterApproval(true);;
			}else {
				dataById.setRate(bidPutRequest.getRate());
				dataById.setTransporterApproval(true);
				dataById.setShipperApproval(false);
			}
		}
		
		if(bidPutRequest.getShipperApproval()!=null&&bidPutRequest.getShipperApproval()==false){
			dataById.setShipperApproval(false);
		}
		
		if(bidPutRequest.getTransporterApproval()!=null&&bidPutRequest.getTransporterApproval()==false) {
			dataById.setTransporterApproval(false);
		}

		
		biddingDao.save(dataById);
		response.setStatus(Constants.success);
		return response;
	}

}

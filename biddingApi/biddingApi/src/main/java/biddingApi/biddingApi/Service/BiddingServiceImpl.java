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
public class BiddingServiceImpl implements BiddingService {

	@Autowired
	private BiddingDao biddingDao;

	@Override
	public BidPostResponse addBid(BidPostRequest request) {

		BiddingData data = new BiddingData();
		BidPostResponse response = new BidPostResponse();

		if (request.getTransporterId() == null) {
			response.setStatus(Constants.TRANSPORTER_ID_NULL);
			return response;

		}

		if (request.getLoadId() == null) {
			response.setStatus(Constants.pLoadIdIsNull);
			return response;
		}

		if (request.getRate() == null) {
			response.setStatus(Constants.pTransporterRateIsNull);
			return response;
		}

		if (request.getUnitValue() == null) {
			response.setStatus(Constants.unitValueisNull);
			return response;
		}

		String id = "bid:" + UUID.randomUUID();

		data.setBidId(id);

		data.setTransporterId(request.getTransporterId());
		data.setLoadId(request.getLoadId());
		data.setRate(request.getRate());
		if ("PER_TON".equals(String.valueOf(request.getUnitValue())))
			data.setUnitValue(BiddingData.Unit.PER_TON);
		else
			data.setUnitValue(BiddingData.Unit.PER_TRUCK);

		if (request.getBiddingDate() != null) {
			data.setBiddingDate(request.getBiddingDate());
		}

		if (request.getTruckId() != null) {
			data.setTruckId(request.getTruckId());
		}

		data.setTransporterApproval(true);
		data.setShipperApproval(false);

		try {
			biddingDao.save(data);
		} catch (DataIntegrityViolationException e) {
			response.setStatus(Constants.pLoadIdAndTransporterIdExists);
			return response;
		} catch (Exception e) {
			response.setStatus(Constants.dataSavingExcetion);
			return response;
		}

		response.setStatus(Constants.success);
		response.setBidId(id);
		response.setLoadId(data.getLoadId());
		response.setRate(data.getRate());
		response.setTransporterId(data.getTransporterId());
		response.setShipperApproval(data.getShipperApproval());
		response.setTransporterApproval(data.getTransporterApproval());
		response.setTruckId(data.getTruckId());
		response.setUnitValue(data.getUnitValue());
		response.setBiddingDate(data.getBiddingDate());

		return response;
	}

	@Override
	public List<BiddingData> getBid(Integer pageNo, String loadId, String transporterId) {
		// TODO Auto-generated method stub
		if (pageNo == null)
			pageNo = 0;
		if (loadId != null && transporterId == null) {
			Pageable p = PageRequest.of(pageNo, (int) Constants.pageSize);
			return biddingDao.findByLoadId(loadId, p);
		} else if (loadId == null && transporterId != null) {
			Pageable p = PageRequest.of(pageNo, (int) Constants.pageSize);
			return biddingDao.findByTransporterId(transporterId, p);
		} else if (loadId != null && transporterId != null) {
			Pageable p = PageRequest.of(pageNo, (int) Constants.pageSize);
			return biddingDao.findByLoadIdAndTransporterId(loadId, transporterId, p);
		} else {
			return biddingDao.findAll();
		}
	}

	@Override
	public BidDeleteResponse deleteBid(String id) {
		// TODO Auto-generated method stub
		BidDeleteResponse response = new BidDeleteResponse();
		if (biddingDao.findById(id).orElse(null) != null) {
			biddingDao.deleteById(id);
			response.setStatus(Constants.dSuccess);
			return response;
		}
		response.setStatus(Constants.dDataNotExists);
		return response;
	}

	@Override
	public BiddingData getBidById(String id) {
		return biddingDao.findById(id).orElse(null);
	}

	@Override
	public BidPutResponse updateBid(String id, BidPutRequest bidPutRequest) {

		BidPutResponse response = new BidPutResponse();
		BiddingData data = biddingDao.findById(id).orElse(null);

		if (data == null) {
			response.setStatus(Constants.uDataNotExists);
			return response;
		}

		if (String.valueOf(bidPutRequest.getTransporterApproval()).equals("true")
				&& String.valueOf(bidPutRequest.getShipperApproval()).equals("null")) {
			if (bidPutRequest.getRate() != null) {

				if (bidPutRequest.getUnitValue() == null) {
					response.setStatus(Constants.unitValueisNull);
					return response;
				} else {
					data.setUnitValue(bidPutRequest.getUnitValue());
					data.setRate(bidPutRequest.getRate());
					if (bidPutRequest.getBiddingDate() != null) {
						data.setBiddingDate(bidPutRequest.getBiddingDate());
					}

					if (bidPutRequest.getTruckId() != null) {
						data.setTruckId(bidPutRequest.getTruckId());
					}

					data.setTransporterApproval(true);
					data.setShipperApproval(false);

					biddingDao.save(data);

					response.setStatus(Constants.uSuccess);
					response.setBidId(id);
					response.setLoadId(data.getLoadId());
					response.setRate(data.getRate());
					response.setTransporterId(data.getTransporterId());
					response.setShipperApproval(data.getShipperApproval());
					response.setTransporterApproval(data.getTransporterApproval());
					response.setTruckId(data.getTruckId());
					response.setUnitValue(data.getUnitValue());
					response.setBiddingDate(data.getBiddingDate());

					return response;
				}

				// accept by transporter
			} else if (bidPutRequest.getRate() == null && data.getShipperApproval() == true) {

				data.setTransporterApproval(true);

				biddingDao.save(data);

				response.setStatus(Constants.uSuccess);
				response.setBidId(id);
				response.setLoadId(data.getLoadId());
				response.setRate(data.getRate());
				response.setTransporterId(data.getTransporterId());
				response.setShipperApproval(data.getShipperApproval());
				response.setTransporterApproval(data.getTransporterApproval());
				response.setTruckId(data.getTruckId());
				response.setUnitValue(data.getUnitValue());
				response.setBiddingDate(data.getBiddingDate());

				return response;

			}

		} // update from shipper
		else if (String.valueOf(bidPutRequest.getShipperApproval()).equals("true")
				&& String.valueOf(bidPutRequest.getTransporterApproval()).equals("null")) {

			if (bidPutRequest.getRate() != null) {
				if (bidPutRequest.getUnitValue() == null) {
					response.setStatus(Constants.unitValueisNull);
					return response;
				} else {
					data.setUnitValue(bidPutRequest.getUnitValue());
					data.setRate(bidPutRequest.getRate());
					if (bidPutRequest.getBiddingDate() != null) {
						data.setBiddingDate(bidPutRequest.getBiddingDate());
					}

					if (bidPutRequest.getTruckId() != null) {
						response.setStatus(Constants.TRUCK_ID_UPDATE_BY_SHIPPER);
						return response;
					}
					data.setShipperApproval(true);
					data.setTransporterApproval(false);

					biddingDao.save(data);

					response.setStatus(Constants.uSuccess);
					response.setBidId(id);
					response.setLoadId(data.getLoadId());
					response.setRate(data.getRate());
					response.setTransporterId(data.getTransporterId());
					response.setShipperApproval(data.getShipperApproval());
					response.setTransporterApproval(data.getTransporterApproval());
					response.setTruckId(data.getTruckId());
					response.setUnitValue(data.getUnitValue());
					response.setBiddingDate(data.getBiddingDate());

					return response;
				}
				// accept by shipper
			} else if (bidPutRequest.getRate() == null && data.getTransporterApproval() == true) {

				data.setShipperApproval(true);

				biddingDao.save(data);

				response.setStatus(Constants.uSuccess);
				response.setBidId(id);
				response.setLoadId(data.getLoadId());
				response.setRate(data.getRate());
				response.setTransporterId(data.getTransporterId());
				response.setShipperApproval(data.getShipperApproval());
				response.setTransporterApproval(data.getTransporterApproval());
				response.setTruckId(data.getTruckId());
				response.setUnitValue(data.getUnitValue());
				response.setBiddingDate(data.getBiddingDate());

				return response;

			}

		} else if (String.valueOf(bidPutRequest.getShipperApproval()).equals("null")
				&& String.valueOf(bidPutRequest.getTransporterApproval()).equals("null")) {

			response.setStatus(Constants.TRANSPORTER_SHIPPER_APPROVAL_NULL);

			return response;

		}else if (!String.valueOf(bidPutRequest.getShipperApproval()).equals("null")
				&& !String.valueOf(bidPutRequest.getTransporterApproval()).equals("null")) {

			response.setStatus(Constants.TRANSPORTER_SHIPPER_APPROVAL_NOT_NULL);

			return response;

		}

		return response;

	}
}

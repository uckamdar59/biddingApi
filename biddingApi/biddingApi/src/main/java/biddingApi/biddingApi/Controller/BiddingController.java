package biddingApi.biddingApi.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.Exception.EntityNotFoundException;
import biddingApi.biddingApi.Model.BidDeleteResponse;
import biddingApi.biddingApi.Model.BidPostRequest;
import biddingApi.biddingApi.Model.BidPostResponse;
import biddingApi.biddingApi.Model.BidPutRequest;
import biddingApi.biddingApi.Model.BidPutResponse;
import biddingApi.biddingApi.Service.BiddingService;

import biddingApi.biddingApi.SecurityConfig.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BiddingController {

	@Autowired
	private BiddingService biddingService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/bid")
	public ResponseEntity<List<BiddingData>> getBid(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "loadId", required = false) String loadId,
			@RequestParam(value = "transporterId", required = false) String transporterId,
			@RequestHeader(value = "Authorization", defaultValue = "") String token) throws EntityNotFoundException {
		log.info("Get with Params Controller Started");
		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.getBid(pageNo, loadId, transporterId), HttpStatus.OK);
	}

	@GetMapping("/bid/{Id}")
	public ResponseEntity<BiddingData> getBidById(@PathVariable String Id,
			@RequestHeader(value = "Authorization", defaultValue = "") String token) throws EntityNotFoundException {
		log.info("Get Controller Started");
		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.getBidById(Id,token), HttpStatus.OK);
	}

	@PostMapping("/bid")
	public ResponseEntity<BidPostResponse> addBid(@RequestBody BidPostRequest bidPostRequest,
			@RequestHeader(value = "Authorization", defaultValue = "") String token) {

		log.info("Post Controller Started");
		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.addBid(bidPostRequest,token), HttpStatus.CREATED);
	}

	@PutMapping("/bid/{id}")
	public ResponseEntity<BidPutResponse> updateBid(@PathVariable String id, @RequestBody BidPutRequest bidPutRequest,
			@RequestHeader(value = "Authorization", defaultValue = "") String token) throws EntityNotFoundException {
		log.info("Put Controller Started");
		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.updateBid(id, bidPutRequest,token), HttpStatus.OK);
	}

	@DeleteMapping("/bid/{Id}")
	public ResponseEntity<BidDeleteResponse> deleteBid(@PathVariable String Id,
			@RequestHeader(value = "Authorization", defaultValue = "") String token) throws EntityNotFoundException {
		log.info("Delete Controller Started");
		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.deleteBid(Id,token), HttpStatus.OK);
	}

}

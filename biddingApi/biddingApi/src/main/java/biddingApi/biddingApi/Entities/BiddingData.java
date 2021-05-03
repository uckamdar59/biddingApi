package biddingApi.biddingApi.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;


import lombok.Data;

@Entity
public @Data class BiddingData {

	@Id
	private String id;
	
	private String transporterId;
	private String loadId;
	private Long rate;
	
	@Column(name="truckId")
	@ElementCollection(targetClass=String.class)
	private List<String> truckId;
	private Boolean transporterApproval;
	private Boolean shipperApproval;
}

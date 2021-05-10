package biddingApi.biddingApi.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"transporterId" , "loadId"})})
@Entity
public @Data class BiddingData {

	@Id
	private String id;
	
	@Column(name="transporterId")
	private String transporterId;
	@Column(name="loadId")
	private String loadId;
	private Long rate;
	
	@Column(name="truckId")
	@ElementCollection(targetClass=String.class)
	private List<String> truckId;
	private Boolean transporterApproval;
	private Boolean shipperApproval;
}

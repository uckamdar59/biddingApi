package biddingApi.biddingApi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import biddingApi.biddingApi.Dao.BiddingDao;
import biddingApi.biddingApi.Entities.BiddingData;
import biddingApi.biddingApi.ErrorConstants.Constants;

@DataJpaTest
public class TestBiddingDao {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BiddingDao biddingDao;

	@Test
	public void testFindById() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		Optional<BiddingData> getFromDb = biddingDao.findById(Constants.ID);

		assertThat(getFromDb).isEqualTo(Optional.ofNullable((savedInDb)));

	}

	@Test
	public void testFindByLoadId() {

		Pageable currentPage;
		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));

		currentPage = PageRequest.of(0, 3);

		Iterable<BiddingData> allBids = biddingDao.findByLoadId("load:123", currentPage);
		List<BiddingData> list = new ArrayList<>();

		for (BiddingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);

	}

	@Test
	public void testFindByLoadIdWithPagination() {

		Pageable currentPage;
		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));
		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(2));

		Iterable<BiddingData> allBids = biddingDao.findByLoadId("load:123");
		List<BiddingData> list = new ArrayList<>();

		for (BiddingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);

	}

//	@Test
//	public void testFindByTruckApproved() {
//
//		Pageable currentPage;
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		BiddingData savedInDb = entityManager.persist(listBiddingData.get(3));
//		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(4));
//		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(5));
//
//		currentPage = PageRequest.of(0, 3);
//
//		Iterable<BiddingData> allBids = biddingDao.findByTruckApproved(true, currentPage);
//		List<BiddingData> list = new ArrayList<>();
//
//		for (BiddingData t : allBids) {
//			list.add(t);
//		}
//		assertThat(list.size()).isEqualTo(2);
//
//	}
//
//	@Test
//	public void testFindByTransporterIdAndTruckApproved() {
//		Pageable currentPage;
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		BiddingData savedInDb = entityManager.persist(listBiddingData.get(3));
//		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(4));
//		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(5));
//
//		currentPage = PageRequest.of(0, 3);
//
//		Iterable<BiddingData> allBids = biddingDao.findByTransporterIdAndTruckApproved(Constants.TRANSPORTER_ID,
//				true, currentPage);
//		List<BiddingData> list = new ArrayList<>();
//
//		for (BiddingData t : allBids) {
//			list.add(t);
//		}
//		assertThat(list.size()).isEqualTo(2);
//
//	}
//
//	@Test
//	public void testFindByTransporterIdAndTruckNo() {
//
//		List<BiddingData> listBiddingData = createBiddingData();
//
//		BiddingData savedInDb = entityManager.persist(listBiddingData.get(3));
//		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(4));
//		BiddingData savedInDb2 = entityManager.persist(listBiddingData.get(5));
//
//		Iterable<BiddingData> allBids = biddingDao.findByTransporterIdAndTruckNo(Constants.TRANSPORTER_ID,
//				Constants.TRUCK_NO);
//		List<BiddingData> list = new ArrayList<>();
//
//		for (BiddingData t : allBids) {
//			list.add(t);
//		}
//		assertThat(list.size()).isEqualTo(1);
//
//	}

	@Test
	public void testUpdate() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));

		// entityManager.persist(truckData);
		Optional<BiddingData> getFromDb = biddingDao.findById(Constants.ID);

		listBiddingData.get(0).setRate((long) 100);

		entityManager.persist(listBiddingData.get(0));

		Iterable<BiddingData> allBids = biddingDao.findAll();

		List<BiddingData> list = new ArrayList<>();

		for (BiddingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	public void testDelete() {

		List<BiddingData> listBiddingData = createBiddingData();

		BiddingData savedInDb = entityManager.persist(listBiddingData.get(0));
		BiddingData savedInDb1 = entityManager.persist(listBiddingData.get(1));

		entityManager.remove(savedInDb1);

		Iterable<BiddingData> allBids = biddingDao.findAll();
		List<BiddingData> list = new ArrayList<>();

		for (BiddingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	public List<BiddingData> createBiddingData() {
		List<BiddingData> biddingList = Arrays.asList(
				new BiddingData(Constants.ID, "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "load:123",
						(long) 20, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id1", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, (long) 20,
						BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
				new BiddingData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb61", "load:123", null,
						BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null)
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

package managers;

import java.util.List;

import models.Tracking;
import dao.TrackingDAO;

public class TrackingManager {
	private TrackingDAO dao = new TrackingDAO();

	public void createTracking(Tracking tracking) {
		dao.createTracking(tracking);
	}

	public List<Tracking> getAll() {
		return dao.getAll();
	}
}

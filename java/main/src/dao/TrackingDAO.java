package dao;

import java.util.List;

import models.Tracking;
import play.db.ebean.Model.Finder;

public class TrackingDAO {
	public static Finder<Integer, Tracking> find = new Finder<Integer, Tracking>(
			Integer.class, Tracking.class);

	public void createTracking(Tracking tracking) {
		tracking.save();
	}

	public List<Tracking> getAll() {
		return find.all();
	}
}

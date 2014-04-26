package controllers;

import managers.TrackingManager;
import models.Tracking;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Tools;

public class TrackingController extends Controller {
	private static TrackingManager manager = new TrackingManager();

	public static Result getAll() {
		return ok(Tools.listToJson(manager.getAll()));
	}

	public static Result createTracking(String address) {
		System.out.println(address);
		try {
			manager.createTracking(new Tracking(address));
		} catch (Exception e) {
			e.printStackTrace();
			return badRequest();
		}
		return ok();
	}
}

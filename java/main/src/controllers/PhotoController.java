package controllers;

import java.util.List;

import managers.FacebookManager;
import facebook4j.Media;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData.FilePart;

public class PhotoController extends Controller{
	public static Result postPhoto(String s) {
		FacebookManager facebookManager = new FacebookManager();
		List<FilePart> fileParts = request().body().asMultipartFormData()
				.getFiles();
		for (FilePart filePart : fileParts) {
			facebookManager.postPhoto(new Media(filePart.getFile()));
		}

		System.out.println("OK");
		return ok("OK men!");
	}

	public static Result getPhoto(String id) {
		FacebookManager facebookManager = new FacebookManager();

		facebookManager.getPhoto(id);
		return ok();
	}
}

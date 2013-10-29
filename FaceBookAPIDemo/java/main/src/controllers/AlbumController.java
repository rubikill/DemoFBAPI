package controllers;

import java.util.List;

import managers.FacebookManager;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Tools;
import facebook4j.Album;
import facebook4j.Photo;

public class AlbumController extends Controller{
	/*************************************************************
	 * 
	 * Album
	 * 
	 *************************************************************/
	/**
	 * 
	 * @return
	 */
	public static Result getAlbums() {
		System.out.println("get album called");
		FacebookManager facebookManager = new FacebookManager();
		List<Album> list = facebookManager.getAlbums();
		System.out.println(list.size());
		return ok(Tools.listToJson(list));
	}

	public static Result getAlbum(String id) {
		System.out.println("-------single album--------");
		FacebookManager facebookManager = new FacebookManager();
		List<Photo> photos = facebookManager.getPhotos(id);
		return ok(Tools.listToJson(photos));
	}
}

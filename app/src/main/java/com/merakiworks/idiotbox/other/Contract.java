package com.merakiworks.idiotbox.other;

/**
 * Created by anuragmaravi on 28/01/17.
 */

public class Contract {
    public final static String API_KEY = "0744794205a0d39eef72cad8722d4fba";
    //Base Url
    public final static String API_URL = "http://api.themoviedb.org/3/";
    //Image Base Url
    public final static String API_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    //Query strings for Movie
    public final static String API_MOVIE = "movie";
    public final static String API_TV = "tv";

    public final static String API_CASTING = "person";
    //Image Sizes
    public final static String apiImageSizeOriginal = "original";
    public final static String apiImageSizeXXXL = "w780";
    public final static String API_IMAGE_SIZE_XXL = "w500";
    public final static String apiImageSizeXL = "w342";
    public final static String API_IMAGE_SIZE_L = "w185";
    public final static String API_IMAGE_SIZE_M = "w154";
    public final static String API_IMAGE_SIZE_S = "w92";
    //Image Url
    public final static String API_IMAGE_URL = API_IMAGE_BASE_URL + API_IMAGE_SIZE_L + "/";

    //Youtube Url
    public final static String youtubeUrl = "https://www.youtube.com/watch?v=";
    //Youtube Thumbnail
    public final static String YOUTUBE_BASE_THUMBNAIL = "https://img.youtube.com/vi/";
    public final static String youtubeQualityThumbnailD = "/default.jpg";
    public final static String YOUTUBE_QUALITY_THUMBNAIL_MQ = "/mqdefault.jpg";
    public final static String youtubeQualityThumbnailSd = "/sddefault.jpg";
    public final static String youtubeQualityThumbnailHq = "/hqdefault.jpg";
    public final static String youtubeQualityThumbnailMax = "/maxresdefault.jpg";


    //Movies
    public final static String API_MOVIE_NOW_PLAYING = "now_playing";
    public final static String API_MOVIE_UPCOMING = "upcoming";
    public final static String API_MOVIE_POPULAR = "popular";
    public final static String API_MOVIE_TOP_RATED = "top_rated";
    public final static String MOVIE_NOW_PLAYING_REQUEST = API_URL + API_MOVIE + "/" + API_MOVIE_NOW_PLAYING + "?api_key=" + API_KEY;
    public final static String MOVIE_UPCOMING_REQUEST = API_URL + API_MOVIE + "/" + API_MOVIE_UPCOMING + "?api_key=" + API_KEY;
    public final static String MOVIE_POPULAR_REQUEST = API_URL + API_MOVIE + "/" + API_MOVIE_POPULAR + "?api_key=" + API_KEY;
    public final static String MOVIE_TOP_RATED_REQUEST = API_URL + API_MOVIE + "/" + API_MOVIE_TOP_RATED + "?api_key=" + API_KEY;

    //Query strings for TvShows

    public Contract(){}

    //Omdb Base Url -- append imdb id
    public final static String OMDB_BASE_URL = "http://www.omdbapi.com/?i=";


}

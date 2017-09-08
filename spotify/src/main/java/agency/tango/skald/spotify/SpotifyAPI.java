package agency.tango.skald.spotify;

import agency.tango.skald.spotify.api.models.BrowseCategories;
import agency.tango.skald.spotify.api.models.BrowsePlaylists;
import agency.tango.skald.spotify.api.models.Category;
import agency.tango.skald.spotify.api.models.Playlists;
import agency.tango.skald.spotify.api.models.SpotifyUser;
import agency.tango.skald.spotify.api.models.TrackSearch;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyAPI {
  String BASE_URL = "https://api.spotify.com";

  @GET("/v1/me")
  Single<SpotifyUser> getSpotifyUser();

  @GET("/v1/me/playlists")
  Single<Playlists> getPlaylists();

  @GET("/v1/browse/categories")
  Single<BrowseCategories> getCategories();

  @GET("/v1/browse/categories/{id}")
  Single<Category> getCategory(@Path("id") String id);

  @GET("/v1/browse/categories/{id}/playlists")
  Single<BrowsePlaylists> getPlaylistsInCategory(@Path("id") String id);

  @GET("/v1/search")
  Single<TrackSearch> getTracksForQuery(@Query("q") String query, @Query("type") String type);

  @GET("/v1/search")
  Single<BrowsePlaylists> getPlaylistsForQuery(@Query("q") String query,
      @Query("type") String type);
}
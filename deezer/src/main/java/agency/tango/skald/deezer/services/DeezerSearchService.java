package agency.tango.skald.deezer.services;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;

import java.util.ArrayList;
import java.util.List;

import agency.tango.skald.core.SearchService;
import agency.tango.skald.core.models.SkaldPlaylist;
import agency.tango.skald.core.models.SkaldTrack;
import agency.tango.skald.deezer.models.DeezerPlaylist;
import agency.tango.skald.deezer.models.DeezerTrack;
import agency.tango.skald.deezer.services.listeners.DeezerRequestListener;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class DeezerSearchService implements SearchService {
  private static final String SEARCH_TRACK_REQUEST_ID = "SEARCH_TRACK_REQUEST";
  private static final String SEARCH_PLAYLIST_REQUEST_ID = "SEARCH_PLAYLIST_REQUEST";
  private final DeezerConnect deezerConnect;

  public DeezerSearchService(DeezerConnect deezerConnect) {
    this.deezerConnect = deezerConnect;
  }

  @Override
  public Single<List<SkaldTrack>> searchForTracks(final String query) {
    return Single.create(new SingleOnSubscribe<List<SkaldTrack>>() {
      @Override
      public void subscribe(@NonNull final SingleEmitter<List<SkaldTrack>> emitter)
          throws Exception {
        final DeezerRequest deezerRequest = DeezerRequestFactory.requestSearchTracks(query);
        deezerRequest.setId(SEARCH_TRACK_REQUEST_ID);
        deezerConnect.requestAsync(deezerRequest,
            new DeezerRequestListener<List<SkaldTrack>>(emitter) {
              @Override
              public void onResult(Object result, Object requestId) {
                if (requestId.equals(SEARCH_TRACK_REQUEST_ID)) {
                  List<Track> tracks = (List<Track>) result;
                  List<SkaldTrack> skaldTracks = mapDeezerTracksToSkaldTracks(tracks);
                  emitter.onSuccess(skaldTracks);
                }
              }
            });
      }
    });
  }

  @Override
  public Single<List<SkaldPlaylist>> searchForPlaylists(final String query) {
    return Single.create(new SingleOnSubscribe<List<SkaldPlaylist>>() {
      @Override
      public void subscribe(@NonNull final SingleEmitter<List<SkaldPlaylist>> emitter)
          throws Exception {
        DeezerRequest deezerRequest = DeezerRequestFactory.requestSearchPlaylists(query);
        deezerRequest.setId(SEARCH_PLAYLIST_REQUEST_ID);
        deezerConnect.requestAsync(deezerRequest,
            new DeezerRequestListener<List<SkaldPlaylist>>(emitter) {
              @Override
              public void onResult(Object result, Object requestId) {
                if (requestId.equals(SEARCH_PLAYLIST_REQUEST_ID)) {
                  List<Playlist> playlists = (List<Playlist>) result;
                  List<SkaldPlaylist> skaldPlaylists = mapDeezerPlaylistsToSkaldPlaylists(
                      playlists);
                  emitter.onSuccess(skaldPlaylists);
                }
              }
            });
      }
    });
  }

  private List<SkaldTrack> mapDeezerTracksToSkaldTracks(List<Track> tracks) {
    List<SkaldTrack> skaldTracks = new ArrayList<>();
    for (Track track : tracks) {
      skaldTracks.add(new DeezerTrack(track));
    }
    return skaldTracks;
  }

  private List<SkaldPlaylist> mapDeezerPlaylistsToSkaldPlaylists(List<Playlist> playlists) {
    List<SkaldPlaylist> skaldPlaylists = new ArrayList<>();
    for (Playlist playlist : playlists) {
      skaldPlaylists.add(new DeezerPlaylist(playlist));
    }
    return skaldPlaylists;
  }
}

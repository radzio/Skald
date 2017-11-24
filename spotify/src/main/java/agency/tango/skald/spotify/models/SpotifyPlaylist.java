package agency.tango.skald.spotify.models;

import android.net.Uri;

import agency.tango.skald.core.models.SkaldPlaylist;
import agency.tango.skald.spotify.api.models.Playlist;

public class SpotifyPlaylist extends SkaldPlaylist {
  public SpotifyPlaylist(Playlist playlist) {
    this(Uri.parse(String.format("skald://spotify/playlist/%s", playlist.getUri())),
        playlist.getImages().get(0).getUrl(), playlist.getName());
  }

  private SpotifyPlaylist(Uri uri, String imageUrl, String name) {
    super(uri, imageUrl, name);
  }
}

package agency.tango.skald.spotify;

import android.content.Context;

import agency.tango.skald.core.ApiCalls;
import agency.tango.skald.core.Player;
import agency.tango.skald.core.Provider;
import agency.tango.skald.core.SkaldAuthData;
import agency.tango.skald.core.SkaldAuthStore;
import agency.tango.skald.core.factories.ApiCallsFactory;
import agency.tango.skald.core.factories.PlayerFactory;
import agency.tango.skald.core.factories.SkaldAuthStoreFactory;
import agency.tango.skald.core.models.SkaldTrack;
import agency.tango.skald.spotify.models.SpotifyTrack;

public class SpotifyProvider extends Provider {
  public static final String EXTRA_CLIENT_ID = "SPOTIFY_CLIENT_ID";
  public static final String EXTRA_REDIRECT_URI = "SPOTIFY_REDIRECT_URI";

  public static final String SPOTIFY_PROVIDER = "spotify";
  private final Context context;
  private final String clientId;
  private final String redirectUri;

  public SpotifyProvider(Context context, String clientId, String redirectUri) {
    this.context = context;
    this.clientId = clientId;
    this.redirectUri = redirectUri;
  }

  @Override
  public String getProviderName() {
    return SPOTIFY_PROVIDER;
  }

  @Override
  public PlayerFactory getPlayerFactory() {
    return new SpotifyPlayerFactory(context, clientId);
  }

  @Override
  public SkaldAuthStoreFactory getSkaldAuthStoreFactory() {
    return new SpotifyAuthStoreFactory();
  }

  @Override
  public ApiCallsFactory getApiCallsFactory() {
    return new SpotifyApiCallsFactory();
  }

  public String getClientId() {
    return clientId;
  }

  public String getRedirectUri() {
    return redirectUri;
  }

  private static class SpotifyPlayerFactory extends PlayerFactory {
    private final Context context;
    private String clientId;

    private SpotifyPlayerFactory(Context context, String clientId) {
      this.context = context;
      this.clientId = clientId;
    }

    @Override
    public Player getPlayerFor(SkaldTrack track, SkaldAuthData skaldAuthData) {
      if (track instanceof SpotifyTrack) {
        String accessToken = ((SpotifyAuthData) skaldAuthData).getOauthToken();
        return new SkaldSpotifyPlayer(context, clientId, accessToken);
      }
      return null;
    }
  }

  private static class SpotifyAuthStoreFactory extends SkaldAuthStoreFactory {
    @Override
    public SkaldAuthStore getSkaldAuthStore() {
      return new SpotifyAuthStore();
    }
  }

  private static class SpotifyApiCallsFactory extends ApiCallsFactory {
    @Override
    public ApiCalls getApiCalls(SkaldAuthData skaldAuthData) {
      return new SpotifyApiCalls(skaldAuthData);
    }
  }
}

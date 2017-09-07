package agency.tango.skald.deezer;

import agency.tango.skald.core.factories.AuthErrorFactory;
import agency.tango.skald.core.factories.PlayerFactory;
import agency.tango.skald.core.Provider;
import agency.tango.skald.core.factories.SkaldAuthStoreFactory;

public class DeezerProvider extends Provider {
  public static final String DEEZER_PROVIDER = "deezer";
  private final String clientId;

  public DeezerProvider(String clientId, String redirectUri) {
    this.clientId = clientId;
  }

  @Override
  public String getProviderName() {
    return DEEZER_PROVIDER;
  }

  @Override
  public PlayerFactory getPlayerFactory() {
    return null;
  }

  @Override
  public SkaldAuthStoreFactory getSkaldAuthStoreFactory() {
    return null;
  }

  @Override
  public AuthErrorFactory getAuthErrorFactory() {
    return null;
  }

  public String getClientId() {
    return clientId;
  }
}

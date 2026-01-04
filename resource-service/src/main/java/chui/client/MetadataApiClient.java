package chui.client;

import chui.model.dto.SongMetadataDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
public class MetadataApiClient {
  private final RestTemplate restTemplate;
  private final String songServiceUrl;

  public MetadataApiClient(RestTemplate restTemplate,
                           @Value("${spring.song-service.url}") String songServiceUrl) {
    this.restTemplate = restTemplate;
    this.songServiceUrl = songServiceUrl;
  }

  public void sendMetadata(SongMetadataDto metadataDto) {
    try {
      restTemplate.postForEntity(songServiceUrl, metadataDto, Void.class);
      log.debug("Sent metadata to song-service: {}", metadataDto);
    } catch (Exception e) {
      log.warn("Failed to send metadata to song-service: {}", e.getMessage());
    }
  }

  public boolean deleteMetadataByIds(String ids) {
    try {
      String url = UriComponentsBuilder.newInstance()
          .uri(java.net.URI.create(songServiceUrl))
          .queryParam("id", ids)
          .build()
          .toString();
      ResponseEntity<Void> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.DELETE, null, Void.class);
      boolean success = response.getStatusCode() == HttpStatus.OK;
      log.debug("Sent DELETE to song-service for ids {}: success={}", ids, success);
      return success;
    } catch (Exception e) {
      log.warn("Failed to send DELETE to song-service for ids {}: {}", ids, e.getMessage());
      return false;
    }
  }
}

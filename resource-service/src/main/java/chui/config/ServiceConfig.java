package chui.config;

import chui.client.MetadataApiClient;
import chui.repository.ResourceRepository;
import chui.service.ResourceService;
import chui.service.Mp3MetadataExtractorService;
import chui.service.Mp3ValidationService;
import chui.service.CsvIdParserService;
import chui.service.IdParserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {

  @Bean
  public ResourceService resourceService(ResourceRepository resourceRepository,
                                         MetadataApiClient metadataApiClient,
                                         Mp3MetadataExtractorService mp3MetadataExtractorService,
                                         Mp3ValidationService mp3ValidationService,
                                         CsvIdParserService csvIdParserService,
                                         IdParserService idParserService) {
    return new ResourceService(resourceRepository, metadataApiClient, mp3MetadataExtractorService, mp3ValidationService, csvIdParserService, idParserService);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public MetadataApiClient metadataApiClient(RestTemplate restTemplate, @Value("${spring.song-service.url}") String songServiceUrl) {
    return new MetadataApiClient(restTemplate, songServiceUrl);
  }

  @Bean
  public Mp3MetadataExtractorService mp3MetadataExtractorService() {
    return new Mp3MetadataExtractorService();
  }

  @Bean
  public Mp3ValidationService mp3ValidationService() {
    return new Mp3ValidationService();
  }

  @Bean
  public CsvIdParserService csvIdParserService() {
    return new CsvIdParserService();
  }

  @Bean
  public IdParserService idParserService() {
    return new IdParserService();
  }
}

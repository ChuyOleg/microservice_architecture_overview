package chui.service;

import chui.client.MetadataApiClient;
import chui.exception.NotFoundException;
import chui.model.dto.DeleteResourcesResponseDto;
import chui.model.dto.SongMetadataDto;
import chui.model.dto.UploadResourceResponseDto;
import chui.model.entity.Resource;
import chui.repository.ResourceRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
public class ResourceService {

  private final ResourceRepository resourceRepository;
  private final MetadataApiClient metadataApiClient;
  private final Mp3MetadataExtractorService mp3MetadataExtractorService;
  private final Mp3ValidationService mp3ValidationService;
  private final CsvIdParserService csvIdParserService;
  private final IdParserService idParserService;

  @Transactional
  public UploadResourceResponseDto saveResource(byte[] audioData, String contentType) {
    mp3ValidationService.validate(audioData, contentType);

    SongMetadataDto metadataDto = mp3MetadataExtractorService.extractMetadata(audioData);

    Resource resource = new Resource();
    resource.setAudioData(audioData);
    Resource saved = resourceRepository.save(resource);

    // Send metadata to song-service via MetadataApiClient
    if (metadataDto != null) {
      metadataDto.setId(saved.getId());
      metadataApiClient.sendMetadata(metadataDto);
    }

    return new UploadResourceResponseDto(saved.getId());
  }

  @Transactional(readOnly = true)
  public byte[] getResourceById(String id) {
    long resourceId = idParserService.parseId(id);
    Resource resource = resourceRepository.findById(resourceId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Resource with ID=%d not found", resourceId)));
    return resource.getAudioData();
  }

  @Transactional
  public DeleteResourcesResponseDto deleteResources(String ids) {
    List<Long> validIds = csvIdParserService.parseIds(ids);

    List<Long> existingIds = resourceRepository.findAllById(validIds).stream()
        .map(Resource::getId)
        .toList();
    resourceRepository.deleteAllById(existingIds);

    if (!existingIds.isEmpty()) {
      String existingIdsCsv = toCsv(existingIds);
      metadataApiClient.deleteMetadataByIds(existingIdsCsv);
    }
    return new DeleteResourcesResponseDto(existingIds);
  }

  private String toCsv(List<Long> ids) {
    return ids.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(","));
  }
}

package chui.client;

import chui.model.dto.SongMetadataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "song-service")
public interface MetadataApiClient {

  @PostMapping("/songs")
  void sendMetadata(@RequestBody SongMetadataDto metadataDto);

  @DeleteMapping("/songs")
  void deleteMetadataByIds(@RequestParam("id") String ids);
}

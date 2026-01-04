package chui.controller;

import chui.model.dto.DeleteResourcesResponseDto;
import chui.model.dto.UploadResourceResponseDto;
import chui.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

  private static final String AUDIO_MPEG = "audio/mpeg";

  private final ResourceService resourceService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UploadResourceResponseDto> uploadResource(
      @RequestBody byte[] audioData,
      @RequestHeader(value = "Content-Type", required = false) String contentType) {
    UploadResourceResponseDto responseDto = resourceService.saveResource(audioData, contentType);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping(value = "/{id}", produces = AUDIO_MPEG)
  public ResponseEntity<byte[]> getResource(@PathVariable String id) {
    byte[] audioData = resourceService.getResourceById(id);
    return ResponseEntity.ok(audioData);
  }

  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DeleteResourcesResponseDto> deleteResources(
      @RequestParam("id") String ids) {
    DeleteResourcesResponseDto responseDto = resourceService.deleteResources(ids);
    return ResponseEntity.ok(responseDto);
  }
}

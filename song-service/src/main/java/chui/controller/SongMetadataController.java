package chui.controller;

import chui.model.dto.DeleteMetadataResponseDto;
import chui.model.dto.SaveSongMetadataResponseDto;
import chui.model.dto.SongMetadataDto;
import chui.service.SongMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongMetadataController {

  private final SongMetadataService songMetadataService;

  @PostMapping
  public ResponseEntity<SaveSongMetadataResponseDto> createMetadata(
      @RequestBody SongMetadataDto metadataDto) {
    SaveSongMetadataResponseDto response = songMetadataService.saveMetadata(metadataDto);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SongMetadataDto> getMetadata(@PathVariable String id) {
    SongMetadataDto metadataDto = songMetadataService.getMetadataById(id);
    return ResponseEntity.ok(metadataDto);
  }

  @DeleteMapping
  public ResponseEntity<DeleteMetadataResponseDto> deleteMetadata(@RequestParam String id) {
    DeleteMetadataResponseDto response = songMetadataService.deleteMetadataByIds(id);
    return ResponseEntity.ok(response);
  }
}

package chui.service;

import chui.exception.SongMetadataAlreadyExistsException;
import chui.exception.SongMetadataNotFoundException;
import chui.mapper.SongMetadataMapper;
import chui.model.dto.DeleteMetadataResponseDto;
import chui.model.dto.SaveSongMetadataResponseDto;
import chui.model.dto.SongMetadataDto;
import chui.model.entity.SongMetadata;
import chui.repository.SongMetadataRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SongMetadataService {

  private final SongMetadataRepository songMetadataRepository;
  private final SongMetadataMapper songMetadataMapper;
  private final CsvIdParserService csvIdParserService;

  @Autowired
  public SongMetadataService(SongMetadataRepository songMetadataRepository,
                             SongMetadataMapper songMetadataMapper,
                             CsvIdParserService csvIdParserService) {
    this.songMetadataRepository = songMetadataRepository;
    this.songMetadataMapper = songMetadataMapper;
    this.csvIdParserService = csvIdParserService;
  }

  @Transactional
  public SaveSongMetadataResponseDto saveMetadata(SongMetadataDto metadataDto) {
    SongMetadataValidator.validate(metadataDto);
    Long id = metadataDto.getId();
    if (id != null && songMetadataRepository.existsById(id)) {
      throw new SongMetadataAlreadyExistsException(id);
    }
    SongMetadata entity = songMetadataMapper.toEntity(metadataDto);
    SongMetadata saved = songMetadataRepository.save(entity);
    return new SaveSongMetadataResponseDto(saved.getId());
  }

  @Transactional(readOnly = true)
  public SongMetadataDto getMetadataById(Integer id) {
    Long longId = Long.valueOf(id);
    SongMetadata entity = songMetadataRepository.findById(longId)
        .orElseThrow(() -> new SongMetadataNotFoundException(longId));
    return songMetadataMapper.toDto(entity);
  }

  @Transactional
  public DeleteMetadataResponseDto deleteMetadataByIds(String rawIds) {
    List<Integer> ids = csvIdParserService.parseIds(rawIds);
    List<Long> longIds = ids.stream().map(Integer::longValue).toList();
    // Find which IDs actually exist
    List<Long> existingIds = songMetadataRepository.findAllById(longIds).stream()
        .map(SongMetadata::getId)
        .toList();
    songMetadataRepository.deleteAllById(existingIds);
    // Convert back to Integer for response
    List<Integer> deletedIds = existingIds.stream().map(Long::intValue).toList();
    return new DeleteMetadataResponseDto(deletedIds);
  }
}

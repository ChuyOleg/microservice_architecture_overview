package chui.service;

import chui.exception.ValidationException;
import chui.model.dto.SongMetadataDto;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SongMetadataValidator {

  private static final Pattern DURATION_PATTERN = Pattern.compile("\\d{2}:\\d{2}");
  private static final Pattern YEAR_PATTERN = Pattern.compile("(19|20)\\d{2}");

  public static void validate(SongMetadataDto metadataDto) {
    Map<String, String> errors = new HashMap<>();
    validateNonNullFields(metadataDto, errors);

    if (metadataDto.getName() != null
        && (metadataDto.getName().isEmpty() || metadataDto.getName().length() > 100)) {
      errors.put("name", "Song name must be between 1 and 100 characters");
    }
    if (metadataDto.getArtist() != null
        && (metadataDto.getArtist().isEmpty() || metadataDto.getArtist().length() > 100)) {
      errors.put("artist", "Artist name must be between 1 and 100 characters");
    }
    if (metadataDto.getAlbum() != null
        && (metadataDto.getAlbum().isEmpty() || metadataDto.getAlbum().length() > 100)) {
      errors.put("album", "Album name must be between 1 and 100 characters");
    }
    if (metadataDto.getDuration() != null
        && !DURATION_PATTERN.matcher(metadataDto.getDuration()).matches()) {
      errors.put("duration", "Duration must be in mm:ss format with leading zeros");
    } else if (metadataDto.getDuration() != null) {
      String[] parts = metadataDto.getDuration().split(":");
      if (parts.length == 2) {
        int seconds = Integer.parseInt(parts[1]);
        if (seconds < 0 || seconds > 59) {
          errors.put("duration", "Duration must be in mm:ss format with leading zeros");
        }
      }
    }
    if (metadataDto.getYear() != null && !YEAR_PATTERN.matcher(metadataDto.getYear()).matches()) {
      errors.put("year", "Year must be between 1900 and 2099");
    }
    if (metadataDto.getId() != null && metadataDto.getId() <= 0) {
      errors.put("id", "ID must match an existing Resource ID.");
    }
    if (!errors.isEmpty()) {
      log.warn("Validation failed: {}", errors);
      throw new ValidationException("Validation error", errors);
    }
  }

  private static void validateNonNullFields(SongMetadataDto metadataDto,
                                            Map<String, String> errors) {
    if (metadataDto.getId() == null) {
      errors.put("id", "ID is required");
    }
    if (metadataDto.getName() == null) {
      errors.put("name", "Song name is required");
    }
    if (metadataDto.getArtist() == null) {
      errors.put("artist", "Artist name is required");
    }
    if (metadataDto.getAlbum() == null) {
      errors.put("album", "Album name is required");
    }
    if (metadataDto.getDuration() == null) {
      errors.put("duration", "Duration is required");
    }
    if (metadataDto.getYear() == null) {
      errors.put("year", "Year is required");
    }
  }
}

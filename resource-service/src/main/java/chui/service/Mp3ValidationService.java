package chui.service;

import chui.exception.InvalidMp3Exception;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Mp3ValidationService {
  public boolean isValidMp3(byte[] data) {
    if (data == null || data.length < 3) {
      log.warn("MP3 validation failed: data is null or too short");
      return false;
    }

    return (data[0] == 0x49 && data[1] == 0x44 && data[2] == 0x33) // ID3
        || (data[0] == (byte) 0xFF && (data[1] & 0xE0) == 0xE0);
  }

  public void validate(byte[] data, String contentType) {
    if (contentType == null || !contentType.equalsIgnoreCase("audio/mpeg")) {
      log.warn("Attempted to upload file with invalid Content-Type: {}", contentType);
      throw new InvalidMp3Exception(
          "Invalid file format: " + (contentType != null ? contentType : "unknown")
              + ". Only MP3 files are allowed");
    }
    if (!isValidMp3(data)) {
      log.warn("Attempted to upload invalid MP3 file");
      throw new InvalidMp3Exception(
          "Invalid file format: " + contentType + ". Only MP3 files are allowed");
    }
  }
}

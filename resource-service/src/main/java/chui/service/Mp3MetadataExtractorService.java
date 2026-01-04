package chui.service;

import chui.exception.ResourceServiceException;
import chui.model.dto.SongMetadataDto;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Log4j2
public class Mp3MetadataExtractorService {

  private static final String KEY_TITLE = "dc:title";
  private static final String KEY_ARTIST = "xmpDM:artist";
  private static final String KEY_ALBUM = "xmpDM:album";
  private static final String KEY_DURATION = "xmpDM:duration";
  private static final String KEY_YEAR = "xmpDM:releaseDate";

  public SongMetadataDto extractMetadata(byte[] audioData) {
    try (ByteArrayInputStream input = new ByteArrayInputStream(audioData)) {
      ContentHandler handler = new DefaultHandler();
      Metadata metadata = new Metadata();
      Mp3Parser parser = new Mp3Parser();
      ParseContext parseCtx = new ParseContext();
      parser.parse(input, handler, metadata, parseCtx);
      String duration = parseDuration(metadata.get(KEY_DURATION));
      return SongMetadataDto.builder()
          .name(metadata.get(KEY_TITLE))
          .artist(metadata.get(KEY_ARTIST))
          .album(metadata.get(KEY_ALBUM))
          .duration(duration)
          .year(metadata.get(KEY_YEAR))
          .build();
    } catch (TikaException | IOException | SAXException e) {
      log.warn("Failed to extract MP3 metadata: {}", e.getMessage());
      throw new ResourceServiceException("Failed to extract MP3 metadata", e);
    }
  }

  private String parseDuration(String durationRaw) {
    if (durationRaw != null) {
      try {
        double seconds = Double.parseDouble(durationRaw);
        int totalSeconds = (int) Math.round(seconds);
        int minutes = totalSeconds / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, secs);
      } catch (NumberFormatException e) {
        log.warn("Failed to parse duration: {}", durationRaw);
      }
    }
    return null;
  }
}

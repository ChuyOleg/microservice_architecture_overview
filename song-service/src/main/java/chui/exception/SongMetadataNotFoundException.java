package chui.exception;

public class SongMetadataNotFoundException extends RuntimeException {
  public SongMetadataNotFoundException(Long id) {
    super("Song metadata for ID=" + id + " not found");
  }
}

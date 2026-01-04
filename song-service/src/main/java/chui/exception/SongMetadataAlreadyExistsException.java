package chui.exception;

public class SongMetadataAlreadyExistsException extends RuntimeException {
  public SongMetadataAlreadyExistsException(Long id) {
    super(String.format("Metadata for resource ID=%s already exists", id));
  }
}

package chui.model.dto;

import lombok.Data;

@Data
public class SongMetadataDto {
  private Long id;
  private String name;
  private String artist;
  private String album;
  private String duration;
  private String year;
}

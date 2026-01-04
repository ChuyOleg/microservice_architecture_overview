package chui.mapper;

import chui.model.dto.SongMetadataDto;
import chui.model.entity.SongMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SongMetadataMapper {
    SongMetadataMapper INSTANCE = Mappers.getMapper(SongMetadataMapper.class);

    SongMetadata toEntity(SongMetadataDto dto);
    SongMetadataDto toDto(SongMetadata entity);
}


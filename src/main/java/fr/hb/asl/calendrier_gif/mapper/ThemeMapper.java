package fr.hb.asl.calendrier_gif.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.hb.asl.calendrier_gif.business.Theme;
import fr.hb.asl.calendrier_gif.dto.ThemeDto;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

	ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

	ThemeDto toDto(Theme theme);

	Theme toEntity(ThemeDto themeDto);
}

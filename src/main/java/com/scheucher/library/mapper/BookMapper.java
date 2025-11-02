package com.scheucher.library.mapper;

import com.scheucher.library.dto.request.BookCreateRequest;
import com.scheucher.library.dto.response.BookResponse;
import com.scheucher.library.entity.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "AVAILABLE")
    @Mapping(target = "availableCopies", source = "totalCopies")
    Book toEntity(BookCreateRequest dto);

    @Mapping(target = "authors", source = "authors")
    BookResponse toResponse(Book entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "availableCopies", ignore = true)
    void updateEntityFromDto(BookCreateRequest dto, @MappingTarget Book entity);
}
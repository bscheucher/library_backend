package com.scheucher.library.mapper;

import com.scheucher.library.dto.request.AuthorCreateRequest;
import com.scheucher.library.dto.request.AuthorUpdateRequest;
import com.scheucher.library.dto.response.AuthorResponse;
import com.scheucher.library.dto.response.AuthorSummaryResponse;
import com.scheucher.library.entity.Author;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "bookCount", expression = "java(author.getBooks() != null ? author.getBooks().size() : 0)")
    @Mapping(target = "fullName", expression = "java(author.getFullName())")
    @Mapping(target = "isAlive", expression = "java(author.isAlive())")
    @Mapping(target = "age", expression = "java(author.getAge())")
    AuthorResponse toResponse(Author author);

    @Mapping(target = "fullName", expression = "java(author.getFullName())")
    AuthorSummaryResponse toSummaryResponse(Author author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Author toEntity(AuthorCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(AuthorUpdateRequest dto, @MappingTarget Author entity);
}
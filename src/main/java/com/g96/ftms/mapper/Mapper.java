package com.g96.ftms.mapper;

import org.modelmapper.ModelMapper;

public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T, U> T mapEntityToDto(U entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public static <T, U> U mapDtoToEntity(T dto, Class<U> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
package fr.orsys.gamesreviews.mapper;

public interface Mapper<E, D> {

    E mapDtoToEntity(D dto);

    D mapEntityToDto(E entity);

}

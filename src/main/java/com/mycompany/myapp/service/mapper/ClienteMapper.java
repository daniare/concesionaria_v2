package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cliente;
import com.mycompany.myapp.service.dto.ClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoId(Cliente cliente);
}

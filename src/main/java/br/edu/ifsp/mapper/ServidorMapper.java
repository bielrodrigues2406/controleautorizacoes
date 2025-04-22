package br.edu.ifsp.mapper;

import br.edu.ifsp.domain.Servidor;
import br.edu.ifsp.dto.ServidorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServidorMapper {

    ServidorMapper INSTANCE = Mappers.getMapper(ServidorMapper.class);

    Servidor servidorDTOToServidor(ServidorDTO dto);
    ServidorDTO servidorToServidorDTO(Servidor servidor);

    List<ServidorDTO> servidorListToServidorDTOList(List<Servidor> servidores);
    List<Servidor> servidorDTOListToServidorList(List<ServidorDTO> dtos);
}

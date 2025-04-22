package br.edu.ifsp.mapper;

import br.edu.ifsp.domain.Ambiente;
import br.edu.ifsp.dto.AmbienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AmbienteMapper {

    AmbienteMapper INSTANCE = Mappers.getMapper(AmbienteMapper.class);

    Ambiente ambienteDTOToAmbiente(AmbienteDTO dto);
    AmbienteDTO ambienteToAmbienteDTO(Ambiente ambiente);

    List<AmbienteDTO> ambienteListToAmbienteDTOList(List<Ambiente> ambientes);
    List<Ambiente> ambienteDTOListToAmbienteList(List<AmbienteDTO> dtos);
}

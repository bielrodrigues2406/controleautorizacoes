package br.edu.ifsp.mapper;

import br.edu.ifsp.domain.SolicitacaoChave;
import br.edu.ifsp.dto.SolicitacaoChaveDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolicitacaoChaveMapper {

    SolicitacaoChaveMapper INSTANCE = Mappers.getMapper(SolicitacaoChaveMapper.class);

    SolicitacaoChave solicitacaoChaveDTOToSolicitacaoChave(SolicitacaoChaveDTO dto);
    SolicitacaoChaveDTO solicitacaoChaveToSolicitacaoChaveDTO(SolicitacaoChave entity);

    List<SolicitacaoChaveDTO> solicitacaoChaveListToDTOList(List<SolicitacaoChave> lista);
    List<SolicitacaoChave> dtoListToSolicitacaoChaveList(List<SolicitacaoChaveDTO> lista);
}

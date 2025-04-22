package br.edu.ifsp.mapper;

import br.edu.ifsp.domain.Autorizacao;
import br.edu.ifsp.dto.AutorizacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AutorizacaoMapper {

    AutorizacaoMapper INSTANCE = Mappers.getMapper(AutorizacaoMapper.class);

    Autorizacao autorizacaoDTOToAutorizacao(AutorizacaoDTO dto);
    AutorizacaoDTO autorizacaoToAutorizacaoDTO(Autorizacao autorizacao);

    List<AutorizacaoDTO> autorizacaoListToAutorizacaoDTOList(List<Autorizacao> autorizacoes);
    List<Autorizacao> autorizacaoDTOListToAutorizacaoList(List<AutorizacaoDTO> dtos);
}

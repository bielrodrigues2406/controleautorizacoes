package br.edu.ifsp.mapper;

import br.edu.ifsp.dto.AlunoDTO;
import br.edu.ifsp.domain.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlunoMapper {

    AlunoMapper INSTANCE = Mappers.getMapper(AlunoMapper.class);

    Aluno alunoDTOToAluno(AlunoDTO alunoDTO);

    AlunoDTO alunoToAlunoDTO(Aluno aluno);

    List<AlunoDTO> alunoListToAlunoDTOList(List<Aluno> alunos);

    List<Aluno> alunoDTOListToAlunoList(List<AlunoDTO> dtos);
}

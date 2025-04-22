# controleautorizacoes
Projeto 2 - Sistema de Controle de Autorizações para Utilização de Laboratórios e Salas do IFSP

O sistema proposto tem como principal objetivo controlar o empréstimo de chaves para acesso a salas do IFSP, onde um servidor da permissão que um determinado aluno utilize uma determinada sala, essa é autorização de utilização. 

Quando o aluno vai utilizar a sala ele registra a sua utilização através da indicação do horário que pegou a chave na Coordenadoria de Apoio ao Ensino - CAE e quando devolver a chave ele faz um registro de devolução da chave.

As permissões de acesso as salas são feitas pelos servidores preenchendo um documento no SUAP encaminhando a CAE, indicando o prontuário e nome do aluno, curso que frequenta, tipo de atividade que realizará na sala (TCC, Pesquisa, Extensão, Ensino entre outras) e o dia da semana e horário que esse aluno pode utilizar a sala, geralmente a permissão é em tempo integral, ou seja, de segunda a sexta-feira no horário de funcionamento do campus. Lembrando que a utilização de uma ambiente por um aluno é responsabilidade do professor que fez a autorização. As autorizações devem ter data de início e final, pois um aluno pode ter autorização em um semestre e no próximo não.

A CAE precisa de forma rápida saber se o aluno pode pegar a chave e se já registrou a solicitação da chave no sistema antes de ter acesso a chave do ambiente, para isso a tela inicial do sistema deve apresentar as solicitações de chaves pelos alunos e confirmar a entrega da chave. A CAE deve ter possibilidade de consultar os alunos que pegaram chaves no período, todas as pessoas que pegaram a chave de um determinado ambiente e quais as chaves dos ambientes estão disponíveis.

Para resolver esse problemas será necessário implementar as seguintes funcionalidades:

Manter/Importar servidores, ação executada pela CAE; 
Manter os alunos, que deve ser feito por um servidor ou a CAE;
Incluir autorização de uso dos ambientes, que deve ser realizada por um servidor;
Cancelar a autorização de uso dos ambiente;
Manter as salas/ambientes que podem ser utilizados pelos alunos pela CAE;
Permitir solicitar o empréstimo da chave pelo aluno;
Confirmar a entrega da chave pelos servidores da CAE;
Registrar a devolução da chave;
Realizar as consultas indicadas no texto.

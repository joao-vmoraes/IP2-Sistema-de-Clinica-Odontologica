# - Sistema de Clínica Odontológica (Projeto IP2)

Este sistema tem como objetivo gerenciar o funcionamento de uma clínica odontológica, incluindo cadastro de pacientes, dentistas, agendamentos, procedimentos e histórico odontológico. Ele permite o controle completo de consultas, tratamentos, faturamento e acompanhamento do histórico de saúde bucal dos pacientes.

O sistema deve registrar detalhes dos procedimentos realizados, controlar a agenda de dentistas, acompanhar pagamentos, gerar relatórios e emitir alertas de consultas e tratamentos futuros. Ele deve garantir organização e rastreabilidade de todas as atividades clínicas.



## Integrantes do Grupo: 
* João Vitor Moraes de Andrade - jmtontxzn@gmail.com
* Gabriel Vitor Santos de Aguiar - gabriel2005aguiar70@gmail.com
* Kalani Rafael Vieira da Mata - kalanidamata@gmail.com
* Luiz Phellipe de Queiroz Silva - phellipegamesl7@gmail.com
* Thiago José de Almeida Barroso Medeiros - thiagojabm@gmail.com

---
## Requisitos Funcionais: 

### 1. Gerenciamento de Pacientes

- **REQ01**: Permitir cadastro de pacientes com dados pessoais, contato e histórico odontológico.
- **REQ02**: Atualizar informações do paciente e registrar consultas anteriores.
- **REQ03**: Visualizar histórico completo de tratamentos e procedimentos realizados.

### 2. Gerenciamento de Dentistas

- **REQ04**: Permitir cadastro de dentistas com especialidade, horários disponíveis e histórico de atendimentos.
- **REQ05**: Associar dentistas a consultas e procedimentos específicos.
- **REQ06**: Atualizar disponibilidade e registrar ausências ou férias.

### 3. Agendamentos

- **REQ07**: Permitir marcação de consultas com data, horário, dentista e tipo de procedimento.
- **REQ08**: Controlar disponibilidade de dentistas e salas de atendimento.
- **REQ09**: Atualizar agendamentos em caso de alterações ou cancelamentos.

### 4. Gerenciamento de Procedimentos

- **REQ10**: Permitir cadastro de procedimentos odontológicos com descrição, preço e tempo estimado.
- **REQ11**: Registrar procedimentos realizados para cada paciente.
- **REQ12**: Atualizar status do procedimento (planejado, em andamento, concluído, cancelado).

### 5. Pagamentos

- **REQ13**: Registrar pagamentos vinculados a consultas ou procedimentos.
- **REQ14**: Suportar diferentes métodos de pagamento (dinheiro, cartão, PIX).
- **REQ15**: Emitir recibos ou comprovantes de pagamento.

### 6. Relatórios e Estatísticas

- **REQ16**: Gerar relatório de consultas por dentista ou período.
- **REQ17**: Relatórios de procedimentos realizados e faturamento da clínica.
- **REQ18**: Relatórios de frequência de pacientes e agendamentos pendentes.
- **REQ19**: Permitir exportação de relatórios em **PDF** e **CSV**.

### 7. Alertas e Notificações

- **REQ20**: Alertar pacientes sobre consultas futuras e procedimentos agendados.
- **REQ21**: Notificar dentistas sobre alterações de agenda ou ausências de pacientes.
- **REQ22**: Alertar sobre pagamentos pendentes ou procedimentos não concluídos.

### 8. Regras e Restrições

- **REQ23**: Não permitir agendamento em horário indisponível do dentista ou sala.
- **REQ24**: Bloquear agendamento de paciente com pagamento pendente.
- **REQ25**: Garantir que cada procedimento esteja vinculado a um dentista e paciente válidos.
- **REQ26**: Não permitir cancelamento de procedimento já concluído sem registro de justificativa.

teste
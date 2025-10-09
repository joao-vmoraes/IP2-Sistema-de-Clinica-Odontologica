import java.time.LocalDateTime;
import java.time.LocalTime;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Sistema de Clínica Odontológica Iniciado ---");
        ClinicaManager manager = new ClinicaManager();
        
        Paciente p1 = new Paciente("João Silva", "111.222.333-44", "9999-8888", "joao@email.com", "Rua A");
        Dentista d1 = new Dentista("Dr. Carlos", "222.333.444-55", "9888-7777", "carlos@clinica.com", "Av B", "Ortodontia", LocalTime.of(8, 0), LocalTime.of(18, 0));
        Procedimento pr1 = new Procedimento("Limpeza Simples", 150.0, 30, d1);
        
        manager.cadastrarPaciente(p1);
        manager.cadastrarDentista(d1);
        manager.adicionarProcedimentoAoCatalogo(pr1);
        
        LocalDateTime dataConsulta = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);
        manager.marcarConsulta(p1, d1, pr1, dataConsulta, "Sala 1");
        
        Pagamento pg1 = new Pagamento(pr1.getPreco(), MetodoPagamento.PIX, pr1);
        
        Agendamento agendamentoPendente = d1.getAgenda().get(0);
        agendamentoPendente.setPagamento(pg1);
        pg1.confirmarPagamento();
    }
}
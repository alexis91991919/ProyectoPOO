package administracion;

import excepciones.Excepciones;
import personas.Medico;
import personas.Paciente;
import java.util.ArrayList;
import java.util.List;

// representa una cita medica entre un paciente y un medico en una fecha y hora especificas
public class CitaMedica {
    private static final List<String> HORARIOS_OCUPADOS = new ArrayList<>();

    private int idCita;
    private String fecha;
    private String hora;
    private String motivo;
    private String estado; // "PENDIENTE", "AGENDADA", "CANCELADA", "COMPLETADA"
    private Paciente paciente;
    private Medico medico;

    public CitaMedica() {
        this.estado = "PENDIENTE";
    }

    // constructor corto llamado desde Main.java
    public CitaMedica(String fecha, String hora, String motivo) {
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = "PENDIENTE";
    }

    public CitaMedica(int idCita, String fecha, String hora, String motivo, Paciente paciente, Medico medico) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = "PENDIENTE";
    }

    // metodo llamado desde Main.java: agendarCita(21, 10) -> lanza 409 si ya esta ocupado
    public void agendarCita(int dia, int horaCita) throws Excepciones {
        String clave = dia + "-" + horaCita;
        if (HORARIOS_OCUPADOS.contains(clave)) {
            throw new Excepciones(
                "El horario del dia " + dia + " a las " + horaCita + ":00 ya no esta disponible.",
                409,
                "CITA_NO_DISPONIBLE"
            );
        }
        HORARIOS_OCUPADOS.add(clave);
        this.fecha = "Dia " + dia;
        this.hora = horaCita + ":00";
        this.estado = "AGENDADA";
        System.out.println("Cita agendada para el " + this.fecha + " a las " + this.hora + " | Motivo: " + motivo);
    }

    // valida que el medico no tenga otra cita agendada el mismo dia y hora
    public void agendarCita(List<CitaMedica> citasExistentes) throws Excepciones {
        if (this.medico == null || this.paciente == null) {
            throw new Excepciones("La cita debe tener un medico y un paciente asignados.", 409, "CITA_NO_DISPONIBLE");
        }

        if (citasExistentes != null) {
            for (CitaMedica cita : citasExistentes) {
                boolean mismoMedico = cita.getMedico() != null &&
                        cita.getMedico().getNumeroCarnet() == this.medico.getNumeroCarnet();
                boolean mismaFecha = cita.getFecha() != null && cita.getFecha().equalsIgnoreCase(this.fecha);
                boolean mismaHora = cita.getHora() != null && cita.getHora().equalsIgnoreCase(this.hora);
                boolean activa = !"CANCELADA".equalsIgnoreCase(cita.getEstado());

                if (mismoMedico && mismaFecha && mismaHora && activa) {
                    throw new Excepciones(
                        "El Dr. " + medico.getNombre() + " ya tiene una cita el " + fecha + " a las " + hora + ".",
                        409,
                        "CITA_NO_DISPONIBLE"
                    );
                }
            }
        }

        this.estado = "AGENDADA";
        System.out.println("Cita #" + idCita + " agendada correctamente para " + paciente.getNombre() +
                " con el Dr. " + medico.getNombre() + " (" + fecha + " - " + hora + ").");
    }

    public void agendarCita() throws Excepciones {
        if ("AGENDADA".equalsIgnoreCase(this.estado)) {
            throw new Excepciones("La cita #" + idCita + " ya se encuentra agendada.", 409, "CITA_NO_DISPONIBLE");
        }
        this.estado = "AGENDADA";
        System.out.println("Cita #" + idCita + " agendada.");
    }

    public void cancelarCita() {
        this.estado = "CANCELADA";
        System.out.println("Cita #" + idCita + " ha sido cancelada.");
    }

    public void completarCita() {
        this.estado = "COMPLETADA";
        System.out.println("Cita #" + idCita + " marcada como completada.");
    }

    public String getDetalleCita() {
        String nombrePaciente = (paciente != null) ? paciente.getNombre() : "No asignado";
        String nombreMedico = (medico != null) ? "Dr. " + medico.getNombre() + " (" + medico.getEspecialidad() + ")" : "No asignado";
        int consultorio = (medico != null) ? medico.getConsultorio() : 0;

        return String.format(
            "--- Cita Medica #%d ---\n" +
            "  * Fecha y Hora: %s a las %s\n" +
            "  * Paciente: %s\n" +
            "  * Medico: %s | Consultorio: %d\n" +
            "  * Motivo: %s\n" +
            "  * Estado: %s",
            idCita, fecha, hora, nombrePaciente, nombreMedico, consultorio, motivo, estado
        );
    }

    // getters y setters
    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    @Override
    public String toString() {
        return getDetalleCita();
    }
}
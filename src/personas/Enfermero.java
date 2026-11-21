package personas;

import clinico.HistorialMedico;
import clinico.SignosVitales;

// subclase de personal que representa a un enfermero con su turno asignado
public class Enfermero extends Personal {
    // turno laboral del enfermero
    private String turno;

    // constructor que pasa los datos generales a personal y define el cargo como enfermero
    public Enfermero(int numeroCarnet, String nombre, int edad, char sexo, double sueldo, String horario, String turno) {
        super(numeroCarnet, nombre, edad, sexo, sueldo, "Enfermero", horario);
        this.turno = turno;
    }

    // crea los signos vitales del paciente y avisa si el estado es critico
    public SignosVitales tomarSignosVitales(String presion, double temp, int pulso, int oxigeno, double peso) {
        SignosVitales signos = new SignosVitales(presion, temp, pulso, oxigeno, peso);
        if (signos.esEstadoCritico()) {
            System.out.println(">> [Enfermero " + nombre + "] Evaluacion critica: PRIORIDAD ALTA <<");
        }
        return signos;
    }

    // guarda los signos vitales en el historial si ambos objetos existen
    public void registrarEnHistorial(HistorialMedico historial, SignosVitales signos) {
        if (historial != null && signos != null) {
            // Guarda en .txt y dispara el PATRON OBSERVER si es critico
            historial.registrarSignos(signos);
        }
    }

    // registra la aplicacion de un medicamento y su dosis en el turno actual
    public void aplicarMedicamento(String medicamento, double dosis) {
        System.out.println("Enfermero " + nombre + " (Turno " + turno + ") aplico " + medicamento + " (" + dosis + " mg).");
    }

    // sobrecarga simple para registrar una muestra de laboratorio sin datos extra
    public void registrarMuestraLab() {
        System.out.println("Enfermero " + nombre + " registro una muestra de laboratorio.");
    }

    // Laboratorio esta en el mismo paquete (personas), no requiere import
    // envia la muestra de un paciente al laboratorio si ambos son validos
    public void registrarMuestraLab(Laboratorio lab, Paciente p, String tipoMuestra) {
        if (lab != null && p != null) {
            lab.recibirMuestra(p, tipoMuestra);
        }
    }

    // PATRON TEMPLATE METHOD
    // define la tarea clinica del enfermero dentro del protocolo de atencion
    @Override
    protected void realizarLaborClinica(Paciente paciente) {
        System.out.println("Enfermero " + nombre + " (Turno " + turno + ") prepara y toma signos a " + paciente.getNombre());
    }

    // PATRON CHAIN OF RESPONSIBILITY
    // atiende solicitudes de enfermeria o las pasa al siguiente responsable en la cadena
    @Override
    public void procesarSolicitud(String tipoSolicitud, Paciente paciente) {
        if (tipoSolicitud.equalsIgnoreCase("SIGNOS") ||
            tipoSolicitud.equalsIgnoreCase("MEDICAMENTO") ||
            tipoSolicitud.equalsIgnoreCase("MUESTRA")) {
            System.out.println("[Cadena] Enfermero " + nombre + " atiende '" + tipoSolicitud + "' para " + paciente.getNombre());
        } else if (siguienteResponsable != null) {
            System.out.println("[Cadena] Enfermero " + nombre + " delega '" + tipoSolicitud + "' al siguiente responsable...");
            siguienteResponsable.procesarSolicitud(tipoSolicitud, paciente);
        } else {
            System.out.println("[Cadena] Nadie pudo atender la solicitud: " + tipoSolicitud);
        }
    }

    // getter y setter para consultar o cambiar el turno
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
}

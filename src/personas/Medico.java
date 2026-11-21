package personas;

import clinico.HistorialMedico;
import clinico.Receta;

// subclase de personal que representa a un medico con su especialidad y area clinica
public class Medico extends Personal {
    // atributos propios del medico
    private String especialidad;
    private int consultorio;
    private int cirugias;

    // constructor que pasa los datos comunes a personal y fija el cargo como medico
    public Medico(int numeroCarnet, String nombre, int edad, char sexo, double sueldo, String horario,
                  String especialidad, int consultorio, int cirugias) {
        super(numeroCarnet, nombre, edad, sexo, sueldo, "Medico", horario);
        this.especialidad = especialidad;
        this.consultorio = consultorio;
        this.cirugias = cirugias;
    }

    // PATRON OBSERVER
    // recibe y muestra un aviso cuando ocurre un evento critico en el historial
    public void recibirAlertaCritica(String alerta) {
        System.out.println("\n*** [ALERTA OBSERVER -> DR. " + nombre.toUpperCase() + " | Consultorio " + consultorio + "] ***");
        System.out.println(alerta);
    }

    // sobrecarga sin paciente para indicar disponibilidad general de consulta
    public String consultarHistorialMedico() {
        return "Dr. " + nombre + " (" + especialidad + ") listo para consultar expedientes.";
    }

    // sobrecarga que busca el ultimo historial del paciente y devuelve sus detalles
    public String consultarHistorialMedico(Paciente p) {
        if (p == null || p.obtenerUltimoHistorial() == null) {
            return "El paciente no tiene historial registrado.";
        }
        return p.obtenerUltimoHistorial().getDetallesCompletos();
    }

    // sobrecarga para dar de alta sin modificar un objeto paciente
    public boolean darDeAlta() {
        return true;
    }

    // marca al paciente como dado de alta si existe y registra el movimiento
    public boolean darDeAlta(Paciente p) {
        if (p != null) {
            p.setDadoDeAlta(true);
            System.out.println("Dr. " + nombre + " dio de alta al paciente " + p.getNombre());
            return true;
        }
        return false;
    }

    // sobrecarga para emitir una receta solo con el nombre del medicamento
    public String recetarMedicamento(String medicamento) {
        return "Dr. " + nombre + " receto: " + medicamento;
    }

    // guarda la receta en el historial y opcionalmente la imprime a archivo
    public String recetarMedicamento(HistorialMedico historial, Receta receta, boolean imprimir) {
        historial.asignarReceta(receta); 
        if (imprimir) {
            receta.imprimirReceta();    
        }
        return receta.getDetalleReceta();
    }

    // PATRON TEMPLATE METHOD
    // define la atencion clinica especifica que realiza el medico dentro del protocolo
    @Override
    protected void realizarLaborClinica(Paciente paciente) {
        System.out.println("Dr. " + nombre + " (" + especialidad + ") revisa diagnostico y tratamiento de " + paciente.getNombre());
    }

    // PATRON CHAIN OF RESPONSIBILITY
    // atiende solicitudes medicas propias o las delega al siguiente responsable de la cadena
    @Override
    public void procesarSolicitud(String tipoSolicitud, Paciente paciente) {
        if (tipoSolicitud.equalsIgnoreCase("RECETA") ||
            tipoSolicitud.equalsIgnoreCase("ALTA") ||
            tipoSolicitud.equalsIgnoreCase("CONSULTA")) {
            System.out.println("[Cadena] Dr. " + nombre + " atiende la solicitud '" + tipoSolicitud + "' para " + paciente.getNombre());
        } else if (siguienteResponsable != null) {
            siguienteResponsable.procesarSolicitud(tipoSolicitud, paciente);
        } else {
            System.out.println("[Cadena] Solicitud no reconocida: " + tipoSolicitud);
        }
    }

    // getters para consultar los datos propios del medico
    public String getEspecialidad() { return especialidad; }
    public int getConsultorio() { return consultorio; }
    public int getCirugias() { return cirugias; }
}

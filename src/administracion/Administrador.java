package administracion;

import personas.Paciente;
import personas.Personal;

// subclase de personal que administra el hospital y gestiona al equipo
public class Administrador extends Personal {
    // atributos propios del administrador
    private String nivelAcceso;
    private String departamento;
    private Hospital hospital;

    // constructor corto llamado desde Main.java
    public Administrador(String nivelAcceso, String departamento, Hospital hospital) {
        super(0, "Admin " + departamento, 30, 'M', 25000.0, "Administrador", "08:00-16:00");
        this.nivelAcceso = nivelAcceso;
        this.departamento = departamento;
        this.hospital = hospital;
    }

    // constructor que envia los datos comunes a personal y asigna el cargo de administrador
    public Administrador(int numeroCarnet, String nombre, int edad, char sexo, double sueldo, String horario,
                         String nivelAcceso, String departamento, Hospital hospital) {
        super(numeroCarnet, nombre, edad, sexo, sueldo, "Administrador", horario);
        this.nivelAcceso = nivelAcceso;
        this.departamento = departamento;
        this.hospital = hospital;
    }

    // agrega un empleado al hospital si aun no esta registrado y los datos son validos
    public void registrarPersonal(Personal p) {
        if (hospital != null && p != null && !hospital.getPersonal().contains(p)) {
            hospital.getPersonal().add(p);
            System.out.println("[Admin - " + departamento + "] Personal registrado: " + p.getNombre() + " (" + p.getCargo() + ")");
        }
    }

    // elimina del hospital al empleado que coincida con el numero de carnet indicado
    public void eliminarPersonal(int carnet) {
        if (hospital != null) {
            boolean eliminado = hospital.getPersonal().removeIf(p -> p.getNumeroCarnet() == carnet);
            System.out.println(eliminado
                ? "[Admin] Personal con carnet " + carnet + " eliminado."
                : "[Admin] No se encontro personal con carnet " + carnet + ".");
        }
    }

    // genera un reporte general del hospital si existe uno asignado
    public String generarReporte() {
        if (hospital == null) return "Sin hospital asignado.";
        return "Reporte generado por Admin (" + nivelAcceso + " - " + departamento + "):\n" + hospital.informesGenerales();
    }

    // actualiza el horario de un miembro del personal y muestra el cambio
    public void asignarHorario(Personal p, String horario) {
        if (p != null) {
            p.setHorario(horario);
            System.out.println("[Admin] Horario de " + p.getNombre() + " actualizado a: " + horario);
        }
    }

    // PATRON TEMPLATE METHOD
    // define la revision administrativa que hace el administrador dentro del protocolo
    @Override
    protected void realizarLaborClinica(Paciente paciente) {
        System.out.println("Admin " + nombre + " (" + departamento + ") verifica la documentacion de " + paciente.getNombre());
    }

    // PATRON CHAIN OF RESPONSIBILITY
    // atiende solicitudes administrativas o las pasa al siguiente responsable de la cadena
    @Override
    public void procesarSolicitud(String tipoSolicitud, Paciente paciente) {
        if (tipoSolicitud.equalsIgnoreCase("REPORTE") || tipoSolicitud.equalsIgnoreCase("REGISTRO")) {
            System.out.println("[Cadena] Admin " + nombre + " atiende '" + tipoSolicitud + "' para " + paciente.getNombre());
        } else if (siguienteResponsable != null) {
            siguienteResponsable.procesarSolicitud(tipoSolicitud, paciente);
        } else {
            System.out.println("[Cadena] Solicitud no reconocida: " + tipoSolicitud);
        }
    }

    // getters para consultar nivel de acceso y departamento
    public String getNivelAcceso() { return nivelAcceso; }
    public String getDepartamento() { return departamento; }
}
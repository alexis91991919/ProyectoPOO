package administracion;

import excepciones.Excepciones;
import personas.Paciente;
import personas.Personal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase central del sistema: representa al hospital.
 *
 * Es el "contenedor" de pacientes y personal, y tiene su propia
 * Autenticacion (UML: Hospital 1 --- 1 Autenticacion).
 * El Administrador trabaja sobre un Hospital (agrega personal, genera reportes).
 */
public class Hospital {
    private String direccion;
    private String nombre;
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Personal> personal = new ArrayList<>();
    // UML: Hospital 1 --- 1 Autenticacion (se crea junto con el hospital)
    private Autenticacion autenticacion = new Autenticacion();

    public Hospital(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    /**
     * Arma un informe de texto con los datos del hospital, la lista de
     * pacientes (con su estado de alta) y la lista de personal con su horario.
     * Lo usa Administrador.generarReporte().
     *
     * @return el informe completo listo para imprimir
     */
    public String informesGenerales() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INFORME GENERAL: ").append(nombre).append(" ===\n");
        sb.append("Direccion: ").append(direccion).append("\n");
        sb.append("Pacientes registrados: ").append(pacientes.size()).append("\n");
        for (Paciente p : pacientes) {
            sb.append("  - [").append(p.getNumeroCarnet()).append("] ").append(p.getNombre())
              .append(" | Alta: ").append(p.isDadoDeAlta() ? "SI" : "NO").append("\n");
        }
        sb.append("Personal activo: ").append(personal.size()).append("\n");
        for (Personal per : personal) {
            sb.append("  - [").append(per.getNumeroCarnet()).append("] ").append(per.getNombre())
              .append(" (").append(per.getCargo()).append(") | Horario: ").append(per.getHorario()).append("\n");
        }
        return sb.toString();
    }

    // Firma tal cual aparece en el UML. Solo avisa que fue invocado: el registro
    // real se hace con la sobrecarga que recibe el Paciente.
    public void registrarPaciente() {
        System.out.println("Registro de paciente invocado en " + nombre);
    }

    /**
     * Registra un paciente en el hospital.
     * No lo agrega si es null o si ya existe un paciente con el mismo carnet.
     *
     * @param p paciente a registrar
     */
    public void registrarPaciente(Paciente p) {
        if (p == null) {
            System.out.println("No se puede registrar un paciente nulo.");
            return;
        }
        // CORRECCION: antes se usaba pacientes.contains(p), que compara por objeto
        // y dejaba registrar dos pacientes distintos con el mismo carnet.
        // Ahora se compara por numero de carnet.
        for (Paciente existente : pacientes) {
            if (existente.getNumeroCarnet() == p.getNumeroCarnet()) {
                System.out.println("Ya existe un paciente con el carnet " + p.getNumeroCarnet() + ": no se registro.");
                return;
            }
        }
        pacientes.add(p);
        System.out.println("Paciente registrado en " + nombre + ": " + p.getNombre());
    }

    /**
     * Busca un paciente por su numero de carnet.
     *
     * @param carnet numero de carnet a buscar
     * @return el paciente encontrado
     * @throws Excepciones 404 PACIENTE_NO_ENCONTRADO si ningun paciente tiene ese carnet
     */
    public Paciente buscarPaciente(int carnet) throws Excepciones {
        for (Paciente p : pacientes) {
            if (p.getNumeroCarnet() == carnet) {
                return p;
            }
        }
        throw new Excepciones("No se encontro ningun paciente con el carnet: " + carnet, 404, "PACIENTE_NO_ENCONTRADO");
    }

    // Getters. Ojo: getPacientes() y getPersonal() devuelven la lista real (no una
    // copia); por eso Administrador puede agregar o quitar personal directamente.
    public Autenticacion getAutenticacion() { return autenticacion; }
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Personal> getPersonal() { return personal; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
}

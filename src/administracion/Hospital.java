package administracion;

import excepciones.Excepciones;
import personas.Medico;
import personas.Paciente;
import personas.Personal;
import java.util.ArrayList;
import java.util.List;

// clase principal de administracion que gestiona personal, pacientes y citas del hospital
public class Hospital {
    private String nombre;
    private String direccion;
    private Autenticacion autenticacion;
    private List<Personal> listaPersonal;
    private List<Paciente> listaPacientes;
    private List<CitaMedica> listaCitas;

    public Hospital() {
        this.autenticacion = new Autenticacion();
        this.listaPersonal = new ArrayList<>();
        this.listaPacientes = new ArrayList<>();
        this.listaCitas = new ArrayList<>();
    }

    public Hospital(String nombre, String direccion) {
        this();
        this.nombre = nombre;
        this.direccion = direccion;
    }

    // --- GESTION DE PERSONAL ---
    public void registrarPersonal(Personal personal) {
        if (personal != null && !listaPersonal.contains(personal)) {
            listaPersonal.add(personal);
            System.out.println("Personal registrado: " + personal.getCargo() + " " + personal.getNombre());
        }
    }

    public Personal buscarPersonal(int numeroCarnet) {
        for (Personal p : listaPersonal) {
            if (p.getNumeroCarnet() == numeroCarnet) {
                return p;
            }
        }
        return null;
    }

    public List<Medico> obtenerMedicos() {
        List<Medico> medicos = new ArrayList<>();
        for (Personal p : listaPersonal) {
            if (p instanceof Medico) {
                medicos.add((Medico) p);
            }
        }
        return medicos;
    }

    // usa polimorfismo llamando al metodo pago() de Personal
    public double calcularNominaTotal() {
        double total = 0.0;
        for (Personal p : listaPersonal) {
            total += p.pago();
        }
        return total;
    }

    // --- GESTION DE PACIENTES ---
    public void registrarPaciente(Paciente paciente) {
        if (paciente != null && !listaPacientes.contains(paciente)) {
            listaPacientes.add(paciente);
            System.out.println("Paciente registrado en " + nombre + ": " + paciente.getNombre());
        }
    }

    // busca un paciente por nombre y lanza la excepcion 404 si no existe
    public Paciente buscarPaciente(String nombre) throws Excepciones {
        if (nombre != null) {
            for (Paciente p : listaPacientes) {
                if (p.getNombre() != null && p.getNombre().equalsIgnoreCase(nombre.trim())) {
                    return p;
                }
            }
        }
        throw new Excepciones(
            "No se encontro ningun paciente con el nombre: " + nombre,
            404,
            "PACIENTE_NO_ENCONTRADO"
        );
    }

    // --- GESTION DE CITAS ---
    public void agendarCita(CitaMedica nuevaCita) throws Excepciones {
        if (nuevaCita == null) {
            throw new Excepciones("La cita no puede ser nula.", 409, "CITA_NO_DISPONIBLE");
        }
        // delega la validacion de empalmes a CitaMedica
        nuevaCita.agendarCita(this.listaCitas);
        this.listaCitas.add(nuevaCita);
    }

    public boolean cancelarCita(int idCita) {
        for (CitaMedica cita : listaCitas) {
            if (cita.getIdCita() == idCita) {
                cita.cancelarCita();
                return true;
            }
        }
        System.out.println("No se encontro la cita con ID: " + idCita);
        return false;
    }

    // --- REPORTES ---
    public String informesGenerales() {
        return String.format(
            "=== INFORME GENERAL: %s ===\n" +
            "Direccion: %s\n" +
            "Total de personal: %d\n" +
            "Total de pacientes: %d\n" +
            "Total de citas: %d\n" +
            "Nomina total: $%.2f",
            (nombre != null ? nombre : "Sin nombre"),
            (direccion != null ? direccion : "Sin direccion"),
            listaPersonal.size(),
            listaPacientes.size(),
            listaCitas.size(),
            calcularNominaTotal()
        );
    }

    public void mostrarPersonal() {
        System.out.println("\n=== Personal de " + nombre + " ===");
        if (listaPersonal.isEmpty()) {
            System.out.println("Sin personal registrado.");
            return;
        }
        for (Personal p : listaPersonal) {
            System.out.println("- [" + p.getNumeroCarnet() + "] " + p.getCargo() + ": " + p.getNombre() +
                    " | Horario: " + p.getHorario());
        }
    }

    public void mostrarPacientes() {
        System.out.println("\n=== Pacientes en " + nombre + " ===");
        if (listaPacientes.isEmpty()) {
            System.out.println("Sin pacientes registrados.");
            return;
        }
        for (Paciente p : listaPacientes) {
            System.out.println("- " + p.getNombre());
        }
    }

    public void mostrarCitas() {
        System.out.println("\n=== Citas Programadas en " + nombre + " ===");
        if (listaCitas.isEmpty()) {
            System.out.println("Sin citas registradas.");
            return;
        }
        for (CitaMedica c : listaCitas) {
            System.out.println(c.getDetalleCita());
        }
    }

    // getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Autenticacion getAutenticacion() { return autenticacion; }
    public void setAutenticacion(Autenticacion autenticacion) { this.autenticacion = autenticacion; }
    public List<Personal> getPersonal() { return listaPersonal; }
    public List<Personal> getListaPersonal() { return listaPersonal; }
    public List<Paciente> getListaPacientes() { return listaPacientes; }
    public List<CitaMedica> getListaCitas() { return listaCitas; }
}
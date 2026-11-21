package personas;

import java.util.ArrayList;
import java.util.List;

// subclase de personal que representa al area/encargado de laboratorio clinico
public class Laboratorio extends Personal {
    private String area;
    private List<String> muestrasPendientes;
    private List<String> resultados;

    // constructor sencillo por si se instancia solo como departamento
    public Laboratorio(String area) {
        super();
        this.cargo = "Laboratorio";
        this.nombre = "Lab. " + area;
        this.area = area;
        this.muestrasPendientes = new ArrayList<>();
        this.resultados = new ArrayList<>();
    }

    // constructor completo como trabajador del hospital
    public Laboratorio(int numeroCarnet, String nombre, int edad, char sexo, double sueldo, String horario, String area) {
        super(numeroCarnet, nombre, edad, sexo, sueldo, "Laboratorio", horario);
        this.area = area;
        this.muestrasPendientes = new ArrayList<>();
        this.resultados = new ArrayList<>();
    }

    // metodo llamado desde Enfermero.registrarMuestraLab()
    public void recibirMuestra(Paciente p, String tipoMuestra) {
        if (p == null || tipoMuestra == null || tipoMuestra.trim().isEmpty()) {
            System.out.println("[Laboratorio] Error: datos de muestra invalidos.");
            return;
        }
        String registro = "Paciente: " + p.getNombre() + " | Muestra: " + tipoMuestra;
        muestrasPendientes.add(registro);
        System.out.println("[Laboratorio - " + area + "] Muestra recibida -> " + registro);
    }

    // procesa una muestra y guarda el resultado del estudio
    public String procesarMuestra(Paciente p, String tipoMuestra, String hallazgo) {
        if (p == null) {
            return "Paciente no valido para analisis.";
        }
        String registroPendiente = "Paciente: " + p.getNombre() + " | Muestra: " + tipoMuestra;
        muestrasPendientes.remove(registroPendiente);

        String resultado = "Resultado (" + tipoMuestra + ") de " + p.getNombre() + ": " + hallazgo;
        resultados.add(resultado);
        System.out.println("[Laboratorio - " + area + "] Analisis completado -> " + resultado);
        return resultado;
    }

    public void mostrarMuestrasPendientes() {
        System.out.println("\n=== Muestras Pendientes en Laboratorio (" + area + ") ===");
        if (muestrasPendientes.isEmpty()) {
            System.out.println("No hay muestras pendientes.");
            return;
        }
        for (String m : muestrasPendientes) {
            System.out.println("- " + m);
        }
    }

    public void mostrarResultados() {
        System.out.println("\n=== Resultados de Laboratorio (" + area + ") ===");
        if (resultados.isEmpty()) {
            System.out.println("No hay resultados registrados.");
            return;
        }
        for (String r : resultados) {
            System.out.println("- " + r);
        }
    }

    // PATRON TEMPLATE METHOD
    @Override
    protected void realizarLaborClinica(Paciente paciente) {
        System.out.println("Laboratorio (" + area + " - " + nombre + ") recolecta y analiza estudios clinicos de " + paciente.getNombre());
    }

    // PATRON CHAIN OF RESPONSIBILITY
    @Override
    public void procesarSolicitud(String tipoSolicitud, Paciente paciente) {
        if (tipoSolicitud.equalsIgnoreCase("LABORATORIO") ||
            tipoSolicitud.equalsIgnoreCase("ANALISIS") ||
            tipoSolicitud.equalsIgnoreCase("ESTUDIO")) {
            System.out.println("[Cadena] Laboratorio (" + area + ") atiende la solicitud '" + tipoSolicitud + "' para " + paciente.getNombre());
        } else if (siguienteResponsable != null) {
            System.out.println("[Cadena] Laboratorio delega '" + tipoSolicitud + "' al siguiente responsable...");
            siguienteResponsable.procesarSolicitud(tipoSolicitud, paciente);
        } else {
            System.out.println("[Cadena] Nadie pudo atender la solicitud: " + tipoSolicitud);
        }
    }

    // getters y setters
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public List<String> getMuestrasPendientes() { return muestrasPendientes; }
    public List<String> getResultados() { return resultados; }
}
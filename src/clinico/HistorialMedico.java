package clinico;
import personas.Medico;
import java.io.File;// buscar archivo
import java.io.FileWriter;// abre el archivo para escribir
import java.io.PrintWriter;// escribe texto con formato
import java.io.IOException;// atrapa errores de archivo
import java.util.ArrayList;// lista dinamica
import java.util.List;

public class HistorialMedico {
    private int version;
    private String fechaModificacion;
    private String diagnostico;
    private String observaciones;
    private SignosVitales signos;
    private Receta receta;

    // Lista de medicos para recibir notificaciones (Patron Observer)
    private List<Medico> observadores = new ArrayList<>();//[Modificador] [Interfaz]<[Tipo]> [nombre] = new [Implementación]<>();


    public HistorialMedico() {
    }
    
    // constructor para poder llevar y hacer variable historialmedico y tiene por parametros todas las variables definidas antes
    public HistorialMedico(int version, String fechaModificacion, String diagnostico, String observaciones, SignosVitales signos, Receta receta) {
        this.version = version;
        this.fechaModificacion = fechaModificacion;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.signos = signos;
        this.receta = receta;
        guardarArchivoHistorial();// guarda el txt desde que se crea
    }

    // mete un medico a la lista revisando que no sea null ni este repetido
    public void agregarObservador(Medico obs) {// le va a llegar un parametro llamado MEdico obs que sera el medico en cuestion 
        if (obs != null && !observadores.contains(obs)) {// comprueba si existe 
            observadores.add(obs);// lo agerga
        }
    }

    // saca al medico de la lista de alertas
    public void quitarObservador(Medico obs) {// Medico obs nuevamente sera el parametro definido dentro del metodo 
        observadores.remove(obs);
    }

    // recorre la lista de medicos y les manda la alerta critica
    private void notificarEstadoCritico() {
        String alerta = "ALERTA CRITICA en Historial V" + version + ":\n" + signos.getResumenSignos();
        for (Medico obs : observadores) {// ene l rango
            obs.recibirAlertaCritica(alerta);// se manda una notificacion con todo lo que escribio en alerta
        }
    }

    // guarda los signos, actualiza el txt y avisa a los medicos si hay peligro
    public void registrarSignos(SignosVitales signos) {// SignosVitales es el tipo variable es signos
        this.signos = signos;
        guardarArchivoHistorial();
        if (signos != null && signos.esEstadoCritico()) {
            notificarEstadoCritico();
        }
    }

    // guarda la receta y actualiza el txt
    public void asignarReceta(Receta receta) {// vienen de los objetos de  las ptras clases para poder pasar datos 
        this.receta = receta;
        guardarArchivoHistorial();
    }

    public SignosVitales getSignos() { return signos; }// interaccones con las otras clases 
    public Receta getReceta() { return receta; }

    // junta toda la info del historial en un solo texto y evita que salga null
    public String getDetallesCompletos() {
        String textoSignos = (signos != null) ? signos.getResumenSignos() : "Sin signos vitales registrados.";// toma cualquiera de las salidas dependiendo
        String textoReceta = (receta != null) ? receta.getDetalleReceta() : "Sin receta asignada.";// ambos se conviertieron en variables q traen metodos

        return String.format(// string format es una manera de acomodar 
            "=== HISTORIAL MEDICO (Version %d) ===\n" +
            "-Fecha de Modificacion: %s\n" +
            "-Diagnostico: %s\n" +
            "-Observaciones: %s\n\n" +
            "%s\n\n" +
            "%s",
            version, fechaModificacion, diagnostico, observaciones,
            textoSignos, textoReceta
        );
    }

    // crea o sobrescribe el archivo txt con la version del historial
    private void guardarArchivoHistorial() {
        if (version <= 0) return;// si no hay version valida no guarda nada
        File archivo = new File("Historial_Medico_V" + version + ".txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {// creacion del archivoescritura version nueva  dentor del archivo recien creado
            pw.println(getDetallesCompletos());// muestra
        } catch (IOException e) {
            System.out.println("Error al guardar historial en txt: " + e.getMessage());
        }
    }

    // getters y setters (cada setter vuelve a guardar el txt actualizado
    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; guardarArchivoHistorial(); }
    public String getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(String fechaModificacion) { this.fechaModificacion = fechaModificacion; guardarArchivoHistorial(); }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; guardarArchivoHistorial(); }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; guardarArchivoHistorial(); }

    @Override
    public String toString() {
        return getDetallesCompletos();
    }
}

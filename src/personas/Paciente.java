package personas;

import clinico.HistorialMedico;
import java.util.ArrayList;
import java.util.List;

// representa a un paciente del hospital con su lista de historiales medicos
public class Paciente {
    private int numeroCarnet;
    private String nombre;
    private int edad;
    private char sexo;
    private boolean dadoDeAlta;
    private List<HistorialMedico> historiales;

    public Paciente() {
        this.historiales = new ArrayList<>();
        this.dadoDeAlta = false;
    }

    public Paciente(int numeroCarnet, String nombre, int edad, char sexo) {
        this();
        this.numeroCarnet = numeroCarnet;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
    }

    public void agregarHistorial(HistorialMedico historial) {
        if (historial != null) {
            this.historiales.add(historial);
        }
    }

    // alias llamado desde Main.java
    public void agregarEntradaHistorial(HistorialMedico historial) {
        agregarHistorial(historial);
    }

    // metodo llamado desde Medico.consultarHistorialMedico(Paciente)
    public HistorialMedico obtenerUltimoHistorial() {
        if (historiales.isEmpty()) {
            return null;
        }
        return historiales.get(historiales.size() - 1);
    }

    // metodo llamado desde Main.java
    public List<HistorialMedico> consultarHistorialCompleto() {
        return historiales;
    }

    // getters y setters
    public int getNumeroCarnet() { return numeroCarnet; }
    public void setNumeroCarnet(int numeroCarnet) { this.numeroCarnet = numeroCarnet; }
    public int getIdPaciente() { return numeroCarnet; }
    public void setIdPaciente(int idPaciente) { this.numeroCarnet = idPaciente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public char getSexo() { return sexo; }
    public void setSexo(char sexo) { this.sexo = sexo; }
    public boolean isDadoDeAlta() { return dadoDeAlta; }
    public void setDadoDeAlta(boolean dadoDeAlta) { this.dadoDeAlta = dadoDeAlta; }
    public List<HistorialMedico> getHistoriales() { return historiales; }

    @Override
    public String toString() {
        return "Paciente #" + numeroCarnet + ": " + nombre + " (" + edad + " años) | Alta: " + (dadoDeAlta ? "Si" : "No");
    }
}
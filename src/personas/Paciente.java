package personas;

import clinico.HistorialMedico;
import java.util.ArrayList;
import java.util.List;

// representa a un paciente del hospital con su lista de historiales medicos
public class Paciente {
    private int idPaciente;
    private String nombre;
    private int edad;
    private char sexo;
    private boolean dadoDeAlta;
    private List<HistorialMedico> historiales;

    public Paciente() {
        this.historiales = new ArrayList<>();
        this.dadoDeAlta = false;
    }

    public Paciente(int idPaciente, String nombre, int edad, char sexo) {
        this();
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
    }

    public void agregarHistorial(HistorialMedico historial) {
        if (historial != null) {
            this.historiales.add(historial);
        }
    }

    // metodo llamado desde Medico.consultarHistorialMedico(Paciente)
    public HistorialMedico obtenerUltimoHistorial() {
        if (historiales.isEmpty()) {
            return null;
        }
        return historiales.get(historiales.size() - 1);
    }

    // getters y setters
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
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
        return "Paciente #" + idPaciente + ": " + nombre + " (" + edad + " años) | Alta: " + (dadoDeAlta ? "Si" : "No");
    }
}
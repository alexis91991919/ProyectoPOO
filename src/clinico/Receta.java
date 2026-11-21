package clinico;

import java.io.File;// busca o crea referencia al archivo
import java.io.FileWriter;// abre el archivo para escribir
import java.io.PrintWriter;// escribe texto con formato
import java.io.IOException;// atrapa errores de archivo

public class Receta {
    private int folio;
    private String fechaEmision;
    private String medicamento;
    private String dosis;
    private int diasTratamiento;

    public Receta() {
    }

    // constructor para crear objetos receta y tiene por parametros todas las variables definidas antes
    public Receta(int folio, String fechaEmision, String medicamento, String dosis, int diasTratamiento) {
        this.folio = folio;
        this.fechaEmision = fechaEmision;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.diasTratamiento = diasTratamiento;
    }

    // junta toda la info de la receta en un solo texto acomodado
    public String getDetalleReceta() {
        return String.format(
            "-Receta Medica (Folio: %d)-\n" +
            "Fecha de Emision: %s\n" +
            "Medicamento: %s\n" +
            "Dosis: %s\n" +
            "Duracion del Tratamiento: %d dias",
            folio, fechaEmision, medicamento, dosis, diasTratamiento
        );
    }

    // Imprimir receta (consola + respaldo en .txt)
    public void imprimirReceta() {
        System.out.println(getDetalleReceta());// muestra la receta en consola
        if (folio > 0) {// si el folio es valido procede a guardar
            File archivo = new File("Receta_Folio_" + folio + ".txt");// nombra el archivo con su folio
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {// creacion y escritura dentro del archivo recien creado
                pw.println(getDetalleReceta());// escribe el texto formateado
            } catch (IOException e) {
                System.out.println("Error al generar archivo de receta: " + e.getMessage());
            }
        }
    }

    // getters y setters para acceder y modificar cada atributo privado
    public int getFolio() { return folio; }
    public void setFolio(int folio) { this.folio = folio; }
    public String getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public int getDiasTratamiento() { return diasTratamiento; }
    public void setDiasTratamiento(int diasTratamiento) { this.diasTratamiento = diasTratamiento; }

    @Override
    public String toString() {// devuelve el detalle completo al imprimir el objeto
        return getDetalleReceta();
    }
}

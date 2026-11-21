package clinico;

// no necesita importar librerias externas porque solo maneja tipos basicos y String
public class SignosVitales {
    private String presionArterial;
    private double temperatura;
    private int frecuenciaCardiaca;
    private int saturacionOxigeno;
    private double peso;

    public SignosVitales() {
    }

    // constructor para crear objetos signos vitales y tiene por parametros todas las variables definidas antes
    public SignosVitales(String presionArterial, double temperatura, int frecuenciaCardiaca, int saturacionOxigeno, double peso) {
        this.presionArterial = presionArterial;
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.saturacionOxigeno = saturacionOxigeno;
        this.peso = peso;
    }

    // junta todas las mediciones en un solo texto
    public String getResumenSignos() {
        return String.format(// string format es una manera de acomodar
            "--- Signos Vitales ---\n" +
            "  * Presion Arterial: %s mmHg\n" +
            "  * Temperatura: %.1f C\n" + // %.1f muestra el decimal redondeado a 1 digito
            "  * Frecuencia Cardiaca: %d lpm\n" +
            "  * Saturacion de Oxigeno: %d%%\n" +// %% imprime el simbolo de porcentaje literal
            "  * Peso: %.1f kg\n" +
            "  * Estado Critico: %s",
            presionArterial, temperatura, frecuenciaCardiaca, saturacionOxigeno, peso,
            (esEstadoCritico() ? "SI (Alerta medica)" : "No")// llama al metodo de abajo para poner SI o No automaticamente
        );
    }

    // evalua los rangos medicos y devuelve true si alguno sale de lo normal (lo consulta HistorialMedico)
    public boolean esEstadoCritico() {
        return (temperatura > 39.0 || temperatura < 35.0) ||// fiebre alta o hipotermia
            (frecuenciaCardiaca > 120 || frecuenciaCardiaca < 50) ||// taquicardia o bradicardia
            (saturacionOxigeno < 90);// falta de oxigenacion
    }

    // getters y setters para acceder y modificar cada medicion privada
    public String getPresionArterial() { return presionArterial; }
    public void setPresionArterial(String presionArterial) { this.presionArterial = presionArterial; }
    public double getTemperatura() { return temperatura; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }
    public int getFrecuenciaCardiaca() { return frecuenciaCardiaca; }
    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) { this.frecuenciaCardiaca = frecuenciaCardiaca; }
    public int getSaturacionOxigeno() { return saturacionOxigeno; }
    public void setSaturacionOxigeno(int saturacionOxigeno) { this.saturacionOxigeno = saturacionOxigeno; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    @Override
    public String toString() {// devuelve el resumen completo al imprimir el objeto
        return getResumenSignos();
    }
}
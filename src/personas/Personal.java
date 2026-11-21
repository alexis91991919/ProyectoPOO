package personas;

// clase base abstracta que representa a cualquier trabajador del hospital
public abstract class Personal {
    // atributos protegidos para que las subclases puedan usarlos directamente
    protected int numeroCarnet;
    protected String nombre;
    protected int edad;
    protected char sexo;
    protected double sueldo;
    protected String cargo;
    protected String horario;

    // PATRON CHAIN OF RESPONSIBILITY
    protected Personal siguienteResponsable;

    // constructor vacio necesario si alguna subclase lo ocupa
    public Personal() {
    }

    // constructor que inicializa todos los datos principales del empleado
    public Personal(int numeroCarnet, String nombre, int edad, char sexo, double sueldo, String cargo, String horario) {
        this.numeroCarnet = numeroCarnet;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.sueldo = sueldo;
        this.cargo = cargo;
        this.horario = horario;
    }

    // obtiene la hora de entrada a partir del texto del horario o devuelve 8 por defecto
    public int horaDeEntrada() {
        if (horario != null && horario.contains("-")) {
            try {
                // separa el rango por guion, toma el inicio y extrae la hora antes de dos puntos
                return Integer.parseInt(horario.split("-")[0].trim().split(":")[0]);
            } catch (NumberFormatException ignored) {
                // si el formato del horario no es numerico valido cae en el valor por defecto
            }
        }
        return 8;
    }

    // obtiene la hora de salida a partir del texto del horario o devuelve 16 por defecto
    public int horaDeSalida() {
        if (horario != null && horario.contains("-")) {
            try {
                // separa el rango por guion, toma el final y extrae la hora antes de dos puntos
                return Integer.parseInt(horario.split("-")[1].trim().split(":")[0]);
            } catch (NumberFormatException ignored) {
                // si el formato del horario no es numerico valido cae en el valor por defecto
            }
        }
        return 16;
    }

    // devuelve el pago base del empleado, pensado para redefinirse si aplica
    public double pago() {
        return sueldo;
    }

    // PATRON TEMPLATE METHOD
    public final void ejecutarProtocoloAtencion(Paciente paciente) {
        System.out.println("\n[Protocolo - " + cargo + " " + nombre + " | Turno: " + horaDeEntrada() + ":00 a " + horaDeSalida() + ":00]");
        // llama al paso especifico que cada subclase debe implementar
        realizarLaborClinica(paciente);
        System.out.println("[Fin de atencion para " + paciente.getNombre() + "]");
    }

    // paso abstracto que cada tipo de personal resuelve segun su rol clinico
    protected abstract void realizarLaborClinica(Paciente paciente);

    // PATRON CHAIN OF RESPONSIBILITY
    public void setSiguienteResponsable(Personal siguiente) {
        this.siguienteResponsable = siguiente;
    }

    // metodo abstracto para manejar o pasar solicitudes segun el tipo y el paciente
    public abstract void procesarSolicitud(String tipoSolicitud, Paciente paciente);

    // metodos de acceso y modificacion para leer o cambiar cada atributo de forma controlada
    public int getNumeroCarnet() { return numeroCarnet; }
    public void setNumeroCarnet(int numeroCarnet) { this.numeroCarnet = numeroCarnet; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public char getSexo() { return sexo; }
    public void setSexo(char sexo) { this.sexo = sexo; }
    public double getSueldo() { return sueldo; }
    public void setSueldo(double sueldo) { this.sueldo = sueldo; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}

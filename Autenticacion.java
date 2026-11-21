package administracion;

import excepciones.Excepciones;
import java.util.List;

/**
 * Controla el inicio de sesion (autenticacion) del hospital.
 *
 * Caso de uso: "Iniciar sesion" --include--> "Validar credencial".
 * Relacion UML: Hospital 1 --- 1 Autenticacion (cada hospital tiene una).
 *
 * Guarda el carnet de quien inicio sesion y el tipo de usuario validado
 * (PACIENTE, PERSONAL, MEDICO o ENFERMERO) para decidir a que tiene acceso.
 */
public class Autenticacion {

    // CORRECCION: lista de tipos permitidos. Antes cualquier texto (ej. "HACKER")
    // se aceptaba como valido.
    private static final List<String> TIPOS_VALIDOS =
            List.of("PACIENTE", "PERSONAL", "MEDICO", "ENFERMERO");

    private int numeroCarnet;
    private String tipoValidado = ""; // vacio = nadie ha iniciado sesion

    public Autenticacion() {
    }

    public Autenticacion(int numeroCarnet) {
        this.numeroCarnet = numeroCarnet;
    }

    /**
     * Valida la credencial de un usuario y guarda su sesion.
     *
     * @param carnet     numero de carnet (debe ser mayor a 0)
     * @param validacion tipo de usuario: PACIENTE, PERSONAL, MEDICO o ENFERMERO
     *                   (no distingue mayusculas ni espacios en los extremos)
     * @return true si la credencial es valida (si no lo es, lanza excepcion)
     * @throws Excepciones 401 si el carnet es invalido o el tipo viene vacio/nulo;
     *                     403 si el tipo no esta en la lista de tipos validos
     */
    public boolean validarCredencial(int carnet, String validacion) throws Excepciones {
        // CORRECCION: se limpia la sesion anterior. Antes, si el nuevo intento fallaba,
        // el usuario anterior conservaba sus permisos.
        numeroCarnet = 0;
        tipoValidado = "";

        if (carnet <= 0 || validacion == null || validacion.trim().isEmpty()) {
            throw new Excepciones("Credencial invalida para el carnet: " + carnet, 401, "CREDENCIAL_INVALIDA");
        }

        String tipo = validacion.trim().toUpperCase();

        // CORRECCION: se rechaza cualquier tipo que no sea uno de los permitidos.
        if (!TIPOS_VALIDOS.contains(tipo)) {
            throw new Excepciones("Tipo de usuario no reconocido: " + validacion, 403, "TIPO_USUARIO_INVALIDO");
        }

        // Solo se llega aqui si todo es correcto: se guarda la sesion.
        this.numeroCarnet = carnet;
        this.tipoValidado = tipo;
        return true;
    }

    /** @return true si la sesion actual es de un paciente */
    public boolean accesoPaciente() {
        return tipoValidado.equals("PACIENTE");
    }

    /** @return true si la sesion actual es de personal (general, medico o enfermero) */
    public boolean accesoPersonal() {
        return tipoValidado.equals("PERSONAL") || tipoValidado.equals("MEDICO") || tipoValidado.equals("ENFERMERO");
    }

    public int getNumeroCarnet() { return numeroCarnet; }
}

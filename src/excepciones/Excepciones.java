package excepciones;

/**
 * Excepcion personalizada del sistema hospitalario.
 *
 * Es una excepcion CHECKED (hereda de Exception), por lo tanto el compilador
 * obliga a quien llame un metodo que la lanza a atraparla con try/catch o a
 * declararla con "throws".
 *
 * Cada excepcion lleva tres datos:
 *   - mensaje: texto que explica que paso.
 *   - codigo:  numero parecido a los codigos HTTP (401, 403, 404, 409).
 *   - tipo:    etiqueta corta en MAYUSCULAS para identificar el error.
 *
 * Codigos que usa el proyecto:
 *   401 CREDENCIAL_INVALIDA      -> Autenticacion.validarCredencial()
 *   403 TIPO_USUARIO_INVALIDO    -> Autenticacion.validarCredencial()
 *   404 PACIENTE_NO_ENCONTRADO   -> Hospital.buscarPaciente()
 *   409 CITA_NO_DISPONIBLE       -> CitaMedica.agendarCita()
 */
public class Excepciones extends Exception {

    // Identificador de version para la serializacion (buena practica en Exception).
    private static final long serialVersionUID = 1L;

    // Nota: "mensaje" repite lo que ya guarda Exception con super(mensaje);
    // getMensaje() y getMessage() devuelven lo mismo. Se deja para no romper
    // el codigo que ya usa getMensaje().
    private String mensaje;
    private int codigo;
    private String tipo;

    /**
     * @param mensaje descripcion legible del error
     * @param codigo  codigo numerico del error (ej. 404)
     * @param tipo    nombre corto del error (ej. "PACIENTE_NO_ENCONTRADO")
     */
    public Excepciones(String mensaje, int codigo, String tipo) {
        super(mensaje); // guarda el mensaje en la clase padre (para getMessage())
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.tipo = tipo;
    }

    public String getMensaje() { return mensaje; }
    public int getCodigo() { return codigo; }
    public String getTipo() { return tipo; }
}

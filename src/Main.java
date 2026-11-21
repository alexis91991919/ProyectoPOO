package main;

import administracion.*;
import clinico.*;
import excepciones.Excepciones;
import personas.*;

/**
 * Clase principal: aqui arranca el programa y se simulan los casos de uso
 * del sistema hospitalario, en este orden:
 *   1. Crear hospital, administrador, laboratorio y personas.
 *   2. Registrar personal y paciente.
 *   3. Iniciar sesion y agendar cita (con manejo de excepciones).
 *   4. Consultar horario y pago.
 *   5. Patrones de diseno: Observer, Template Method y Chain of Responsibility.
 *   6. Signos vitales, laboratorio, historial, receta y alta.
 *   7. Prueba de errores (excepciones).
 *   8. Reporte final del administrador.
 *
 * Nota: el programa crea archivos .txt (Historial_Medico_V1.txt y
 * Receta_Folio_101.txt) en la carpeta desde donde se ejecuta.
 */
public class Main {

    public static void main(String[] args) {
        // ---------- 1. Objetos principales ----------
        Hospital hospital = new Hospital("Hospital General Universitario", "Av. Universidad 3000");
        Administrador admin = new Administrador("Nivel 1", "Direccion Medica", hospital);
        Laboratorio lab = new Laboratorio("Lab Clinico Central");
        // La autenticacion es la del propio hospital (relacion 1 a 1 del UML)
        Autenticacion auth = hospital.getAutenticacion();

        // ---------- 2. Alta de personal y paciente (caso de uso: Registrar paciente) ----------
        Medico medico = new Medico(2001, "Ana Torres", 38, 'F', 28000.0, "08:00 - 16:00", "Medicina Interna", 104, 15);
        Enfermero enfermero = new Enfermero(3001, "Luis Perez", 29, 'M', 14500.0, "07:00 - 15:00", "Matutino");
        Paciente paciente = new Paciente(1001, "Carlos Lopez", 32, 'M');

        admin.registrarPersonal(medico);
        admin.registrarPersonal(enfermero);
        admin.asignarHorario(enfermero, "07:00 - 15:00");
        hospital.registrarPaciente(paciente);

        // PATRON 3: CHAIN OF RESPONSIBILITY (Enfermero -> Medico)
        // Si el enfermero no puede atender una solicitud, se la pasa al medico.
        enfermero.setSiguienteResponsable(medico);

        // ---------- 3. Iniciar sesion (include: Validar credencial) ----------
        // CORRECCION: antes todo iba en un solo try; si fallaba la cita, ya no se
        // ejecutaba buscarPaciente. Ahora cada operacion tiene su propio try/catch.
        try {
            auth.validarCredencial(paciente.getNumeroCarnet(), "PACIENTE");
            if (auth.accesoPaciente()) {
                System.out.println("Acceso concedido al paciente: " + paciente.getNombre());
            }

            auth.validarCredencial(medico.getNumeroCarnet(), "MEDICO");
            if (auth.accesoPersonal()) {
                System.out.println("Acceso concedido al personal: " + medico.getNombre());
            }
        } catch (Excepciones e) {
            mostrarError(e);
        }

        // Agendar cita (include: Comprobar espacio disponible)
        try {
            CitaMedica cita = new CitaMedica("", "", "Fiebre alta y dolor de garganta");
            cita.agendarCita(21, 10); // dia 21 a las 10:00
        } catch (Excepciones e) {
            mostrarError(e);
        }

        // Buscar paciente por carnet (puede lanzar 404)
        try {
            Paciente encontrado = hospital.buscarPaciente(paciente.getNumeroCarnet());
            System.out.println("Paciente encontrado: " + encontrado.getNombre());
        } catch (Excepciones e) {
            mostrarError(e);
        }

        // ---------- 4. Consultar horario / Consultar pago (Personal) ----------
        System.out.println("\nHorario de " + medico.getNombre() + ": " + medico.horaDeEntrada() + ":00 a " + medico.horaDeSalida() + ":00");
        System.out.println("Pago de " + medico.getNombre() + ": $" + medico.pago());
        System.out.println("Pago de " + enfermero.getNombre() + ": $" + enfermero.pago());

        // ---------- 5. Patrones de diseno ----------
        // PATRON 1: OBSERVER (el Medico se suscribe al historial y recibe alertas criticas)
        HistorialMedico historial = new HistorialMedico(1, "20/11/2026", "Cuadro febril agudo", "Paciente ingresa con malestar", null, null);
        historial.agregarObservador(medico);
        paciente.agregarEntradaHistorial(historial);

        // PATRON 2: TEMPLATE METHOD (mismo protocolo de atencion, cada cargo hace su labor)
        enfermero.ejecutarProtocoloAtencion(paciente);
        medico.ejecutarProtocoloAtencion(paciente);

        // ---------- 6. Flujo clinico ----------
        // Tomar signos vitales (include: Registrar en el historial; extend: Evaluar estado critico)
        // Estos valores son criticos a proposito: disparan la alerta del Observer.
        SignosVitales signos = enfermero.tomarSignosVitales("135/85", 39.6, 124, 88, 72.5);
        enfermero.registrarEnHistorial(historial, signos);
        enfermero.aplicarMedicamento("Paracetamol", 500.0);

        // Registrar muestra de laboratorio y su resultado
        enfermero.registrarMuestraLab(lab, paciente, "Biometria hematica");
        lab.registrarResultado(paciente.getNumeroCarnet(), "Leucocitos elevados");
        System.out.println(lab.consultarResultado(paciente.getNumeroCarnet()));

        // PATRON 3: CHAIN OF RESPONSIBILITY
        System.out.println("\n--- Probando Cadena de Responsabilidad ---");
        enfermero.procesarSolicitud("MUESTRA", paciente); // Lo atiende el Enfermero
        enfermero.procesarSolicitud("RECETA", paciente);  // El Enfermero delega al Medico

        // Consultar historial (Paciente: Ver ultimo historial) y Consultar historial medico (Medico)
        System.out.println("\n--- Consultar historial (Paciente) ---");
        System.out.println("Versiones en el expediente: " + paciente.consultarHistorialCompleto().size());
        System.out.println(paciente.obtenerUltimoHistorial());
        System.out.println("\n--- Consultar historial medico (Medico) ---");
        System.out.println(medico.consultarHistorialMedico(paciente));

        // Recetar medicamento (include: Registrar en el historial; extend: Imprimir receta) y Dar de alta
        Receta receta = new Receta(101, "20/11/2026", "Amoxicilina", "500mg cada 8 horas", 7);
        medico.recetarMedicamento(historial, receta, true); // true = tambien imprime la receta
        medico.darDeAlta(paciente);

        // ---------- 7. Prueba de excepciones ----------
        // Cada caso provoca un error a proposito para comprobar que se atrapa bien.
        System.out.println("\n--- Prueba de excepciones ---");

        try {
            auth.validarCredencial(0, "PACIENTE"); // carnet invalido -> 401
        } catch (Excepciones e) {
            mostrarError(e);
        }

        try {
            auth.validarCredencial(1001, "HACKER"); // tipo no permitido -> 403
        } catch (Excepciones e) {
            mostrarError(e);
        }

        try {
            hospital.buscarPaciente(9999); // paciente que no existe -> 404
        } catch (Excepciones e) {
            mostrarError(e);
        }

        try {
            new CitaMedica("", "", "Revision").agendarCita(21, 10); // mismo horario ya ocupado -> 409
        } catch (Excepciones e) {
            mostrarError(e);
        }

        // ---------- 8. Generar informes ----------
        System.out.println("\n" + admin.generarReporte());
    }

    /**
     * Imprime una excepcion del sistema con el formato [TIPO - Codigo N] mensaje.
     * Evita repetir el mismo println en cada catch.
     */
    private static void mostrarError(Excepciones e) {
        System.out.println("[" + e.getTipo() + " - Codigo " + e.getCodigo() + "] " + e.getMensaje());
    }
}


package controller;

import view.VentanaPrincipal;
import view.VentanaClientes;
import view.VentanaVehiculos;
import view.VentanaReparaciones;
import view.VentanaConsultas;
import controller.ClienteController;
import controller.VehiculoController;
import controller.ReparacionController;
import controller.ConsultasController;

import javax.swing.JOptionPane;

public class MenuController {

    private final VentanaPrincipal vista;

    public MenuController(VentanaPrincipal vista) {

        this.vista = vista;

        iniciarEventos();
    }

    private void iniciarEventos() {

        vista.btnSalir.addActionListener(e -> salir());
        

        vista.btnClientes.addActionListener(e -> {
            VentanaClientes ventana = new VentanaClientes();
            new ClienteController(ventana);
        });

        vista.btnVehiculos.addActionListener(e -> {
            VentanaVehiculos ventana = new VentanaVehiculos();
            new VehiculoController(ventana);
        });

        vista.btnReparaciones.addActionListener(e -> {
            VentanaReparaciones ventana = new VentanaReparaciones();
            new ReparacionController(ventana);
        });

        vista.btnConsultas.addActionListener(e -> {
            VentanaConsultas ventana = new VentanaConsultas();
            new ConsultasController(ventana);
        });
    }

    private void salir() {

        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

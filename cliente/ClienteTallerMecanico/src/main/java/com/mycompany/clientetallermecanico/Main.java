
package com.mycompany.clientetallermecanico;

import controller.MenuController;
import view.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        VentanaPrincipal vista = new VentanaPrincipal();

        new MenuController(vista);
    }
}

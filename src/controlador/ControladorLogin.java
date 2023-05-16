/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.swing.JOptionPane;
import modelo.dao.UsuarioDAO;
import modelo.vo.Usuario;
import vista.VentanaLogin;
import vista.VentanaPrincipal;

/**
 *
 * @author krypt97
 */
 // CONSTRUCTOR
    public ControladorLogin(VentanaLogin miVentanaLogin, VentanaPrincipal miVentanaPrincipal, UsuarioDAO miUsuarioDAO) {
        this.miVentanaLogin = miVentanaLogin;
        this.miVentanaPrincipal = miVentanaPrincipal;
        this.miUsuarioDAO = miUsuarioDAO;
    }

    // MÉTODO PARA INICIAR SESIÓN
    public void logearUsuario() {
        username = miVentanaLogin.getUsername();
        password = miVentanaLogin.getPassword();
        miUsuarioBuscado = miUsuarioDAO.buscarUsuarioUsername(username);
        if (miUsuarioBuscado != null && username.equals(miUsuarioBuscado.getUsername()) && password.equals(miUsuarioBuscado.getPassword())) {
            // Setea usuario principal del main
            miUsuario = new Usuario(miUsuarioBuscado.getDniUsuario(), username, password, miUsuarioBuscado.getRol()); // crear un nuevo Usuario en lugar de establecer sus atributos uno por uno
            // Intercambia ventanas
            miVentanaLogin.setVisible(false);
            miVentanaPrincipal.setVisible(true);
            miVentanaPrincipal.panelHome.setVisible(true);
            validarPermisoUsuario();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos"); // se puede mostrar un mensaje más genérico para mejorar la seguridad
        }
        miVentanaLogin.limpiarCampos();        
    }

    // MÉTODO PARA CERRAR LA APLICACIÓN
    public void cerrarAplicacion() {
        System.exit(0);
    }

    // MÉTODO PARA VALIDAR EL PERMISO DEL USUARIO
    public void validarPermisoUsuario() {
        String rol = miUsuario.getRol();
        miVentanaPrincipal.btnUsuarios.setVisible(!rol.equalsIgnoreCase("VENDEDOR")); // usar ! en lugar de if else para simplificar el código
        setLayoutBotonesVentanaPrincipal(5, rol.equalsIgnoreCase("VENDEDOR") ? 35 : 24); // usar un operador ternario para simplificar el código
    }

    // MÉTODO PARA ESTABLECER EL LAYOUT DE LOS BOTONES EN LA VENTANA PRINCIPAL
    public void setLayoutBotonesVentanaPrincipal(int ver, int hor) {
        miVentanaPrincipal.panLatBtns.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, ver, hor)); // no es necesario crear una nueva instancia de FlowLayout cada vez que se llama a este método
    }
}

package controlador;

import modelo.vo.Usuario;
import modelo.dao.UsuarioDAO;
import vista.PanelUsuarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorUsuario {

    private PanelUsuarios miPanelUsuarios;
    private UsuarioDAO miUsuarioDAO = UsuarioDAO.getInstancia();
    private Usuario miUsuario;
    private List<Usuario> listaUsuarios;

    public void setPanelUsuarios(PanelUsuarios miPanelUsuarios) {
        this.miPanelUsuarios = miPanelUsuarios;
    }

    public void registrarUsuario() {
        miUsuario = miPanelUsuarios.empaquetarDatosUsuario();
        miUsuarioDAO.registrarUsuario(miUsuario);
        listaUsuarios = miUsuarioDAO.listarUsuarios();
        miPanelUsuarios.setTablaUsuarios(listaUsuarios);
        miPanelUsuarios.limpiarCampos();
    }

    public void eliminarUsuario() {
        String dniUsuario = miPanelUsuarios.txtDni.getText();
        miUsuarioDAO.eliminarUsuario(dniUsuario);
        listaUsuarios = miUsuarioDAO.listarUsuarios();
        miPanelUsuarios.setTablaUsuarios(listaUsuarios);
        miPanelUsuarios.limpiarCampos();
    }

    public void modificarUsuario() {
        miUsuario = miPanelUsuarios.empaquetarDatosUsuario();
        miUsuarioDAO.modificarUsuario(miUsuario);
        listaUsuarios = miUsuarioDAO.listarUsuarios();
        miPanelUsuarios.setTablaUsuarios(listaUsuarios);
        miPanelUsuarios.limpiarCampos();
    }

    public void buscarUsuario() {
        String dniUsuario = miPanelUsuarios.txtDni.getText();
        Usuario usuario = miUsuarioDAO.buscarUsuario(dniUsuario);
        if (usuario != null) {
            miPanelUsuarios.desempaquetarDatosUsuario(usuario);
        }
    }

    public void cargarUsuarioSeleccionado() {
        String dniUsuario = miPanelUsuarios.dniSeleccionado();
        miPanelUsuarios.txtDni.setText(dniUsuario);
        miPanelUsuarios.desempaquetarDatosUsuario(miUsuarioDAO.buscarUsuario(dniUsuario));
        miPanelUsuarios.txtDni.requestFocus();
    }

    public static void main(String[] args) {
        PanelUsuarios panelUsuarios = new PanelUsuarios();
        ControladorUsuario controladorUsuario = new ControladorUsuario();
        controladorUsuario.setPanelUsuarios(panelUsuarios);
        panelUsuarios.setControlador(controladorUsuario);

        panelUsuarios.btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorUsuario.registrarUsuario();
            }
        });

        panelUsuarios.btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorUsuario.eliminarUsuario();
            }
        });

        panelUsuarios.btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorUsuario.modificarUsuario();
            }
        });

        panelUsuarios.btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorUsuario.buscarUsuario();
            }
        });

        panelUsuarios.tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controladorUsuario.cargarUsuarioSeleccionado();
            }
        });

        // Resto del código para configurar la interfaz de usuario y ejecutar la aplicación
        // ...
    }
}

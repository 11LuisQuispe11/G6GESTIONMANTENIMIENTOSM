package controlador;

import modelo.dao.*;
import vista.PanelHistorialVentas;

public class ControladorHistorialVenta {

    private PanelHistorialVentas miPanelHistorialVentas;
    private DAOFactory daoFactory = DAOFactory.getInstancia();
    private int idVentaSeleccionado;
    private String dniUsuarioSelecionado;
    private String dniClienteSeleccionado;

    public void setPanelHistorialVentas(PanelHistorialVentas miPanelHistorialVentas) {
        this.miPanelHistorialVentas = miPanelHistorialVentas;
    }

    public void cargarPedidosSeleccionados() {
        idVentaSeleccionado = miPanelHistorialVentas.getIdVentaSeleccionado();
        dniUsuarioSelecionado = miPanelHistorialVentas.getDniUsuarioSeleccionado();
        dniClienteSeleccionado = miPanelHistorialVentas.getDniClienteSeleccionado();
        UsuarioDAO usuarioDAO = daoFactory.getUsuarioDAO();
        ClienteDAO clienteDAO = daoFactory.getClienteDAO();
        PedidoDAO pedidoDAO = daoFactory.getPedidoDAO();
        ProductoDAO productoDAO = daoFactory.getProductoDAO();
        CategoriaDAO categoriaDAO = daoFactory.getCategoriaDAO();

        miPanelHistorialVentas.desempaquetarDatosUsuario(usuarioDAO.buscarUsuario(dniUsuarioSelecionado));
        miPanelHistorialVentas.desempaquetarDatosCliente(clienteDAO.buscarCliente(dniClienteSeleccionado));
        miPanelHistorialVentas.setTablaPedidos(pedidoDAO.listarCarritoPedidos(idVentaSeleccionado), productoDAO.listarProductos(), categoriaDAO.listarCategorias());
        miPanelHistorialVentas.requestFocusOnPedidosTable();
    }
}

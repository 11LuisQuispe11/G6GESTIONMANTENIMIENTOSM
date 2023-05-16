/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import modelo.dao.CategoriaDAO;
import modelo.dao.ClienteDAO;
import modelo.dao.PedidoDAO;
import modelo.dao.ProductoDAO;
import modelo.dao.VentaDAO;
import modelo.vo.Categoria;
import modelo.vo.Cliente;
import modelo.vo.Pedido;
import modelo.vo.Producto;
import modelo.vo.Usuario;
import modelo.vo.Venta;
import vista.PanelVentas;

/**
 *
 * @author krypt97
 */
public class ControladorVenta {

    // ATRIBUTOS DE CLASE
    private PanelVentas miPanelVentas;
    private VentaDAO miVentaDAO;
    private ClienteDAO miClienteDAO;
    private PedidoDAO miPedidoDAO;
    private ProductoDAO miProductoDAO;
    private CategoriaDAO miCategoriaDAO;
    private Venta miVenta;
    private Cliente miCliente;
    private Pedido miPedido;
    private int idPedido;
    private int cantidad;
    private Usuario miUsuario;

    // ENLACE VISTA
    public void setPanelVentas(PanelVentas miPanelVentas) {
        this.miPanelVentas = miPanelVentas;
    }

    // ENLACE MODELO
    // Vo
    public void setVenta(Venta miVenta) {
        this.miVenta = miVenta;
    }

    public void setCliente(Cliente miCliente) {
        this.miCliente = miCliente;
    }

    public void setPedido(Pedido miPedido) {
        this.miPedido = miPedido;
    }

    public void setUsuario(Usuario miUsuario) {
        this.miUsuario = miUsuario;
    }

    // Dao
    public void setVentaDAO(VentaDAO miVentaDAO) {
        this.miVentaDAO = miVentaDAO;
    }

    public void setClienteDAO(ClienteDAO miClienteDAO) {
        this.miClienteDAO = miClienteDAO;
    }

    public void setPedidoDAO(PedidoDAO miPedidoDAO) {
        this.miPedidoDAO = miPedidoDAO;
    }

    public void SetProductoDAO(ProductoDAO miProductoDAO) {
        this.miProductoDAO = miProductoDAO;
    }

    public void setCategoriaDAO(CategoriaDAO miCategoriaDAO) {
        this.miCategoriaDAO = miCategoriaDAO;
    }

    // METODOS DE CLASE
    // VentaDAO
    public void registrarVenta() {
        miVenta = miPanelVentas.empaquetarDatosVenta(miUsuario.getDniUsuario());
        miVentaDAO.registrarVenta(miVenta);
        miPanelVentas.setTablaPedido(miPedidoDAO.listarCarritoPedidos(miVentaDAO.generarIdVenta()), miCategoriaDAO.listarCategorias());
        miPanelVentas.limpiarCamposPanelProducto();
    }

    public int generarIdVenta() {
        return miVentaDAO.generarIdVenta();
    }

    // PedidoDAO
    public void registrarPedido() {
        miPedido = miPanelVentas.empaquetarDatosPedido();
        miPedidoDAO.registrarPedido(miPedido);
        miPanelVentas.setTablaPedido(miPedidoDAO.listarCarritoPedidos(miVentaDAO.generarIdVenta()), miCategoriaDAO.listarCategorias());
        miPanelVentas.limpiarCamposPanelProducto();
    }

    public void buscarPedido() { // siempre existe
        idPedido = miPanelVentas.idPedidoSeleccionado();
        Pedido miPedidoBuscado = miPedidoDAO.buscarPedido(idPedido);
        Producto miProductoBuscado = miProductoDAO.buscarProducto(miPedidoBuscado.getIdProducto());
        Categoria miCategoriaBuscada = miCategoriaDAO.buscarCategoria(miProductoBuscado.getIdCategoria());
        miPanelVentas.desempaquetarDatosPedido(miPedidoBuscado);
        miPanelVentas.desempaquetarDatosProducto(miProductoBuscado, miCategoriaBuscada);
    }

    public void eliminarPedido() {
        idPedido = miPanelVentas.idPedidoSeleccionado();
        if (idPedido != -1) {
            miPedidoDAO.eliminarPedido(idPedido);
            miPanelVentas.setTablaPedido(miPedidoDAO.listarCarritoPedidos(miVentaDAO.generarIdVenta()), miCategoriaDAO.listarCategorias());
        }
        miPanelVentas.limpiarCamposPanelProducto();
    }
    
    
    public void eliminarCarritoPedidos() {
    int idVenta = miVentaDAO.generarIdVenta();
    miPedidoDAO.listarCarritoPedidos(idVenta)
            .forEach(pedido -> miPedidoDAO.eliminarPedido(pedido.getIdPedido()));
    miPanelVentas.setTablaPedido(miPedidoDAO.listarCarritoPedidos(idVenta), miCategoriaDAO.listarCategorias());
    }
    /*
    public void eliminarCarritoPedidos() {
        ArrayList<Pedido> miCarritoPedidos = miPedidoDAO.listarCarritoPedidos(miVentaDAO.generarIdVenta());
        miCarritoPedidos.forEach((var pedido) -> {
            miPedidoDAO.eliminarPedido(pedido.getIdPedido());
        });
        miPanelVentas.setTablaPedido(miPedidoDAO.listarCarritoPedidos(miVentaDAO.generarIdVenta()), miCategoriaDAO.listarCategorias());
    }*/

    public void modificarPedido() {
        if (!"".equals(miPanelVentas.txtCantProd.getText())) {
            idPedido = miPanelVentas.idPedidoSeleccionado();
            cantidad = Integer.parseInt(miPanelVentas.txtCantProd.getText());
            miPedidoDAO.modificarPedido(cantidad, idPedido);
            miPanelVentas.setTablaPedido(miPedidoDAO.listarCarritoPedidos(miVentaDAO.generarIdVenta()), miCategoriaDAO.listarCategorias());
        }
        miPanelVentas.limpiarCamposPanelProducto();
    }

    // ClienteDAO
    public void registrarCliente() {
        miCliente = miPanelVentas.empaquetarDatosCliente();
        if (miClienteDAO.buscarCliente(miCliente.getDniCliente()) == null) {
            miClienteDAO.registrarCliente(miCliente);
        }
        miPanelVentas.limpiarCamposPanelCliente();
    }

    // ProductoDAO
    public Producto buscarIdProducto(int idProducto) {
        return miProductoDAO.buscarProducto(idProducto);
    }
}

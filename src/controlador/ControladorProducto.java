/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
esto: package controlador;

import modelo.dao.CategoriaDAO;
import modelo.vo.Producto;
import modelo.dao.ProductoDAO;
import modelo.dao.ProveedorDAO;
import vista.PanelProducto;

public class ControladorProducto {

    private PanelProducto miPanelProducto;
    private ProductoDAO miProductoDAO = ProductoDAO.getInstancia();
    private CategoriaDAO miCategoriaDAO = CategoriaDAO.getInstancia();
    private ProveedorDAO miProveedorDAO = ProveedorDAO.getInstancia();
    private Producto miProducto;
    private int idProducto;

    public void setPanelProducto(PanelProducto miPanelProducto) {
        this.miPanelProducto = miPanelProducto;
    }

    public void registrarProducto() {
        miProducto = miPanelProducto.empaquetarDatosProducto();
        miProductoDAO.registrarProducto(miProducto);
        int idProductoRegistrado = miProductoDAO.obtenerUltimoIDProductoRegistrado();
        miProducto = miProductoDAO.buscarProducto(idProductoRegistrado);
        miPanelProducto.setTablaProductos(miProductoDAO.listarProductos(), miCategoriaDAO.listarCategorias(), miProveedorDAO.listarProveedores());
        miPanelProducto.limpiarCampos();
    }

    public void buscarProducto() {
        idProducto = Integer.parseInt(miPanelProducto.txtIdProducto.getText());
        miPanelProducto.desempaquetarDatosProducto(miProductoDAO.buscarProducto(idProducto));
    }

    public void modificarProducto() {
        miProducto = miPanelProducto.empaquetarDatosProducto();
        miProductoDAO.modificarProducto(miProducto);
        miPanelProducto.setTablaProductos(miProductoDAO.listarProductos(), miCategoriaDAO.listarCategorias(), miProveedorDAO.listarProveedores());
        miPanelProducto.limpiarCampos();
    }

    public void eliminarProducto() {
        idProducto = Integer.parseInt(miPanelProducto.txtIdProducto.getText());
        miProductoDAO.eliminarProducto(idProducto);
        miPanelProducto.setTablaProductos(miProductoDAO.listarProductos(), miCategoriaDAO.listarCategorias(), miProveedorDAO.listarProveedores());
        miPanelProducto.limpiarCampos();
    }

    public void cargarProductoSeleccionado() {
        idProducto = Integer.parseInt(miPanelProducto.idProductoSeleccionado());
        miPanelProducto.txtIdProducto.setText(String.valueOf(idProducto));
        miPanelProducto.desempaquetarDatosProducto(miProductoDAO.buscarProducto(idProducto));
        mi

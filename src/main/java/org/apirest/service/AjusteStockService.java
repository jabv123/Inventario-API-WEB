package org.apirest.service;

import java.time.LocalDate;
import java.util.List;

import org.apirest.modelo.AjusteStock;
import org.apirest.repository.AjusteStockRepo;

public class AjusteStockService {

    private final AjusteStockRepo ajusteStockRepo;
    private final ProductoService productoService;

    public AjusteStockService(AjusteStockRepo ajusteStockRepo, ProductoService productoService) {
        this.ajusteStockRepo = ajusteStockRepo;
        this.productoService = productoService;
    }

    // === MÉTODOS CRUD BÁSICOS ===

    public List<AjusteStock> obtenerTodosLosAjustes() {
        return ajusteStockRepo.getAll();
    }

    public AjusteStock obtenerAjustePorId(int id) {
        return ajusteStockRepo.getById(id);
    }

    public List<AjusteStock> obtenerAjustesPorProducto(int idProducto) {
        return ajusteStockRepo.getByProducto(idProducto);
    }

    public List<AjusteStock> obtenerAjustesPorUsuario(int idUsuario) {
        return ajusteStockRepo.getByUsuario(idUsuario);
    }

    public AjusteStock actualizarAjuste(AjusteStock ajuste) {
        return ajusteStockRepo.update(ajuste);
    }

    public boolean eliminarAjuste(int id) {
        return ajusteStockRepo.delete(id);
    }

    // === MÉTODOS DE NEGOCIO ===

    /**
     * Procesa un ajuste de stock completo (registra el ajuste y actualiza el inventario)
     * @param idProducto ID del producto a ajustar
     * @param cantidadAjuste Cantidad a ajustar (positiva para aumentar, negativa para reducir)
     * @param motivo Motivo del ajuste
     * @param idUsuario ID del usuario que realiza el ajuste
     * @return El ajuste creado
     */
    public AjusteStock procesarAjusteStock(int idProducto, int cantidadAjuste, String motivo, int idUsuario) {
        // Validar entrada
        validarDatosAjuste(idProducto, cantidadAjuste, motivo, idUsuario);
        
        // Verificar que el producto existe
        if (!productoService.existeProducto(idProducto)) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe");
        }
        
        // Validar que el ajuste no resulte en stock negativo
        int stockActual = productoService.obtenerStockActual(idProducto);
        if (stockActual + cantidadAjuste < 0) {
            throw new IllegalArgumentException("El ajuste resultaría en stock negativo. Stock actual: " + 
                                             stockActual + ", ajuste solicitado: " + cantidadAjuste);
        }
        
        // Crear el registro de ajuste
        AjusteStock ajuste = new AjusteStock(idProducto, cantidadAjuste, motivo, LocalDate.now(), idUsuario);
        AjusteStock ajusteCreado = ajusteStockRepo.add(ajuste);
        
        // Aplicar el ajuste al inventario
        productoService.ajustarStock(idProducto, cantidadAjuste);
        
        return ajusteCreado;
    }

    // === MÉTODO DE VALIDACIÓN DE AJUSTE DE STOCK ===

    private void validarDatosAjuste(int idProducto, int cantidadAjuste, String motivo, int idUsuario) {
        if (idProducto <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0");
        }
        if (cantidadAjuste == 0) {
            throw new IllegalArgumentException("La cantidad de ajuste no puede ser 0");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo del ajuste es obligatorio");
        }
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }
    }
}

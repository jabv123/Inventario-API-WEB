package org.apirest.service;

import java.util.Date;
import java.util.List;

import org.apirest.modelo.CuponDescuento;
import org.apirest.repository.CuponDescuentoRepo;

public class CuponDescuentoService {

    private final CuponDescuentoRepo cuponRepository;

    public CuponDescuentoService(CuponDescuentoRepo cuponRepository) {
        this.cuponRepository = cuponRepository;
    }

    // === MÉTODOS CRUD BÁSICOS ===
    
    public List<CuponDescuento> obtenerTodosLosCupones() {
        return cuponRepository.getCupones();
    }

    public CuponDescuento obtenerCuponPorId(int id) {
        return cuponRepository.getById(id);
    }

    public CuponDescuento obtenerCuponPorCodigo(String codigo) {
        return cuponRepository.getByCodigo(codigo);
    }

    public CuponDescuento crearCupon(CuponDescuento cupon) {
        // Validación básica
        if (cupon.getCodigo() == null || cupon.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del cupón es obligatorio");
        }
        
        // Verificar que el código no exista
        if (cuponRepository.getByCodigo(cupon.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un cupón con este código");
        }

        if (cupon.getValor() <= 0) {
            throw new IllegalArgumentException("El valor del cupón debe ser mayor a 0");
        }

        return cuponRepository.crear(cupon);
    }

    public CuponDescuento actualizarCupon(CuponDescuento cupon) {
        if (cupon.getId() == 0) {
            throw new IllegalArgumentException("ID del cupón es requerido para actualizar");
        }
        
        CuponDescuento cuponExistente = cuponRepository.getById(cupon.getId());
        if (cuponExistente == null) {
            throw new IllegalArgumentException("No se encontró el cupón con ID: " + cupon.getId());
        }

        return cuponRepository.actualizar(cupon);
    }

    public boolean eliminarCupon(int id) {
        CuponDescuento cupon = cuponRepository.getById(id);
        if (cupon == null) {
            throw new IllegalArgumentException("No se encontró el cupón con ID: " + id);
        }
        return cuponRepository.eliminar(id);
    }

    // === MÉTODOS ESPECÍFICOS PARA GESTIÓN DE CUPONES ===

    public List<CuponDescuento> obtenerCuponesActivos() {
        return cuponRepository.getCuponesActivos();
    }

    public List<CuponDescuento> obtenerCuponesValidos() {
        return cuponRepository.getCuponesValidos();
    }

    public boolean activarCupon(int id) {
        return cuponRepository.activar(id);
    }

    public boolean desactivarCupon(int id) {
        return cuponRepository.desactivar(id);
    }

    // === MÉTODOS PARA APLICAR CUPONES EN VENTAS ===

    public boolean validarCupon(String codigo) {
        CuponDescuento cupon = cuponRepository.getByCodigo(codigo);
        
        if (cupon == null) {
            return false; // Cupón no existe
        }
        
        if (!cupon.isActivo()) {
            return false; // Cupón inactivo
        }
        
        // Verificar si está expirado
        if (cupon.getFechaExpiracion() != null) {
            Date fechaActual = new Date();
            if (cupon.getFechaExpiracion().before(fechaActual)) {
                return false; // Cupón expirado
            }
        }
        
        return true;
    }

    public double calcularDescuento(String codigoCupon, double montoTotal) {
        if (!validarCupon(codigoCupon)) {
            throw new IllegalArgumentException("Cupón inválido o expirado: " + codigoCupon);
        }

        CuponDescuento cupon = cuponRepository.getByCodigo(codigoCupon);
        double descuento = 0;

        if ("porcentaje".equalsIgnoreCase(cupon.getTipoValor())) {
            // Descuento por porcentaje
            descuento = montoTotal * (cupon.getValor() / 100);
        } else if ("fijo".equalsIgnoreCase(cupon.getTipoValor())) {
            // Descuento fijo
            descuento = cupon.getValor();
            // No puede ser mayor al total
            if (descuento > montoTotal) {
                descuento = montoTotal;
            }
        }

        return descuento;
    }

    public double aplicarDescuento(String codigoCupon, double montoTotal) {
        double descuento = calcularDescuento(codigoCupon, montoTotal);
        return montoTotal - descuento;
    }

}

package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.CuponDescuento;

public class CuponDescuentoRepo {

    private final List<CuponDescuento> cupones = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    // Obtener todos los cupones
    public List<CuponDescuento> getCupones() {
        return cupones;
    }

    // Obtener cupón por ID
    public CuponDescuento getById(int id) {
        return cupones.stream()
                .filter(cupon -> cupon.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Obtener cupón por código
    public CuponDescuento getByCodigo(String codigo) {
        return cupones.stream()
                .filter(cupon -> cupon.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    // Obtener cupones activos
    public List<CuponDescuento> getCuponesActivos() {
        return cupones.stream()
                .filter(cupon -> cupon.isActivo())
                .toList();
    }

    // Obtener cupones por tipo de valor
    public List<CuponDescuento> getCuponesByTipo(String tipoValor) {
        return cupones.stream()
                .filter(cupon -> cupon.getTipoValor().equalsIgnoreCase(tipoValor))
                .toList();
    }

    // Obtener cupones válidos (activos y no expirados)
    public List<CuponDescuento> getCuponesValidos() {
        Date fechaActual = new Date();
        return cupones.stream()
                .filter(cupon -> cupon.isActivo() && 
                        (cupon.getFechaExpiracion() == null || cupon.getFechaExpiracion().after(fechaActual)))
                .toList();
    }

    // Crear nuevo cupón
    public CuponDescuento crear(CuponDescuento cupon) {
        cupon.setId(id.getAndIncrement());
        cupones.add(cupon);
        return cupon;
    }

    // Actualizar cupón existente
    public CuponDescuento actualizar(CuponDescuento cupon) {
        for (int i = 0; i < cupones.size(); i++) {
            CuponDescuento c = cupones.get(i);
            if (c.getId() == cupon.getId()) {
                // Actualizar solo los campos que no son nulos
                if (cupon.getCodigo() != null) {
                    c.setCodigo(cupon.getCodigo());
                }
                if (cupon.getValor() != 0) {
                    c.setValor(cupon.getValor());
                }
                if (cupon.getTipoValor() != null) {
                    c.setTipoValor(cupon.getTipoValor());
                }
                if (cupon.getFechaExpiracion() != null) {
                    c.setFechaExpiracion(cupon.getFechaExpiracion());
                }
                // Para boolean siempre actualizamos
                c.setActivo(cupon.isActivo());
                return c;
            }
        }
        return null;
    }

    // Eliminar cupón
    public boolean eliminar(int id) {
        return cupones.removeIf(cupon -> cupon.getId() == id);
    }

    // Desactivar cupón (soft delete)
    public boolean desactivar(int id) {
        CuponDescuento cupon = getById(id);
        if (cupon != null) {
            cupon.setActivo(false);
            return true;
        }
        return false;
    }

    // Activar cupón
    public boolean activar(int id) {
        CuponDescuento cupon = getById(id);
        if (cupon != null) {
            cupon.setActivo(true);
            return true;
        }
        return false;
    }
}

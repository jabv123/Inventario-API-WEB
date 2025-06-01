package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.MetodoPago;

public class MetodoPagoRepo {
    private final List<MetodoPago> metodosPago = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    public MetodoPago save(MetodoPago metodoPago) {
        metodoPago.setId(id.getAndIncrement());
        metodoPago.setDetalles(new ArrayList<>()); // Inicializar la lista de detalles
        metodoPago.setFechaCreacion(java.time.LocalDateTime.now().toString());
        metodosPago.add(metodoPago);
        return metodoPago;
    }

    public MetodoPago findById(int id) {
        return metodosPago.stream()
                .filter(metodo -> metodo.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<MetodoPago> findByClienteId(int idCliente) {
        List<MetodoPago> resultados = new ArrayList<>();
        for (MetodoPago metodo : metodosPago) {
            if (metodo.getIdCliente() == idCliente) {
                resultados.add(metodo);
            }
        }
        return resultados;
    }

    public MetodoPago update(MetodoPago metodoPago) {
        for (int i = 0; i < metodosPago.size(); i++) {
            MetodoPago mp = metodosPago.get(i);
            if (mp.getId() == metodoPago.getId()) {
                // Actualizar los campos necesarios
                if(metodoPago.getTipoPago() != null) {
                    mp.setTipoPago(metodoPago.getTipoPago());
                }
                if(metodoPago.getDetalles() != null) {
                    mp.setDetalles(metodoPago.getDetalles());
                }
                mp.setActivo(metodoPago.isActivo());
            }
        }
        return null;
    }

    // Eliminacion persistente
    public boolean deleteById(int id) {
        return metodosPago.removeIf(metodo -> metodo.getId() == id);
    }

    // Eliminacion o actualizacion logica
    public boolean updateActivoById(int id, boolean activo) {
        for (MetodoPago metodo : metodosPago) {
            if (metodo.getId() == id) {
                metodo.setActivo(activo);
                return true;
            }
        }
        return false;
    } 
}

package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.AjusteStock;

public class AjusteStockRepo {
    private final List<AjusteStock> ajustes = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    public AjusteStock add(AjusteStock ajuste) {
        ajuste.setId(id.getAndIncrement());
        ajustes.add(ajuste);
        return ajuste;
    }

    public List<AjusteStock> getAll() {
        return ajustes;
    }

    public AjusteStock getById(int id) {
        return ajustes.stream()
                .filter(ajuste -> ajuste.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<AjusteStock> getByUsuario(int idUsuario) {
        List<AjusteStock> result = new ArrayList<>();
        for (AjusteStock ajuste : ajustes) {
            if (ajuste.getIdUsuario() == idUsuario) {
                result.add(ajuste);
            }
        }
        return result;
    }

    public List<AjusteStock> getByProducto(int idProducto) {
        List<AjusteStock> result = new ArrayList<>();
        for (AjusteStock ajuste : ajustes) {
            if (ajuste.getIdProducto() == idProducto) {
                result.add(ajuste);
            }
        }
        return result;
    }

    public AjusteStock update(AjusteStock ajuste) {
        for (int i = 0; i < ajustes.size(); i++) {
            AjusteStock a = ajustes.get(i);
            if (a.getId() == ajuste.getId()) {
                if (ajuste.getIdProducto() != 0) {
                    a.setIdProducto(ajuste.getIdProducto());
                }
                if (ajuste.getCantidadAjuste() != 0) {
                    a.setCantidadAjuste(ajuste.getCantidadAjuste());
                }
                if (ajuste.getMotivo() != null) {
                    a.setMotivo(ajuste.getMotivo());
                }
                if (ajuste.getFechaAjuste() != null) {
                    a.setFechaAjuste(ajuste.getFechaAjuste());
                }
                if (ajuste.getIdUsuario() != 0) {
                    a.setIdUsuario(ajuste.getIdUsuario());
                }
                return a;
            }
        }
        return null;
    }

    public boolean delete(int id) {
        return ajustes.removeIf(ajuste -> ajuste.getId() == id);
    }
}

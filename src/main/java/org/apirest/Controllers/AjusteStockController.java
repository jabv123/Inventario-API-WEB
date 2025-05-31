package org.apirest.Controllers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;

import org.apirest.modelo.AjusteStock;
import org.apirest.service.AjusteStockService;
import org.apirest.Util.Mensaje;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class AjusteStockController {

    private final AjusteStockService ajusteStockService;

    public AjusteStockController(AjusteStockService ajusteStockService) {
        this.ajusteStockService = ajusteStockService;
    }

    public void rutasAjustesStock() {
        path("/api/ajustes-stock", () -> {
            post(this::procesarAjusteStock);
            get(this::obtenerTodosLosAjustes);
            path("/producto/{idProducto}", () -> {
                get(this::obtenerAjustesPorProducto);
            });
            path("/usuario/{idUsuario}", () -> {
                get(this::obtenerAjustesPorUsuario);
            });
            path("/{id}", () -> {
                get(this::obtenerAjustePorId);
                put(this::actualizarAjuste);
                delete(this::eliminarAjuste);
            });
        });
    }

    private void procesarAjusteStock(Context ctx) {
        // Esperamos un objeto con los datos del ajuste
        var requestBody = ctx.bodyAsClass(AjusteStock.class);
        
        AjusteStock ajusteCreado = ajusteStockService.procesarAjusteStock(
            requestBody.getIdProducto(),
            requestBody.getCantidadAjuste(),
            requestBody.getMotivo(),
            requestBody.getIdUsuario()
        );
        
        ctx.status(201).json(new Mensaje("Ajuste de stock procesado correctamente", ajusteCreado));
    }

    private void obtenerTodosLosAjustes(Context ctx) {
        List<AjusteStock> ajustes = ajusteStockService.obtenerTodosLosAjustes();
        ctx.status(200).json(new Mensaje("Lista de ajustes de stock", ajustes));
    }

    private void obtenerAjustesPorProducto(Context ctx) {
        int idProducto = Integer.parseInt(ctx.pathParam("idProducto"));
        List<AjusteStock> ajustes = ajusteStockService.obtenerAjustesPorProducto(idProducto);
        ctx.status(200).json(new Mensaje("Ajustes del producto " + idProducto, ajustes));
    }

    private void obtenerAjustesPorUsuario(Context ctx) {
        int idUsuario = Integer.parseInt(ctx.pathParam("idUsuario"));
        List<AjusteStock> ajustes = ajusteStockService.obtenerAjustesPorUsuario(idUsuario);
        
        if (ajustes != null && !ajustes.isEmpty()) {
            ctx.status(200).json(new Mensaje("Ajustes del usuario " + idUsuario, ajustes));
        } else {
            throw new NotFoundResponse("No se encontraron ajustes para el usuario " + idUsuario);
        }
    }

    private void obtenerAjustePorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        AjusteStock ajuste = ajusteStockService.obtenerAjustePorId(id);
        
        if (ajuste != null) {
            ctx.status(200).json(new Mensaje("Ajuste de stock encontrado", ajuste));
        } else {
            throw new NotFoundResponse("Ajuste de stock no encontrado");
        }
    }

    private void actualizarAjuste(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        AjusteStock ajuste = ctx.bodyAsClass(AjusteStock.class);
        ajuste.setId(id);
        
        AjusteStock ajusteActualizado = ajusteStockService.actualizarAjuste(ajuste);
        ctx.status(200).json(new Mensaje("Ajuste de stock actualizado", ajusteActualizado));
    }

    private void eliminarAjuste(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean eliminado = ajusteStockService.eliminarAjuste(id);
        
        if (eliminado) {
            ctx.status(200).json(new Mensaje("Ajuste de stock eliminado correctamente", null));
        } else {
            throw new NotFoundResponse("Ajuste de stock no encontrado");
        }
    }
}

package org.apirest.Controllers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;

import org.apirest.modelo.CuponDescuento;
import org.apirest.service.CuponDescuentoService;
import org.apirest.Util.Mensaje;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class CuponDescuentoController {

    private final CuponDescuentoService cuponDescuentoService;

    public CuponDescuentoController(CuponDescuentoService cuponDescuentoService) {
        this.cuponDescuentoService = cuponDescuentoService;
    }    
    
    public void rutasCupones() {
        path("/api/cupones", () -> {
            post(this::crearCupon);
            get(this::obtenerTodosLosCupones);
            path("/activos", () -> {
                get(this::obtenerCuponesActivos);
            });
            path("/validos", () -> {
                get(this::obtenerCuponesValidos);
            });
            path("/codigo/{codigo}", () -> {
                get(this::obtenerCuponPorCodigo);
            });
            path("/{id}", () -> {
                get(this::obtenerCuponPorId);
                put(this::actualizarCupon);
                delete(this::eliminarCupon);
                patch("/activar", this::activarCupon);
                patch("/desactivar", this::desactivarCupon);
            });
        });
    }

    private void crearCupon(Context ctx) {
        CuponDescuento cupon = ctx.bodyAsClass(CuponDescuento.class);
        CuponDescuento cuponCreado = cuponDescuentoService.crearCupon(cupon);
        ctx.status(201).json(new Mensaje("Cupón creado", cuponCreado));
    }    
    
    private void obtenerTodosLosCupones(Context ctx) {
        List<CuponDescuento> cupones = cuponDescuentoService.obtenerTodosLosCupones();
        ctx.status(200).json(new Mensaje("Lista de cupones", cupones));
    }
    
    private void obtenerCuponesActivos(Context ctx) {
        List<CuponDescuento> cupones = cuponDescuentoService.obtenerCuponesActivos();
        ctx.status(200).json(new Mensaje("Lista de cupones activos", cupones));
    }

    private void obtenerCuponesValidos(Context ctx) {
        List<CuponDescuento> cupones = cuponDescuentoService.obtenerCuponesValidos();
        ctx.status(200).json(new Mensaje("Lista de cupones válidos", cupones));
    }

    private void obtenerCuponPorCodigo(Context ctx) {
        String codigo = ctx.pathParam("codigo");
        CuponDescuento cupon = cuponDescuentoService.obtenerCuponPorCodigo(codigo);
        
        if (cupon != null) {
            ctx.status(200).json(new Mensaje("Cupón encontrado", cupon));
        } else {
            throw new NotFoundResponse("Cupón no encontrado");
        }
    }

    private void obtenerCuponPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        CuponDescuento cupon = cuponDescuentoService.obtenerCuponPorId(id);
        
        if (cupon != null) {
            ctx.status(200).json(new Mensaje("Cupón encontrado", cupon));
        } else {
            throw new NotFoundResponse("Cupón no encontrado");
        }
    }

    private void actualizarCupon(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        CuponDescuento cupon = ctx.bodyAsClass(CuponDescuento.class);
        cupon.setId(id);
        
        CuponDescuento cuponActualizado = cuponDescuentoService.actualizarCupon(cupon);
        ctx.status(200).json(new Mensaje("Cupón actualizado", cuponActualizado));
    }

    private void eliminarCupon(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean eliminado = cuponDescuentoService.eliminarCupon(id);
        
        if (eliminado) {
            ctx.status(200).json(new Mensaje("Cupón eliminado correctamente", null));
        } else {
            throw new NotFoundResponse("Cupón no encontrado");
        }
    }

    private void activarCupon(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean activado = cuponDescuentoService.activarCupon(id);
        
        if (activado) {
            ctx.status(200).json(new Mensaje("Cupón activado correctamente", null));
        } else {
            throw new NotFoundResponse("Cupón no encontrado");
        }
    }

    private void desactivarCupon(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean desactivado = cuponDescuentoService.desactivarCupon(id);
        
        if (desactivado) {
            ctx.status(200).json(new Mensaje("Cupón desactivado correctamente", null));
        } else {
            throw new NotFoundResponse("Cupón no encontrado");
        }
    }
}

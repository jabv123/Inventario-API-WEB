package org.apirest.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.apirest.modelo.Factura;
import org.apirest.service.FacturaService;
import org.apirest.Util.ResponseUtil;
import java.util.List;
import java.util.Optional;

public class FacturaController {
    private final FacturaService facturaService;
    private final ObjectMapper objectMapper;

    public FacturaController() {
        this.facturaService = FacturaService.getInstance();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();
    }

    public void crearFactura(Context ctx) {
        try {
            Factura factura = objectMapper.readValue(ctx.body(), Factura.class);
            Factura facturaCreada = facturaService.crearFactura(factura);
            ctx.status(201).json(ResponseUtil.success("Factura creada correctamente", facturaCreada));
        } catch (Exception e) {
            ctx.status(400).json(ResponseUtil.error("Error al crear factura: " + e.getMessage()));
        }
    }

    public void obtenerTodasLasFacturas(Context ctx) {
        try {
            List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
            if (facturas.isEmpty()) {
                ctx.status(404).json(ResponseUtil.error("No hay facturas registradas"));
            } else {
                ctx.status(200).json(ResponseUtil.success("Facturas listadas correctamente", facturas));
            }
        } catch (Exception e) {
            ctx.status(500).json(ResponseUtil.error("Error al obtener facturas: " + e.getMessage()));
        }
    }

    public void obtenerFacturaPorId(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            Optional<Factura> factura = facturaService.obtenerFacturaPorId(id);
            if (factura.isPresent()) {
                ctx.status(200).json(ResponseUtil.success("Factura encontrada", factura.get()));
            } else {
                ctx.status(404).json(ResponseUtil.error("Factura no encontrada"));
            }
        } catch (Exception e) {
            ctx.status(500).json(ResponseUtil.error("Error al obtener factura: " + e.getMessage()));
        }
    }

    public void obtenerFacturasPorVenta(Context ctx) {
        try {
            String idVenta = ctx.pathParam("idVenta");
            List<Factura> facturas = facturaService.obtenerFacturasPorVenta(idVenta);
            if (facturas.isEmpty()) {
                ctx.status(404).json(ResponseUtil.error("No hay facturas para la venta especificada"));
            } else {
                ctx.status(200).json(ResponseUtil.success("Facturas de la venta encontradas", facturas));
            }
        } catch (Exception e) {
            ctx.status(500).json(ResponseUtil.error("Error al obtener facturas: " + e.getMessage()));
        }
    }

    public void actualizarFactura(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            Factura facturaActualizada = objectMapper.readValue(ctx.body(), Factura.class);
            Optional<Factura> factura = facturaService.actualizarFactura(id, facturaActualizada);
            if (factura.isPresent()) {
                ctx.status(200).json(ResponseUtil.success("Factura actualizada correctamente", factura.get()));
            } else {
                ctx.status(404).json(ResponseUtil.error("Factura no encontrada"));
            }
        } catch (Exception e) {
            ctx.status(400).json(ResponseUtil.error("Error al actualizar factura: " + e.getMessage()));
        }
    }

    public void eliminarFactura(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            boolean eliminada = facturaService.eliminarFactura(id);
            if (eliminada) {
                ctx.status(200).json(ResponseUtil.success("Factura eliminada correctamente", true));
            } else {
                ctx.status(404).json(ResponseUtil.error("Factura no encontrada"));
            }
        } catch (Exception e) {
            ctx.status(500).json(ResponseUtil.error("Error al eliminar factura: " + e.getMessage()));
        }
    }
}

package org.apirest.Util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    
    public static Map<String, Object> success(String mensaje, Object objeto) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", mensaje);
        response.put("objeto", objeto);
        return response;
    }
    
    public static Map<String, Object> error(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", mensaje);
        response.put("objeto", null);
        return response;
    }
    
    public static Map<String, Object> error(String mensaje, Object objeto) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", mensaje);
        response.put("objeto", objeto);
        return response;
    }
}

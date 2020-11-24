package pe.tuna.commonsmicroservicios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.tuna.commonsmicroservicios.services.ICommonService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonController<E, S extends ICommonService<E>> {

    @Autowired
    protected S servicio;

    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        Map<String, Object> response = new HashMap<>();
        List<E> alumnos = null;
        try {
            alumnos = servicio.findAll();
        } catch (DataAccessException ex) {
            response.put("isSucces", false);
            response.put("message", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause()
                    .getMessage()));
            System.out.println("error al listar alumnos: " + ex.getMessage());
            return new ResponseEntity<Map<String, Object>>(response,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("data", alumnos);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") Long id) {

        E entity;
        Map<String, Object> response = new HashMap<>();
        try {
            entity = servicio.findById(id);
        } catch (DataAccessException ex) {
            response.put("isSuccess", false);
            response.put("message", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause()
                    .getMessage()));

            return new ResponseEntity<Map<String, Object>>(response,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (entity == null){
            response.put("isSuccess", false);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", entity);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@RequestBody E entity){
        Map<String, Object> response = new HashMap<String, Object>();
        E alumnoNew;
        try {
            alumnoNew = servicio.save(entity);
        } catch (DataAccessException ex) {
            response.put("isSuccess", false);
            response.put("message", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause()
                    .getMessage()));

            return new ResponseEntity<Map<String, Object>>(response,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("data", alumnoNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumno(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            servicio.deleteById(id);
        } catch (DataAccessException ex) {
            response.put("isSuccess", false);
            response.put("message", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause()
                    .getMessage()));

            return new ResponseEntity<Map<String, Object>>(response,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  ResponseEntity.noContent().build();
    }

}

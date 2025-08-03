package ar.edu.huergo.lcarera.ejemplo_spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.edu.huergo.lcarera.ejemplo_spring.dto.EmpleadoDto;
import ar.edu.huergo.lcarera.ejemplo_spring.entity.Empleado;
import ar.edu.huergo.lcarera.ejemplo_spring.service.EmpleadoService;

@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/empleado") //El dominio con el que acciona el controller
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping // http://localhost:8080/empleado
    public List<EmpleadoDto> getEmpleados() {
        return this.empleadoService.getEmpleados();
    }

    @GetMapping("/{id}") // http://localhost:8080/empleado/1
    public ResponseEntity<EmpleadoDto> getEmpleado(@PathVariable Long id) {
        try {
            Optional<Empleado> empleadoOpt = this.empleadoService.getEmpleado(id); //Optional es un contenedor que puede contener un valor o no, sirve para manejar los casos en los que el dato que se busca puede no existir
            if(empleadoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Empleado empleado = empleadoOpt.get();
            return ResponseEntity.ok(new EmpleadoDto(empleado.getId(), empleado.getNombre()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping //Indica que el metodo es un POST en localhost:8080/empleado
    public ResponseEntity<String> crearEmpleado(@RequestBody EmpleadoDto empleadoDto) { //@RequestBody indica que el cuerpo de la peticion es un JSON y se lo asigna a un objeto EmpleadoDto
        try {
            this.empleadoService.crearEmpleado(empleadoDto);
            return ResponseEntity.created(null).body("Empleado creado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}") //Indica que el metodo es un PUT en localhost:8080/empleado/1
    public ResponseEntity<String> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDto empleadoDto) {
        try {
            this.empleadoService.actualizarEmpleado(id, empleadoDto);
            return ResponseEntity.ok("Empleado actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}") //Indica que el metodo es un DELETE en localhost:8080/empleado/1
    public ResponseEntity<String> eliminarEmpleado(@PathVariable Long id) {
        try {
            this.empleadoService.eliminarEmpleado(id);
            return ResponseEntity.ok("Empleado eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

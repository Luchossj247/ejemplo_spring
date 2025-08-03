package ar.edu.huergo.lcarera.ejemplo_spring.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.huergo.lcarera.ejemplo_spring.dto.EmpleadoDto;


@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/empleado") //El dominio con el que acciona el controller
public class EmpleadoController {

    private final List<EmpleadoDto> empleados = new ArrayList<>(List.of(
        new EmpleadoDto(1, "Lucho"),
        new EmpleadoDto(2, "Lucho2"),
        new EmpleadoDto(3, "Lucho3"),
        new EmpleadoDto(4, "Lucho4")
    ));

    @GetMapping // http://localhost:8080/empleado
    public List<EmpleadoDto> getEmpleados() {
        return this.empleados;
    }
    

    @GetMapping("/{id}") // http://localhost:8080/1
    public EmpleadoDto getEmpleado(@PathVariable Long id) { //@PathVariable indica que el parametro se encuentra en la url con el nombre "id"
        for (EmpleadoDto empleadoDto : empleados) {
            if(empleadoDto.id() == id) {
                return empleadoDto;
            }
        }
        return null;
    }




























    

    @GetMapping("/mejor/{id}") // http://localhost:8080/mejor/1
    public ResponseEntity<EmpleadoDto> getEmpleadoCorrecto(@PathVariable Long id) { //@PathVariable indica que el parametro se encuentra en la url con el nombre "id"
        for (EmpleadoDto empleadoDto : empleados) {
            if(empleadoDto.id() == id) {
                return ResponseEntity.ok(empleadoDto);
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping //Indica que el metodo es un POST en localhost:8080/empleado
    public ResponseEntity<String> crearEmpleado(@RequestBody EmpleadoDto empleadoDto) { //@RequestBody indica que el cuerpo de la peticion es un JSON y se lo asigna a un objeto EmpleadoDto
        for (EmpleadoDto empleado : empleados) {
            if(empleado.id() == empleadoDto.id()) {
                return ResponseEntity.badRequest().body("El empleado ya existe");
            }
        }
        empleados.add(empleadoDto);
        return ResponseEntity.created(null).body("Empleado creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDto empleadoDto) {
        //Primer problema, al estar usando DTOs inmutables, no se puede modificar el nombre del empleado
        for (EmpleadoDto empleado : empleados) {
            if(empleado.id() == id) {
                empleado.nombre = empleadoDto.nombre();
            }
        }
        return ResponseEntity.notFound().body("Empleado no encontrado");
    }
    

}

package ar.edu.huergo.lcarera.ejemplo_spring.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.huergo.lcarera.ejemplo_spring.dto.EmpleadoDto;
import ar.edu.huergo.lcarera.ejemplo_spring.entity.Empleado;
import ar.edu.huergo.lcarera.ejemplo_spring.repository.EmpleadoRepository;

@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/empleado") //El dominio con el que acciona el controller
public class EmpleadoController {

    private final EmpleadoRepository empleadoRepository;
    public EmpleadoController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping // http://localhost:8080/empleado
    public List<EmpleadoDto> getEmpleados() {
        return ((List<Empleado>) this.empleadoRepository.findAll()).stream().map(empleado -> new EmpleadoDto(empleado.getId(), empleado.getNombre())).toList();
    }

    @GetMapping("/{id}") // http://localhost:8080/empleado/1
    public ResponseEntity<EmpleadoDto> getEmpleadoCorrecto(@PathVariable Long id) {
        Optional<Empleado> empleadoOpt = this.empleadoRepository.findById(id); //Optional es un contenedor que puede contener un valor o no, sirve para manejar los casos en los que el dato que se busca puede no existir
        if(empleadoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empleado empleado = empleadoOpt.get();
        return ResponseEntity.ok(new EmpleadoDto(empleado.getId(), empleado.getNombre()));
    }


    @PostMapping //Indica que el metodo es un POST en localhost:8080/empleado
    public ResponseEntity<String> crearEmpleado(@RequestBody EmpleadoDto empleadoDto) { //@RequestBody indica que el cuerpo de la peticion es un JSON y se lo asigna a un objeto EmpleadoDto
        this.empleadoRepository.save(new Empleado(empleadoDto.nombre()));
        return ResponseEntity.created(null).body("Empleado creado correctamente");
    }

    @PutMapping("/{id}") //Indica que el metodo es un PUT en localhost:8080/empleado/1
    public ResponseEntity<String> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDto empleadoDto) {
        //Primer problema, al estar usando DTOs inmutables, no se puede modificar el nombre del empleado
        //Solucion: El DTO como lo indica su nombre solo se usa para transportar datos, no se debe usar para modificarlos
        //Implementamos una entidad que se pueda modificar y que se guarde en la base de datos
        Optional<Empleado> empleadoOpt = this.empleadoRepository.findById(id);
        if(empleadoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empleado empleado = empleadoOpt.get();
        empleado.setNombre(empleadoDto.nombre());
        this.empleadoRepository.save(empleado);
        return ResponseEntity.ok("Empleado actualizado correctamente");
    }

    @DeleteMapping("/{id}") //Indica que el metodo es un DELETE en localhost:8080/empleado/1
    public ResponseEntity<String> eliminarEmpleado(@PathVariable Long id) {
        this.empleadoRepository.deleteById(id);
        return ResponseEntity.ok("Empleado eliminado correctamente");
    }

}

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

import ar.edu.huergo.lcarera.ejemplo_spring.dto.JugadorDto;


@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/empleado") //El dominio con el que acciona el controller
public class EmpleadoController {

    private final List<JugadorDto> jugadores = new ArrayList<>(List.of(
    new JugadorDto(2, "Lucho2", "Brasil", 88, "PSG", "Medio", 80, 75, 85, 78, 65, 70)
    ));

    @GetMapping // http://localhost:8080/empleado
    public List<JugadorDto> getjugadores() {
        return this.jugadores;
    }
    

    @GetMapping("/{id}") // http://localhost:8080/1
    public JugadorDto getEmpleado(@PathVariable Long id) { //@PathVariable indica que el parametro se encuentra en la url con el nombre "id"
        for (JugadorDto JugadorDto : jugadores) {
            if(JugadorDto.id() == id) {
                return JugadorDto;
            }
        }
        return null;
    }





    @GetMapping("/mejor/{id}") // http://localhost:8080/mejor/1
    public ResponseEntity<JugadorDto> getEmpleadoCorrecto(@PathVariable Long id) { //@PathVariable indica que el parametro se encuentra en la url con el nombre "id"
        for (JugadorDto JugadorDto : jugadores) {
            if(JugadorDto.id() == id) {
                return ResponseEntity.ok(JugadorDto);
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping //Indica que el metodo es un POST en localhost:8080/empleado
    public ResponseEntity<String> crearEmpleado(@RequestBody JugadorDto JugadorDto) { //@RequestBody indica que el cuerpo de la peticion es un JSON y se lo asigna a un objeto JugadorDto
        for (JugadorDto empleado : jugadores) {
            if(empleado.id() == JugadorDto.id()) {
                return ResponseEntity.badRequest().body("El empleado ya existe");
            }
        }
        jugadores.add(JugadorDto);
        return ResponseEntity.created(null).body("Empleado creado correctamente");
    }

    @PutMapping("/{id}")
public ResponseEntity<String> actualizarEmpleado(@PathVariable Long id, @RequestBody JugadorDto nuevoJugador) {
    for (int i = 0; i < jugadores.size(); i++) {
        if (jugadores.get(i).id() == id) {
            jugadores.set(i, nuevoJugador); // Reemplaza el objeto completo
            return ResponseEntity.ok("Jugador actualizado correctamente");
        }
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
}



}

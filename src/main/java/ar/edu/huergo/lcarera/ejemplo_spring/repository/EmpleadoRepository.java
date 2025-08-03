package ar.edu.huergo.lcarera.ejemplo_spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import ar.edu.huergo.lcarera.ejemplo_spring.entity.Empleado;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
//<Empleado, Long> indica que el repositorio se encarga de los empleados y que la clave primaria es Long
@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Long> {
    
}

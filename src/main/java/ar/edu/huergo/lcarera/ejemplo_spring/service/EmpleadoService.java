package ar.edu.huergo.lcarera.ejemplo_spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.huergo.lcarera.ejemplo_spring.dto.EmpleadoDto;
import ar.edu.huergo.lcarera.ejemplo_spring.entity.Empleado;
import ar.edu.huergo.lcarera.ejemplo_spring.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<EmpleadoDto> getEmpleados() {
        return ((List<Empleado>) this.empleadoRepository.findAll()).stream().map(empleado -> new EmpleadoDto(empleado.getId(), empleado.getNombre())).toList();
    }

    public Optional<Empleado> getEmpleado(Long id) {
        return this.empleadoRepository.findById(id);
    }

    public void crearEmpleado(EmpleadoDto empleadoDto) {
        this.empleadoRepository.save(new Empleado(empleadoDto.nombre()));
    }

    public void actualizarEmpleado(Long id, EmpleadoDto empleadoDto) throws NotFoundException {
        Empleado empleado = this.empleadoRepository.findById(id).orElseThrow(() -> new NotFoundException());
        empleado.setNombre(empleadoDto.nombre());
        this.empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado(Long id) {
        this.empleadoRepository.deleteById(id);
    }
}


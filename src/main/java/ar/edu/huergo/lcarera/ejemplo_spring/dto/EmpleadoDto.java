package ar.edu.huergo.lcarera.ejemplo_spring.dto;

//Los record son clases inmutables que se usan para representar datos
//Autogeneran los metodos equals, hashCode, toString, getters y setters
public record EmpleadoDto(Long id, String nombre) {
    
}

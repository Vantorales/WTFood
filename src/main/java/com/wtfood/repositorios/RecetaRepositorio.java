
package com.wtfood.repositorios;

import com.wtfood.entidades.Receta;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepositorio extends JpaRepository<Receta, String> {

    @Query("SELECT r FROM Receta r")
    public ArrayList<Receta> listarRecetas();
    
    @Query("SELECT r FROM Receta r WHERE r.id LIKE :id")
    public Receta buscarPorId(@Param("id") String id);
    
    @Query("SELECT r FROM Receta r WHERE r.usuario.nombre LIKE :nombre")
    public ArrayList<Receta> buscarRecetaPorNombreUsuario(@Param("nombre") String nombre);
    
    @Query("SELECT r FROM Receta r WHERE r.calificaciones = :calificacion")
    public ArrayList<Receta> buscarRecetaPorCalificaciones(@Param("calificacion") Integer calificacion);
    
    @Query("SELECT r FROM Receta r WHERE r.cantidadIngredientes = :cantidadIngredientes")
    public ArrayList<Receta> buscarRecetaPorCantidadIngredientes(@Param("cantidadIngredientes") Integer cantidadIngredientes);
    
    @Query("SELECT r.pasoAPaso FROM Receta r")
    public ArrayList<String> listarPasos();
    
}

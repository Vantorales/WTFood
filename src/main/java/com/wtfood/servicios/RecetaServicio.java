
package com.wtfood.servicios;

import com.wtfood.entidades.Foto;
import com.wtfood.entidades.Ingrediente;
import com.wtfood.entidades.Receta;
import com.wtfood.entidades.Usuario;
import com.wtfood.errores.ErrorServicio;
import com.wtfood.repositorios.RecetaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class RecetaServicio {
    
    @Autowired
    private RecetaRepositorio recetaRepositorio;
      
    @Transactional(propagation = Propagation.NESTED)
    public void registrarReceta(String nombre, Integer calificaciones, Integer cantidadIngredientes, List<Ingrediente> ingredientes, Usuario usuario, Foto foto) throws ErrorServicio {
        
        validar(nombre, calificaciones, cantidadIngredientes, ingredientes, usuario, foto);
        
        Receta receta = new Receta();
        
        receta.setNombre(nombre);
        receta.setCalificaciones(calificaciones);
        receta.setCantidadIngredientes(cantidadIngredientes);
        receta.setUsuario(usuario);
        receta.setFoto(foto);
        
        recetaRepositorio.save(receta);
        
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void modificarReceta(String id, String nombre, Integer calificaciones, Integer cantidadIngredientes, List<Ingrediente> ingredientes, Usuario usuario, Foto foto) throws ErrorServicio {
        
        validar(nombre, calificaciones, cantidadIngredientes, ingredientes, usuario, foto);
        
        Optional<Receta> respuesta = recetaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Receta receta = new Receta();
            receta = respuesta.get();
            
            receta.setNombre(nombre);
            receta.setCalificaciones(calificaciones);
            receta.setCantidadIngredientes(cantidadIngredientes);
            receta.setIngredientes(ingredientes);
            receta.setUsuario(usuario);
            receta.setFoto(foto);
            
            recetaRepositorio.save(receta);
        } else {
            throw new ErrorServicio("No se encontro la receta solicitada.");
        }
        
    }
    
    @Transactional(readOnly = true)
    public ArrayList<Receta>  listarRecetas() throws ErrorServicio {
        
        ArrayList<Receta> recetas = recetaRepositorio.listarRecetas();
        if (!recetas.isEmpty()) {
            return recetas;
        } else {
            throw new ErrorServicio("No se ha encontrado ninguna receta.");
        }
        
    }
    
    @Transactional(readOnly = true)
    public Receta buscarRecetaPorId(String id) throws ErrorServicio {
        
        if (id == null) {
            throw new ErrorServicio("El id no puede ser nulo");
        }
        
        if (id.isEmpty()) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }
        
        Optional<Receta> respuesta = recetaRepositorio.findById(id);
        if(respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("No se ah encontrado la receta solicitada.");
        }
        
    }
    
    private void validar(String nombre, Integer calificaciones, Integer cantidadIngredientes, List<Ingrediente> ingredientes, Usuario usuario, Foto foto) throws ErrorServicio {
        
        if (nombre == null) {
            throw new ErrorServicio("El nombre de la receta no puede ser nulo.");
        }
        
        if (nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la receta no puede estar vacio.");
        }
        
        if (calificaciones == null) {
            throw new ErrorServicio("Las calificaciones no pueden ser nulas.");
        }
        
        if (calificaciones < 0) {
            throw new ErrorServicio("Las calificaciones no pueden ser menor a 0 (cero).");
        }
        
        if (cantidadIngredientes == null) {
            throw new ErrorServicio("La cantidad de ingredientes no pueden ser nulo.");
        }
        
        if (cantidadIngredientes < 0) {
            throw new ErrorServicio("La caantidad de ingredientes no pueden ser menor a 0 (cero).");
        }
        
        if (usuario == null) {
            throw new ErrorServicio("El usuario no puede ser nulo.");
        }
        
        if (ingredientes == null) {
            throw new ErrorServicio("Los ingredientes no pueden ser nulo.");
        }
        
        if (foto == null) {
            throw new ErrorServicio("La foto no puede ser nula.");
        }
        
    }
    
}

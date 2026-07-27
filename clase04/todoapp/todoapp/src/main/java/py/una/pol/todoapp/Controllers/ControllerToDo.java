package py.una.pol.todoapp.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.una.pol.todoapp.DTO.ToDoDTO;
import py.una.pol.todoapp.Entity.ToDo;

@RestController
@RequestMapping("/todos")
public class ControllerToDo {

    List<ToDo> listaToDo = new ArrayList<ToDo>();
    int id_actual = 0;

    @GetMapping("/hola")
    public String hola(){
        return "Hola";
    }

    @GetMapping
    public List<ToDo> listarTodos(){
        return listaToDo;
    }

    @PostMapping
    public ResponseEntity<ToDo> crearToDo(@RequestBody ToDoDTO todoDto){
        ToDo nuevoToDo = new ToDo(id_actual++, todoDto.getDescripcion(), false);
        listaToDo.add(nuevoToDo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoToDo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> listarPorId(@PathVariable int id){
        for(ToDo todo : listaToDo){
            if(todo.getId() == id){
                return ResponseEntity.status(HttpStatus.OK).body(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

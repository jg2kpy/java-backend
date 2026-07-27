package py.una.pol.todoapp.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.una.pol.todoapp.DTO.ToDoDTO;
import py.una.pol.todoapp.Entity.ToDo;

import py.una.pol.todoapp.Repository.ToDoRepository;

@Service
public class ToDoService {

    //Antigua "DB"
    int id_actual = 0;
    List<ToDo> listaToDo = new ArrayList<ToDo>();

    //Nueva DB
    @Autowired
    ToDoRepository toDoRepository;

    public List<ToDo> listarTodos(){
        return toDoRepository.findAll();
    }

    public ResponseEntity<ToDo> crearToDo( ToDoDTO todoDto){

        ToDo nuevoToDo = new ToDo();
        nuevoToDo.setDescripcion(todoDto.getDescripcion());
        nuevoToDo.setCompletado(0);

        toDoRepository.save(nuevoToDo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoToDo);
    }


    // --- Recurso individual ---
    public ResponseEntity<ToDo> listarPorId(int id){
        Optional<ToDo> toDoOp = toDoRepository.findById(id);
        if (toDoOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(toDoOp.get());
    }

    public ResponseEntity<ToDo> actualizarToDo(int id, ToDoDTO todoDto){
        Optional<ToDo> toDoOp = toDoRepository.findById(id);
        if (toDoOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ToDo todo = toDoOp.get();
        todo.setDescripcion(todoDto.getDescripcion());
        return ResponseEntity.status(HttpStatus.OK).body(todo);
    }

    public ResponseEntity<Void> eliminarToDo(int id){
        Optional<ToDo> toDoOp = toDoRepository.findById(id);
        if (toDoOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ToDo todo = toDoOp.get();
        toDoRepository.delete(todo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

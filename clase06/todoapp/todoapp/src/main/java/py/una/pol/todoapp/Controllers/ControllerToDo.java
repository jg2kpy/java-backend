package py.una.pol.todoapp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.una.pol.todoapp.DTO.ToDoDTO;
import py.una.pol.todoapp.Entity.ToDo;
import py.una.pol.todoapp.Service.ToDoService;
import py.una.pol.todoapp.Entity.User;
import py.una.pol.todoapp.Repository.UserRepository;

@RestController
@RequestMapping("/todos")
public class ControllerToDo {

    @Autowired
    ToDoService toDoService;

    @Autowired
    UserRepository userRepository;

    // --- Endpoint de prueba ---

    @GetMapping("/helloWorld")
    public String hola(Authentication authentication){
        return "HelloWorld!";
    }

    @GetMapping("/helloWorldPublic")
    public String holaPublic(){
        return "HelloWorld! Public";
    }

    // --- Colección ---

    @GetMapping
    public List<ToDo> listarTodos(Authentication authentication){

        String username = authentication.getName();
        User usuario = userRepository.findByUsername(username).orElseThrow();

        return toDoService.listarTodos(usuario);
    }

    @PostMapping
    public ResponseEntity<ToDo> crearToDo(@RequestBody ToDoDTO todoDto){
        return toDoService.crearToDo(todoDto);
    }

    // --- Recurso individual ---

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> listarPorId(@PathVariable int id){
        return toDoService.listarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> actualizarToDo(@PathVariable int id, @RequestBody ToDoDTO todoDto){
        return toDoService.actualizarToDo(id, todoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarToDo(@PathVariable int id){
        return toDoService.eliminarToDo(id);
    }
}
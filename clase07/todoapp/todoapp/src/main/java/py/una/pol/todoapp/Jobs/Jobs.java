package py.una.pol.todoapp.Jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import py.una.pol.todoapp.Entity.ToDo;
import py.una.pol.todoapp.Repository.ToDoRepository;

@Component
public class Jobs {

    @Autowired
    ToDoRepository toDoRepository;

    @Scheduled(cron = "-")
    public void holaJobs(){
        System.out.println("Hola Jobs!!!");
        List<ToDo> lista = toDoRepository.findByCompletado(0);
        for(ToDo todo : lista){
            String mensaje = todo.getUsuario().getUsername() + ", no te olvides de " + todo.getDescripcion();
            System.out.println(mensaje);
        }
    }

}

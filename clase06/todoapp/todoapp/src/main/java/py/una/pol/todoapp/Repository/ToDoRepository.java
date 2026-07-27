package py.una.pol.todoapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.una.pol.todoapp.Entity.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Integer>{

}

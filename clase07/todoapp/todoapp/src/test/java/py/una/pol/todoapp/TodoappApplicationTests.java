package py.una.pol.todoapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import py.una.pol.todoapp.DTO.ToDoDTO;
import py.una.pol.todoapp.Entity.ToDo;
import py.una.pol.todoapp.Entity.User;
import py.una.pol.todoapp.Repository.ToDoRepository;
import py.una.pol.todoapp.Repository.UserRepository;
import py.una.pol.todoapp.Service.ToDoService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TodoappApplicationTests {

	@Mock
    ToDoRepository toDoRepository;

	@Mock
    UserRepository userRepository;

	@InjectMocks
	ToDoService toDoService;

	@Test
	void crearToDoTest() {
		System.out.println("------------------CREAR TODO TEST--------------------");
		//Arrange
		String descripcion = "test";


		ToDoDTO todoDto = new ToDoDTO();
		todoDto.setDescripcion(descripcion);

		String username = "usuarioTest";
		User usuario = new User();
		usuario.setUsername(username);
		ToDo nuevoToDo = new ToDo();
		nuevoToDo.setDescripcion(descripcion);
		nuevoToDo.setUsuario(usuario);
		Optional<User> userOp = Optional.of(usuario);

		when(toDoRepository.save(nuevoToDo)).thenReturn(nuevoToDo);
		when(userRepository.findByUsername(username)).thenReturn(userOp);

		//Act
		ResponseEntity<ToDo> responseEntityToDo = toDoService.crearToDo(todoDto, username);

		//Assert
		assertEquals(HttpStatus.CREATED, responseEntityToDo.getStatusCode());
		assertEquals(descripcion, responseEntityToDo.getBody().getDescripcion());
		assertEquals(username, responseEntityToDo.getBody().getUsuario().getUsername());

		System.out.println("------------------FINAL CREAR TODO TEST--------------------");
	}
}

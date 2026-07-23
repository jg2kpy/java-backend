package py.una.pol.todoapp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToDo {
    int id;
    String descripcion;
    boolean completado;
}

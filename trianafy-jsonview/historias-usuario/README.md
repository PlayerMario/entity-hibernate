## Aclaración:
La idea realmente debería ser que cada tarea, fuese una historia de usuario y no una funcionalidad, de manera que,
cuando se accediera a los detalles de la historia, dentro tuviera una lista de tareas en las que se especifiquen
las funcionalidades a implementar para poder cumplir con el objetivo de la historia. Además, habría que añadir los
código de aceptación, puntos de dificultad, tiempo empleado y observaciones, por ejemplo:

### COMO USUARIO QUIERO OBTENER UNA LISTA DE CANCIONES PARA MOSTRAR LOS DETALLES DE CADA UNA.
#### Tareas:
- (X) Método GET listado (completada)
- () Método GET una canción

#### Código de aceptación:
- 200 - Aceptado: Se devuelve la lista correctamente.
- 404 - Not found: No se ha encontrado ninguna canción.

#### Tiempo dedicado:
- 15 minutos.

#### Puntos de dificultad:
- No se encuentra dificultad en la implementación.

#### Observaciones:
(A modo de ejemplo, no es el caso)
- Necesario la creación de un DTO para mostrar una respuesta a la petición distinta a la entidad.
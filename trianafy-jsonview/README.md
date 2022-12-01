# TRIANAFY
Proyecto de desarrollo de una API-REST de música en el centro Salesianos San Pedro de Sevilla.

## Tecnología y lenguaje utilizado:
Para el desarrollo de la aplicación, se han utilizado los siguientes elementos:
- **Spring Boot** como framework.
- **Java** para el desarrollo del código que atiende las peticiones a la API.

## Entorno de desarrollo y ejecución:
Para el desarrollo del proyecto, se ha utilizado el entorno de desarrollo **IntelliJ IDEA**. Para su ejecución en dicho entorno, abrimos el proyecto y,
en la barra superior, en la parte derecha, en los primeros iconos, seleccionamos sobre **Curent File**, y luego **Edit Configuration**. Al aparecer la nueva
ventana, pulsamos, en el menú superior, en el icono **+**, en el desplegable, seleccionamos **Maven**, y en la opción **Run**, en **Command line**, escribimos
**spring-boot:run** y lo seleccionamos en el menú. Pulsamos en **Aplicar** y **Aceptar**.
Ahora solo tendremos que pulsar el icono de **Play** junto a **trianafy spring-boot:run**, y, una vez finalice la ejecución en consola, tendremos el proyecto ejecutado,
y accesible desde la dirección **http://localhost:8080/** como ruta raíz.

## Pruebas:
Para poder probar la API, tendremos dos vías principales:
- **Documentación del proyecto en Swagger**: Accederemos a través de la ruta **http://localhost:8080/swagger-trianafy-docs.html**. Aquí, podremos probar todos los endpoints
disponibles en la API, y ver ejemplos de retorno.
- **Aplicación de Postman**: Dentro del proyecto, se encuentra el archivo **Trianafy.postman_collection.json**. Este archivo, podremos importarlo en las colecciones de Postman,
en el que hay preparadas una serie de peticiones a todos los posibles métodos de la API. En las peticiones POST y PUT, cuando la seleccionemos, al pulsar en **Body**, podremos
indicar el cuerpo que se envía en la petición para crear o modificar algún recurso.

## Organización del proyecto:
En la carpeta principal nos encontramos diferentes elementos a tener en cuenta:
- **src**: Es la carpeta donde se aloja todo el código fuente utilizado en el desarrollo de la aplicación.
- **historias-usuario**: En esta carpeta nos encontramos con imágenes del dashboard que se ha utilizado para la organización del proyecto. En este caso, el proyecto se
organiza por tareas, y en cada tarea que tenga que ver con una petición, tendremos en sus detalles la historia de usuario a la que pertenece y los códigos de aceptación.
- **Trianafy.postman_collection.json**: Es una colección de Postman, que podremos importar en dicho programa, y que nos permitirá acceder a los distintos endpoints de la API para
probar todas sus funcionalidades.

## Rutas disponibles:
### Artista: ({id} es el ID del artista)
- **GET: http://localhost:8080/artist/**: Obtiene el listado completo de artistas.
- **GET: http://localhost:8080/artist/{id}**: Obtiene la información de un artista si lo encuentra.
- **POST: http://localhost:8080/artist/**: Crea un nuevo artista.
- **PUT: http://localhost:8080/artist/{id}**: Modifica un artista si lo encuentra.
- **DELETE: http://localhost:8080/artist/{id}**: Borra un artista si lo encuentra, y, si tiene canciones asociadas, estas cambian su artista por un "Indefinido".

### Canción: ({id} es el ID de la canción)
- **GET: http://localhost:8080/song/**: Obtiene el listado completo de canciones.
- **GET: http://localhost:8080/song/{id}**: Obtiene la información de una canción si la encuentra.
- **POST: http://localhost:8080/song/**: Crea una nueva canción.
- **PUT: http://localhost:8080/song/{id}**: Modifica una canción si la encuentra.
- **DELETE: http://localhost:8080/song/{id}**: Borra una canción si la encuentra, y, si existe en alguna playlist, la borra de ahí.

### Lista:
#### Playlist: ({id} es el ID de la playlist)
- **GET: http://localhost:8080/list/**: Obtiene el listado completo de playlists.
- **GET: http://localhost:8080/list/{id}**: Obtiene la información de una playlist si la encuentra.
- **POST: http://localhost:8080/list/**: Crea una nueva playlist.
- **PUT: http://localhost:8080/list/{id}**: Modifica una playlist si la encuentra.
- **DELETE: http://localhost:8080/list/{id}**: Borra una playlist si la encuentra.

#### Canción-Playlist: ({idL} es el ID de la playlist, e {idS} es el ID de la canción)
- **GET: http://localhost:8080/list/{idL}/song/**: Obtiene el listado completo de canciones de una playlist si la encuentra.
- **GET: http://localhost:8080/list/{idL}/song/{idS}**: Obtiene la información de una canción existente en una playlist, y encuentra ambos.
- **POST: http://localhost:8080/list/{idL}/song/{idS}**: Añade una canción a una playlist si encuentra ambos.
- **DELETE: http://localhost:8080/list/{idL}/song/{idS}**: Borra una canción de una playlist si encuentra ambos.
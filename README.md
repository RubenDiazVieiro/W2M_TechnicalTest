# Prueba Técnica Spring Boot - Mantenimiento CRUD de Súper Héroes
Este proyecto es una implementación en Java utilizando Spring Boot y Maven para realizar un mantenimiento CRUD de súper héroes. A continuación, se detallan los puntos cubiertos en la prueba:

# Requisitos
- Utilizar la última versión LTS de Java, Spring y de cualquier librería utilizada en el proyecto.
- Desarrollar una API que permita realizar un mantenimiento CRUD de súper héroes.

# Funcionalidades implementadas
- Consultar todos los súper héroes
- Consultar un único súper héroe por id
- Consultar todos los súper héroes que contienen, en su nombre, el valor de un parámetro
enviado en la petición. Por ejemplo, si enviamos “man” devolverá “Spiderman”, “Superman”,
“Manolito el fuerte”
- Crear un súper héroe
- Modificar un súper héroe
- Eliminar un súper héroe
- Test unitarios de como mínimo un servicio

# Puntos opcionales de mejora implementados
- Implementar una anotación personalizada que sirva para medir cuánto tarda en ejecutarse.
una petición. Se podría anotar alguno o todos los métodos de la API con esa anotación.
Funcionaría de forma similar al @Timed de Spring, pero imprimiendo la duración en un log: **Se ha creado una anotación "TrackExecutionTime" que mide el tiempo en el que tarda en ejecutarse una petición**
- Presentar la aplicación dockerizada: **Se ha creado un Dockerfile sencillo con el cual se puede crear una imagen**
```console
root@localhost:proyecto$ docker build --tag=superhero-app:latest .
root@localhost:proyecto$ docker run -p 8080:8080 superhero-app
```
- Poder cachear peticiones: **Se utilizan las anotaciones @EnableCaching, @Cacheable y @CacheEvict para implementar el cacheo de algunas peticiones**
- Documentación de la API: **He utilizado Swagger para la parte relacionada con la documentación de la API**

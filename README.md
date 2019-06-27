# TestJuniorV3
Test Junior v3 - Idealista - Backend Developer

Para iniciar el proyecto, se realizó un estudio de las Historias de Usuario adjuntadas para así convertirlas en requisitos. Estos requisitos definiran pequeños objetivos o metas que cumplir, descomponiendo el trabajo global en diferentes apartados.

## Requisitos:
 - Cada anuncio recibirá una puntuación entre 0 y 100 puntos
 - Si el anuncio no tiene fotos: -10 puntos
 - Por cada foto HD: 20 puntos, si no lo es: 10 puntos
 - Si el anuncio tiene descripción: 5 puntos
 - Si el anuncio es un piso, y tiene más de 20 palabras: 10 puntos, más de 50 palabras: 30 puntos
 - Si el anuncio es un chalet, y tiene más de 50 palabras: 20 puntos.
 - Cada aparición en descripción de las palabras {Luminoso, Nuevo, Céntrico, Reformado, Ático}: 5 puntos
 - Anuncio completo: 40 puntos
   - Descripción (excepto Garajes)
   - Una foto mínimo
   - Datos tipología (pisos: tamaño vivienda, chalets: tamaño vivienda y de jardín, garaje: ninguno)
 - Los usuarios no deben ver anuncios irrelevantes (puntuación menor a 40 puntos) y ordenados de mejor a peor
 - El encargado de calidad debe ver los anuncios irrelevantes y desde cuando lo son

## Tecnologías

Tras esta descomposición se observó la información a tratar, que seguía la siguiente estructura:

```sh
[
  {
    "id": 1,
    "description": "Este piso es una ganga, compra, compra, COMPRA!!!!!",
    "typology": "CHALET",
    "houseSize": 300,
    "pictures": []
    },
(...)

[
  {
    "id": 1,
    "url": "http://www.idealista.com/pictures/1",
    "quality": "SD"
  },
(...)
```

Como se puede observar, esta información sigue la estructura de [JSON][json]. Sabiendo entonces el formato que seguirá el intercambio de información, buscaremos una librería, dentro de el lenguaje seleccionado, la cual soporte la conversión entre json y objetos.

Puesto que en la oferta de empleo uno de los requisitos era experiencia en [Java][java] en este, decidí utilizarlo.

Buscando la mejor librería para tratar el formato JSON en Java encontraremos [Gson][gson] de Google. Esta será la que utilizaremos en el proyecto.

Como podemos ver en la [página][gitgson] de la librería, su inclusión en el proyecto a través de [Gradle][gradle] será tan sencillo como introducir una línea en el archivo build.gradle. Por lo tanto, utilizaremos la herramienta Gradle en el proyecto.

Puesto que es el IDE en el cual tengo más experiencia, y dispone de la construcción de proyectos a través Gradle de forma nativa, utilizaremos [Eclipse][eclipse] para desarrollar el proyecto.

## Modelo de datos

Ahora empezaremos a estudiar la información a almacenar. Esta serán Anuncios y Fotos. A mayores de los campos que se muestran en la información a tratar introduciremos 2 campos nuevos en los Anuncios. Estos campos serán la valoración y la fecha de la valoración. Con el objetivo de que el asegurador de calidad pueda ver desde cuando un anuncio tiene una valoración en concreto, y la propia valoración. Estos campos también se utilizarán para poder ordenar de mejor a peor los anuncios, y mostrarle los relevantes al usuario.

También podemos observar que typology dentro de los anuncios, y quality dentro de las fotos, tendrán un valor dentro de un rango limitado predeterminado. Por lo tanto crearemos enumerados de Java para ambos, facilitando así enormemente su uso.
 ### Typology
 
| Valor posible |
| ------ |
| CHALET |
| FLAT |
| GARAGE |
| NONE |

 ### Quality
 
| Valor posible |
| ------ |
| SD |
| HD |
| NONE |

Las tablas de anuncio (ad) y de foto (picture) quedarán como podemos ver a continuación.

### Ad

| Nombre | Tipo |
| ------ | ------ |
| id | int |
| description | String |
| typology | Typology |
| houseSize | int |
| gardenSize | int |
| pictures | java.util.List\<Picture\> |
| rating | int |
| ratingDate | java.util.Date |

### Picture

| Nombre | Tipo |
| ------ | ------ |
| id | int |
| url | String |
| quality | Quality |

Puesto que los JSON que observamos, en el caso de los anuncios, tendrá tan solo el identificador de las fotos y no el objeto completo, crearemos otra clase para almacenar la información recibida y posteriormente almacenada de forma automática con Gson. Esta clase será la siguiente:

### AdVO

| Nombre | Tipo |
| ------ | ------ |
| id | int |
| description | String |
| typology | Typology |
| houseSize | int |
| gardenSize | int |
| pictures | java.util.List\<Integer\> |
| rating | int |
| ratingDate | java.util.Date |

Como comentábamos, tan solo almacenará los identificadores de las fotos, no el objeto completo. El nombre de la clase vendrá de Ad + [VO][vo] (Value Object). El cual identifica que es un objeto simplemente destinado al intercambio de información, encargado de almacenar valores y no referencias.

## Paquetes

Para estructurar el proyecto se creará el paquete com.idealista.testJuniorV3 y desde aquí seguiremos una estructura [MVC][mvc] (Modelo, Vista y Controlador). Dentro de cada uno de estos subpaquetes crearemos la parte correspondiente del sistema.

### Model
La estructura de datos del sistema, la cual se acaba de especificar en las tablas anteriores.

### Controller
La lógica de negocio. En la cual se creará una clase encargada de iniciar y ejecutar el sistema de valoraciones (RatingSystem). Se creará una fachada (Facade) que siga el patrón de diseño [Facade][facade] para la centralización del flujo del programa, conectando los componentes del sistema mediante esta "centralita". Esta fachada será mucho más útil a medida que el proyecto crezca, controlando el flujo del mismo de forma ordenada, evitando, por ejemplo que varios sistemas, en el caso de que se creasen nuevos, necesitasen instanciar de nuevo clases comunes.

Habrá dos clases más en este paquete, las cuales serán [DAOs][dao]. Estas serán las encargadas de las conexiones con los almacenes de datos de anuncios (AdsDAO) y fotos (PicturesDAO). Estas clases serán muy útiles para poder encapsular todo el funcionamiento de el almacenamiento de datos, pudiendo ser sustituidos de forma independiente uno de ellos por una conexión a una BD de MySql sin que el resto del sistema tenga que modificarse lo más mínimo. Desde fuera de los DAOs lo único que se hará será pedirles datos y administrarles datos para que los actualicen, guarden o borren.

### View
Este paquete estará destinado a las vistas y GUIs que existan en el sistema. En este caso se pedía explícitamente que no se implementase interfaz gráfica. Por lo tanto, en este caso, le paquete tan solo tendrá una clase (Printer), la cual se encargará de imprimir por pantalla los resultados que se piden en el proyecto.

### Utils
Aunque no se especifique dentro del modelo MVC este 4º paquete, es bastante útil (valga la redundancia), tener un paquete común en el cual almacenar funciones las cuales se utilizarán a todos los niveles, en todos los paquetes. Estas funciones pueden ser leer/escribir de/en ficheros (FileUtils), comprobaciones en colecciones de elementos (CollectionUtils), o valoraciones de anuncios (RatingUtils).



## Puntos fuertes
Mediante la propia línea de comandos, a parte de los datos almacenados, se podrá indicar la lectura de un archivo de anuncios, o un archivo de anuncios y uno de fotos, desde los cuales poder cargar en el sistema los datos dentro de los mismos. Permitiendo así que cualquier persona que ejecuta el código, pueda incluir nueva información facilmente.

El sistema está completamente creado y comentado en inglés para así facilitar que pueda ser manipulado por cualquier otra persona en el futuro.

El sistema está creado siguiendo una política de código limpio y claro, para mejorar la rápida compresión visual de su funcionamiento.

La fecha de valoración no se actualiza hasta que se introduce un nuevo anuncio, o el resultado de la valoración da un resultado diferente tras un reinicio del sistema o actualización del anuncio. De este modo asguramos que el encargado de calidad sepa la fecha correcta de la valoración actual del anuncio.

## Puntos a mejorar
Puesto que el sistema que se planteaba era bastante sencillo, y no se valoraría persistencia de datos, se utilizan ficheros para la lectura y escritura de datos. Este sistema, en un proyecto real y de mayor embergadura debería implementarse en una BD.

La interfaz en este caso simplemente es la impresión por terminal del resultado de las valoraciones. En un proyecto real, una interfaz gráfica bien implementada, podría facilitar la inclusión de nuevas fuentes de datos. Del mismo modo, la revisión de resultados sería presentada de una forma más intuitiva y visual.

[json]: <https://es.wikipedia.org/wiki/JSON>
[java]: <https://www.java.com/es/download/>
[gson]: <https://github.com/google/gson>
[gitgson]: <https://github.com/google/gson>
[gradle]: <https://gradle.org/>
[eclipse]: <https://www.eclipse.org/>
[vo]: <https://stackoverflow.com/questions/1612334/difference-between-dto-vo-pojo-javabeans>
[dao]: <https://en.wikipedia.org/wiki/Data_access_object>
[mvc]: <https://si.ua.es/es/documentacion/asp-net-mvc-3/1-dia/modelo-vista-controlador-mvc.html>
[facade]: <https://es.wikipedia.org/wiki/Facade_(patr%C3%B3n_de_dise%C3%B1o)>

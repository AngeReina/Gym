Instrucciones para correr el proyecto:

Hola, en este caso voy a especificar las tecnologías que usé y el paso a pasó de como hice cada una de las cosas, voy a empezar capa por capa ya que asi se debe ejecutar para que no ocurra
ningún problema.

1. Base de datos:
	*Software necesario: PostgreSQL
	En este caso lo primero es descargar la última versión de PostgreSQL (si ya está instalada omitan esta parte)
	1. Normalmente se descarga de aquí: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads, lo importante es tener el pgAdmin y que cuando se pida poner la contraseña
	   la tengan en un lugar seguro, ya que se necesita más adelante
	2. Ya cuando puedan acceder al pgAdmin, abren las pestañas de Servers > PostgreSQL 18 > Databases, aquí click derecho encima de Databases y en la opción create > Database.
	3. Solo se cambia el nombre y tiene que ser estrictamente 'gym'
	4. Ya creada la base de datos, click derecho encima y Query Tool (o pueden hacer Alt+Shift+Q)
	5. En esta ventana deben copiar lo que dice en CreateObjects.sql y copiar lo que dice en InsertData.sql, es importante que sea en ese orden ya que uno depende de otro. Lo pegan y le dan al botón de correr (o Alt+F5)
	6. Si ven un mensaje de ejecución exitoso, han acabado
	7. Opcionalmente de verlo necesario se puede poblar aun más la base de datos, pero esto se puede hacer más fácil en la última capa o proyecto

2. Backend | Lógica de negocio | Intermediario 
	*Software necesario: IntelliJ IDEa - (Eclipse*)
	En este caso nuevamente descargar IntelliJ, en caso de no tener una licencia o cuenta institucional se puede descargar la community edition
	1. Ya instalado y viendo la ventana de proyectos, van a open y dentro del zip es la carpeta que dice Gym
	2. Abajo podrán ver que empieza a indexar, cargar o descargar dependencias, mientras eso ocurre no se puede correr nada, pero significa que todo va bien
	3. Ahora antes de correrlo, en la parte superior, aproximadamente en la mitad (un poco más hacia la derecha) habrá una especificación de la clase main (GymApplication), un botón de correr, otro de debug y posteriormente hay 3 puntos, los clickean y dan en 'edit'
	4. Aquí van a editar la variable de entorno y van a poner la contraseña de la base de datos del primer paso, o en caso de que quieran obviar las variables de entorno O *esten usando ECLIPSE*, en el application.properties (src > main > resources > application.properties) en la linea 4 cambian '${PASSWORD}' por la contraseña en bruto.
IMPORTANTE:
La conexión a PostgreSQL debe usar el puerto 5432 (puerto por defecto de PostgreSQL).

Ejemplo correcto en `application.properties`:

spring.datasource.url=jdbc:postgresql://localhost:5432/gym
	5. Antes de correr es necesario que el puerto 8080 esté libre, en caso de que no, agreguen la siguiente linea al inicio del application.properties: 'server.port=8081' u otro puerto
	6. Correr y verificar que la ultima linea en la consola sea: 'INFO 16576 --- [gym] [  restartedMain] c.m.s.GymApplication          : Started GymApplication in 4.538 seconds (process running for 8.832)'

3. FrontEnd | Usuario | UX/UI
	*Software necesario: Visual Studio Code
	En este caso se necesita la extensión LiveServer (logo morado y autor Ritwick Dey)
	1. Abrir la carpeta de 'gym.web'
	2. En la parte inferior derecha verán un botón que dice 'Go Live'
	3. Esto abre automáticamente la página web.
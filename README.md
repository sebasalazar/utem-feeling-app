
# Aplicación de Ejemplo

Esta aplicación es un servicio REST que evalúa una clase impartida en una fecha, busca presentar el funcionamiento de un servicio REST, esta implementación de referencia está programada en Java usando el framework Spring Boot.
La recomendación es desarrollar en un sistema operativo Linux, mi recomendación es utilizar alguna distribución basada en Ubuntu LTS, recomiendo usar [Kubuntu 22.04 LTS](https://cdimage.ubuntu.com/kubuntu/releases/22.04.3/release/kubuntu-22.04.3-desktop-amd64.iso).
En caso de utilizar Mac, se conseja instalar [Homebrew](https://brew.sh/)

## Requisitos Previos
 - **Java JDK 17** instalado en tu sistema.
   - En Windows se aconseja obtener desde: https://learn.microsoft.com/en-us/java/openjdk/download#openjdk-17
   - En Ubuntu: **sudo apt-get install openjdk-17-jdk**
   - En Mac: **brew install openjdk@17**
   - En general podrá encontrar para casi todas los sistemas desde acá: https://adoptium.net/es/temurin/releases/
 - **Maven**: https://maven.apache.org/download.cgi es importante notar que para usar maven es necesario tener java dentro de las rutas del sistema.
 - **Git**  Asegúrate de tener git instalado en tu sistema.
   - En Windows: https://git-scm.com/download/win
   - En Ubuntu: **sudo apt-get install git**
   - En Mac: **brew install git**
 - **El código fuente de la aplicación**: https://github.com/sebasalazar/utem-feeling-app
 - **PostgreSQL** necesitará acceso a una base de datos PostgreSQL. Para probar pueden usar una instancia en la nube, por ejemplo el plan tiny turtle de https://www.elephantsql.com/plans.html, inscribanse con el correo de la universidad.

## Pasos

 - **Descargar e Instalar Java JDK:**
Asegúrate de tener Java JDK instalado en tu sistema. Revisa el adecuado en los enlaces de los requisitos previos.

 - **Descarga el proyecto:**
Es necesario clonar el proyecto, abre una terminal en tu sistema operativo, sitúate en la carpeta que contendrá el proyecto y ejecuta:
**`git clone https://github.com/sebasalazar/utem-feeling-app.git`**
Este proceso sólo se realiza la primera vez.

 - **Configura la base de datos**
Es necesario configurar una base de datos PostgreSQL, dentro de la carpeta db encontrarás dos scripts:
   - 01-model.sql Con la definición de las tablas.
   - 02-data.sql Con los datos iniciales.
Recuerda modificar tu archivo app/src/main/resources/application.properties con los cambios de configuración, también es posible usar variables de entorno.

 - **Compilar el Proyecto:**
Navega a la carpeta que contiene el código fuente de la aplicación.
`cd app/`
Luego, utiliza Maven compilar el proyecto.
`mvn clean install`
Esto compilará tu proyecto y generará un archivo JAR ejecutable en la carpeta "target" del proyecto.
`app/target/feeling.war`

- **Ejecutar la Aplicación:**
Una vez que el proyecto se haya compilado con éxito, puedes ejecutar la aplicación Spring Boot utilizando el siguiente comando:
**`java -jar app/target/feeling.war`**

- **Acceder a la Aplicación:**

Una vez que la aplicación Spring Boot se haya iniciado correctamente, puedes acceder a ella en tu navegador web utilizando la URL predeterminada: http://localhost:8080/feeling. La documentación del proyecto estará disponible en http://localhost:8080/feeling/swagger-ui/index.html

- **Detener la Aplicación:**

Para detener la aplicación, puedes presionar **Ctrl + C** en la ventana de la terminal donde se está ejecutando la aplicación. Esto detendrá la ejecución de la aplicación Spring Boot.

# Sistema de Validación de Seguros
### Arquitectura de microservicios en Java (Spring Boot) para consultar catálogos de seguros y validar de manera automatizada si un cliente cumple con los requisitos de edad y género, implementando comunicación REST, pruebas unitarias y despliegue contenerizado con Docker.

## Arquitectura y Lógica de Implementación
### El flujo del sistema se divide en dos microservicios comunicados a través de una red interna:

1. **Microservicio de Consulta (mso-es-consulta-seguros-v1):** Actúa como proveedor de información. Expone un endpoint `GET /seguros/{id}` que retorna en formato JSON los datos del seguro, incluyendo sus reglas de negocio (edad mínima, edad máxima y géneros permitidos).

2. **Microservicio de Validación (mso-ts-validaciones-v1):** Actúa como el motor de reglas de negocio.
+ **Recepción de Datos:** Expone un endpoint `POST /validaciones` que recibe el ID del seguro deseado y los datos personales del cliente (incluyendo fecha de nacimiento).
+ **Consumo de API Externa:** Utiliza `RestTemplate` para consultar al microservicio de Consulta y obtener los requisitos del seguro.
+ **Lógica de Validación:** Calcula la edad exacta del cliente partiendo de su fecha de nacimiento y la evalúa contra las reglas obtenidas. Si el seguro no existe devuelve HTTP 404; si el cliente es rechazado devuelve HTTP 401; si el cliente es apto devuelve HTTP 201.

3. **Infraestructura y Redes (Docker):** Los servicios se comunican de forma directa mediante una red privada de Docker (`red-seguros`), resolviendo la conexión por nombre de contenedor (`ms-seguros`) y evadiendo bloqueos del firewall del sistema operativo anfitrión.

## Requisitos del Sistema
1. Java Development Kit 17
2. Apache Maven
3. Docker Desktop

## Compilación y Ejecución
### Desde la terminal, genera los ejecutables, construye las imágenes y levanta los contenedores en su red privada:
```bash

# 1. Compilar proyectos (Ejecutar dentro de la carpeta de cada microservicio)
mvn clean package -DskipTests

# 2. Construir imágenes de Docker (Ejecutar dentro de la carpeta de cada microservicio)
docker build -t api-seguros .
docker build -t api-validaciones .

# 3. Crear red privada de Docker
docker network create red-seguros

# 4. Levantar contenedores y exponer puertos
docker run -d -p 8080:8080 --network red-seguros --name ms-seguros api-seguros
docker run -d -p 8081:8081 --network red-seguros --name ms-validaciones api-validaciones


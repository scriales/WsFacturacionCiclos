## Instalación y configuración de Wildfly v10.1.0.Final

Descargar la versión 10.1.0.Final de la siguiente dirección: http://wildfly.org/downloads/

Descomprimir el archivo descargado en un directorio de instalación, llamaremos a este directorio de descompresión:
```sh
WILDFLY_HOME
```

Iniciar el servidor de aplicaciones ejecutando el siguiente comando:
```sh
WILDFLY_HOME\bin\standalone.bat
```

Comprobar que el servidor de aplicaciones se ha iniciado correctamente, ingresar a:
```sh
http://127.0.0.1:8080
```

Adicionar un usuario administrador ejecutando:
```sh
WILDFLY_HOME\bin\add-user.bat
```
proporcionar los parámetros solicitados.

Ingresar a la consola de administración:
```sh
http://127.0.0.1:9090
```
ingresar los parámetros anteriormente creados.

## Despliegue del proyecto de servicio web de ciclos

Abrir el proyecto con Netbeans v8 + y construir el mismo hasta obtener el archivo .war

Ingresar a la consola de administración y realizar el despliegue del archivo .war


## Configuración de los certificados

Para esta implementación el certificado con la llave privada deberá estar registrado en:

```sh
ROOT
```

y los certificados con las llaves públicas en:
```sh
MY-WINDOWS
```

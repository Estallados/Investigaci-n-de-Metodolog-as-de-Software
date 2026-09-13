# MetodologiaEspiral - Backend (Spring Boot)

Backend simple con **login sin JWT** (validacion directa contra la base de datos, sesion sin token). Usa Java 17 + Spring Boot 3 + Spring Data JPA.

## Como correrlo

```bash
mvn spring-boot:run
```

Por defecto usa **H2 en memoria**, asi que no necesitas instalar nada para probarlo. La consola de H2 esta en `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:metodologiaespiral`, user: `sa`, password: vacio).

Si prefieres MySQL, en `src/main/resources/application.properties` comenta el bloque de H2 y descomenta el bloque de MySQL.

## Endpoints

### Registrar usuario
```
POST /api/auth/registro
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

Respuesta exitosa (200):
```json
{ "success": true, "message": "Login exitoso", "username": "admin" }
```

Respuesta con credenciales invalidas (401):
```json
{ "success": false, "message": "Usuario o contrasena incorrectos", "username": null }
```

## Notas

- No hay JWT ni tokens: el login solo valida usuario/contrasena contra la BD y responde exito o fallo. Si mas adelante necesitas mantener la sesion del usuario en el front, puedes guardar la respuesta en el estado del cliente (localStorage/sessionStorage) o pedirme que agregue sesiones con cookies.
- El password se guarda hasheado con BCrypt, nunca en texto plano.
- CORS esta abierto (`allowedOriginPatterns("*")`) para que tu front en Angular/JSF/Netlify pueda consumir la API sin problemas. Restringelo al dominio real antes de entregar el proyecto si quieres mas prolijidad.
- Como no hay usuarios precargados, primero registra uno con `/api/auth/registro` y luego haz login con esas credenciales.

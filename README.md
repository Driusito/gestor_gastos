# 💸 Gestor de Gastos

Aplicación web para gestionar gastos personales, desarrollada con **Spring Boot (backend)** y **HTML + JavaScript (frontend)**.  
Permite registrar, editar, eliminar y visualizar gastos, además de importar/exportar datos en **Excel** y **CSV**.

---

## 🚀 Características
- 📊 Visualización de **total de gastos** y **distribución por categorías**.
- 📝 CRUD completo de gastos (crear, editar, eliminar).
- 🔎 Filtros por categoría y rango de fechas.
- 📥 Importación desde archivos **Excel** y **CSV**.
- 📤 Exportación a **Excel** y **CSV**.
- 🎨 Gráficas dinámicas con **Chart.js**.

---


## ⚙️ Requisitos
- [Java 17+](https://adoptium.net/)  
- [Maven](https://maven.apache.org/)  

---

## ▶️ Ejecución
1. Clona este repositorio:
   ```bash
   git clone https://github.com/driusito/gestor-gastos.git
   cd gestor-gastos

2. Empaqueta el proyecto con Maven
   ```bash
   mvn clean package

3. Ejecuta el JAR generado
  ```bash
   java -jar target/gestor-gastos-0.0.1-SNAPSHOT.jar

```
4. Abre el sitio web del frontend:
  localhost:8080

## 📦 Importar y Exportar
Exportar: Botones disponibles en el frontend (Exportar Excel / Exportar CSV).
Importar: Subir archivo desde el formulario (Importar Excel / Importar CSV).

## 📷 Capturas de pantalla
### Lista de gastos
 <img width="1901" height="716" alt="1" src="https://github.com/user-attachments/assets/dbab9e3c-29b4-46df-b8b2-40ba8b5652be" />

### Nuevo gasto
<img width="1245" height="404" alt="2" src="https://github.com/user-attachments/assets/e4cd0889-5bb1-4e12-b1c9-39b0a2f1bd94" />



## 🛠️ Tecnologías usadas
Backend: Spring Boot, Spring Data JPA, Maven
Frontend: HTML5, CSS3, JavaScript, Chart.js
Base de datos: H2 (memoria)

## 🤝 Contribución
¡Las contribuciones son bienvenidas! 🎉.

Haz un fork 🍴.

Crea una rama (feature/nueva-funcionalidad).

Haz un commit con tus cambios.

Envía un Pull Request.

## 📜 Licencia
Este proyecto está bajo la licencia MIT – ¡siéntete libre de usarlo y mejorarlo!

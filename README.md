1. Las dependencias que se usaron, fueron:

<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>com.oracle.database.jdbc</groupId>
			<artifactId>ojdbc11</artifactId>
			<scope>runtime</scope>
		</dependency>

  2. # Guía de Ejecución de la Aplicación

## Prerequisitos

- **Java JDK 17** (o la versión requerida por tu aplicación)
- **Maven** (asegúrate de que está instalado y configurado)
- **Spring Tool Suite (STS)** o **Eclipse** (opcional, pero recomendado para facilitar el desarrollo)

## Configura las propiedades de la base de datos:

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

## Prueba los Endpoints de la API:
POST localhost:8080/api/autores
{
    "authorID": 3,
    "name": "Lucas"
}

POST localhost:8080/api/libros
{
    "bookID": 3,
    "title": "Lapiz",
    "authorID": 3
}
PUT localhost:8080/api/libros/2
{
    "title": "El niño",
    "authorID": 2
}
PUT localhost:8080/api/autores/1
{
    "AuthorID": 1,
    "name": "Gabriel García Márquez"
}
DELETE localhost:8080/api/libros/3
DELETE localhost:8080/api/autores/3

## Query que se uso para la base de datos en oracle

-- Crear tablas
CREATE TABLE Authors (
    AuthorID NUMBER PRIMARY KEY,
    Name VARCHAR2(100) NOT NULL
);

CREATE TABLE Books (
    BookID NUMBER PRIMARY KEY,
    Title VARCHAR2(100) NOT NULL,
    AuthorID NUMBER REFERENCES Authors(AuthorID)
);

-- Procedimiento Almacenado

create or replace NONEDITIONABLE PROCEDURE AddAuthor (
    p_AuthorID IN NUMBER,
    p_Name IN VARCHAR2
) AS
BEGIN
    INSERT INTO Authors (AuthorID, Name) VALUES (p_AuthorID, p_Name);
    COMMIT;
END;

create or replace NONEDITIONABLE PROCEDURE UpdateBook(
    p_BookID IN NUMBER,
    p_Title IN VARCHAR2,
    p_AuthorID IN NUMBER
) AS
BEGIN
    UPDATE Books
    SET Title = p_Title,
        AuthorID = p_AuthorID
    WHERE BookID = p_BookID;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'No se encontró el libro con BookID: ' || p_BookID);
    END IF;
END;

create or replace NONEDITIONABLE PROCEDURE DeleteAuthor (
    p_AuthorID IN NUMBER
) AS
BEGIN
    DELETE FROM Authors
    WHERE AuthorID = p_AuthorID;

    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Author with ID ' || p_AuthorID || ' does not exist.');
END;

create or replace NONEDITIONABLE PROCEDURE AddBook (
    p_BookID IN NUMBER,
    p_Title IN VARCHAR2,
    p_AuthorID IN NUMBER
) AS
BEGIN
    INSERT INTO Books (BookID, Title, AuthorID) VALUES (p_BookID, p_Title, p_AuthorID);
    COMMIT;
END;

create or replace NONEDITIONABLE PROCEDURE UPDATEAUTHOR(
    p_AuthorID IN NUMBER,
    p_Name IN VARCHAR2
) AS
BEGIN
    UPDATE Authors
    SET Name = p_Name
    WHERE AuthorID = p_AuthorID;

    -- Puedes incluir excepciones para manejar errores
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('No se encontró el autor con ID: ' || p_AuthorID);
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error inesperado: ' || SQLERRM);
END;

create or replace NONEDITIONABLE PROCEDURE GET_BOOK (
    p_book_id IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
    SELECT * FROM Books WHERE BookID = p_book_id;
END;

create or replace NONEDITIONABLE PROCEDURE GET_AUTHOR (
    p_AuthorID IN NUMBER,
    p_Result OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_Result FOR
        SELECT * FROM Authors WHERE AuthorID = p_AuthorID;
END GET_AUTHOR;

create or replace NONEDITIONABLE PROCEDURE DeleteBook (
    p_BookID IN NUMBER
) AS
BEGIN
    DELETE FROM Books
    WHERE BookID = p_BookID;

    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20002, 'Book with ID ' || p_BookID || ' does not exist.');
END;

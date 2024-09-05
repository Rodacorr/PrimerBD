package Practico3;

/*Ejercicio 1
	Para cada una de las siguientes afirmaciones sobre aplicaciones en arquitecturas de 1 capa,
	indique si es correcta o incorrecta de acuerdo a los conceptos vistos en el teórico. Fundamente
	todas sus respuestas.
	
	a) Si se decide cambiar el mecanismo de persistencia utilizado (por ejemplo, de archivos de texto
	a bases de datos), no es necesario recompilar código fuente en la interfaz de usuario.
	
		Incorrecto, mcuhos de las aplicaciones acceden a la base de datos desde la interfaz del usuario, por lo tanto, 
		es necesario recompilar el codigo fuente para que acepte los cambios.
	
	b) La definición de una clase que encapsula el acceso a la base de datos asegura que la
	arquitectura de la aplicación deja de estar en 1 capa y pasa a estar en 2 capas.
	
		Incorrecto, el encapsular el acceso a la base de datos, no asegura que la base de datos pase de 1 capa a 2 capas. 
		Puede ser una aplicacion standalone, que por beunas practicas uno encapsule el acceso a la base de datos para que la misma sea mas facil de mantener.
		La conexion no la maneja la nueva clase, eso lo sigue haciendo el componente anterior.
	
	c) Si la aplicación es standalone y cada requerimiento se resuelve en una sola consulta,
	entonces es posible migrarla a cliente-servidor sin necesitar modificar código fuente.
	
		Incorrecto, porque al migrar a cliente-servidor, pasa de ser de 1 capa a una de 2 capas.
	
	d) El manejo de transacciones sobre la base de datos se reserva exclusivamente para
	aplicaciones en arquitecturas de 2 y 3 capas.
	
		Incorrecto, se puede realizar en una aplicacion de una capa, de arquitectura distribuida, solo se debe de cumplir con las propiedades A.S.I.C
	
	e) Las aplicaciones que priorizan el comportamiento a nivel de la aplicación permiten un mejor
	aprovechamiento de los recursos del DBMS en términos de optimización de las consultas.
	
		Incorrecto, 
	
	f) Una reducción en la cantidad de consultas que ejecuta la aplicación implica un aumento en la
	cantidad de causas que pueden provocar excepciones de SQL.
	
		Correcto, delego a la BD, para infromale a la aplicacion lo hago a tarves de las SLQ exceptions.
*/

public class Main {

}

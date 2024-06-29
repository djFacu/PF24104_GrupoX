
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

   // Definimos los parámetros de conexión
	// Estos atributos (CONTROLADOR, URL, USER, PASS) se definen como static final 
	// porque son constantes y no cambian.
	// Además que estamos encapsulando estos atributos.
    private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/conexion_usuario";
    private static final String USER = "root";
    private static final String PASS = "";

    // Cargamos el controlador JDBC 
    // Este bloque se ejecuta "una vez" cuando la clase se carga en memoria. 
    // Se utiliza para cargar el controlador JDBC.
    static {
        try {
            Class.forName(CONTROLADOR);
            System.out.println("Driver cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver JDBC");
            e.printStackTrace();
        }
    }

    // Método para establecer la conexión
    // Este método establece la conexión con la base de datos y maneja posibles excepciones.
    // Si hay una excepción, se imprime un mensaje de error y se devuelve null.
    public Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión establecida");
        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión con la base de datos");
            e.printStackTrace();
        }
        return conexion;
    }
}

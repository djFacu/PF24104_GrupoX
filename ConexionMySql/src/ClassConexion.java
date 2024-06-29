
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassConexion {

	// Método main: Muestra un menú de opciones y llama a los métodos correspondientes 
	// insertarUsuario, actualizarUsuario, eliminarUsuario, listarUsuarios
	// según la opción seleccionada por el usuario.
    public static void main(String[] args) {
       
    	// Se crea una instancia de la clase Conexion para utilizar su método conectar, 
    	// que establece la conexión con la base de datos.
    	// Esto permite modularizar el código de conexión a la base de datos y 
    	// reutilizarlo en diferentes partes del programa.
    	Conexion conexion = new Conexion();

    	// La variable Connection va a almacena la conexión activa a la base de datos.
        Connection cn = null;
        
        Scanner scan = new Scanner(System.in);

        try {
            // Establecer conexión
            cn = conexion.conectar();

            // Menú de opciones
            int opcion;
            do {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Insertar un nuevo usuario");
                System.out.println("2. Actualizar un usuario");
                System.out.println("3. Eliminar un usuario");
                System.out.println("4. Listar todos los usuarios");
                System.out.println("5. Salir");
                opcion = scan.nextInt();

                switch (opcion) {
                    case 1:
                        insertarUsuario(cn, scan);
                        break;
                    case 2:
                        actualizarUsuario(cn, scan);
                        break;
                    case 3:
                        eliminarUsuario(cn, scan);
                        break;
                    case 4:
                        List<Usuario> usuarios = listarUsuarios(cn);
                        usuarios.forEach(System.out::println);
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } while (opcion != 5);

        } finally {
            // Cerrar recursos en el orden inverso de su apertura
            try {
                if (cn != null) cn.close();
                scan.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertarUsuario(Connection cn, Scanner scan) {
        System.out.println("Ingrese su nombre: ");
        String nombre = scan.next();
        System.out.println("Ingrese su clave: ");
        String password = scan.next();

        String insertQuery = "INSERT INTO usuario (name, clave) VALUES (?, ?)";

        try (PreparedStatement pstm = cn.prepareStatement(insertQuery)) 
        {
           pstm.setString(2, password);
           pstm.setString(1, nombre);

            pstm.executeUpdate();

            System.out.println("Usuario insertado exitosamente.");

        } catch (SQLException e) {

            System.err.println("Error al insertar el usuario");
            e.printStackTrace();
        }
    }

    private static void actualizarUsuario(Connection cn, Scanner scan) {
        System.out.println("Ingrese el ID del usuario a actualizar: ");
        int id = scan.nextInt();
        System.out.println("Ingrese el nuevo nombre: ");
        String nombre = scan.next();
        System.out.println("Ingrese la nueva clave: ");
        String password = scan.next();

        String updateQuery = "UPDATE usuario SET name = ?, clave = ? WHERE id_usuario = ?";

        try (PreparedStatement pstm = cn.prepareStatement(updateQuery)) 
        {
            pstm.setString(1, nombre);
            pstm.setString(2, password);
            pstm.setInt(3, id);

            int result = pstm.executeUpdate();

            if (result > 0) {
                System.out.println("Usuario actualizado exitosamente.");
            } else {
                System.out.println("No se encontró un usuario con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el usuario");
            e.printStackTrace();
        }
    }

    private static void eliminarUsuario(Connection cn, Scanner scan) {

        System.out.println("Ingrese el ID del usuario a eliminar: ");
        int id = scan.nextInt();

        String deleteQuery = "DELETE FROM usuario WHERE id_usuario = ?";

        try (PreparedStatement pstm = cn.prepareStatement(deleteQuery)) 
        {
            pstm.setInt(1, id);

            int result = pstm.executeUpdate();

            if (result > 0) {
                System.out.println("Usuario eliminado exitosamente.");
            } else {
                System.out.println("No se encontró un usuario con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario");
            e.printStackTrace();
        }
    }

    private static List<Usuario> listarUsuarios(Connection cn) {

        List<Usuario> usuarios = new ArrayList<>();

        String selectQuery = "SELECT (name) FROM usuario";


        try (Statement stm = cn.createStatement();
             ResultSet rs = stm.executeQuery(selectQuery)) 
            {

               // pstm = cn.prepareStatement(selectQuery);
               // pstm.executeQuery;

            while (rs.next()) {

                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("name");
                String password = rs.getString("clave");

                
                Usuario usuario = new Usuario(id, nombre, password);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los usuarios");
            e.printStackTrace();
        }
        return usuarios;
    }
}

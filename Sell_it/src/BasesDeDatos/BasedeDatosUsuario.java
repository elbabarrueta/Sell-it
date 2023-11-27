package BasesDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import clases.Usuario;

public class BasedeDatosUsuario {

	private static Logger logger = Logger.getLogger("BaseDeDatos");
    private static Connection con;
    private static Statement s;
    private static ResultSet rs;
    
    public static void main(String[] args) {
        String com = "";
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
            s = con.createStatement();
            try {
            	com = "create table usuario (nombreUsuario string, correoUsuario string, tipoUsuario string, contrasena string)";
                logger.log(Level.INFO, "BD: " + com);
                s.executeUpdate(com);
            } catch (SQLException e) {
                // Se lanza si la tabla ya existe - no hay problema
            }
            // Ver si existe admin
            com = "select * from Usuario where correoUsuario = 'admin'";
            logger.log(Level.INFO, "BD: " + com);
            rs = s.executeQuery(com);
            if (!rs.next()) {
                // Añadirlo si no existe
                com = "insert into Usuario ( nombreUsuario, correoUsuario, tipoUsuario, contrasena ) values ('admin', 'admin', 'admin', 'admin')";
                logger.log(Level.INFO, "BD: " + com);
                s.executeUpdate(com);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Último comando: " + com);
            e.printStackTrace();
        }
    }
    public void anadirUsuarioNuevo(Usuario usu) {
			String com = "";
			try {
				// Ver si existe usuario
				// Si queremos asegurar el string habría que hacer algo así...
				// String nick = tfUsuario.getText().replaceAll( "'", "''" );
				// ...si no, cuidado con lo que venga en el campo de entrada.
				// "select * from Usuario where nick = 'admin'";
				com = "select * from Usuario where correoUsuario = '" + usu.getCorreoUsuario() + "'";
				logger.log( Level.INFO, "BD: " + com );
				rs = s.executeQuery( com );
				if (!rs.next()) {
					// "insert into Usuario ( nick, pass ) values ('admin', 'admin')";
					com = "insert into Usuario (nombreUsuario, correoUsuario, tipoUsuario, contrasena) values ('"+ 
							usu.getNombreUsuario() +"', '" + usu.getCorreoUsuario() +"', '" + usu.getTipoUsuario()+"', '" + usu.getContrasena()+ "')";
					logger.log( Level.INFO, "BD: " + com );
					int val = s.executeUpdate( com );
					if (val!=1) {
						JOptionPane.showMessageDialog( null, "Error en inserción" );
					}
				} else {
					JOptionPane.showMessageDialog( null, "Usuario " + usu.getCorreoUsuario() + " ya existe" );
				}
			} catch (SQLException e2) {
				System.out.println( "Último comando: " + com );
				e2.printStackTrace();
			}
    }
    
    public void modificarUsuarioYaRegistrado(Usuario usu) {
    	//update Usuario set contrasena = 'valor1' where correoUsuario = 'valor2'
		String sent = "update Usuario set contrasena = '" + secu(usu.getContrasena()) + "' where correoUsuario = '" + secu(usu.getCorreoUsuario()) + "'";
		logger.log(Level.INFO, "BD: " + sent);
		try {
			s.executeUpdate(sent);
		} catch (SQLException e1) {
			logger.log(Level.WARNING, sent, e1);
			e1.printStackTrace();
		}
    }
    
    public void borrarUsuarioRegistrado(Usuario usu) {
    	if (!usu.getCorreoUsuario().isEmpty() && !usu.getContrasena().isEmpty()) {
			String com = "";
			try {
				// Borrar usuario
				com = "delete from Usuario where correoUsuario = '"+ secu(usu.getCorreoUsuario()) +"'";
				logger.log( Level.INFO, "BD: " + com );
				s.executeUpdate( com );
			} catch (SQLException e2) {
				System.out.println( "Último comando: " + com );
				e2.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog( null, "Debes rellenar los dos campos" );
		}
    }
    
    public void cerrarConexiones() {
    	try {
			rs.close();
			s.close();
			con.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
    }
    
 // Posible función de "securización" para evitar errores o ataques
 	private static String secu( String sqlInicial ) {
 		return sqlInicial;
 		// Si lo reemplazamos por esto, es mucho más seguro:
 		// return sqlInicial.replaceAll( "'", "''" );
 	}
    
 	
// visualizar por consola los usuarios registrados	
 	public void verUsuarios() {
        String com = "select * from Usuario";
        logger.log(Level.INFO, "BD: " + com);

        try {
            rs = s.executeQuery(com);

            System.out.println("Usuarios en la base de datos:");

            while (rs.next()) {
                String nombreUsuario = rs.getString("nombreUsuario");
                String correoUsuario = rs.getString("correoUsuario");
                String tipoUsuario = rs.getString("tipoUsuario");
                String contrasena = rs.getString("contrasena");

                System.out.println("Nombre: " + nombreUsuario +
                        ", Correo: " + correoUsuario +
                        ", Tipo: " + tipoUsuario +
                        ", Contraseña: " + contrasena);
            }

        } catch (SQLException e) {
            System.out.println("Último comando: " + com);
            e.printStackTrace();
        }
    }
}


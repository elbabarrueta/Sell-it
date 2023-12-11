package BasesDeDatos;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clases.Entrada;
import clases.Evento;
import clases.Usuario;
import java.sql.Statement;

public class BaseDeDatos {
	private Usuario usu;
	private Evento even;
	private Entrada ent;
	private static Logger logger;
	private static Connection con;
	private static Statement s;
	private static ResultSet rs;
	private static HashMap<String, Usuario> mapaUsuarios;

	
	public static void main(String[] args) {
		
		try {
			logger = Logger.getLogger("BaseDeDatos");
			logger.addHandler(new FileHandler("BasesDeDatos.xml"));
		}catch (Exception e){}
		
		String com = "";
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
			s = con.createStatement();
			
			//crear tabla Usuario
			try {				
				com = "create table usuario (nombreUsuario string, correoUsuario string, tipoUsuario string, contrasena string, imagenPerfil string)";
				logger.log(Level.INFO, "BD: " + com);
				s.executeUpdate(com);
			} catch (SQLException e) {
				// se lanza si la tabla ya existe - no hay problema
				logger.log(Level.INFO, "La tabla ya está creada");
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
				Usuario moma = new Usuario("Discoteca Moma", "moma@gmail.com", "Usuario entidad", "MmMon345627#", " ");
				anadirUsuarioNuevo(moma);
				Usuario kepa = new Usuario("Kepa Galindo", "k10galindo@gmail.com", "Usuario corriente", "GK842aeiou", " ");
				anadirUsuarioNuevo(kepa);
				Usuario miguel = new Usuario("Miguel Diaz", "mdiaz@gmail.com", "Usuario corriente", "mMiaz45#g", " ");
				anadirUsuarioNuevo(miguel);
				Usuario laura = new Usuario("Laura Lopez", "laura.lopez@gmail.com", "Usuario corriente", "abcABC33", " ");
				anadirUsuarioNuevo(laura);
			}			
			crearTablas(con);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Último comando: " + com);
			e.printStackTrace();
			}
		}
	
	public Connection getConnection() {
		return con;
	}
	public static void anadirUsuarioNuevo(Usuario usu) {
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
				com = "insert into Usuario (nombreUsuario, correoUsuario, tipoUsuario, contrasena, ImagenPerfil) values ('"+ 
						usu.getNombreUsuario() +"', '" + usu.getCorreoUsuario() +"', '" + usu.getTipoUsuario()+"', '" + usu.getContrasena()+ "', '" + usu.getImgPerfil() + "')";
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
	public static void crearTablas(Connection con) {
//		String sql = "CREATE TABLE IF NOT EXISTS Evento (codigo String, nombre String,desc String,fecha String,ubicacion String, nEntradas Integer, precio Double, rutaImg String)";
//		String sql2 = "CREATE TABLE IF NOT EXISTS Entrada (codigo String,desc String, fecha String, precio Double)";
		String sql = "CREATE TABLE IF NOT EXISTS Evento (codigo Integer, nombre String, desc String, fecha String,ubicacion String, nEntradas Integer, rutaImg String)";
		String sql2 = "CREATE TABLE IF NOT EXISTS Entrada (codigo Integer, evento_cod Integer, propietario_correo String, precio Double)";

		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.executeUpdate(sql2);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void anadirEventoNuevo(Evento evento) {
		String com = "";
		try {
			com = "select * from Evento where codigo = '" + evento.getCodigo() + "'";
			logger.log( Level.INFO, "BD: " + com );
			rs = s.executeQuery( com );
			if (!rs.next()) {
				com = "insert into Evento (codigo, nombre, desc, fecha, ubicacion, nEntradas, rutaImg) values ('"+ 
						evento.getCodigo() +"', '" + evento.getNombre() +"', '" + evento.getDesc() +"', '" + evento.getFecha() + "', '" + evento.getUbicacion() + "', '" + evento.getnEntradas() + "', '" + evento.getRutaImg() +"')";
				logger.log( Level.INFO, "BD: " + com );
				int val = s.executeUpdate( com );
				if (val!=1) {
					JOptionPane.showMessageDialog( null, "Error en inserción" );
				}
			} else {
				JOptionPane.showMessageDialog( null, "Evento " + evento.getCodigo() + " ya existe" );
			}
		} catch (SQLException e2) {
			System.out.println( "Último comando: " + com );
			e2.printStackTrace();
		}
	}
	
	public static void anadirEntradaNueva(Entrada entrada) {
		String com = "";
		try {
			com = "select * from Evento where codigo = '" + entrada.getCod() + "'";
			logger.log( Level.INFO, "BD: " + com );
			rs = s.executeQuery( com );
			if (!rs.next()) {
				String propietario_correo;
				if(entrada.getPropietario() == null) {
					propietario_correo = "null";
				}else {
					propietario_correo = entrada.getPropietario().getCorreoUsuario();
				}
				com = "insert into Entrada (codigo, evento_cod, propietario_correo, precio) values ('"+ 
						entrada.getCod() +"', '" + entrada.getEventoAsociado().getCodigo() +"', '" + propietario_correo +"', '" + entrada.getPrecio() +"')";
				logger.log( Level.INFO, "BD: " + com );
				int val = s.executeUpdate( com );
				if (val!=1) {
					JOptionPane.showMessageDialog( null, "Error en inserción" );
				}
			} else {
				JOptionPane.showMessageDialog( null, "Entrada " + entrada.getCod() + " ya existe" );
			}
		} catch (SQLException e2) {
			System.out.println( "Último comando: " + com );
			e2.printStackTrace();
		}
	}
	
	//Devuelve una lista con los eventos de la tabla Eventos
	public static List<Evento> obtenerListaEventos(Connection con){
		String sql = "SELECT * FROM Evento";
		List<Evento> listaEventos = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				int codigo = rs.getInt("codigo");
				String nombre = rs.getString("nombre");
				//String dni = rs.getString("dni");
				String desc = rs.getString("desc");
				String fecha = rs.getString("fecha");
				String ubicacion = rs.getString("ubicacion");
				int nEntradas = rs.getInt("nEntradas");
				//double precio = rs.getDouble("precio");
				String rutaImg = rs.getString("rutaImg");
				
				Evento even = new Evento(nombre, desc, fecha, ubicacion, nEntradas);
				listaEventos.add(even);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaEventos;
	}
	//Devuelve una lista con las entradas de la tabla Entrada
		public static List<Entrada> obtenerListaEntradas(Connection con){
			String sql = "SELECT * FROM Entrada";
			List<Entrada> listaEntrada = new ArrayList<>();
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				while(rs.next()) {
					int codigo = rs.getInt("codigo");
					//String desc = rs.getString("desc");
					//String fecha = rs.getString("fecha");
					int evento_cod = rs.getInt("evento_cod");
					String propietario_correo = rs.getString("propietario_correo");
					double precio = rs.getDouble("precio");
					
					Evento evento = obtenerEventoPorCodigo(evento_cod);
					Usuario propietario = getUsuarioPorCorreo(propietario_correo);
					if (evento != null ) {
					    Entrada entrada = new Entrada(codigo, evento, propietario, precio);
						listaEntrada.add(entrada);
					    // Agregar la entrada a tu lista o realizar otras operaciones necesarias
					} else {
					    // Manejar el caso en que no se encuentre el evento
					    System.out.println("No se encontró el evento con el código: " + evento_cod);
					}
					//Entrada e = new Entrada(codigo, desc, fecha,precio);
				}
				rs.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return listaEntrada;
		}
		

public void modificarUsuarioYaRegistradoContrasena(Usuario usu) {
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
public void modificarUsuarioYaRegistrado(Usuario usu) {		
	String sent = "update Usuario set nombreUsuario= '"+ secu(usu.getNombreUsuario())+  "' where correoUsuario = '"+ secu(usu.getCorreoUsuario()) + "'";
//	String sentCorreo = "update Usuario set nombreUsuario= '"+ secu(usu.getNombreUsuario())+ "' where correoUsuario = '"+ secu(usu.getCorreoUsuario()) + "'";
	logger.log(Level.INFO, "BD: " + sent);
//	logger.log(Level.INFO, "BD: " + sentCorreo);

	try {
		s.executeUpdate(sent);
//		s.executeUpdate(sentCorreo);
	} catch (SQLException e1) {
		logger.log(Level.WARNING, sent, e1);
//		logger.log(Level.WARNING, sentCorreo, e1);

		e1.printStackTrace();
	}
}
public void modificarUsuarioImagenPerfil(Usuario usu) {
	String sent = "update Usuario set imagenPerfil= '" +
            secu(usu.getImgPerfil()) + "' where correoUsuario = '"+ secu(usu.getCorreoUsuario()) + "'";
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

	
//visualizar por consola los usuarios registrados	
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
            String imagenPerfil = rs.getString("imagenPerfil");
            Usuario usuario = new Usuario(nombreUsuario, correoUsuario, tipoUsuario, contrasena, imagenPerfil);

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
	
	public void verEvento() {
	    String com = "select * from Evento ";
	    logger.log(Level.INFO, "BD: " + com);

	    try {
	    	rs = s.executeQuery(com);
	    	System.out.println(" Eventos en la base de datos: ");
	    	while(rs.next()) {
	    		 int codigo = rs.getInt("codigo");
	    		 String nombre = rs.getString("nombre");
		         String desc = rs.getString("desc");
		         String fecha = rs.getString("fecha");
		         String ubicacion = rs.getString("ubicacion");
		         int nEntradas = rs.getInt("nEntradas");
		         String rutaImg = rs.getString("rutaImg");
		         
		         Evento evento = new Evento(nombre, desc, fecha, ubicacion, nEntradas);
		         System.out.println("Nombre: " + nombre + ", Descripcion: " + desc + ", Fecha: " + fecha + ", Ubicacion: " + ubicacion + "; Numero de entradas: "+ nEntradas);
	    	}
	    } catch (SQLException e) {
	    	System.out.println("Ultimo comando: " + com);
	    	e.printStackTrace();
	    }
	}
	

	public HashMap<String, Usuario> crearMapa() {
        mapaUsuarios = new HashMap<>();

        String com = "select * from Usuario";
        logger.log(Level.INFO, "BD: " + com);

        try {
            rs = s.executeQuery(com);

            while (rs.next()) {
                String nombreUsuario = rs.getString("nombreUsuario");
                String correoUsuario = rs.getString("correoUsuario");
                String tipoUsuario = rs.getString("tipoUsuario");
                String contrasena = rs.getString("contrasena");
                String imagenPerfil = rs.getString("imagenPerfil");
                Usuario usuario = new Usuario(nombreUsuario, correoUsuario, tipoUsuario, contrasena, imagenPerfil);
                mapaUsuarios.put(correoUsuario, usuario);
            }

        } catch (SQLException e) {
            System.out.println("Último comando: " + com);
            e.printStackTrace();
        }

        return mapaUsuarios;
    }
	public static Usuario getUsuarioPorCorreo(String correo) {
		if(mapaUsuarios.containsKey(correo)) {
			return mapaUsuarios.get(correo);
		}
		else {
			return null;
		}
	}

	public static Evento obtenerEventoPorCodigo(int evento_cod) {
	    String com = "SELECT * FROM Evento WHERE codigo = ?";
	    logger.log(Level.INFO, "BD: " + com);

	    try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
	        preparedStatement.setInt(1, evento_cod);
	        ResultSet rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            String nombre = rs.getString("nombre");
	            String desc = rs.getString("desc");
	            String fecha = rs.getString("fecha");
	            String ubicacion = rs.getString("ubicacion");
	            int nEntradas = rs.getInt("nEntradas");
	            String rutaImg = rs.getString("rutaImg");
	            Evento evento = new Evento(nombre, desc, fecha, ubicacion, nEntradas);
	            return evento;
	        }
	    } catch (SQLException e) {
	        System.out.println("Último comando: " + com);
	        e.printStackTrace();
	    }
	    return null;  // Devuelve null si no se encuentra el evento
	}

	public void marcarEntradaComoComprada(String codigoEntrada, String correoComprador) {
	    String com = "UPDATE Entrada SET propietario_correo = ? WHERE codigo = ?";
	    logger.log(Level.INFO, "BD: " + com);

	    try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
	        preparedStatement.setString(1, correoComprador);
	        preparedStatement.setString(2, codigoEntrada);

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Entrada marcada como comprada exitosamente.");
	        } else {
	            System.out.println("No se pudo marcar la entrada como comprada.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Último comando: " + com);
	        e.printStackTrace();
	    }
	}
	
// Esto seria para marcar la entrada como comprada
//	String codigoEntrada = "tu_codigo_de_entrada";
//	String correoComprador = "correo_del_comprador";
//
//	baseDatos.marcarEntradaComoComprada(codigoEntrada, correoComprador);

}
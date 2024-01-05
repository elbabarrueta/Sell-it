package BasesDeDatos;

import java.util.logging.FileHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import clases.*;
import clases.Evento;
import clases.Usuario;
import ventanas.VentanaVentaEntidad;


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
	
	/**
     * Punto de entrada principal para la aplicación.
     * @param args Los argumentos de la línea de comandos.
     */
	public static void main(String[] args) throws SQLException{
			
		try {		
            // Inicializar el logger y establecer la conexión con la base de datos.
            inicializarLogger();
            establecerConexion();
            // Crear la tabla 'Usuario' si no existe.
            crearTablaUsuario();
            // Añadir columna 'ultimoCambioContrasena' a la tabla 'Usuario' si no existe.
            agregarColumnaUltimoCambioContrasena();
            // Crear otras tablas necesarias en la base de datos.
			crearTablas(con);
			// Actualizar 'ultimoCambioContrasena' para usuarios con valor 'null'.
            actualizarUltimoCambioContrasena();			
            // Verificar y agregar el usuario administrador si no existe.
            verificarYAgregarAdmin();
            		
		} catch (SQLException | ClassNotFoundException e) {
            // Manejar excepciones e imprimir traza de error.
			manejarExcepcion(e);
		} finally {
//            // Cerrar la conexión con la base de datos.
//            cerrarConexiones();
        }
	}
		
	/**
     * Inicializa el logger para registrar eventos y errores en un archivo XML.
     */
	private static void inicializarLogger() {
        try {
            logger = Logger.getLogger("BaseDeDatos");
            logger.addHandler(new FileHandler("BasesDeDatos.xml"));
        } catch (Exception e) {
            manejarExcepcion(e);
        }
    }
	/**
     * Establece la conexión con la base de datos SQLite.
     * @throws SQLException Si hay un error al intentar establecer la conexión.
     * @throws ClassNotFoundException Si no se encuentra la clase del controlador JDBC.
     */
	private static void establecerConexion() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
        s = con.createStatement();
    }
	/**
     * Maneja una excepción imprimiendo el mensaje de error y la traza de la excepción.
     * @param e La excepción que se debe manejar.
     */
	private static void manejarExcepcion(Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
	/**
     * Crea la tabla 'usuario' en la base de datos si no existe.
     */
    private static void crearTablaUsuario() {
        String comentarioSQL = "CREATE TABLE IF NOT EXISTS usuario (nombreUsuario string, correoUsuario string, tipoUsuario string, contrasena string, imagenPerfil string, descripcion string)";
        ejecutarSQL(comentarioSQL, Level.INFO);
    }
    /**
     * Agrega la columna 'ultimoCambioContrasena' a la tabla 'Usuario' si no existe.
     */
    private static void agregarColumnaUltimoCambioContrasena() {
        String comentarioSQL = "ALTER TABLE Usuario ADD COLUMN ultimoCambioContrasena string";
        ejecutarSQL(comentarioSQL, Level.INFO);
    }
    /**
     * Actualiza la columna 'ultimoCambioContrasena' para usuarios con valor 'null'.
     */
    private static void actualizarUltimoCambioContrasena() {
        String comentarioSQL = "UPDATE Usuario SET ultimoCambioContrasena = '2023-12-01' WHERE ultimoCambioContrasena = 'null'";
        ejecutarSQL(comentarioSQL, Level.INFO);
    }
    /**
     * Ejecuta la sentencia SQL proporcionada y registra eventos en el logger.
     * @param sql La sentencia SQL a ejecutar.
     * @param logLevel El nivel de log para el evento.
     */
    private static void ejecutarSQL(String sql, Level logLevel) {
        try {
            logger.log(logLevel, "BD: " + sql);
            s.executeUpdate(sql);
        } catch (SQLException e) {
            if (logLevel == Level.INFO) {
                logger.log(Level.INFO, "La operación ya se realizó anteriormente");
            } else {
                manejarExcepcion(e);
            }
        }
    }
    /**
     * Verifica y agrega el usuario administrador si no existe.
     */
    private static void verificarYAgregarAdmin() {
        String comentarioSQL = "SELECT * FROM Usuario WHERE correoUsuario = 'admin'";
        logger.log(Level.INFO, "BD: " + comentarioSQL);

        try {
            rs = s.executeQuery(comentarioSQL);
            if (!rs.next()) {
                // Añadir el usuario administrador si no existe.
				comentarioSQL = "insert into Usuario ( nombreUsuario, correoUsuario, tipoUsuario, contrasena, descripcion ) values ('admin', 'admin', 'admin', 'admin', 'admin')";
                ejecutarSQL(comentarioSQL, Level.INFO);

                // Añadir otros usuarios y eventos iniciales...
                agregarUsuariosYEventosIniciales();
            }
        } catch (SQLException e) {
            manejarExcepcion(e);
        }
    }
    /**
     * Añade usuarios y eventos iniciales a la base de datos.
     */
    private static void agregarUsuariosYEventosIniciales() {
        // Código para agregar usuarios y eventos iniciales...
    	Usuario moma = new Usuario("Discoteca Moma", "moma@gmail.com", "Usuario entidad", "MmMon345627#", "Sell_it/src/imagenes/perfil.png", "null");
		anadirUsuarioNuevo(moma);
		Usuario kepa = new Usuario("Kepa Galindo", "k10galindo@gmail.com", "Usuario corriente", "GK842aeiou", "Sell_it/src/imagenes/perfil.png", "null");
		anadirUsuarioNuevo(kepa);
		Usuario miguel = new Usuario("Miguel Diaz", "mdiaz@gmail.com", "Usuario corriente", "mMiaz45#g", "Sell_it/src/imagenes/perfil.png", "null");
		anadirUsuarioNuevo(miguel);
		Usuario laura = new Usuario("Laura Lopez", "laura.lopez@gmail.com", "Usuario corriente", "abcABC33", "Sell_it/src/imagenes/perfil.png", "null");
		anadirUsuarioNuevo(laura);
		
		Evento e1 = new Evento("Concierto Melendi","Concierto del cantante Melendi. Gira de sus canciones mas miticas!","10-11-2023","Bilbao",300, "Sell_it/src/imagenes/melendi.png", "moma@gmail.com");
		anadirEventoNuevo(e1);
		int cod = VentanaVentaEntidad.obtenerCod();
        for(int i=0; i<e1.getnEntradas(); i++) {
        	Entrada entrada = new Entrada(cod+i, e1, null, 25);
        	anadirEntradaNueva(entrada);
        }
		Evento e2 = new Evento("Concierto Alejandro Sanz","Concierto del cantante Alejandro Sanz. Gira de su nuevo album!","30-12-2022","Logroño",250, "Sell_it/src/imagenes/alejandroSanz.jpg",null);
		anadirEventoNuevo(e2);
		int cod2 = VentanaVentaEntidad.obtenerCod();
        for(int i=0; i<e2.getnEntradas(); i++) {
        	Entrada entrada = new Entrada(cod2+i, e2, null, 20.5);
        	anadirEntradaNueva(entrada);
        }
		Evento e3 = new Evento("Exposición de Fotografía Urbana","Explora la belleza de la fotografía urbana con esta exposición única.","15-03-2023","Madrid",100, "Sell_it/src/imagenes/arteUrbano.png",null);
		anadirEventoNuevo(e3);
		int cod3 = VentanaVentaEntidad.obtenerCod();
        for(int i=0; i<e3.getnEntradas(); i++) {
        	Entrada entrada = new Entrada(cod3+i, e3, null, 10.2);
        	anadirEntradaNueva(entrada);
        }
		Evento e4 = new Evento("Festival de Jazz en el Parque","Disfruta de una tarde de música jazz al aire libre en nuestro hermoso parque.","05-05-2023","Barcelona",150, "Sell_it/src/imagenes/jazzParque.jpeg",null);
		anadirEventoNuevo(e4);
		int cod4 = VentanaVentaEntidad.obtenerCod();
        for(int i=0; i<e4.getnEntradas(); i++) {
        	Entrada entrada = new Entrada(cod4+i, e4, null, 15);
        	anadirEntradaNueva(entrada);
        }
		Evento e5 = new Evento("Conferencia de Ciencia y Tecnología","Únete a expertos de la industria para explorar las últimas tendencias en ciencia y tecnología.","20-06-2023","Valencia",30, "Sell_it/src/imagenes/cienciaTec.jpg",null);
		anadirEventoNuevo(e5);
		int cod5 = VentanaVentaEntidad.obtenerCod();
        for(int i=0; i<e5.getnEntradas(); i++) {
        	Entrada entrada = new Entrada(cod5+i, e5, null, 30);
        	anadirEntradaNueva(entrada);
        }
		Evento e6 = new Evento("Carrera Solidaria por la Naturaleza","Participa en esta carrera para apoyar la conservación del medio ambiente.","08-09-2023","Sevilla",100,"Sell_it/src/imagenes/naturaleza.jpg", null);
		anadirEventoNuevo(e6);
		int cod6 = VentanaVentaEntidad.obtenerCod();
        for(int i=0; i<e6.getnEntradas(); i++) {
        	Entrada entrada = new Entrada(cod6+i, e6, null, 12);
        	anadirEntradaNueva(entrada);
        }
		Evento e7 = new Evento("Noche de Comedia con Ricky Gervais","Una noche llena de risas con el famoso comediante Ricky Gervais. ¡Prepárate para reír a carcajadas!","12-11-2023","Málaga",60,"Sell_it/src/imagenes/comedia.jpg", null);
		anadirEventoNuevo(e7);
		int cod7 = VentanaVentaEntidad.obtenerCod();
        for(int i=0; i<e7.getnEntradas(); i++) {
        	Entrada entrada = new Entrada(cod7+i, e7, null, 25);
        	anadirEntradaNueva(entrada);
        }
    }
    
    /**
     * Obtiene la conexión actual a la base de datos.
     * @return Objeto Connection que representa la conexión a la base de datos.
     */
	public Connection getConnection() {
		return con;
	}
	
	/**
	 * Añade un nuevo usuario a la base de datos.
	 * @param usu Objeto Usuario que se va a añadir.
	 */
	public static void anadirUsuarioNuevo(Usuario usu) {
		String com = "";
		try {			
	        // Verificar si el usuario ya existe en la base de datos.
			com = "select * from Usuario where correoUsuario = '" + usu.getCorreoUsuario() + "'";
			logger.log( Level.INFO, "BD: " + com );
			rs = s.executeQuery( com );
			if (!rs.next()) {
				// "insert into Usuario ( nick, pass ) values ('admin', 'admin')";
				com = "insert into Usuario (nombreUsuario, correoUsuario, tipoUsuario, contrasena, ImagenPerfil, ultimoCambioContrasena) values ('"+ 
						usu.getNombreUsuario() +"', '" + usu.getCorreoUsuario() +"', '" + usu.getTipoUsuario()+"', '" + usu.getContrasena()+ "', '" + usu.getImgPerfil() + "', '" + usu.getUltimaCambioContrasena() +"')";
				logger.log( Level.INFO, "BD: " + com );
				
				int val = s.executeUpdate( com );
				
				if (val!=1) {
					// Mostrar mensaje de error si la inserción falla.
                    showError("Error en inserción");				
				}
			} else {
				// Mostrar mensaje si el usuario ya existe.
                showError("Usuario " + usu.getCorreoUsuario() + " ya existe");
			}
		} catch (SQLException e2) {
			// Manejar excepciones e imprimir información de error.
	        handleException(e2, com);
		}
	}
	
	/**
	 * Crea las tablas necesarias en la base de datos si no existen.
	 * @param con Conexión a la base de datos.
	 */
	public static void crearTablas(Connection con) {
	    // Definición de las sentencias SQL para la creación de las tablas.
		String sql = "CREATE TABLE IF NOT EXISTS Evento (codigo Integer, nombre String, desc String, fecha String,ubicacion String, nEntradas Integer, rutaImg String, creador String)";
		String sql2 = "CREATE TABLE IF NOT EXISTS Entrada (codigo Integer, evento_cod Integer, propietario_correo String, precio Double)";
		String notificacion = "CREATE TABLE IF NOT EXISTS Notificacion (id Integer, mensaje String)";
		String relacion = "CREATE TABLE IF NOT EXISTS Relacion (correo String, id_noti Integer, leido Boolean)";
		String valoraciones = "CREATE TABLE IF NOT EXISTS Valoracion (id Integer, usuario_revisor String, usuario_valorado String, puntuacion Integer, comentario String)";
		try {
			Statement st = con.createStatement();
	        // Ejecutar las sentencias SQL para la creación de las tablas.
			st.executeUpdate(sql);
			st.executeUpdate(sql2);
			st.executeUpdate(notificacion);
			st.executeUpdate(relacion);
			st.executeUpdate(valoraciones);
	        // Cerrar la declaración después de ejecutar las sentencias.
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Añade un nuevo evento a la base de datos.
	 * @param evento Objeto Evento que se va a añadir.
	 */
	public static void anadirEventoNuevo(Evento evento) {
		String com = "";
		try {
	        // Verificar si el evento ya existe en la base de datos.
			com = "select * from Evento where codigo = '" + evento.getCodigo() + "'";
			logger.log( Level.INFO, "BD: " + com );
			rs = s.executeQuery( com );
			if (!rs.next()) {
                // Insertar nuevo evento si no existe.
				com = "insert into Evento (codigo, nombre, desc, fecha, ubicacion, nEntradas, rutaImg, creador) values ('"+ 
						evento.getCodigo() +"', '" + evento.getNombre() +"', '" + evento.getDesc() +"', '" + evento.getFecha() + "', '" + evento.getUbicacion() + "', '" + evento.getnEntradas() + "', '" + evento.getRutaImg() + "', '" + evento.getCreador() +"')";
				logger.log( Level.INFO, "BD: " + com );
				int val = s.executeUpdate( com );
				
				if (val!=1) {
					// Mostrar mensaje de error si la inserción falla.
                    showError("Error en inserción");				}
			} else {
				// Mostrar mensaje si el evento ya existe.
                showError("Evento " + evento.getCodigo() + " ya existe");			}
		} catch (SQLException e2) {
			// Manejar excepciones e imprimir información de error.
	        handleException(e2, com);
		}
	}
	
	/**
	 * Añade una nueva entrada a la base de datos.
	 * @param entrada Objeto Entrada que se va a añadir.
	 */
	public static void anadirEntradaNueva(Entrada entrada) {
		String com = "";
		try {
			com = "select * from Entrada where codigo = '" + entrada.getCod() + "'";
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
					// Mostrar mensaje de error si la inserción falla.
                    showError("Error en inserción");
                    }
			} else {
				// Mostrar mensaje si la entrada ya existe.
                showError("Entrada " + entrada.getCod() + " ya existe");
			}
		} catch (SQLException e) {
			// Manejar excepciones e imprimir información de error.
	        handleException(e, com);
		}
	}
	
	/**
	 * Obtiene una lista de eventos en venta asociados al usuario.
	 * @param usuario Usuario para el que se obtienen los eventos en venta.
	 * @return Lista de objetos Evento.
	 */
	public static List<Evento> obtenerEventosEnVentaDelUsuario(Usuario usuario) {
        String com = "SELECT * FROM Evento WHERE creador = ?";
        logger.log(Level.INFO, "BD: " + com);

        List<Evento> eventosEnVenta = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
            preparedStatement.setString(1, usuario.getCorreoUsuario());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
            	//Código para obtener los datos del ResultSet y crear objetos Evento
            	int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String desc = rs.getString("desc");
                String fecha = rs.getString("fecha");
                String ubicacion = rs.getString("ubicacion");
                int nEntradas = rs.getInt("nEntradas");
                String rutaImg = rs.getString("rutaImg");
                String creador = rs.getString("creador");

                Evento evento = new Evento(codigo, nombre, desc, fecha, ubicacion, nEntradas, rutaImg, creador);
                eventosEnVenta.add(evento);
            }

        } catch (SQLException e) {
            // Manejar excepciones e imprimir información de error.
            handleException(e, com);
        }
        return eventosEnVenta;        
    }
	
	/**
	 * Obtiene una lista de todos los eventos almacenados en la base de datos.
	 * @return Lista de objetos Evento.
	 */	
	public static List<Evento> obtenerListaEventos(){
		String sql = "SELECT * FROM Evento";
		List<Evento> listaEventos = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				//Código para obtener los datos del ResultSet y crear objetos Evento
				int codigo = rs.getInt("codigo");
				String nombre = rs.getString("nombre");
				String desc = rs.getString("desc");
				String fecha = rs.getString("fecha");
				String ubicacion = rs.getString("ubicacion");
				int nEntradas = rs.getInt("nEntradas");
				String rutaImg = rs.getString("rutaImg");
				String creador = rs.getString("creador");
				
				Evento even = new Evento(codigo, nombre, desc, fecha, ubicacion, nEntradas,rutaImg, creador);
				listaEventos.add(even);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// Manejar excepciones e imprimir información de error.
	        handleException(e, "Último comando: " + sql);
		}
		return listaEventos;
	}
	/**
	 * Maneja una excepción, imprime la información de error y muestra un mensaje de error.
	 * @param e Excepción que se debe manejar.
	 * @param lastCommand Último comando SQL ejecutado.
	 */
	private static void handleException(SQLException e, String lastCommand) {
	    System.out.println("Último comando: " + lastCommand);
	    e.printStackTrace();
	    // Mostrar mensaje de error.
	    showError("Error en la base de datos: " + e.getMessage());
	}
	/**
	 * Muestra un mensaje de error en una ventana de diálogo.
	 * @param message Mensaje de error a mostrar.
	 */
	private static void showError(String message) {
	    JOptionPane.showMessageDialog(null, message);
	}
	
//
	/**
	 * Obtiene una lista de entradas sin comprar para un evento específico.
	 * @param evento_cod Código del evento.
	 * @return Lista de objetos Entrada.
	 */
	public static List<Entrada> obtenerListaEntradasSinComprarPorEvento(int evento_cod) {
	    String sql = "SELECT * FROM Entrada WHERE evento_cod = ?";
	    List<Entrada> listaEntrada = new ArrayList<>();

	    try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	        preparedStatement.setInt(1, evento_cod);
	        ResultSet rs = preparedStatement.executeQuery();
            Evento evento = obtenerEventoPorCodigo(evento_cod);

	        while (rs.next()) {
	            int codigo = rs.getInt("codigo");
	            String propietario_correo = rs.getString("propietario_correo");
	            double precio = rs.getDouble("precio");

	            Usuario propietario = getUsuarioPorCorreo(propietario_correo);

	            if (evento != null) {
	            	if(propietario == null) {
//		                System.out.println(evento);
		                Entrada entrada = new Entrada(codigo, evento, propietario, precio);
//		                System.out.println(entrada);
		                listaEntrada.add(entrada);
	            	}
	            } else {
	                // Manejar el caso en que no se encuentre el evento 
	                System.out.println("No se encontró el evento con el código: " + evento_cod);
	            }
	        }
	    } catch (SQLException e) {
	    	// Manejar la excepción y mostrar mensaje de error
	        handleException(e, "Error al obtener entradas sin comprar para el evento " + evento_cod);
	    }
	    return listaEntrada;
	}

	/**
	 * Modifica la contraseña de un usuario registrado.
	 * @param usu Objeto Usuario con la nueva contraseña.
	 */
	public void modificarUsuarioYaRegistradoContrasena(Usuario usu) {
	    String sent = "update Usuario set contrasena = '" + secu(usu.getContrasena()) + "', ultimoCambioContrasena = '" + usu.getUltimaCambioContrasena() + "' where correoUsuario = '" + secu(usu.getCorreoUsuario()) + "'";
		logger.log(Level.INFO, "BD: " + sent);
		try {
			s.executeUpdate(sent);
		} catch (SQLException e1) {
			logger.log(Level.WARNING, sent, e1);
	        handleException(e1, "Error al modificar la contraseña del usuario " + usu.getCorreoUsuario());
		}
	}
	/**
	 * Modifica el nombre de usuario de un usuario registrado.
	 * @param usu Objeto Usuario con el nuevo nombre.
	 */
	public void modificarUsuarioYaRegistrado(Usuario usu) {		
		String sent = "update Usuario set nombreUsuario= '"+ secu(usu.getNombreUsuario())+  "' where correoUsuario = '"+ secu(usu.getCorreoUsuario()) + "'";
		logger.log(Level.INFO, "BD: " + sent);
	
		try {
			s.executeUpdate(sent);
		} catch (SQLException e1) {
			logger.log(Level.WARNING, sent, e1);	
			// Manejar la excepción y mostrar mensaje de error
	        handleException(e1, "Error al modificar el nombre del usuario " + usu.getCorreoUsuario());
		}
	}
	/**
	 * Modifica la imagen de perfil de un usuario registrado.
	 * @param usu Objeto Usuario con la nueva imagen de perfil.
	 */
	public void modificarUsuarioImagenPerfil(Usuario usu) {
		String sent = "update Usuario set imagenPerfil= '" +
	            secu(usu.getImgPerfil()) + "' where correoUsuario = '"+ secu(usu.getCorreoUsuario()) + "'";
		logger.log(Level.INFO, "BD: " + sent);
	
		try {
			s.executeUpdate(sent);
		} catch (SQLException e1) {
			logger.log(Level.WARNING, sent, e1);
			// Manejar la excepción y mostrar mensaje de error
	        handleException(e1, "Error al modificar la imagen de perfil del usuario " + usu.getCorreoUsuario());
		}
	}
	
	/**
	 * Modifica la descripción de un usuario registrado.
	 * @param usu Objeto Usuario con la nueva descripción.
	 */
	public void modificarDescripcionUsuario(Usuario usu) {
	    String sent = "update Usuario set descripcion= '" + usu.getDescripcion() + "' where correoUsuario= '" + secu(usu.getCorreoUsuario())+"'";
	    logger.log(Level.INFO, "BD: " + sent);
	    try {
	    	s.executeUpdate(sent);
	    } catch (SQLException e) {
	    	// Manejar la excepción y mostrar mensaje de error
	        handleException(e, "Error al modificar la descripción del usuario " + usu.getCorreoUsuario());
	    }
	}

	/**
	 * Borra un usuario registrado.
	 * @param usu Objeto Usuario que se va a borrar.
	 */
	public void borrarUsuarioRegistrado(Usuario usu) {
		if (!usu.getCorreoUsuario().isEmpty() && !usu.getContrasena().isEmpty()) {
			String com = "";
			try {
				// Borrar usuario
				com = "delete from Usuario where correoUsuario = '"+ secu(usu.getCorreoUsuario()) +"'";
				logger.log( Level.INFO, "BD: " + com );
				s.executeUpdate( com );
			} catch (SQLException e2) {
				// Manejar la excepción y mostrar mensaje de error
	            handleException(e2, "Error al borrar el usuario " + usu.getCorreoUsuario());
			}
		} else {
			JOptionPane.showMessageDialog( null, "Debes rellenar los dos campos" );
		}
	}

	/**
	 * Borra un evento por su código.
	 * @param codigo Código del evento a borrar.
	 */
	public void borrarEvento(int codigo) {
	    String url = "jdbc:sqlite:usuarios.db";

	    try (Connection connection = DriverManager.getConnection(url);
	         Statement statement = connection.createStatement()) {

	        String query = "DELETE FROM Evento WHERE codigo = " + codigo;
	        statement.executeUpdate(query);

	    } catch (SQLException e) {
	        handleException(e, "Error al borrar el evento con código " + codigo);
	    }
	}
	
	/**
     * Cierra la conexión, el statement y el resultado si están abiertos.
     */
	public void cerrarConexiones() {
		try {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            manejarExcepcion(e);
        }
	}
	
// Posible función de "securización" para evitar errores o ataques
	private static String secu( String sqlInicial ) {
		return sqlInicial;
		// Si lo reemplazamos por esto, es mucho más seguro:
		// return sqlInicial.replaceAll( "'", "''" );
	}

	
	/**
	 * Visualiza por consola los usuarios registrados.
	 */	
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
            String descripcion = rs.getString("descripcion");
            Usuario usuario = new Usuario(nombreUsuario, correoUsuario, tipoUsuario, contrasena, imagenPerfil, descripcion);

            System.out.println("Nombre: " + nombreUsuario +
                    ", Correo: " + correoUsuario +
                    ", Tipo: " + tipoUsuario +
                    ", Contraseña: " + contrasena);
        }

    } catch (SQLException e) {
    	// Manejar la excepción y mostrar mensaje de error
        handleException(e, "Error al visualizar usuarios");
    }
}
	/**
	 * Visualiza por consola los eventos registrados.
	 */
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
		         String creador = rs.getString("creador");
		         
		         Evento evento = new Evento(nombre, desc, fecha, ubicacion, nEntradas, rutaImg, creador);
		         System.out.println("Nombre: " + nombre + ", Descripcion: " + desc + ", Fecha: " + fecha + ", Ubicacion: " + ubicacion + "; Numero de entradas: "+ nEntradas + "; Creador del evento: "+ creador);
	    	}
	    } catch (SQLException e) {
	    	System.out.println("Ultimo comando: " + com);
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * Crea un mapa de usuarios a partir de la información en la base de datos.
	 * @return HashMap donde la clave es el correo del usuario y el valor es el objeto Usuario correspondiente.
	 */
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
                String descripcion = rs.getString("descripcion");
                Usuario usuario = new Usuario(nombreUsuario, correoUsuario, tipoUsuario, contrasena, imagenPerfil, descripcion);
                mapaUsuarios.put(correoUsuario, usuario);
            }

        } catch (SQLException e) {
            System.out.println("Último comando: " + com);
            e.printStackTrace();
        }

        return mapaUsuarios;
    }
	
	/**
	 * Obtiene un usuario por su correo electrónico desde el mapa de usuarios.
	 * @param correo Correo electrónico del usuario a buscar.
	 * @return Objeto Usuario correspondiente al correo o null si no se encuentra.
	 */
	public static Usuario getUsuarioPorCorreo(String correo) {
		if(mapaUsuarios.containsKey(correo)) {
			return mapaUsuarios.get(correo);
		}
		else {
			return null;
		}
	}

	/**
	 * Obtiene un evento por su código desde la base de datos.
	 * @param evento_cod Código del evento a buscar.
	 * @return Objeto Evento correspondiente al código o null si no se encuentra.
	 */
	public static Evento obtenerEventoPorCodigo(int evento_cod) {
	    String com = "SELECT * FROM Evento WHERE codigo = ?";

	    try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
	        preparedStatement.setInt(1, evento_cod);
	        ResultSet rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	        	int codigo = rs.getInt("codigo");
	            String nombre = rs.getString("nombre");
	            String desc = rs.getString("desc");
	            String fecha = rs.getString("fecha");
	            String ubicacion = rs.getString("ubicacion");
	            int nEntradas = rs.getInt("nEntradas");
	            String rutaImg = rs.getString("rutaImg");
	            String creador = rs.getString("creador");
	            Evento evento = new Evento(codigo, nombre, desc, fecha, ubicacion, nEntradas, rutaImg, creador);
	            return evento;
	        }
	    } catch (SQLException e) {
	        System.out.println("Último comando: " + com);
	        e.printStackTrace();
	    }
	    System.out.println("No se encontró el evento con el código: " + evento_cod);
	    return null;  // Devuelve null si no se encuentra el evento
	}

	/**
	 * Obtiene la fecha del último cambio de contraseña para un usuario.
	 * @param usuario Usuario del cual se quiere obtener la fecha del último cambio de contraseña.
	 * @return LocalDate que representa la fecha del último cambio de contraseña.
	 */
	public LocalDate obtenerUltimoCambioContrasena(Usuario usuario) {
	    String com = "SELECT ultimoCambioContrasena FROM Usuario WHERE correoUsuario = ?";
	    logger.log(Level.INFO, "BD: " + com);

	    try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
	        preparedStatement.setString(1, usuario.getCorreoUsuario());
	        ResultSet rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            String fechaUltimoCambio = rs.getString("ultimoCambioContrasena");
	            // Ajusta el formato de parseo según el formato real de tu fecha
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            
	            // Verificar si la cadena no es nula
	    	    if (fechaUltimoCambio != null) {
	    	        try {
	    	            // Intentar parsear la cadena a LocalDate
	    	            return LocalDate.parse(fechaUltimoCambio, formatter);
	    	        } catch (DateTimeParseException e) {
	    	            // Manejar la excepción (puedes imprimir un mensaje de error, log, etc.)
	    	            e.printStackTrace();
	    	        }
	    	    }
	            
	            return LocalDate.parse(fechaUltimoCambio, formatter);	       
	        }
	    } catch (SQLException e) {
	        System.out.println("Último comando: " + com);
	        e.printStackTrace();
	    }

	    // Si la cadena es nula o no se puede parsear, devolver un valor por defecto
	    return LocalDate.now();
	}

	/**
	 * Marca una entrada como comprada en la base de datos.
	 * @param codigoEntrada Código de la entrada a marcar como comprada.
	 * @param correoComprador Correo del comprador.
	 */
	public void marcarEntradaComoComprada(Integer codigoEntrada, String correoComprador) {
	    String com = "UPDATE Entrada SET propietario_correo = ? WHERE codigo = ?";
	    logger.log(Level.INFO, "BD: " + com);

	    try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
	        preparedStatement.setString(1, correoComprador);
	        preparedStatement.setInt(2, codigoEntrada);

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

	/**
	 * Obtiene el correo del propietario de una entrada en la base de datos.
	 * @param codigoEntrada Código de la entrada.
	 * @return Correo del propietario de la entrada o null si no se encuentra.
	 */
	public String obtenerPropietarioCorreoEntrada(int codigoEntrada) {
	    String com = "SELECT propietario_correo FROM Entrada WHERE codigo = ?";

	    try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
	        preparedStatement.setInt(1, codigoEntrada);
	        ResultSet rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            return rs.getString("propietario_correo");
	        }
	    } catch (SQLException e) {
	        System.out.println("Último comando: " + com);
	        e.printStackTrace();
	    }
	    return null;  // Devuelve null si no se encuentra la entrada o hay un error
	}
	
	/**
	 * Actualiza el número de entradas de un evento en la base de datos.
	 * @param nEntradas Nuevo número de entradas.
	 * @param codigo Código del evento.
	 */
	public void updateNEntradas(int nEntradas, int codigo) {
		String updateQuery = "UPDATE Evento SET nEntradas = ? WHERE codigo = ?";
		try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
		    preparedStatement.setInt(1, nEntradas);
		    preparedStatement.setInt(2, codigo);
		    preparedStatement.executeUpdate();
		} catch (SQLException e) {
		    e.printStackTrace(); 
		}
	}
	
	/**
	 * Obtiene la lista de entradas compradas por un usuario.
	 * @param usuario Usuario para el cual se obtienen las entradas compradas.
	 * @return Lista de entradas compradas por el usuario.
	 */
	public static List<Entrada> obtenerEntradasCompradas(Usuario usuario) {
	    List<Entrada> entradasCompradas = new ArrayList<>();
	    String query = "SELECT * FROM Entrada WHERE propietario_correo = ?";

	    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
	        preparedStatement.setString(1, usuario.getCorreoUsuario());
	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            int codigo = rs.getInt("codigo");
	            int evento_cod = rs.getInt("evento_cod");
	            double precio = rs.getDouble("precio");

	            // Esta función devuelve un evento dado su código.
	            Evento evento = BDEventos.obtenerEventoPorCodigo(evento_cod);
	            
	            if (evento != null) {
	                Entrada entrada = new Entrada(codigo, evento, usuario, precio);
	                entradasCompradas.add(entrada);
	            }
	        }
	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "Error al obtener entradas compradas", e);
	        e.printStackTrace();
	    }
	    return entradasCompradas;
	}

	/**
	 * Guarda una notificación en la base de datos.
	 * @param usuario Usuario al cual se le asocia la notificación.
	 * @param notificacion Notificación a guardar.
	 */
	public static void guardarNotificacion(Usuario usuario, Notificacion notificacion) {
		String com = "";
		try {
			com = "select * from Notificacion where id = '" + notificacion.getId() + "'";
			logger.log( Level.INFO, "BD: " + com );
			rs = s.executeQuery( com );
			
			if (!rs.next()) {
				com = "insert into Notificacion (id, mensaje) values ('"+ 
						notificacion.getId() +"', '" + notificacion.getMensaje() +"')";
				logger.log( Level.INFO, "BD: " + com );
				int val = s.executeUpdate( com );
				
				if (val!=1) {
					JOptionPane.showMessageDialog( null, "Error en inserción" );
				}
			}
			// Insertar la relación en la tabla UsuariosNotificaciones
	        com = "insert into Relacion (correo, id_noti, leido) values ('" +
	                usuario.getCorreoUsuario() + "', '" + notificacion.getId() + "', '" + notificacion.isLeido() + "')";
	        logger.log(Level.INFO, "BD: " + com);
	        int valRelacion = s.executeUpdate(com);

	        if (valRelacion != 1) {
	            JOptionPane.showMessageDialog(null, "Error en inserción de relación usuario-notificación");
	        }
		} catch (SQLException e2) {
			System.out.println( "Último comando: " + com );
			e2.printStackTrace();
		}
	}
	
	/**
	 * Obtiene las entradas de un usuario desde la base de datos.
	 * @param u Usuario del cual se obtienen las entradas.
	 * @return Lista de entradas del usuario.
	 */
	public static List<Entrada> obtenerEntradasDeUsuario(Usuario u){
        List<Entrada> entradas = new ArrayList<>();
        String com = "";
        try {
            com = "select * from Entrada where propietario_correo = '" + u.getCorreoUsuario() + "'";
            ResultSet rs = s.executeQuery(com);

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                int evento_cod = rs.getInt("evento_cod");
                Evento evento = obtenerEventoPorCodigo(evento_cod);
                Double precio = rs.getDouble("precio");
                Entrada e = new Entrada(codigo, evento, u, precio);
                entradas.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener entradas del usuario " + u.getCorreoUsuario());
            System.out.println("Último comando: " + com);
            e.printStackTrace();
        }
        return entradas;
    }
	
	/**
	 * Obtiene las notificaciones asociadas a un usuario desde la base de datos.
	 * @param u Usuario del cual se obtienen las notificaciones.
	 * @return Lista de notificaciones para el usuario.
	 */
	public static List<Notificacion> obtenerNotificacionesPorUsuario(Usuario u){
        List<Notificacion> notificaciones = new ArrayList<>();
        String com = "";
        try {
            com = "select * from Relacion where correo = '" + u.getCorreoUsuario() + "'";
            ResultSet rs = s.executeQuery(com);

            while (rs.next()) {
                // Para cada notificación asociada al usuario, obtén la información de la notificación
                int idNotificacion = rs.getInt("id_noti");
                Boolean leido = rs.getBoolean("leido");
                String mensaje = obtenerMensaje(idNotificacion);
                if(leido == false) {
                    Notificacion notificacion = new Notificacion(mensaje, leido);
                    if (notificacion != null) {
                        notificaciones.add(notificacion);
                        marcarLeidoBD(idNotificacion, u.getCorreoUsuario());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener notificaciones para el usuario " + u.getCorreoUsuario());
            System.out.println("Último comando: " + com);
            e.printStackTrace();
        }
        return notificaciones;
    }
	
	/**
	 * Obtiene el mensaje asociado a una notificación desde la base de datos.
	 * @param id Identificador de la notificación.
	 * @return Mensaje de la notificación o null si no se encuentra.
	 */
	private static String obtenerMensaje(int id) {
		String com = "SELECT * FROM Notificacion WHERE id = ?";
	    logger.log(Level.INFO, "BD: " + com);

	    try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
	        preparedStatement.setInt(1, id);
	        ResultSet rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            String mensaje = rs.getString("mensaje");
	            return mensaje;
	        }
	    } catch (SQLException e) {
	        System.out.println("Último comando: " + com);
	        e.printStackTrace();
	    }
	    return null;  // Devuelve null si no se encuentra el evento
    }
	
	/**
	 * Marca una notificación como leída en la base de datos.
	 * @param idNotificacion Identificador de la notificación.
	 * @param correo Correo del usuario asociado a la notificación.
	 */
	public static void marcarLeidoBD(int idNotificacion, String correo) {
		String com = "UPDATE Relacion SET leido = true WHERE id_noti = ? and correo = ?";
	    try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
	    	preparedStatement.setInt(1, idNotificacion);
	    	preparedStatement.setString(2, correo);
	    	preparedStatement.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Error al ejecutar la consulta: " + com);
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Obtiene el precio de una entrada mediante su código.
	 * @param codigoEntrada Código de la entrada.
	 * @return Precio de la entrada. Si no se encuentra, se devuelve 0.
	 */
	public static double obtenerPrecioEntrada(int codigoEntrada) {
        double precio = 0;

        // Utiliza el código de la entrada para obtener el precio
        String com = "SELECT precio FROM Entrada WHERE codigo = " + codigoEntrada;

        try {
            ResultSet resultSet = s.executeQuery(com);

            if (resultSet.next()) {
                precio = resultSet.getDouble("precio");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el precio de la entrada.");
            e.printStackTrace();
        }

        return precio;
    }
	
	/**
	 * Inserta una nueva valoración en la base de datos.
	 * @param id Identificador único de la valoración.
	 * @param usuarioRevisor Correo del usuario que realiza la valoración.
	 * @param usuarioValorado Correo del usuario que recibe la valoración.
	 * @param puntuacion Puntuación asignada en la valoración.
	 * @param comentario Comentario asociado a la valoración.
	 */
    public static void insertarValoracion(Integer id, String usuarioRevisor, String usuarioValorado, int puntuacion, String comentario) {
        String com = "INSERT INTO Valoracion (id, usuario_revisor, usuario_valorado, puntuacion, comentario) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
            preparedStatement.setInt(1, id);
        	preparedStatement.setString(2, usuarioRevisor);
            preparedStatement.setString(3, usuarioValorado);
            preparedStatement.setInt(4, puntuacion);
            preparedStatement.setString(5, comentario);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Valoración registrada exitosamente.");
            } else {
                System.out.println("No se pudo registrar la valoración.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar la valoración.");
            e.printStackTrace();
        }
    }
 
    /**
     * Obtiene la lista de valoraciones recibidas por un usuario.
     * @param usuario Usuario para el que se obtienen las valoraciones.
     * @return Lista de objetos Valoracion asociadas al usuario.
     */
    public static List<Valoracion> obtenerValoracionesPorUsuario(Usuario usuario) {
        String com = "SELECT * FROM Valoracion WHERE usuario_valorado = ?";
  
        List<Valoracion> valoraciones = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement(com)) {
            preparedStatement.setString(1, usuario.getCorreoUsuario());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int puntuacion = rs.getInt("puntuacion");
                String comentario = rs.getString("comentario");
                String usuarioRevisor = rs.getString("usuario_revisor");
                String usuarioA = rs.getString("usuario_valorado");
                Valoracion valoracion = new Valoracion(usuarioRevisor, usuarioA, puntuacion, comentario);
                valoraciones.add(valoracion);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener valoraciones.");
            e.printStackTrace();
        }
        return valoraciones;
    }
	
// Esto seria para marcar la entrada como comprada
//	String codigoEntrada = "tu_codigo_de_entrada";
//	String correoComprador = "correo_del_comprador";
//
//	baseDatos.marcarEntradaComoComprada(codigoEntrada, correoComprador);

}
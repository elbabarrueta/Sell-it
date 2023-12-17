package correoJUnitTest;

import static org.junit.Assert.*;
import org.junit.Test;

import ventanas.VentanaInicio;

public class ComprobarCorreosTest {

    @Test
    public void testValidarCorreo() {
        // Casos de correos válidos
        assertTrue(VentanaInicio.validarCorreo("usuario@dominio.com"));
        assertTrue(VentanaInicio.validarCorreo("nombre.apellido@dominio.co.uk"));

        // Casos de correos inválidos
        assertFalse(VentanaInicio.validarCorreo("")); // Correo vacío
        assertFalse(VentanaInicio.validarCorreo("correoInvalido")); // Sin @
        assertFalse(VentanaInicio.validarCorreo("usuario@dominio")); // Sin extensión (com, org, etc.)
        assertFalse(VentanaInicio.validarCorreo("correo espacios@dominio.com")); // Espacios en el correo
        assertFalse(VentanaInicio.validarCorreo("@dominio.com")); // Sin nombre de usuario
        assertFalse(VentanaInicio.validarCorreo("usuario@dominio@com")); // Múltiples @
    }

    @Test
    public void testConNumeros() {
        assertTrue(VentanaInicio.validarCorreo("nombre123@dominio.com")); // Con números
        assertTrue(VentanaInicio.validarCorreo("23@gmail.com")); // Con números, pero al inicio del nombre de usuario
    }

    @Test
    public void testConCaracteresEspeciales() {
        assertTrue(VentanaInicio.validarCorreo("usuario@dominio.com")); // Sin caracteres especiales
        assertTrue(VentanaInicio.validarCorreo("usuario+apellido@dominio.com")); // Con +
        assertTrue(VentanaInicio.validarCorreo("usuario.apellido@dominio.com")); // Con .
        assertTrue(VentanaInicio.validarCorreo("usuario_apellido@dominio.com")); // Con _
        assertTrue(VentanaInicio.validarCorreo("usuario-123@dominio.com")); // Con -
        assertTrue(VentanaInicio.validarCorreo("usuario&apellido@dominio.com")); // Con &
        assertTrue(VentanaInicio.validarCorreo("correo_con_numeros123@dominio.org"));
    }

    @Test
    public void testConStringVacio() {
        assertFalse(VentanaInicio.validarCorreo("")); // String vacío
        assertFalse(VentanaInicio.validarCorreo("   ")); // String de espacios
    }

    @Test
    public void testExtensionCorreo() {
        assertFalse(VentanaInicio.validarCorreo("usuario@dominio")); // Sin extensión (com, org, etc.)
        assertFalse(VentanaInicio.validarCorreo("usuario@dominio.")); // Extensión vacía
        assertFalse(VentanaInicio.validarCorreo("usuario@dominio.c")); // Extensión de una sola letra
        assertTrue(VentanaInicio.validarCorreo("usuario@dominio.co.uk")); // Extensión válida
    }
}

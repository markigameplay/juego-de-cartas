import javax.swing.*;
import java.awt.*;

public class Main {

    // Clase que define un Pokémon con sus atributos
    static class Pokemon {
        String nombre;  // Nombre del Pokémon
        String tipo;    // Tipo del Pokémon (fuego, agua, planta)
        int vida;       // Vida o HP del Pokémon
        int ataque;     // Poder de ataque del Pokémon
        ImageIcon imagen; // Imagen del Pokémon

        // Constructor de la clase Pokemon, que inicializa sus atributos
        public Pokemon(String nombre, String tipo, int vida, int ataque, ImageIcon imagen) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.vida = vida;
            this.ataque = ataque;
            this.imagen = imagen;
        }

        // Método que permite que un Pokémon ataque a otro
        public void atacar(Pokemon oponente) {
            int daño = calcularDaño(oponente);
            oponente.vida -= daño; // Resta el ataque al oponente
            if (oponente.vida < 0) oponente.vida = 0; // Evita que la vida sea negativa
            JOptionPane.showMessageDialog(null, this.nombre + " atacó a " + oponente.nombre + " causando " + daño + " de daño. " +
                            oponente.nombre + " tiene " + oponente.vida + " de vida restante.",
                    "Ataque", JOptionPane.INFORMATION_MESSAGE, this.imagen); // Muestra la imagen del atacante
        }

        // Método para calcular el daño basado en tipos
        private int calcularDaño(Pokemon oponente) {
            int daño = this.ataque;

            // Efectividad basada en tipos
            if (this.tipo.equals("fuego") && oponente.tipo.equals("planta")) {
                daño *= 2; // Fuego es fuerte contra Planta
            } else if (this.tipo.equals("agua") && oponente.tipo.equals("fuego")) {
                daño *= 2; // Agua es fuerte contra Fuego
            } else if (this.tipo.equals("planta") && oponente.tipo.equals("agua")) {
                daño *= 2; // Planta es fuerte contra Agua
            } else if (this.tipo.equals("planta") && oponente.tipo.equals("fuego")) {
                daño /= 2; // Planta es débil contra Fuego
            } else if (this.tipo.equals("fuego") && oponente.tipo.equals("agua")) {
                daño /= 2; // Fuego es débil contra Agua
            } else if (this.tipo.equals("agua") && oponente.tipo.equals("planta")) {
                daño /= 2; // Agua es débil contra Planta
            }

            return daño; // Retorna el daño calculado
        }

        // Método para verificar si el Pokémon sigue vivo
        public boolean estaVivo() {
            return this.vida > 0;
        }
    }

    // Método para escalar imágenes a un tamaño fijo
    private static ImageIcon escalarImagen(ImageIcon icono, int ancho, int alto) {
        Image imagenEscalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    // Método para seleccionar un Pokémon
    private static Pokemon seleccionarPokemon(Pokemon p1, Pokemon p2) {
        ImageIcon[] options = {
                p1.imagen,
                p2.imagen
        };

        String[] nombres = { p1.nombre, p2.nombre };

        int seleccion = JOptionPane.showOptionDialog(null,
                "Elige tu Pokémon:",
                "Selección de Pokémon",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, // No hay icono
                options,
                null);

        if (seleccion == 0) {
            return p1; // Retorna el Pokémon 1 (p1)
        } else {
            return p2; // Retorna el Pokémon 2 (p2)
        }
    }

    // Método principal que ejecuta el juego
    public static void main(String[] args) {
        // Cargamos las imágenes de los Pokémon y las escalamos
        ImageIcon imagenCharmander = escalarImagen(new ImageIcon("C:\\Users\\mauro\\IdeaProjects\\JuegoPokemonCartas\\src\\charmander.jpeg"), 300, 400);
        ImageIcon imagenSquirtle = escalarImagen(new ImageIcon("C:\\Users\\mauro\\IdeaProjects\\JuegoPokemonCartas\\src\\sq.jpeg"), 300, 400);

        // Creación de los Pokémon
        Pokemon charmander = new Pokemon("Charmander", "fuego", 100, 20, imagenCharmander);
        Pokemon squirtle = new Pokemon("Squirtle", "agua", 100, 15, imagenSquirtle);

        // Selecciona el Pokémon del jugador
        Pokemon jugador = seleccionarPokemon(charmander, squirtle);
        Pokemon oponente = (jugador == charmander) ? squirtle : charmander;

        // Bucle del juego: ambos atacan hasta que uno se quede sin vida
        while (jugador.estaVivo() && oponente.estaVivo()) {
            // Turno del jugador
            int respuesta = JOptionPane.showConfirmDialog(null, "¿" + jugador.nombre + " quiere atacar?", "Turno de " + jugador.nombre, JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                jugador.atacar(oponente);
            }

            // Verificamos si el oponente sigue vivo
            if (!oponente.estaVivo()) {
                JOptionPane.showMessageDialog(null, "¡" + jugador.nombre + " ha ganado! " + oponente.nombre + " ha sido derrotado.", "Fin del juego", JOptionPane.INFORMATION_MESSAGE, jugador.imagen);
                break;
            }

            // Turno del oponente (Computadora) - Ataca automáticamente
            JOptionPane.showMessageDialog(null, "Es el turno de " + oponente.nombre + " para atacar.", "Turno de " + oponente.nombre, JOptionPane.INFORMATION_MESSAGE, oponente.imagen);
            oponente.atacar(jugador);

            // Verificamos si el jugador sigue vivo
            if (!jugador.estaVivo()) {
                JOptionPane.showMessageDialog(null, "¡" + oponente.nombre + " ha ganado! " + jugador.nombre + " ha sido derrotado.", "Fin del juego", JOptionPane.INFORMATION_MESSAGE, oponente.imagen);
                break;
            }
        }
    }
}

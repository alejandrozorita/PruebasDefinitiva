package tp.pr2;

import java.util.Scanner;

import tp.pr2.control.Controlador;
import tp.pr2.logica.Partida;
import tp.pr2.logica.Tablero;

public class Main {
	/**
	 * Punto de inicio del programa.
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Tablero tablero = new Tablero(7,6);
		tablero.reset();
		Partida partida = new Partida(tablero);
		Controlador controlador = new Controlador(partida, in);
		controlador.run();
	}
}

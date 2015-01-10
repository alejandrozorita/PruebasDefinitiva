package tp.pr2.control;

import java.util.Scanner;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Partida;
import tp.pr2.logica.Tablero;

public class Controlador {
	private Partida partida;
	private Scanner in;
	
	/**
	 * Constructor, con dos parametros una partida y un escaner.
	 * @param p
	 * @param in
	 */
	public Controlador(Partida p, Scanner in){
		partida = p;
		this.in = in;
	}
	
	/**
	 * Se encarga de la ejecucion del programa.
	 */
	public void run(){
		boolean enPartida = true;
		boolean ok;
		Ficha winner;
		int columna;
		do {
			partida.getTablero().pintarTablero();
			System.out.println();
			System.out.print("Juegan ");
			if (partida.getTurno() == Ficha.BLANCA) {
				System.out.println("blancas");
			} else {
				System.out.println("negras");
			}
			System.out.print("Que quieres hacer? ");
			String eleccion = in.next();
			eleccion = eleccion.toLowerCase();
			switch (eleccion) {
			case "poner":
				System.out.print("Introduce la columna: ");
				columna = in.nextInt();
				if ((columna <= 0) || (columna > partida.getTablero().getAncho())) {
					System.err.println("Movimiento incorrecto");
				} else {
					partida.ejecutaMovimiento(partida.getTurno(), columna);
					ok = partida.isTerminada();
					
					if (ok && partida.getTablas() == partida.getTablero().getAlto() *partida.getTablero().getAncho()) {
						partida.getTablero().pintarTablero();
						partida.setGanador(Ficha.VACIA);
						System.out.println("La partida ha acabado en tablas");
						enPartida = false;
					}
					else if(ok){
						winner = partida.getTurnoAnterior();
						partida.getTablero().pintarTablero();
						partida.setGanador(winner);
						System.out.println(winner + " is the winner!!");
						enPartida = false;
					}
				}
				break;
			//EN ESPERA A TERMINAR PA TI ENTERO ALE
			case "deshacer":
				if (!partida.undo()) {
					System.err.println("Imposible deshacer.");
				}
				break;
			case "reiniciar":
				partida.reset();
				System.out.println("Partida reiniciada");	
				break;
			case "salir":
				enPartida = false; 
				break;	
			default:
				System.err.println("No te entiendo.");
				in.nextLine();
				break;
			}
		} while (enPartida);
	}	
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		Tablero tablero = new Tablero(6,5);
		tablero.reset();
		Partida partida = new Partida(tablero);
		Controlador controlador = new Controlador(partida, in);
		controlador.run();
	}
}

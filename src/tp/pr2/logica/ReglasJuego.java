package tp.pr2.logica;

public interface ReglasJuego {
	Tablero iniciaTablero();
	Ficha jugadorInicial();
	Ficha hayGanador(Movimiento ultimoMovimiento,Tablero t);
	boolean tablas(Ficha ultimoEnPoner,Tablero t);
	Ficha siguienteTurno(Ficha ultimoEnPoner,Tablero t);
	int getReglas();
}

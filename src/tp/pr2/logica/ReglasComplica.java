package tp.pr2.logica;

public class ReglasComplica implements ReglasJuego {
	
	private final int TX = 4, TY = 7;
	

	public ReglasComplica() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tablero iniciaTablero() {
		Tablero t = new Tablero(TX, TY);
		return t;
	}

	@Override
	public Ficha jugadorInicial() {
		Ficha f = Ficha.BLANCA;
		return f;
	}

	@Override
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		Ficha ganador = Ficha.VACIA;
		Partida p;
		p = new Partida(t);
		if(p.comprobarTodo(ultimoMovimiento)){
			ganador = ultimoMovimiento.getJugador();
		}

		return ganador;
	}

	@Override
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		Ficha siguiente = Ficha.BLANCA;
		if(ultimoEnPoner == Ficha.BLANCA){
			siguiente = Ficha.NEGRA;
		} else {
			siguiente = Ficha.BLANCA;
		}
		return siguiente;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
}

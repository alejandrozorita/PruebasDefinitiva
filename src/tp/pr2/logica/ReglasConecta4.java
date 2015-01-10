package tp.pr2.logica;

public class ReglasConecta4 implements ReglasJuego{

	private final int TX = 7, TY = 6;
	
	public  ReglasConecta4() {
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
		return ganador;
	}

	@Override
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {
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
	
	public static void main(String[] args){
		
	}

}

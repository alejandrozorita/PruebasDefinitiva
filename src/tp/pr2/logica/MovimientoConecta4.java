package tp.pr2.logica;

public class MovimientoConecta4 extends Movimiento {
	

	public MovimientoConecta4(int donde, Ficha color){
		jugador = color;
		ColumnaMovimiento = donde;
		
	}

	public Ficha getJugador() {
		return jugador;
	}

	public boolean ejecutaMovimiento(Tablero tab) {
		boolean posible = true;
			//Si la columna esta en rango pasa el if
			if (ColumnaMovimiento > 0 && ColumnaMovimiento < tab.getAncho()+1) {	
				//Si es posible ejecutar movimiento Salta este if y va al else
				if ((jugador == Ficha.VACIA) || (tab.fichaUltimaJugada(ColumnaMovimiento) < 0)) {
					posible = false;
				}
				else
				{
				//	reseteada = false;
					tab.setCasilla(ColumnaMovimiento, tab.fichaUltimaJugada(ColumnaMovimiento) + 1, jugador);
					if (jugador == Ficha.BLANCA) {
						jugador = Ficha.NEGRA;
					} else if(jugador == Ficha.NEGRA) {
						jugador = Ficha.BLANCA;
					}
				}
			} else{
				posible = false;
			}
		return posible;
	}

	public void undo(Tablero tab) {
		
	}
	
}

package tp.pr2.logica;

import tp.pr2.logica.Ficha;

public class MovimientoConecta4 extends Movimiento {
	
	private int claseRegla = 1;

	public MovimientoConecta4(int donde, Ficha color){
		jugador = color;
		ColumnaMovimiento = donde;
	}
	
	public int getClaseRegla(){
		return claseRegla;
	}
	
	public int getColumnaMovimiento(){
		return ColumnaMovimiento;
	}

	public Ficha getJugador() {
		return jugador;
	}

	public boolean ejecutaMovimiento(Tablero tab) {
		boolean posible = true;
			//Si la columna esta en rango pasa el if
			if (ColumnaMovimiento > 0 && ColumnaMovimiento < tab.getAncho()+1) {	
				//Si es posible ejecutar movimiento Salta este if y va al else
				if ((tab.fichaUltimaJugada(ColumnaMovimiento) < 0)) {
					posible = false;
				}
				else
				{
					tab.setCasilla(ColumnaMovimiento, tab.fichaUltimaJugada(ColumnaMovimiento) + 1, jugador);
				}
			}
			else {
				posible = false;
			}
		return posible;
	}

	public void undo(Tablero tab) {
		tab.setCasilla(ColumnaMovimiento, tab.fichaUltimaJugada(ColumnaMovimiento) + 2, Ficha.VACIA);
	}
	
}

package tp.pr2.logica;

public class MovimientoComplica extends Movimiento {
	
	private int claseRegla = 2;
	private Ficha fichaAnterior;
	private boolean columnaLlena = false;

	public MovimientoComplica(int donde, Ficha color){
		jugador = color;
		ColumnaMovimiento = donde;
	}
	
	public int getClaseRegla() {
		return claseRegla;
	}
	
	@Override
	public Ficha getJugador() {
		return jugador;
	}

	@Override
	public boolean ejecutaMovimiento(Tablero tab) {
		boolean posible = true;
		//Si la columna esta en rango pasa el if
		if (ColumnaMovimiento > 0 && ColumnaMovimiento < tab.getAncho()+1) {	
			//Si es posible ejecutar movimiento Salta este if y va al else
			if ((tab.fichaUltimaJugada(ColumnaMovimiento) < 0)) {
				columnaLlena = true;
				fichaAnterior = tab.getCasilla(ColumnaMovimiento, tab.getAlto());
				desplazarColumna(tab);
			} 
			else{
				fichaAnterior = tab.getCasilla(ColumnaMovimiento, tab.fichaUltimaJugada(ColumnaMovimiento) + 1);
			}
				tab.setCasilla(ColumnaMovimiento, tab.fichaUltimaJugada(ColumnaMovimiento) + 1, jugador);
		}
		else {
			posible = false;
		}
	return posible;
	}

	private void desplazarColumna(Tablero tab) {
		for (int i = tab.getAlto(); i > 0; i--) {
			tab.setCasilla(ColumnaMovimiento, i, tab.getCasilla(ColumnaMovimiento, i - 1)); 
		}
	}

	@Override
	public void undo(Tablero tab) {
		if((tab.fichaUltimaJugada(ColumnaMovimiento) < 0) && columnaLlena){
			desplazarColumnaArriba(tab);
			tab.setCasilla(ColumnaMovimiento, tab.getAlto(), fichaAnterior);
		}
		else {
			tab.setCasilla(ColumnaMovimiento, tab.fichaUltimaJugada(ColumnaMovimiento) + 2, Ficha.VACIA);
		}
	}

	private void desplazarColumnaArriba(Tablero tab) {
		for (int i = 0; i < tab.getAlto(); i++) {
			tab.setCasilla(ColumnaMovimiento, i + 1, tab.getCasilla(ColumnaMovimiento, i + 2)); 
		}
	}

	@Override
	public int getColumnaMovimiento() {
		return ColumnaMovimiento;
	}

}

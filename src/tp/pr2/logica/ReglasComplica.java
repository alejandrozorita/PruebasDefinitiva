package tp.pr2.logica;

public class ReglasComplica implements ReglasJuego {

	private final int TX = 4, TY = 7;
	
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
		if(comprobarAlto(t, ultimoMovimiento) || comprobarAncho(t, ultimoMovimiento) || comprobarDiagonal(t, ultimoMovimiento)){
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
	
	private boolean comprobarAlto(Tablero t , Movimiento m) {
		boolean altoOk = false;
		int fila, columna, contadorAlto = 0;
		Ficha casilla, siguienteCasilla;
		fila = GetFilaUltimoMovimiento(t , m) + 2;
		columna = m.getColumnaMovimiento();
		//COMPROBAR ALTO HACIA ABAJO
		if (fila + 2 < t.getAlto()) {
			do {
				casilla = t.getCasilla(columna, fila);
				siguienteCasilla = t.getCasilla(columna, fila + 1);
				if (casilla.equals(siguienteCasilla)) {
					contadorAlto++;
					fila++;
				} 
			} while (casilla.equals(siguienteCasilla) && contadorAlto < 3);
		}
		if (contadorAlto >= 3) {
			altoOk = true;
		}
		else{
			contadorAlto = 0;
		}
		return altoOk;
	}
	
	private boolean comprobarAncho(Tablero t , Movimiento m) {
		boolean anchoOk = false;
		int fila, columna, contadorAncho = 0;
		Ficha casilla, siguienteCasilla;
		fila = GetFilaUltimoMovimiento(t , m) + 2;
		columna = m.getColumnaMovimiento();
		//COMPROBAR EL ANCHO HACIA LA DERECHA 
		if (columna  < t.getAncho()) {
			do {
				casilla = t.getCasilla(columna, fila);
				siguienteCasilla = t.getCasilla(columna + 1, fila);
				if (casilla.equals(siguienteCasilla)) {
					contadorAncho++;
					columna++;
				} 
			} while (casilla.equals(siguienteCasilla) && contadorAncho < 3);
		}
		//------------------
		//COMPROBAR EL ANCHO HACIA LA IZQUIERDA 
		fila = GetFilaUltimoMovimiento(t , m) + 2;
		columna = m.getColumnaMovimiento();
			if (columna - 2 >= 0) {
				do {
					casilla = t.getCasilla(columna, fila);
					siguienteCasilla = t.getCasilla(columna -1, fila);
					if (casilla.equals(siguienteCasilla)) {
						contadorAncho++;
						columna--;
					} 
				} while (casilla.equals(siguienteCasilla) && contadorAncho < 3);
			}
		if (contadorAncho >= 3) {
			anchoOk = true;
		}
		else{
			contadorAncho = 0;
		}
		return anchoOk;
	}
	
	private boolean comprobarDiagonal(Tablero t , Movimiento m){
		int fila, columna;
		Ficha casilla, siguienteCasilla;
		boolean ok = false;
		fila = GetFilaUltimoMovimiento(t , m) + 2;
		columna = m.getColumnaMovimiento();
		//COMPROBAR DIAGONAL HACIA ARRIBA DERECHA
		int diagonalMayor = 0;
		int diagonalMenor = 0;
		while ((fila > 0) && (columna < t.getAncho())) {
			casilla = t.getCasilla(columna, fila);
			siguienteCasilla = t.getCasilla(columna +1, fila - 1);
			if ((casilla.equals(siguienteCasilla)) && (!casilla.equals(Ficha.VACIA))) {
				diagonalMayor++;
			}
			fila--;
			columna++;		
		}
		if (diagonalMayor >= 3) {
			ok = true;
		}
		//COMPROBAR DIAGONAL HACIA ARRIBA IZQUIERDA	
		fila = GetFilaUltimoMovimiento(t , m) + 2;
		columna = m.getColumnaMovimiento();
		while ((fila > 0) && (columna > 0)) {
			casilla = t.getCasilla(columna, fila);
			siguienteCasilla = t.getCasilla(columna -1, fila - 1);
			if ((casilla.equals(siguienteCasilla)) && (!casilla.equals(Ficha.VACIA))) {
				diagonalMenor++;
			}
			fila--;
			columna--;		
		}
		if (diagonalMenor >= 3) {
			ok = true;
		}
		//COMPROBAR DIAGONAL ABAJO IZQUIERDA
		fila = GetFilaUltimoMovimiento(t , m) + 2;
		columna = m.getColumnaMovimiento();
		while ((fila < t.getAlto()) && (columna > 0)) {
			casilla = t.getCasilla(columna, fila);
			siguienteCasilla = t.getCasilla(columna -1, fila +1);
			if ((casilla.equals(siguienteCasilla)) && (!casilla.equals(Ficha.VACIA))) {
				diagonalMayor++;
			}
			fila++;
			columna--;		
		}
		if (diagonalMayor >= 3) {
			ok = true;
		}
		//COMPROBAR DIAGONAL ABAJO DERECHA
		fila = GetFilaUltimoMovimiento(t , m) + 2;
		columna = m.getColumnaMovimiento();
		while ((fila < t.getAlto()) && (columna < t.getAncho())) {
			casilla = t.getCasilla(columna, fila);
			siguienteCasilla = t.getCasilla(columna +1, fila +1);
			if ((casilla.equals(siguienteCasilla)) && (!casilla.equals(Ficha.VACIA))) {
				diagonalMenor++;
			}
			fila++;
			columna++;		
		}
		if (diagonalMenor >= 3) {
			ok = true;
		}

		return ok;
	}
	
	private int GetFilaUltimoMovimiento(Tablero t, Movimiento m) {
		int fila = 0;
		fila = t.fichaUltimaJugada(m.getColumnaMovimiento());
		return fila;
	}

	@Override
	public int getReglas() {
		return 2;
	}

}

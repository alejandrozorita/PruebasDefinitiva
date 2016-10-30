package tp.pr2.logica;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Partida;
import tp.pr2.logica.Pila;
import tp.pr2.logica.Tablero;

/**
 * 
 * @author nico y alejandro
 *
 */
public class Partida {
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada;
	private Ficha ganador;
	private int tablas = 0;
	private boolean reseteada = false;
	private Pila pila;
	private int tipoPartida;
	private Ficha CuatroEnRaya = Ficha.VACIA;
	private Ficha CuatroEnRayaAnterior = Ficha.VACIA;
	private boolean terminadaDesplazamiento;
	private boolean desplazamiento = false;

	/**
	 * Constructor por parametros de Partida
	 * @param tablero
	 */
	public Partida(Tablero tablero){
		this.tablero = tablero;
		turno = Ficha.BLANCA;
		ganador = Ficha.VACIA;
		terminada = false;
		pila = new Pila();
	}

	/**
	 * Constructor sin parametros de partida
	 */
	public Partida(){
		this.tablero = new Tablero(7,6);
		turno = Ficha.BLANCA;
		ganador = Ficha.VACIA;
		terminada = false;
		pila = new Pila();
	}
	
	public Partida(ReglasJuego reglas){
		tipoPartida = reglas.getReglas();
		this.tablero = reglas.iniciaTablero();
		turno = reglas.jugadorInicial();
		ganador = Ficha.VACIA;
		terminada = false;
		pila = new Pila();
	}

	/**
	 * Resetea la partida poniendo todo a cero y reiniciando tablero
	 */
	public void reset(){
		tablero.reset();
		turno = Ficha.BLANCA;
		pila.reset();
		reseteada = true;
	}
	
	public void reset(ReglasJuego reglas){
		this.tablero = reglas.iniciaTablero();
		turno = reglas.jugadorInicial();
		ganador = Ficha.VACIA;
		reseteada = true;
		terminada = false;
	}
	
	/**
	 * Devuelve el estado de las jugadas totales
	 * @return tablas
	 */
	public int getTablas(){
		return tablas;
	}
	
	/**
	 * Asignamos el ganador a la ficha en juego
	 * @param ficha
	 */
	public void setGanador(Ficha ficha){
		ganador = ficha;
	}
	
/**
 * Ejecuta el movimiento de la partida en funcion de la ficha y la columna seleccionada
 * @param color
 * @param col
 * @return posible
 */
	
	public boolean ejecutaMovimiento(Movimiento movimiento){
		boolean posible = true;
		int auxContadorArrayJugadas = 1;
		
		//En caso de que la partida no este terminada
		if(!terminada){	
			//Si la columna esta en rango pasa el if
			if ((movimiento.getColumnaMovimiento() > 0) &&(movimiento.getColumnaMovimiento() < tablero.getAncho()+1)) {	
				//Si es posible ejecutar movimiento Salta este if y va al else
				if ((turno == Ficha.VACIA) ||(turno != movimiento.getJugador()) /*|| (tablero.fichaUltimaJugada(movimiento.getColumnaMovimiento()) < 0)*/) {
					posible = false;
				}
				else if((tablero.fichaUltimaJugada(movimiento.getColumnaMovimiento()) < 0) && movimiento.getClaseRegla() == 1) {
					posible = false;
				}
				else
				{
					reseteada = false;
					/*tablero.setCasilla(movimiento.getColumnaMovimiento(), tablero.fichaUltimaJugada(movimiento.getColumnaMovimiento()) + 1,  movimiento.getJugador());*/
					movimiento.ejecutaMovimiento(tablero);
					/*if (tipoPartida == 2) {
						if(tablero.fichaUltimaJugada(movimiento.getColumnaMovimiento()) < 0){
							desplazamiento = true;
							terminadaDesplazamiento = comprobarTodoDesplazamiento();
						}
						if (comprobarTodo()) {
							CuatroEnRaya = movimiento.getJugador();
						}
					}*/
					if (turno == Ficha.BLANCA) {
						turno = Ficha.NEGRA;
					} else if(turno == Ficha.NEGRA) {
						turno = Ficha.BLANCA;
					}
					if (pila.getContadorArrayJugadas() == 10) {
						pila.desplazarArray();
						auxContadorArrayJugadas = auxContadorArrayJugadas + pila.getContadorArrayJugadas();
						pila.setArrayJugadas(auxContadorArrayJugadas - 1, movimiento.getColumnaMovimiento());
					}
					else
					{
						auxContadorArrayJugadas = auxContadorArrayJugadas + pila.getContadorArrayJugadas();
						if(auxContadorArrayJugadas == 11){
							auxContadorArrayJugadas = 10;
						}
						pila.setArrayJugadas(auxContadorArrayJugadas, movimiento.getColumnaMovimiento());
						pila.plusPlusContador();
					}
					pila.aumentarContador();
					//mueves el array una posici�n a la izquierda
					if(movimiento.getClaseRegla() == 1){
						tablas++;
					}
				}
			}
			else {
				posible = false;
			}
		}else {
			posible = false;
		}
		return posible;
	}

	/**
	 * Nos da la columna donde se puso la ficha en el ultimo movimiento
	 * @return arrayJugadas[contadorArrayJugadas - 1]
	 */
	private int GetColumnaUltimoMovimiento(){
		int [] auxArray = pila.getArrayJugadas();
		return auxArray[pila.getContadorArrayJugadas() - 1];
	}

	/**
	 * Devuleve la fila del último movimiento
	 * @return fila
	 */
	private int GetFilaUltimoMovimiento(){
		int fila = 0;
		int [] auxArray = pila.getArrayJugadas();
		if(pila.getContadorArrayJugadas() == 0)
			pila.setContadorArrayJugadas(1);
		fila = tablero.fichaUltimaJugada(auxArray[pila.getContadorArrayJugadas() - 1]);
		return fila;
	}

	/**
	 * Esta funcion deshace el ultimo movimiento de la partida siempre que sea posible.
	 * @return ok
	 */
	public boolean undo(){
		boolean ok = true;
		if (turno == Ficha.BLANCA && pila.getContadorArrayJugadas() == 0 && tablas == 0){
			ok = false;
		}
		else if(reseteada) {
			ok = false;
		}
		else if(pila.getContadorArrayJugadas() == 0){
			ok = false;
		}
		else{
			pila.undo(turno, tablas, reseteada, tablero);
			setTurno();
			}
		return ok;
	}
	
	/**
	 * Llamamos a la funcion getTurnoAnterior para devolver el turno anterior al actual
	 */
	public void setTurno() {
		turno = getTurnoAnterior();
	}

	/**
	 * Devolvemos el ganador o su es tablas, devolvemos ficha vacia
	 * @return ganador
	 */
	public Ficha getGanador(){
		if (isTerminada()) {
			if (getTablas() == getTablero().getAlto() *getTablero().getAncho()) {
				ganador = Ficha.VACIA;
			} else{
				ganador = getTurnoAnterior();
			}
		}
		return ganador;
	}

	/**
	 * Verificamos si la partida esta terminada de alguna de todas las posibls maneras
	 * @return terminada
	 */
	public boolean isTerminada() {
		terminada = false;
		/*if(CuatroEnRaya != 1){
			CuatroEnRaya = 0;
		}*/
		if ((tablas == 0) && (tipoPartida == 1)) {
			terminada = false;
		}
		else if(pila.getContadorArrayJugadas() == 0){
			terminada = false;
		}
		/*else if(tipoPartida == 2 && (comprobarAncho() || comprobarAlto() || comprobarDiagonal())){
			
			if (desplazamiento) {
				if (CuatroEnRaya == getTurnoAnterior()) {
					terminada = true;
				}
			} else {
				if(comprobarTodo()){
					terminada = true;
				}
			}
		}
		
		else if(tipoPartida == 2){
			if(desplazamiento){
				if (comprobarTodoDesplazamiento()) {
					terminada = true;
				}	
			}
			else {
				if(comprobarTodo()){
					terminada = true;
				}
			}
		}
		*/
		else if(tipoPartida == 2 && comprobar()){
			terminada = true;
		}
		else if (tipoPartida == 1 && comprobarAncho() || comprobarAlto() || comprobarDiagonal()) {
			terminada = true;
		}
		else if ((tablas == getTablero().getAlto() *getTablero().getAncho()) && (tipoPartida == 1)) {
			terminada = true;
		}
		else if (pila.getContadorArrayJugadas() == 0) {
			terminada = false;
		}
		/*else if (terminadaDesplazamiento) {
			terminada = true;
		}*/
		return terminada;
	}

	/**
	 * Comprobamos la columna donde se ha puesto la ficha
	 * @return altoOk
	 */
	private boolean comprobarAlto() {
		boolean altoOk = false;
		int fila, columna, contadorAlto = 0;
		Ficha casilla, siguienteCasilla;
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		//COMPROBAR ALTO HACIA ABAJO
		if (fila + 2 < tablero.getAlto()) {
			do {
				casilla = tablero.getCasilla(columna, fila);
				siguienteCasilla = tablero.getCasilla(columna, fila + 1);
				if (casilla.equals(siguienteCasilla) && !casilla.equals(Ficha.VACIA)) {
					contadorAlto++;
					fila++;
				} 
			} while (casilla.equals(siguienteCasilla) && contadorAlto < 3 && (!casilla.equals(Ficha.VACIA)));
		}
		if (contadorAlto >= 3) {
			altoOk = true;
		}
		else{
			contadorAlto = 0;
		}
		return altoOk;
	}

	/**
	 * Comprobamos la fila donde se ha puesto la ficha
	 * @return anchoOk
	 */
	private boolean comprobarAncho() {
		boolean anchoOk = false;
		int fila, columna, contadorAncho = 0;
		Ficha casilla, siguienteCasilla;
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		//COMPROBAR EL ANCHO HACIA LA DERECHA 
		if (columna  < tablero.getAncho()) {
			do {
				casilla = tablero.getCasilla(columna, fila);
				siguienteCasilla = tablero.getCasilla(columna + 1, fila);
				if (casilla.equals(siguienteCasilla) && (!casilla.equals(Ficha.VACIA))) {
					contadorAncho++;
					columna++;
				} 
			} while (casilla.equals(siguienteCasilla) && contadorAncho < 3 && (!casilla.equals(Ficha.VACIA)));
		}
		//------------------
		if (contadorAncho != 3) {
			contadorAncho = 0;
		}
		//COMPROBAR EL ANCHO HACIA LA IZQUIERDA 
		if(contadorAncho == 0){
			if (columna - 2 > 0) {
				do {
					casilla = tablero.getCasilla(columna, fila);
					siguienteCasilla = tablero.getCasilla(columna -1, fila);
					if (casilla.equals(siguienteCasilla) && (!casilla.equals(Ficha.VACIA))) {
						contadorAncho++;
						columna--;
					} 
				} while (casilla.equals(siguienteCasilla) && contadorAncho < 3 && (!casilla.equals(Ficha.VACIA)));
			}
		}
		if (contadorAncho >= 3) {
			anchoOk = true;
		}
		return anchoOk;
	}

	/**
	 * Comprobamos las diagonales desde la posicion de la ficha
	 * @return ok
	 */
	private boolean comprobarDiagonal(){
		int fila, columna;
		Ficha casilla, siguienteCasilla;
		boolean ok = false;
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		//COMPROBAR DIAGONAL HACIA ARRIBA DERECHA
		int diagonalMayor = 0;
		int diagonalMenor = 0;
		while ((fila > 0) && (columna < tablero.getAncho())) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna +1, fila - 1);
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
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila > 0) && (columna > 0)) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna -1, fila - 1);
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
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila < tablero.getAlto()) && (columna > 0)) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna -1, fila +1);
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
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila < tablero.getAlto()) && (columna < tablero.getAncho())) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna +1, fila +1);
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

	/**
	 * Devuelve el tablero que usaremos en la partida
	 * @return tablero
	 */
	public Tablero getTablero(){
		return tablero;
	} 
	
	/**
	 * Devuelve la ficha del turno
	 * @return turno
	 */
	public Ficha getTurno(){
		return turno;
	}
	
	/**
	 * Nos da el turno anterior a la ficha actual
	 * @return turno
	 */
	public Ficha getTurnoAnterior(){
		Ficha f = Ficha.VACIA;
		if (turno == Ficha.BLANCA) {
			f = Ficha.NEGRA;
		} else if (turno == Ficha.NEGRA){
			f = Ficha.BLANCA;
		}
		return f;
	}
	
	public static void main(String[] args) {
		Partida p = new Partida(new ReglasComplica());
		Tablero t = p.getTablero();
		t.setCasilla(1, 7, Ficha.NEGRA);
		t.setCasilla(2, 7, Ficha.NEGRA);
		t.setCasilla(3, 7, Ficha.NEGRA);
		t.setCasilla(4, 7, Ficha.NEGRA);
		t.setCasilla(1, 6, Ficha.BLANCA);
		t.setCasilla(2, 6, Ficha.BLANCA);
		t.setCasilla(3, 6, Ficha.BLANCA);
		t.pintarTablero();
		Movimiento mov = new MovimientoComplica(4, Ficha.BLANCA);
		p.ejecutaMovimiento(mov);
		t.pintarTablero();
		if (p.isTerminada()) {
			System.out.println("hol");
		}
	}


	/*private boolean comprobarTodo() {
		boolean ok = false;
		boolean cuatroRaya = false;
		int cantidad = 0;
		for (int i = 1; i < tablero.getAlto(); i++) {
			for (int j = 1; j < tablero.getAncho(); j++) {
				if (comprobarAncho(i, j) || comprobarAlto(i, j) || comprobarDiagonal(i, j)) {
					cuatroRaya = true;
					}
				}
			if(cuatroRaya == true)
				cantidad++;
			}
		if(cantidad == 1){
			ok = true;
		}
		return ok;
	}*/
	
	/*private boolean comprobarTodoDesplazamiento(){
		boolean ok = false; 	
			for (int i = 0; i < tablero.getAlto()-1; i++) {
				for (int j = 0; j < tablero.getAncho()-1; j++) {
					if (comprobarAncho(i, j) || comprobarAlto(i, j) || comprobarDiagonal(i, j)) {
						CuatroEnRaya = tablero.getCasilla(i, j);
						if (CuatroEnRayaAnterior == Ficha.VACIA) {
							CuatroEnRayaAnterior = CuatroEnRaya;
							ok = true;
						}
						else if (CuatroEnRaya != CuatroEnRayaAnterior) {
							return false;
						}
					}
				}
		}	
		return ok;
	}*/
	
	private boolean comprobar(){
		Ficha [] array;
		array = new Ficha[tablero.getAlto()*tablero.getAncho()];
		boolean ok = true;
		int contadorArray = 0;
		for (int i = 0; i < tablero.getAlto(); i++) {
			for (int j = 0; j < tablero.getAncho(); j++) {
				if (comprobarAncho(i, j) || comprobarAlto(i, j) || comprobarDiagonal(i, j)) {
					array[contadorArray] = tablero.getCasilla(j, i);
					contadorArray++;
				}
			}
		}
		if (recorrerArray(array)) {
			ok = false;
		}
		return ok;
	}
	
	private boolean recorrerArray(Ficha [] array){
		boolean masDeUno = true;
		int contador = 0;
		int i = 0;
		while(array[i] == array[i + 1] && i < array.length){
			contador++;
			i++;
		}
		if (contador == 1) {
			masDeUno = false;
		}
		return masDeUno;
	}
	
	private boolean comprobarAlto(int columnad, int filad) {
		boolean altoOk = false;
		int fila, columna, contadorAlto = 0;
		Ficha casilla, siguienteCasilla;
		fila = filad;
		columna = columnad;
		//COMPROBAR ALTO HACIA ABAJO
		if (fila + 2 < tablero.getAlto()) {
			do {
				casilla = tablero.getCasilla(columna, fila);
				siguienteCasilla = tablero.getCasilla(columna, fila + 1);
				if (casilla.equals(siguienteCasilla) && !casilla.equals(Ficha.VACIA)) {
					contadorAlto++;
					fila++;
				} 
			} while (casilla.equals(siguienteCasilla) && contadorAlto < 3 && (!casilla.equals(Ficha.VACIA)));
		}
		if (contadorAlto >= 3) {
			altoOk = true;
		}
		else{
			contadorAlto = 0;
		}
		return altoOk;
	}
	
	private boolean comprobarAncho(int columnad, int filad) {
		boolean anchoOk = false;
		int fila, columna, contadorAncho = 0;
		Ficha casilla, siguienteCasilla;
		fila = filad;
		columna = columnad;
		//COMPROBAR EL ANCHO HACIA LA DERECHA 
		if (columna  < tablero.getAncho()) {
			do {
				casilla = tablero.getCasilla(columna, fila);
				siguienteCasilla = tablero.getCasilla(columna + 1, fila);
				if (casilla.equals(siguienteCasilla) && (!casilla.equals(Ficha.VACIA))) {
					contadorAncho++;
					columna++;
				} 
			} while (casilla.equals(siguienteCasilla) && contadorAncho < 3 && (!casilla.equals(Ficha.VACIA)));
		}
		//------------------
		if (contadorAncho != 3) {
			contadorAncho = 0;
		}
		//COMPROBAR EL ANCHO HACIA LA IZQUIERDA 
		if(contadorAncho == 0){
			if (columna - 2 > 0) {
				do {
					casilla = tablero.getCasilla(columna, fila);
					siguienteCasilla = tablero.getCasilla(columna -1, fila);
					if (casilla.equals(siguienteCasilla) && (!casilla.equals(Ficha.VACIA))) {
						contadorAncho++;
						columna--;
					} 
				} while (casilla.equals(siguienteCasilla) && contadorAncho < 3 && (!casilla.equals(Ficha.VACIA)));
			}
		}
		if (contadorAncho >= 3) {
			anchoOk = true;
		}
		return anchoOk;
	}
	
	private boolean comprobarDiagonal(int columnad, int filad){
		int fila, columna;
		Ficha casilla, siguienteCasilla;
		boolean ok = false;
		fila = filad;
		columna = columnad;
		//COMPROBAR DIAGONAL HACIA ARRIBA DERECHA
		int diagonalMayor = 0;
		int diagonalMenor = 0;
		while ((fila > 0) && (columna < tablero.getAncho())) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna +1, fila - 1);
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
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila > 0) && (columna > 0)) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna -1, fila - 1);
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
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila < tablero.getAlto()) && (columna > 0)) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna -1, fila +1);
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
		fila = GetFilaUltimoMovimiento() + 2;
		columna = GetColumnaUltimoMovimiento();
		while ((fila < tablero.getAlto()) && (columna < tablero.getAncho())) {
			casilla = tablero.getCasilla(columna, fila);
			siguienteCasilla = tablero.getCasilla(columna +1, fila +1);
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
	
}



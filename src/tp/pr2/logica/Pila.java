package tp.pr2.logica;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public class Pila {
	private int [] arrayJugadas;
	private int contadorArrayJugadas;
	private Ficha[] arrayFichasUndo;
	private int posicionUltimaFichaEnI;
	private int auxContadorArrayJugadas;
	
	public Pila(){
		this.arrayJugadas = new int[10];
		contadorArrayJugadas = 0;
		arrayFichasUndo = new Ficha[10];
	}
	
	public void reset(){
		contadorArrayJugadas = 0;
		arrayJugadas[contadorArrayJugadas] = -1;
	}
	
	public void desplazarArray(){
		for (int i = 0; i < arrayJugadas.length-1;i++) {
			arrayJugadas[i] = arrayJugadas[i + 1];
		}
	}
	
	public void plusPlusContador(){
		++contadorArrayJugadas;
	}
	
	public void aumentarContador(){
		if(contadorArrayJugadas == 11) {
			desplazarArray();
		}
	}
	
	public void disminuirContador(){
		if (contadorArrayJugadas != 0) {
			contadorArrayJugadas--;
		}
	}
	
	public void setArrayJugadas(int contador, int columna){
		arrayJugadas[contador - 1] = columna;
	}
	public int [] getArrayJugadas(){
		return arrayJugadas;
	}
	
	public int getContadorArrayJugadas(){
		return contadorArrayJugadas;
	}
	
	public void setContadorArrayJugadas(int i) {
		contadorArrayJugadas = i;
	}
	
	public int posicionUltimaFichaI(int columna){
		//Sacamos la posicion Y del tablero de la última jugada
		return posicionUltimaFichaEnI = columna;
	}
	
	public void undo (Ficha turno, int tablas, boolean reseteada,Tablero tablero){
		@SuppressWarnings("unused")
		boolean ok = true;
		if (turno == Ficha.BLANCA && contadorArrayJugadas == 0 && tablas == 0){
			ok = false;
		}
		else if(reseteada) {
			ok = false;
		}
		else{
			
			//Iniciamos contador para no machacar contador original de partida
			auxContadorArrayJugadas = 0;

			//Si contador es mayor que length de array es porque apunta a ultima direcccion
			if (contadorArrayJugadas > arrayJugadas.length - 1) {
				auxContadorArrayJugadas = arrayJugadas.length;
			}
			else {
				auxContadorArrayJugadas = contadorArrayJugadas;
			}
			if (arrayJugadas[auxContadorArrayJugadas -1] < 0) {
				ok = false;
			}
			else{
			
			//Pasamos X e Y a tablero para que ponga vacia en la posicion de la ultima jugada
			tablero.setCasilla(/*Pasamos la x*/arrayJugadas[contadorArrayJugadas - 1], /*Pasamos la i*/(posicionUltimaFichaI(tablero.fichaUltimaJugada(arrayJugadas[contadorArrayJugadas - 1])) + 2), /*Pasamos la vacia*/Ficha.VACIA);
			//Ponemos a -1 launcher posicion del array
			arrayJugadas[contadorArrayJugadas-1] = -1;
			contadorArrayJugadas = auxContadorArrayJugadas;
			disminuirContador();
			//Quitamos un movimiento en el contador de partida en tablas
			tablas--;
			//Asignamos el turno
			// setTurno();
			}
		}
	}
	
	/**
	 * Funcion tipoca Set para el array de tipo Movimiento del Complica
	 * 
	 * @return arrayMovimientosUndo
	 */
	public Ficha[] getArrayFichaUndo(){
		return arrayFichasUndo;
	}
	
	/**
	 * Recibe 2 parametros, contador INT y m Movimiento.
	 * 
	 * Hace funcion tipica Set y guarda en la posicion contador, el Movimiento m
	 * 
	 * @param contador
	 * @param m
	 * 
	 * 
	 */
	public void setArrayMoimientosJugadas(int contador, Ficha f){
		arrayFichasUndo[contador - 1] = f;
	}

}

package tp.pr2.logica;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public class Pila {
	private Movimiento [] arrayJugadas;
	private int contadorArrayJugadas;
	
	public Pila(){
		this.arrayJugadas = new Movimiento[10];
		contadorArrayJugadas = 0;
	}
	
	public void reset(){
		contadorArrayJugadas = 0;
		arrayJugadas[contadorArrayJugadas].setJugador(Ficha.BLANCA);
		arrayJugadas[contadorArrayJugadas].setColumnaMoviminento(-1);
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
		arrayJugadas[contador - 1].ColumnaMovimiento = columna;
	}
	public Movimiento [] getArrayJugadas(){
		return arrayJugadas;
	}
	
	public int getContadorArrayJugadas(){
		return contadorArrayJugadas;
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
			int auxContadorArrayJugadas = 0;

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
			//Sacamos la posicion Y del tablero de la última jugada
			int posicionUltimaFichaEnI = (tablero.fichaUltimaJugada(arrayJugadas[contadorArrayJugadas - 1]));
			//Pasamos X e Y a tablero para que ponga vacia en la posicion de la ultima jugada
			tablero.setCasilla(/*Pasamos la x*/arrayJugadas[contadorArrayJugadas - 1], /*Pasamos la i*/(posicionUltimaFichaEnI + 2), /*Pasamos la vacia*/Ficha.VACIA);
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

	public void setContadorArrayJugadas(int i) {
		contadorArrayJugadas = i;
	}
	
	
}

package tp.pr2.logica;

public class Tablero {
	
	private Ficha [ ][ ] tablero; 
	private int ancho;
	private int alto;

	/**
	 * Constructor, con dos valores que representan ancho y alto.
	 * Ademas inicializa todas las posiciones del tablero a vacia.
	 * @param tx
	 * @param ty
	 */
	public Tablero(int tx, int ty){//CONTSTRUCTOR CON VALORES
		ancho = tx;
		alto = ty;
		if (ancho <= 0 || alto <= 0) {//SI EL ANCHO O EL ALTO ES IMPOSIBLE GENERA UN TABLERO DE TAMA�O 1,1
			ancho = 1;
			alto = 1;
		}
		this.tablero  = new Ficha[ancho][alto];
		for (int i =0; i < ancho; ++i) {
			for (int j = 0; j < alto; ++j) { 
				tablero[i][j] = Ficha.VACIA; // RELLENA TODAS LA POSICIONES A VACIA
			}
		}
	}
	
	/**
	 * Constructor, sin valores.
	 * Crea un tablero predeterminado de 8 por 10.
	 */
	public Tablero(){
		this.tablero  = new Ficha[8][10]; //CONSTRUCTOR SIN VALORES CREA UN TABLERO DE 8,10
	}

	/**
	 * Muestra el tablero en pantalla.
	 */
	public void pintarTablero(){
		System.out.println(toString()); 
	}
	
	/**
	 * Muestra una ficha por pantalla.
	 * @param ficha
	 * @return tipoFicha
	 */
	private String pintarFicha(Ficha ficha){
		String tipoFicha = "";	// SE LE PASA UN ENUMERADO Y
		switch (ficha) {		//DEVUELVE EL TIPO DE FICHA CORRESPONDIENTE
		case VACIA:
			tipoFicha = " ";
			break;
		case NEGRA:
			tipoFicha = "X";
			break;
		case BLANCA:
			tipoFicha = "O";
			break;		
		default:
			break;
		}
		return tipoFicha;
	}
	
	/**
	 * Crea el string que representa el tablero.
	 */
	public String toString(){
		String strTablero = "";
		for (int y = 0; y < alto; y++) {
			strTablero += "|";
			for (int x = 0; x < ancho; x++) { 
				strTablero += pintarFicha(tablero[x][y]);
			}
			strTablero += "|";
			strTablero += "\n";
		}
		strTablero += "+";
		for (int i = 0; i < ancho; i++) {
			strTablero += "-";
		}
		strTablero += "+";
		strTablero += "\n";
		strTablero += " ";
		for (int i = 0; i < ancho; i++) {
			strTablero += i+1;
		}
		strTablero += " ";
		
		return strTablero;
	}
	
	/**
	 * Reinicia un tablero colocando todas las posiciones a vacia.
	 */
	public void reset(){
		for (int i =0; i < ancho; ++i) {
			for (int j = 0; j < alto; ++j) { 
				tablero[i][j] = Ficha.VACIA;
			}
		}
	}
	
	/**
	 * Retorna el alto del tablero
	 * @return alto
	 */	
	public int getAlto(){
		return alto;
	}
	
	/**
	 * Retorna el ancho del tablero
	 * @return ancho
	 */
	public int getAncho(){
		return ancho;
	}
	
	/**
	 * Devuelve una ficha dados un ancho y alto.
	 * @param x
	 * @param y
	 * @return tablero[aux_x][aux_y]
	 */
 	public Ficha getCasilla(int x, int y) {
 		int aux_x, aux_y;
 		aux_x = x - 1;
 		aux_y = y - 1;
		if (aux_x < 0 || aux_x >= getAncho()) {
			return Ficha.VACIA;
		}
 		else if(aux_y < 0 || aux_y >= getAlto()){
 			return Ficha.VACIA;
 		}
 		return tablero[aux_x][aux_y];
 	}
 	
 	/**
 	 * Coloca una ficha en una posicion dada del tablero y un color.
 	 * @param x
 	 * @param y
 	 * @param color
 	 */
 	public void setCasilla(int x,int y, Ficha color) {
 		if((x > 0) && (y > 0) && (x <= ancho) && (y <= alto)){
 		tablero[x-1][y-1] = color;
 		}
 	}
	
 	/**
 	 * Retorna la fila superior a la ultima ficha jugada.
 	 * @param x
 	 * @return fila
 	 */
 	public int fichaUltimaJugada(int x) {
 		int fila =-1;
 		if(x > 0){
	 		for (int i = 0; i < alto; i++) {
	 			if (tablero[x-1][i].equals(Ficha.VACIA) ) {
					fila = i; 
				}
			}
 		} 
 		return fila;
 	}
 	
 	public void desplazarColumnaComplica(int columna){
 		for (int i = 1; i < getAlto() ;i++) {
			setCasilla(i, columna, getCasilla(i-1, columna));
		}
 		setCasilla(getAlto()-1, columna, Ficha.VACIA);
 	}
	
 	public static void main(String[] args) {
 		Tablero nuevoTablero = new Tablero(5,5);
 		nuevoTablero.setCasilla(1, 1, Ficha.BLANCA);
 		nuevoTablero.pintarTablero();
 	}
}

package tp.pr2.logica;

public abstract class Movimiento {
	
	protected Ficha jugador;
	protected int ColumnaMovimiento;
	
	
	public abstract Ficha getJugador();
	public abstract boolean ejecutaMovimiento(Tablero tab);
	public abstract void undo(Tablero tab);	
	public abstract int getClaseRegla();
	public abstract int getColumnaMovimiento();
	public void setJugador(Ficha jugador){
		this.jugador = jugador;
	}
	public void setColumnaMoviminento(int i) {
		this.ColumnaMovimiento = i;
	}
	
}

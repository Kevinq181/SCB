package com.kjquito.rdf.entity;

public class Results1 {

	int cantidad;
	String nombre;
	String fecha;

	public Results1(int cantidad, String nombre, String fecha) {
		super();
		this.cantidad = cantidad;
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}

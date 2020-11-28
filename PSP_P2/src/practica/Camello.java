package practica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Camello implements Runnable, Comparable<Camello> {

	private String nombre;
	private int recorrido;
	private static int numCamellos;
	private static int distancia;
	private Boolean finCarrera = false;
	public static ArrayList<Camello> posiciones = new ArrayList<>();

	public Camello() {
		this.nombre = "";
		this.recorrido = 0;
	}

	public Camello(String nombre) {
		this.nombre = nombre;
		this.recorrido = 0;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getRecorrido() {
		return recorrido;
	}

	public void setRecorrido(int recorrido) {
		this.recorrido = recorrido;
	}

	public String toString() {
		int fin = (distancia-getRecorrido());
		if(fin == 0) {
			return getNombre();
		} else if (fin == 1) {
			return getNombre() + " a " + fin + " posición.";
		} else {
			return getNombre() + " a " + fin + " posiciones";
		}
	}

	public void run() {
		try	{
			while(finCarrera==false) {
				Random x = new Random();
				int y = (int) (x.nextDouble()*750);
				Thread.sleep(y);
				cursoCarrera();
			}
			if(finCarrera == false){
				Thread.interrupted();
			}
		}
		catch (InterruptedException e) {
			e.getMessage();
		}
	}

	private synchronized void cursoCarrera() {
		tirada();
		if(getRecorrido() >= distancia) {
			setRecorrido(distancia);
			finCarrera = true;
			System.out.println(" ");
			System.out.println("¡¡¡" + getNombre() + " finaliza la carrera el 1º!!!");
			System.out.println("¡¡¡Ha ganado!!!");
			System.out.println(" ");
			System.out.println("El ranking ha quedado del siguiente modo:");
			orden();
			System.exit(1);
		} else {
			System.out.println(" ");
		}
	}
	
	private void orden() {
		Collections.sort(posiciones);
		for(int i = 0; i<posiciones.size(); i++) {
			System.out.println((i+1) + "º - " + posiciones.get(i));
		}
	}

	private synchronized void tirada() {
		Random z = new Random();
		int k = (int) (z.nextDouble()*10);
		String nombre = Thread.currentThread().getName();
		int restante = distancia-getRecorrido();
		String mensaje = "";
		if(restante == 1) {
			mensaje = "posición";
		} else {
			mensaje = "posiciones";
		}
		if(k >= 9) {
			System.out.println("¡¡¡" + nombre + " acierta en el rojo!!!");
			if(getRecorrido()+3>=distancia) {
				setRecorrido(distancia);
				System.out.println(nombre + " avanza " + restante + " " + mensaje + " y lleva " 
						+ getRecorrido() + " posiciones. " + posicionActual(nombre));
			} else {
				setRecorrido(getRecorrido() + 3);
				System.out.println(nombre 
						+ " avanza 3 posiciones y lleva " +getRecorrido()
						+" posiciones. " + posicionActual(nombre));
			}			
		} else if(k >= 7) {
			System.out.println("¡" + nombre 
					+ " la cuela por un agujero azul!");
			if(getRecorrido()+2>=distancia) {
				setRecorrido(distancia);
				System.out.println(nombre + " avanza " + restante + " " + mensaje + " y lleva " 
						+ getRecorrido() + " posiciones. " + posicionActual(nombre));
			} else {
				setRecorrido(getRecorrido() + 2);
				System.out.println(nombre
						+ " avanza 2 posiciones y lleva " +getRecorrido()
						+" posiciones. " + posicionActual(nombre));
			}
		} else if(k >= 3) {
			System.out.println(nombre
					+ " introduce su bola en un agujero amarillo");
			if(getRecorrido()+1>=distancia) {
				setRecorrido(distancia);
				System.out.println(nombre + " avanza " + restante + " " + mensaje + " y lleva " 
						+ getRecorrido() + " posiciones. " + posicionActual(nombre));
			} else {
				setRecorrido(getRecorrido() + 1);
				System.out.println(nombre 
						+ " avanza 1 posición y lleva " +getRecorrido()
						+" posiciones. " + posicionActual(nombre));
			}
		} else {
			System.out.println("¡" + Thread.currentThread().getName() 
					+ " ha fallado!");
			System.out.println(Thread.currentThread().getName() 
					+ " no avanza y lleva " +getRecorrido()
					+" posiciones. " + posicionActual(Thread.currentThread().getName()));
		}
	}

	private synchronized String posicionActual(String nombre) {
		String res = "";
		int recorrido = 0;
		int posicion = 1;
		for(int i = 0; i<posiciones.size();i++){
			if(posiciones.get(i).getNombre()==nombre) {
				recorrido = posiciones.get(i).getRecorrido();
			}
		}
		for(int i = 0; i<posiciones.size();i++){
			if(posiciones.get(i).getRecorrido()>recorrido) {
				posicion++;
			}
		}
		if(posicion == 1) {
			res = "¡Va primero!";
		} else {
			res = "Está a " + (posicion-1) + " posiciones del líder.";
		}
		return res;
	}

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Número de camellos:");
		numCamellos = sc.nextInt();
		System.out.println("Recorrido total:");
		distancia = sc.nextInt();
		sc.close();
		for(int i = 0; i<numCamellos; i++) {
			Camello c = new Camello("Camello " + (i+1));
			posiciones.add(c);
			new Thread(c, c.getNombre()).start();
		}
	}

	@Override
	public int compareTo(Camello c) {
		if(c.getRecorrido()>recorrido){
            return 1;
        }else if(c.getRecorrido()==recorrido){
            return 0;
        }else{
            return -1;
        }
	}


}

package br.com.bcp.thread;

/**
 * Created by bcp on 24/03/17.
 */
public class ThreadCheck implements Runnable {

    public static void main(String[] args) {
	Thread thread = new Thread(new ThreadCheck());
	thread.start();

    }

    public void run() {
	System.out.println("Iniciando ThreadCheck");
	while (true) {
	    System.out.println("ThreadCheck verificando " + System.currentTimeMillis());
	    //Pause for 5 seconds
	    try {
		Thread.sleep(5000);
	    } catch (InterruptedException pE) {
		pE.printStackTrace();
	    }
	}
    }
}

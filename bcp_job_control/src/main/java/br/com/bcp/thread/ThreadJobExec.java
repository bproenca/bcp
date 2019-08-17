package br.com.bcp.thread;

import br.com.bcp.db.ConnectionFactory;
import br.com.bcp.db.JobDAO;

/**
 * Created by bcp on 24/03/17.
 */
public class ThreadJobExec implements Runnable {

    public int total;

    private JobDAO jobDAO;

    private long jobNumber;

    public ThreadJobExec(long pJobNumber) {
	jobNumber = pJobNumber;
	jobDAO = new JobDAO(ConnectionFactory.getConnection());
    }

    public void run() {
	synchronized (this) {
	    System.out.println("** Inicio JobExex - " + Thread.currentThread());
	    System.out.print("**** JobExex - " + Thread.currentThread() + " - ");
	    for (int i = 0; i < 10; i++) {
		total = i;
		jobDAO.updateJobStatus(jobNumber, "Executando " + total);
		System.out.print(i + ",");
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException pE) {
		    pE.printStackTrace();
		}
	    }
	    jobDAO.updateJobStatus(jobNumber, "Concluido");
	    notify();
	    System.out.println("\n** Fim JobExex - " + Thread.currentThread());
	}
    }
}

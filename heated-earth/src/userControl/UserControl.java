package userControl;

import java.util.Observable;
import java.util.Observer;

import presentation.Presentation;

import simulation.SimConstant;
import simulation.SimEngine;

public class UserControl implements Observer {
        
        private Thread appThread        = null;
        private Thread simThread        = null;
        private Thread presThread       = null;
        
        private boolean paused = false;
        
        public UserControl() {
                // Observe the simulation options
                SimOptions.getInstance().addObserver(this);
        }
        
        public void update(Observable observable, Object arg1) {
                // The observable should be the simulation options, so cast it for clarity
                SimOptions opts = (SimOptions) observable;
                
                // Check the status of the options and if we should run, then run
                if (opts.getRun() == true) {
                        
                        // If we are restarting, then we need to kill the thread before we go forward with a run
                        if (opts.getResetOnStart() == true) {
                                // Kill everything
                                killAllThreads();
                                paused = false;
                        }

                        // Should we start a new thread or just come back from a hold? 
                        if (!paused) {
                                // Let's go!
                                startAllThreads();
                        }
                        else {
                                // Let's get things moving again!
                                setAllThreadHolds(false);
                        }
                }
                else if (opts.getRun() == false){
                        // Just hold everything
                        setAllThreadHolds(true);
                        paused = true;
                }
        }
        
        private void startAllThreads() {
                SimOptions opts = SimOptions.getInstance();

                // Do we need a simulation thread?
                SimEngine sim = SimEngine.getInstance();
                SimEngine.setKill(false);
                SimEngine.setHold(false);
                sim.initStatus();
                if (opts.getThreadConfig() == SimOptions.THREAD_CONFIG.SIM_ONLY         ||
                        opts.getThreadConfig() == SimOptions.THREAD_CONFIG.SIM_AND_PRES ) {
                        // OK, create our simulation thread
                        simThread = new Thread(sim,"Sim Thread");
                }
                
                // Do we need a presentation thread?
                Presentation pres = Presentation.getInstance();
                Presentation.setKill(false);
                Presentation.setHold(false);
                if (opts.getThreadConfig() == SimOptions.THREAD_CONFIG.PRES_ONLY        ||
                        opts.getThreadConfig() == SimOptions.THREAD_CONFIG.SIM_AND_PRES ) {
                        // OK, create our presentation thread
                        presThread = new Thread(pres,"Pres Thread");
                }

                // Setup the application thread based on the threads that currently exists / don't exist
                if (simThread == null || presThread == null) {
                        appThread = new Thread(new AppThread(simThread, presThread), "App Thread");
                }

                // Run any necessary threads
                if (simThread != null)  {simThread.start();}
                if (presThread != null) {presThread.start();}
                if (appThread != null)  {appThread.start();}
                
        }

        private void setAllThreadHolds(boolean bHold) {
                SimEngine.setHold(bHold);
                Presentation.setHold(bHold);
                AppThread.setHold(bHold);
        }

        private void killAllThreads() {
                // Kill everything
                SimEngine.setKill(true);
                Presentation.setKill(true);
                AppThread.kill();               // This should kill the threads nearly instantaneously... but let's make sure   
                
//              boolean simDead = false, presDead = false, appDead = false;
//              while (!simDead || !presDead || !appDead) {
//                      // Threads haven't died yet... update and wait for them here
//                      if (simThread != null)  {simDead = !simThread.isAlive();}       else {simDead = true;}
//                      if (presThread != null) {presDead = !presThread.isAlive();}     else {presDead = true;}
//                      if (appThread != null)  {appDead = !appThread.isAlive();}       else {appDead = true;}
//                      try {
//                              Thread.sleep(1);
//                      } catch (InterruptedException e) {
//                               e.printStackTrace();
//                      }
//              }
                
                try {
                        if (simThread != null)  {simThread.join();}
                        if (presThread != null) {presThread.join();}
                        if (appThread != null)  {appThread.join();}
                } catch (InterruptedException e) {
                         e.printStackTrace();
                }
                
                // All threads are dead... return them for re-use
                simThread = null;
                presThread = null;
                appThread = null;
                
                // Also kill classes
                SimEngine.destroy();
                Presentation.destroy();
        }
}

class AppThread implements Runnable {
        private static boolean hold = false;
        private static boolean kill = false;
        
        public static void setHold(boolean bHold) {hold = bHold;
                if (SimConstant.DEBUG) {
                        System.out.println("Holding AppThread!");
                }
        }
        public static void kill() {kill = true;}
        
        private Thread simThread = null;
        private Thread presThread = null;
        
        public AppThread(Thread simThread_in, Thread presThread_in) {
                kill = false; 
                hold = false;
                simThread = simThread_in;
                presThread = presThread_in;
        }
        
        public void run() {
        
                if (SimConstant.DEBUG) {
                        System.out.println("Enterring Application Thread!");
                }
                
                // Get the necessary instances of simulation and presentation
                SimEngine sim = SimEngine.getInstance();
                Presentation pres = Presentation.getInstance();
                
                // run the main application loop... if we have to
                boolean simFinished = false, presFinished = false;      // Assume that we're already done!
                while ( (simThread == null || presThread == null) 
                                 && (!simFinished || !presFinished) 
                                 && !kill ) {
                        
                        // Take a simulation step only if it's not running in a thread
                        if (!hold) {
                                if (simThread == null) {simFinished = !sim.runStep();} else {simFinished = true;}
                                if (presThread == null) {presFinished = !pres.updateSimData();} else {presFinished = true;}
                        }
                        else {
                                // wait a minute so that the computer doesn't slow down too much
                                try {
                                        Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                        //e.printStackTrace();
                                }
                        }
                }
                
                if (SimConstant.DEBUG) {
                        System.out.println("Leaving Application Thread!");
                }
        }
}
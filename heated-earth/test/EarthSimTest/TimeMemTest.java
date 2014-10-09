package EarthSimTest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import simulation.SimConstant;
import userControl.SimOptions;
import userControl.UserControl;
import simulation.SimEngine;
import junit.framework.TestCase;

class BaseTest {
        public static void test(int gridSpacing, int simRate, int visRate,
                        SimOptions.THREAD_CONFIG thread, SimOptions.COMM_CONFIG comm ) {
                SimConstant.TESTING = true;
                SimOptions opts = SimOptions.getInstance();
                opts.setGridSpacing(gridSpacing);
                opts.setSimulationRate(simRate);
                opts.setVisualizationRate(visRate);
                opts.setThreadConfig(thread);
                opts.setCommConfig(comm);
                opts.setResetOnStart(true);
                
                long starttime = System.currentTimeMillis();
                
                new UserControl();
                opts.setRun(true);      // This starts everything!... user control will see it happen and get things moving.
                long total = Runtime.getRuntime().totalMemory();
                try {
                        Thread.sleep(1000);
                } catch (InterruptedException e) {                      
                }
                long free = Runtime.getRuntime().freeMemory();
                int steps = SimEngine.getInstance().getSteps();
                long endtime = System.currentTimeMillis();
                opts.setRun(false);

                long memory = total - free;
                long time = endtime-starttime;

                float timePerSteps = (float)time / steps;
                System.out.println("the memory usage is :" + memory
                                + " and the runtime per steps is "+ timePerSteps + " Milliseconds.");

                // Swaroop: ADD WRITE TO CSV FILE FUNCTIONS HERE
                // OUTPUT is: gridSpacing, simRate, visRate, ThreadConfig, COmmCOnif, mem, timePerSteps
                try
                {
                    String filename= "MyDataFile.csv";
                    boolean append = true;
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, append)));

                    out.print(gridSpacing);
                    out.print(",");
                    out.print(simRate);
                    out.print(",");
                    out.print(visRate);
                    out.print(",");
                    out.print(thread);
                    out.print(",");
                    out.print(comm);
                    out.print(",");
                    out.print(memory);
                    out.print(",");
                    out.print(timePerSteps);
                    out.print(",");
                    out.println();
                    out.close();
                }

                catch(IOException ioe)
                {
                    System.err.println("IOException: " + ioe.getMessage());
                } 

        }
}
public class TimeMemTest  extends TestCase {

        public void test_Buffer_Pres() {
                BaseTest.test(1, 10, 1, 
                                SimOptions.THREAD_CONFIG.PRES_ONLY, SimOptions.COMM_CONFIG.BUFFER);
                BaseTest.test(1, 5, 4, 
                              SimOptions.THREAD_CONFIG.PRES_ONLY, SimOptions.COMM_CONFIG.BUFFER);
        }
}

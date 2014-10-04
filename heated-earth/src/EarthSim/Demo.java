package EarthSim;

import presentation.Gui;
import userControl.SimOptions;
import userControl.UserControl;

public class Demo {

        /**
         * Main entry function for the application
         */
        public static void main(String[] args) {
                
                // Parse the command line arguments and setup the sim options
                if(parseCommandLine(args)) {
                        // Create the instance of the GUI
                        Gui.getInstance();
                        new UserControl();
                }
        }
        
        public static boolean parseCommandLine(String[] args) {
                // Get the sim options
                SimOptions opts = SimOptions.getInstance();
                
                // Set default options
                boolean bSimThread = false;
                boolean bPresThread = false;
                boolean bCommConfigSet = false;
                SimOptions.COMM_CONFIG commConfig = SimOptions.COMM_CONFIG.PUSH;
                
                // Parse the command line options
                boolean contProg = true;
                
                for (int nArg = 0; nArg < args.length; nArg++) {

                        // Parse THREAD_CONFIG
                        if (args[nArg].equalsIgnoreCase("-s")) {
                                bSimThread = true;
                        }                       
                        else if (args[nArg].equalsIgnoreCase("-p")) {
                                bPresThread = true;
                        }
                        // Parse COMM_CONFIG
                        else if (args[nArg].equalsIgnoreCase("-t")) {
                                if(!bCommConfigSet) {
                                        commConfig = SimOptions.COMM_CONFIG.PUSH;
                                        bCommConfigSet = true;
                                }
                                else {
                                        contProg = false;
                                }
                        }
                        else if (args[nArg].equalsIgnoreCase("-r")) {
                                if(!bCommConfigSet) {
                                        commConfig = SimOptions.COMM_CONFIG.PULL;
                                        bCommConfigSet = true;
                                }
                                else {
                                        contProg = false;
                                }
                        }
                        else if (args[nArg].equalsIgnoreCase("-b")) {
                                if(!bCommConfigSet) {
                                        commConfig = SimOptions.COMM_CONFIG.BUFFER;
                                        bCommConfigSet = true;
                                }
                                else {
                                        contProg = false;
                                }
                        }
                }
                if(!bCommConfigSet) {
                        contProg = false;
                }
                
                if (contProg) {
                        if(!bSimThread && !bPresThread) {
                                opts.setThreadConfig(SimOptions.THREAD_CONFIG.NONE);
                        }
                        else if(!bSimThread && bPresThread) {
                                opts.setThreadConfig(SimOptions.THREAD_CONFIG.PRES_ONLY);
                        }
                        else if(bSimThread && !bPresThread) {
                                opts.setThreadConfig(SimOptions.THREAD_CONFIG.SIM_ONLY);
                        }
                        else if(bSimThread && bPresThread) {
                                opts.setThreadConfig(SimOptions.THREAD_CONFIG.SIM_AND_PRES);
                        }
                        opts.setCommConfig(commConfig);

//                      new SimulationController().runSimulation(new TextOutput());
                }
                else {
                        String usage = 
                                "Usage:\n" +
                                "\n" +
                                "java " + "EarthSim" 
                                        + " " + "[" + "-s" + "]"
                                        + " " + "[" + "-p" + "]"
                                        + " " + "[" + "-t OR -r OR -b" + "]";
                        
                        System.out.println(usage);
                }
                
                return contProg;
        }
}
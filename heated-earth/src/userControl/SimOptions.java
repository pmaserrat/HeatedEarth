package userControl;

import java.util.Observable;

/*
 * Simulation Option: Singleton 
 * */
public class SimOptions extends Observable {
	
	public enum COMM_CONFIG {PUSH,PULL,BUFFER};
	public enum THREAD_CONFIG {NONE,SIM_ONLY,PRES_ONLY,SIM_AND_PRES};
	
	// Provide some hardcoded defaults... I hate it but it's necessary
	private boolean 	run 					= false;
	private boolean 	resetOnStart 			= false;
	private int 		gridSpacing 			= 15;
	private int 		simulationRate 			= 1;
	private int			simulationDelay_ms		= 0;
	private int 		visualizationRate 		= 2;
	private int			visualizationDelay_ms	= 0;
	private COMM_CONFIG	commConfig 				= COMM_CONFIG.BUFFER;
	private THREAD_CONFIG threadConfig			= THREAD_CONFIG.NONE;
	private SimOptions(){};

	private static SimOptions instance;
	public static SimOptions getInstance() {
		if(instance == null) {
			instance = new SimOptions();
		}
		return instance;
	}
	public static void destroy() {
		if(instance != null) {
			instance = null;
		}
	}
	
	public void setGridSpacing(int gridSpacing) {
		this.gridSpacing = gridSpacing;
	}
	
	public int getGridSpacing() {
		return gridSpacing;
	}

	public void setSimulationRate(int simulationRate) {
		this.simulationRate = simulationRate;
	}
	
	public int getSimulationRate() {
		return simulationRate;
	}

	public void setVisualizationRate(int visualizationRate) {
		this.visualizationRate = visualizationRate;
	}
	
	public int getVisualizationRate() {
		return visualizationRate;
	}

	public void setRun(boolean run) {
		this.run = run;
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean getRun() {
		return run;
	}

	public void setResetOnStart(boolean resetOnStart) {
		this.resetOnStart = resetOnStart;
	}
	
	public boolean getResetOnStart() {
		return resetOnStart;
	}

	public void setCommConfig(COMM_CONFIG commConfig) {
		this.commConfig = commConfig;
	}

	public COMM_CONFIG getCommConfig() {
		return commConfig;
	}

	public void setThreadConfig(THREAD_CONFIG threadConfig) {
		this.threadConfig = threadConfig;
	}

	public THREAD_CONFIG getThreadConfig() {
		return threadConfig;
	}

	public void setSimulationDelay_ms(int simulationDelay_ms) {
		this.simulationDelay_ms = simulationDelay_ms;
	}

	public int getSimulationDelay_ms() {
		return simulationDelay_ms;
	}

	public void setVisualizationDelay_ms(int visualizationDelay_ms) {
		this.visualizationDelay_ms = visualizationDelay_ms;
	}

	public int getVisualizationDelay_ms() {
		return visualizationDelay_ms;
	}

}

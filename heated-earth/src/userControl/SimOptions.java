package userControl;

import java.util.Observable;

public class SimOptions extends Observable {
		
	// Provide some hardcoded defaults... I hate it but it's necessary
	private boolean 	run 					= false;
	private boolean 	resetOnStart 			= false;
	private int 		gridSpacing 			= 15;
	private int 		simulationRate 			= 1;
	private int			simulationDelay_ms		= 0;
	private int 		visualizationRate 		= 2;
	private int			visualizationDelay_ms	= 0;
	private CommunicationConfig	commConfig 				= CommunicationConfig.BUFFER;
	private ThreadConfig threadConfig			= ThreadConfig.NONE;
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

	public void setCommConfig(CommunicationConfig commConfig) {
		this.commConfig = commConfig;
	}

	public CommunicationConfig getCommConfig() {
		return commConfig;
	}

	public void setThreadConfig(ThreadConfig threadConfig) {
		this.threadConfig = threadConfig;
	}

	public ThreadConfig getThreadConfig() {
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





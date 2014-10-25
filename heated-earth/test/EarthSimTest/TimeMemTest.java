package EarthSimTest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import simulation.SimConstant;
import userControl.CommunicationConfig;
import userControl.SimulationOptions;
import userControl.ThreadConfig;
import userControl.UserControl;
import simulation.SimEngine;
import junit.framework.TestCase;

class BaseTest {
	public static void test(int gridSpacing, int simRate, int visRate,
			ThreadConfig thread, CommunicationConfig comm) {
		SimConstant.TESTING = true;
		SimulationOptions opts = SimulationOptions.getInstance();
		opts.setGridSpacing(gridSpacing);
		opts.setSimulationRate(simRate);
		opts.setVisualizationRate(visRate);
		opts.setThreadConfig(thread);
		opts.setCommConfig(comm);
		opts.setResetOnStart(true);

		long starttime = System.currentTimeMillis();

		new UserControl();
		opts.setRun(true); // This starts everything!... user control will see
							// it happen and get things moving.
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
		long time = endtime - starttime;

		float timePerSteps = (float) time / steps;
		System.out.println("the memory usage is :" + memory
				+ " and the runtime per steps is " + timePerSteps
				+ " Milliseconds.");

		// Swaroop: ADD WRITE TO CSV FILE FUNCTIONS HERE
		// OUTPUT is: gridSpacing, simRate, visRate, ThreadConfig, COmmCOnif,
		// mem, timePerSteps
		try {
			String filename = "MyDataFile.csv";
			boolean append = true;
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(filename, append)));

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

		catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}

	}
}

public class TimeMemTest extends TestCase {

	public void test_Scenario_1() {
		AppendToFile("Scenario 1");

		BaseTest.test(15, 10, 1, ThreadConfig.NONE, CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PUSH);

		BaseTest.test(15, 10, 1, ThreadConfig.NONE, CommunicationConfig.PULL);
		BaseTest.test(15, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PULL);

		BaseTest.test(15, 10, 1, ThreadConfig.NONE, CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.BUFFER);
	}

	public void test_Scenario_2() {
		AppendToFile("Scenario 2");

		BaseTest.test(10, 10, 1, ThreadConfig.NONE, CommunicationConfig.PUSH);
		BaseTest.test(10, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(10, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(10, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PUSH);

		BaseTest.test(10, 10, 1, ThreadConfig.NONE, CommunicationConfig.PULL);
		BaseTest.test(10, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(10, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(10, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PULL);

		BaseTest.test(10, 10, 1, ThreadConfig.NONE, CommunicationConfig.BUFFER);
		BaseTest.test(10, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(10, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(10, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.BUFFER);
	}

	public void test_Scenario_3() {
		AppendToFile("Scenario 3");

		BaseTest.test(20, 10, 1, ThreadConfig.NONE, CommunicationConfig.PUSH);
		BaseTest.test(20, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(20, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(20, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PUSH);

		BaseTest.test(20, 10, 1, ThreadConfig.NONE, CommunicationConfig.PULL);
		BaseTest.test(20, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(20, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(20, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PULL);

		BaseTest.test(20, 10, 1, ThreadConfig.NONE, CommunicationConfig.BUFFER);
		BaseTest.test(20, 10, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(20, 10, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(20, 10, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.BUFFER);
	}

	public void test_Scenario_4() {
		AppendToFile("Scenario 4");

		BaseTest.test(15, 15, 1, ThreadConfig.NONE, CommunicationConfig.PUSH);
		BaseTest.test(15, 15, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 15, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 15, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PUSH);

		BaseTest.test(15, 15, 1, ThreadConfig.NONE, CommunicationConfig.PULL);
		BaseTest.test(15, 15, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 15, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 15, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PULL);

		BaseTest.test(15, 15, 1, ThreadConfig.NONE, CommunicationConfig.BUFFER);
		BaseTest.test(15, 15, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 15, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 15, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.BUFFER);
	}

	public void test_Scenario_5() {
		AppendToFile("Scenario 5");

		BaseTest.test(15, 5, 1, ThreadConfig.NONE, CommunicationConfig.PUSH);
		BaseTest.test(15, 5, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 5, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 5, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PUSH);

		BaseTest.test(15, 5, 1, ThreadConfig.NONE, CommunicationConfig.PULL);
		BaseTest.test(15, 5, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 5, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 5, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PULL);

		BaseTest.test(15, 5, 1, ThreadConfig.NONE, CommunicationConfig.BUFFER);
		BaseTest.test(15, 5, 1, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 5, 1, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 5, 1, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.BUFFER);
	}

	public void test_Scenario_6() {
		AppendToFile("Scenario 6");

		BaseTest.test(15, 10, 3, ThreadConfig.NONE, CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 3, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 3, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 3, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PUSH);

		BaseTest.test(15, 10, 3, ThreadConfig.NONE, CommunicationConfig.PULL);
		BaseTest.test(15, 10, 3, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 10, 3, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 10, 3, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PULL);

		BaseTest.test(15, 10, 3, ThreadConfig.NONE, CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 3, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 3, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 3, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.BUFFER);
	}

	public void test_Scenario_7() {
		AppendToFile("Scenario 7");

		BaseTest.test(15, 10, 5, ThreadConfig.NONE, CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 5, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 5, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PUSH);
		BaseTest.test(15, 10, 5, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PUSH);

		BaseTest.test(15, 10, 5, ThreadConfig.NONE, CommunicationConfig.PULL);
		BaseTest.test(15, 10, 5, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 10, 5, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.PULL);
		BaseTest.test(15, 10, 5, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.PULL);

		BaseTest.test(15, 10, 5, ThreadConfig.NONE, CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 5, ThreadConfig.SIMULATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 5, ThreadConfig.PRESENTATIONONLY,
				CommunicationConfig.BUFFER);
		BaseTest.test(15, 10, 5, ThreadConfig.SIMULATIONANDPRESENTATION,
				CommunicationConfig.BUFFER);
	}

	private void AppendToFile(String scenario) {
		String filename = "MyDataFile.csv";
		boolean append = true;
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(filename, append)));

			out.print(scenario);
			out.println();
			out.print("Grid Spacing");
			out.print(",");
			out.print("Simulation Rate");
			out.print(",");
			out.print("Visulation Rate");
			out.print(",");
			out.print("Thread Config");
			out.print(",");
			out.print("Communication Config");
			out.print(",");
			out.print("Memory");
			out.print(",");
			out.print("Time");
			out.println();
			out.close();
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}
}
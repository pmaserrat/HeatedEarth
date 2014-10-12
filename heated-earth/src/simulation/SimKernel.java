package simulation;

import userControl.SimulationOptions;

public class SimKernel {

	public SimKernel() {

	}

	private float wattsPerArea;

	public float step(Grid source, Grid target, float rotationAngle_deg,
			int simulationRate) {
		// Compute lattice point temperature as average of neighbors
		float maxDiff = 0;
		// SimOptions instance = SimOptions.getInstance();;
		// final int cols = source.getColumns();//so that I dont need to call a
		// function every time
		// final int rows = source.getRows();
		// final int simulationRate = (int) instance.getSimulationRate();

		float tempSunEffect = 0;
		float tempCooling = 0;
		float tempDiffusion = 0;
		float newTemp; // newTemp = tempSunEffect+tempCooling+tempDiffusion

		float absDiffTemp;

		Cell sourceCell;
		Cell targetCell;
		do {
			sourceCell = source.getCurrent();
			targetCell = target.getCurrent();

			// diffusion thing
			// tempDiffusion = 0;
			tempDiffusion = calcTempDiffusion(sourceCell);

			// TODO: Get sun influence
			tempSunEffect = 0;
			tempSunEffect = calcTempSun(sourceCell, rotationAngle_deg,
					simulationRate);

			// TODO: Get cooling influence
			tempCooling = 0;
			float sigma = sourceCell.getTemperature()
					/ SimConstant.AVERAGE_TEMP_K;
			tempCooling = -(float) wattsPerArea * SimConstant.K
					* simulationRate * sigma;

			// newTemp = tempDiffusion + tempCooling + tempSunEffect;
			newTemp = tempDiffusion + tempCooling * (float) 1e6;
			// newTemp = 290;

			targetCell.setTemperature(newTemp);

			absDiffTemp = Math.abs(sourceCell.getTemperature()
					- targetCell.getTemperature());
			if (absDiffTemp > maxDiff) {
				maxDiff = absDiffTemp;
			}

			source.next();
		} while (target.next());

		return maxDiff;
	}

	private float calcTempDiffusion(Cell sourceCell) {
		// required for diffusion effect
		float cellPerimeter;
		float pE, pW, pN, pS; // Proportion of a cells border shared
								// with its neighbors to the East, West, north
								// and south

		float tempDiffusion = 0;
		cellPerimeter = sourceCell.getPerimeter();
		pE = sourceCell.getRightLength() / cellPerimeter;
		pW = sourceCell.getLeftLength() / cellPerimeter;
		pN = sourceCell.getTopLength() / cellPerimeter;
		pS = sourceCell.getBottomLength() / cellPerimeter;

		tempDiffusion = pE * (sourceCell.getRight().getTemperature()) + pW
				* (sourceCell.getLeft().getTemperature()) + pN
				* (sourceCell.getTop().getTemperature()) + pS
				* (sourceCell.getBottom().getTemperature());
		return tempDiffusion;
	}

	private float calcTempSun(Cell sourceCell, float rotationAngle_deg,
			int simulationRate) {
		float tempSunEffect = 0;

		// required for sun effect temp
		// float wattsPerArea;
		float attenuationFactor;
		float latitudeFactor;
		float longitudeFactor;
		float effectiveWattsPerArea;

		wattsPerArea = SimConstant.SOLAR_CONSTANT * sourceCell.getSurfaceArea();
		latitudeFactor = (float) Math.cos((sourceCell.getLatitude()) * Math.PI
				/ 180);// convert it into a radian value
		float var1 = rotationAngle_deg - sourceCell.getLongitude();
		if (Math.abs(var1) < 90) {
			longitudeFactor = (float) Math.cos(var1 * Math.PI / 180);
		} else {
			longitudeFactor = 0;
		}
		attenuationFactor = latitudeFactor * longitudeFactor;
		effectiveWattsPerArea = wattsPerArea * attenuationFactor;
		tempSunEffect = effectiveWattsPerArea * simulationRate * SimConstant.K;

		return tempSunEffect;
	}
}
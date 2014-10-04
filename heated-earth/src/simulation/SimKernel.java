package simulation;

public class SimKernel {
        
//      private static final float _SUN_HEATING_FAKE = (float)1e-14;
//      private static final float _EARTH_COOLING_FAKE = (float)1e-15;
        private static final float _SUN_HEATING_FAKE = (float)10e-12;
        private static final float _EARTH_COOLING_FAKE = (float)10e-13;
        
         
    public SimKernel() {
        
    }
        
//    private float wattsPerArea;
    
        public float step(Grid source, Grid target, float rotationAngle_deg, int simulationRate_min) {
          // Compute lattice point temperature as average of neighbors  
        float maxDiff = 0;
        //SimOptions instance = SimOptions.getInstance();;
        //final int cols = source.getColumns();//so that I dont need to call a function every time
        //final int rows = source.getRows();
        //final int simulationRate = (int) instance.getSimulationRate();
                
        float tempSunEffect_K = 0;
        float tempCooling_K = 0;
        float tempDiffusion_K = 0;
        float newTemp_K; //newTemp = tempSunEffect+tempCooling+tempDiffusion

        float absDiffTemp_K;
 
        source.reset();
        target.reset();
        Cell sourceCell;
        Cell targetCell;
        do {
                sourceCell = source.getCurrent();
                targetCell = target.getCurrent();               

                // diffusion thing
//              tempDiffusion = 0;
//              tempDiffusion_K = calcTempDiffusion(sourceCell, simulationRate_min);
                tempDiffusion_K = SimConstant.AVERAGE_TEMP_K;
      
                //Get sun influence
                tempSunEffect_K = 0;
                tempSunEffect_K = calcTempSun(sourceCell, rotationAngle_deg, simulationRate_min);
                if(tempSunEffect_K < 0) {
                        System.out.println(tempSunEffect_K);
                }
                
                //Get cooling influence
//              tempCooling_K = 0;
//              float incedentPower_W = SimConstant.SOLAR_CONSTANT_Wpm2 * sourceCell.getSurfaceArea();
//              float sigma = sourceCell.getTemperature() / SimConstant.AVERAGE_TEMP_K;
////            tempCooling_K = -(float)wattsPerArea * SimConstant.K * simulationRate * sigma;
//              tempCooling_K = - incedentPower_W / SimConstant.K_JpK * (simulationRate_min * 60 /*min2sec*/) * sigma;
                tempCooling_K = -_EARTH_COOLING_FAKE * simulationRate_min * 60 * sourceCell.getSurfaceArea();
                
//              newTemp = tempDiffusion + tempCooling + tempSunEffect;
//              newTemp = tempDiffusion_K + tempSunEffect_K * (float)7e6 + tempCooling_K * (float)2.3e5;
                newTemp_K = tempDiffusion_K + tempSunEffect_K + tempCooling_K;
                targetCell.setTemperature(newTemp_K);
                
                absDiffTemp_K = Math.abs(sourceCell.getTemperature() - targetCell.getTemperature());
                if(absDiffTemp_K > maxDiff) {
                        maxDiff = absDiffTemp_K;
                }
                
//              source.next();
                } while(source.next() && target.next());
                
                return maxDiff;
    }
        
        @SuppressWarnings("unused")
        private float calcTempDiffusion(Cell sourceCell, int simulationRate_min) {
        //required for diffusion effect 
        float cellPerimeter;
        float pE, pW, pN, pS; //Proportion of a cells border shared 
                                                  //with its neighbors to the East, West, north and south

        float tempDiffusion = 0;                
                cellPerimeter = sourceCell.getPerimeter();
        pE = sourceCell.getRightLength()/cellPerimeter;
        pW = sourceCell.getLeftLength()/cellPerimeter;
        pN = sourceCell.getTopLength()/cellPerimeter;
        pS = sourceCell.getBottomLength()/cellPerimeter;
        
        tempDiffusion = pE*(sourceCell.getRight().getTemperature()) + pW*(sourceCell.getLeft().getTemperature())
                                                +pN*(sourceCell.getTop().getTemperature()) +pS*(sourceCell.getBottom().getTemperature());
 
        return tempDiffusion /** simulationRate_min * 60*/;
        }
    
        private float calcTempSun(Cell sourceCell, float rotationAngle_deg, int simulationRate_min) {

//        float wattsPerArea;
        float latitudeFactor;
        float longitudeFactor;
//        float effectiveWattsPerArea;
 
        float attenuationFactor;
        
                latitudeFactor = (float) Math.cos(Math.toRadians((sourceCell.getLatitude())));  //convert it into a radian value
                
                float sum_deg = Math.abs(rotationAngle_deg - sourceCell.getLongitude());
                sum_deg = Math.min(sum_deg, 360 - sum_deg);
                if(sum_deg<90) {
                        longitudeFactor = (float) Math.cos(Math.toRadians(sum_deg));
                }
                else {
                        longitudeFactor = 0;
                }
                
                attenuationFactor = latitudeFactor * longitudeFactor;
                
//              wattsPerArea = SimConstant.SOLAR_CONSTANT_Wpm2 * sourceCell.getSurfaceArea();
//              float incedentPower_W = SimConstant.SOLAR_CONSTANT_Wpm2 * sourceCell.getSurfaceArea();
                
//              effectiveWattsPerArea =  wattsPerArea * attenuationFactor;
//              float actualPowerReceived_W = incedentPower_W * attenuationFactor;
                
//              tempSunEffect = effectiveWattsPerArea * simulationRate * SimConstant.K;

                float tempSunEffect_K = 0;
//              tempSunEffect_K = (actualPowerReceived_W * (simulationRate_min * 60 /*min2sec*/)) //<--Actual Engergy Received in Joules
//                                              / SimConstant.K_JpK;    //<-- Joules DIVIDED BY Boltzman's constant equals temperature in Kelvin
                
                tempSunEffect_K = _SUN_HEATING_FAKE * simulationRate_min * 60 * sourceCell.getSurfaceArea() * attenuationFactor;
                
                return tempSunEffect_K;
        }
}
package symbolicRegression

class DataSet {
    
    TreeMap<Double, Double> data = new TreeMap<Double, Double>()
    def numberOfTimeSteps
    def initialPopulationDepth = 5
    Random random = new Random()

    public DataSet(numberOfTimeSteps){
        //populationOfTrees
        this.numberOfTimeSteps = numberOfTimeSteps
        createData()
    }
    
    public createData(){
        
        Double lowerRange = -1, topRange = 1
        
        Double timeStepSize = (topRange-lowerRange)/this.numberOfTimeSteps
        
        Double currentInputValue = lowerRange
        
        for(Integer i = 0; i <= this.numberOfTimeSteps; i++){
            this.data.put(currentInputValue, createFunction(currentInputValue))
            currentInputValue += timeStepSize
        }
    }
    
    public createFunction(Double input){
		
        Math.sin(input)
    }
    
}

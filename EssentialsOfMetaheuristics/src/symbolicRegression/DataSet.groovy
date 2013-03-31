package symbolicRegression

class DataSet {
    
    TreeMap<Double, Double> data = new TreeMap<Double, Double>()
    def numberOfTimeSteps
    def initialPopulationDepth = 5
    def lowerRange
    def upperRange
    
    Random random = new Random()

    public DataSet(numberOfTimeSteps, lowerRange, upperRange){
        //populationOfTrees
        this.numberOfTimeSteps = numberOfTimeSteps
        this.lowerRange = lowerRange
        this.upperRange = upperRange
        createData()
    }
    
    public createData(){
        
        Double timeStepSize = (upperRange-lowerRange)/this.numberOfTimeSteps
        
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

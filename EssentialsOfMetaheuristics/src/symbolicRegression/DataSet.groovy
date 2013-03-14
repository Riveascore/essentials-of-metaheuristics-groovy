package symbolicRegression

class DataSet {
    
    TreeMap<Double, Double> data = new TreeMap<Double, Double>()
    def numberOfTimeSteps
    def maxDepthLimit = 20
    def initialPopulationDepth = 5
    Random random = new Random()

    public DataSet(numberOfTimeSteps){
        //populationOfTrees
        this.numberOfTimeSteps = numberOfTimeSteps
        createData()
    }
    
    public createData(){
        
        Double lowerRange = 0, topRange = Math.PI
        
        Double timeStepSize = topRange/this.numberOfTimeSteps
        
        Double currentInputValue = 0
        
        for(Integer i = 0; i <= this.numberOfTimeSteps; i++){
            this.data.put(currentInputValue, createFunction(currentInputValue))
            currentInputValue += timeStepSize
        }
    }
    
    public createFunction(Double input){
		
		//TODO SOMEHOW sin[x] ISN'T WORKING CORRECTLY! FIX FIX FIX
        Math.sin(input)
        //approximate sin(x) here^
    }
    
}

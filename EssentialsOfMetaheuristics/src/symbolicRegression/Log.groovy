package symbolicRegression

class Log {

    List<>
    //List<Gen, Best, dBest, Avg, dAvg>
    // Gen + "\t" + Best + "\t" + dBest + "\t" + Avg + "\t" + dAvg
    public Log(){
    }
    
    public add(Population population, generationNumber){
        def bestFitness = population.getMostFitIndividual().normalizedFitness
        def averageFitness = population.totalFitness/population.size()
        def changeInBestFitness = 0
        def changeInAverageFitness = 0
        
        if(size() > 0){
            def lastAverageFitness = DataTable.get(size()-1)
        }
                
    }
    
    public size(){
        DataTable.size()
    }
}

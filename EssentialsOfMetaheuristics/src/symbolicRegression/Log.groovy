package symbolicRegression

class Log {

//    List<Integer, Double, Double, Double, Double> DataTable = new ArrayList<Integer, Double, Double, Double, Double>()
//    List<<Integer>, <Double>> DataTable = new ArrayList<<Integer>, <Double>>()
    //List<Gen, Best, dBest, Avg, dAvg>
    // Gen + "\t" + Best + "\t" + dBest + "\t" + Avg + "\t" + dAvg
    def previousBest
    def previousAverage
    
    public Log(){
        previousBest = 0.0
        previousAverage = 0.0
    }
    
    public log(Population population, generationNumber){
        def totalFitness = 0
        population.population.each {
            totalFitness += it.fitness
        }
        
        Double bestFitness = population.getBestFitness()
        Double averageFitness = totalFitness/population.size()
        Double changeInBestFitness = bestFitness - previousBest
        Double changeInAverageFitness = averageFitness - previousAverage
        
        previousBest = bestFitness
        previousAverage = averageFitness
        
        "\n${generationNumber}\t" + String.format("%e\t%e\t%e\t%e", bestFitness, changeInBestFitness, averageFitness, changeInAverageFitness)
    }
    
    //println "${s.toString()}\t${p.toString()}\t${p.quality(result)}\t${result}"
}

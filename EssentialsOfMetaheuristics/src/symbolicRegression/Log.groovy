package symbolicRegression

class Log {

//    List<Integer, Double, Double, Double, Double> DataTable = new ArrayList<Integer, Double, Double, Double, Double>()
//    List<<Integer>, <Double>> DataTable = new ArrayList<<Integer>, <Double>>()
    //List<Gen, Best, dBest, Avg, dAvg>
    // Gen + "\t" + Best + "\t" + dBest + "\t" + Avg + "\t" + dAvg
    def previousBest
    def previousAverage
    
    public Log(){
        println "Gen\tBestFitness\tdBestFitness\tAverageFitness\tdAverageFitness"
        previousBest = 0
        previousAverage = 0
    }
    
    public log(Population population, generationNumber){
        def totalFitness = 0
        population.population.each {
            println it.fitness
            totalFitness += it.fitness
        }
        def bestFitness = population.getBestFitness()
        def averageFitness = totalFitness/population.size()
        def changeInBestFitness = bestFitness - previousBest
        def changeInAverageFitness = averageFitness - previousAverage
        println"total fitness: ${totalFitness}  population size: ${population.size()}"
        println "${generationNumber}\t${bestFitness}\t${changeInBestFitness}\t${averageFitness}\t${changeInAverageFitness}"
        
        previousBest = bestFitness
        previousAverage = averageFitness
        //now update previous values^
    }
    
    //println "${s.toString()}\t${p.toString()}\t${p.quality(result)}\t${result}"
}

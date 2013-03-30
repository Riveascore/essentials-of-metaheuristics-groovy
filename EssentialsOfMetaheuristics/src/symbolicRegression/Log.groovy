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
            totalFitness += it.fitness
        }
        Double bestFitness = population.getBestFitness()
        Double averageFitness = totalFitness/population.size()
        Double changeInBestFitness = bestFitness - previousBest
        Double changeInAverageFitness = averageFitness - previousAverage
        
//        Double shit = 234234234
//        printf("%e", shit)
//        println "${generationNumber}\t${bestFitness}\t${changeInBestFitness}\t${averageFitness}\t${changeInAverageFitness}"
        print "${generationNumber}\t"
        printf("%e\t", bestFitness)
        printf("%e\t", averageFitness)
        printf("%e\t", changeInBestFitness)
        printf("%e\n", changeInAverageFitness)
        
        previousBest = bestFitness
        previousAverage = averageFitness
        //now update previous values^
    }
    
    //println "${s.toString()}\t${p.toString()}\t${p.quality(result)}\t${result}"
}

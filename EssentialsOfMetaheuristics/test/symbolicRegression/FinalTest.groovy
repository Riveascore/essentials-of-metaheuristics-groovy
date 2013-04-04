package symbolicRegression

import spock.lang.*


class FinalTest extends Specification{
    
    @Ignore
    def "stuff"(){
        given:
        File fun = new File('results/neg1To1/fittness.txt')
        fun.write("hi")
    }
//    @Ignore
    def "-1 to 1"(){
        given:
        File fitnessFile = new File('results/zeroToPi/fitnessResults.txt')
        File functionFile = new File('results/zeroToPi/functionResults.txt')

        Integer maxTreeHeight = 10
        GeneticOperators operators = new GeneticOperators()

        Population currentPopulation = new Population('x', 4, maxTreeHeight)
        currentPopulation.createPopulation(500)

        DataSet dataSet = new DataSet(20, 0, Math.PI)
        dataSet.createData()

        currentPopulation.generateFitness(dataSet.data)

        Integer currentGeneration = 0
        Log log = new Log()
        
        

        List<Tree> mostFitThroughHistory = new ArrayList<Tree>()
        Tree mostFitOverall = currentPopulation.getMostFitIndividual()
        
        fitnessFile.write("Gen\tBestFitness\tdBestFitness\tAverageFitness\tdAverageFitness")
        
        functionFile.write(mostFitOverall.root.stringForm())

        while(currentPopulation.getBestFitness() > 0.1 && currentGeneration < 100){
            fitnessFile.append(log.log(currentPopulation, currentGeneration))

            currentPopulation = operators.matingSeason(currentPopulation)
            currentPopulation.generateFitness(dataSet.data)
            currentGeneration++
            if(mostFitOverall.fitness > currentPopulation.getBestFitness()){
                mostFitOverall = currentPopulation.getMostFitIndividual()
                functionFile.append("\n${mostFitOverall.root.stringForm()}")
            }
        }
        log.log(currentPopulation, currentGeneration)
        fitnessFile.append(log.log(currentPopulation, currentGeneration))
    }
}

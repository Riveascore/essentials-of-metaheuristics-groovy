package symbolicRegression

import spock.lang.*

class ERCTest extends Specification{

    @Ignore
    def "Random double -5 to 5 range"(){
        given:

        Tree tree = new Tree('x', 6, 6)

        Double erc
        Boolean inRange = true
        1000.times{
            erc = tree.ERC()
            if(erc < -5.0 || erc > 5.0){
                inRange = false
                println "broken erc: " + erc
            }
        }
        expect:
        inRange
    }

    @Ignore
    def "printing trees with ERCs"(){
        given:
        Tree tree = new Tree('x', 6, 6)

        tree.printTree()
        
        expect:
        true
    }
    
    @Ignore
    def "Trees printing and computing correctly with ERC added"(){
        given:
        
        Tree tree = new Tree('x', 5, 10)
        tree.allNodes.each{
            println "Node value: ${it.value}"
        }
        
        println "Tree evaluated at 0.5: ${tree.root.computeNodeValue(0.5)}"
        println "Tree evaluated at 1: ${tree.root.computeNodeValue(1)}"
        
        tree.printTree()
        
    }
	
	@Ignore
	def "How to handle /0"(){
		given:
		Double l = 5.0
		Double r = -5.0 - -5.0
		println r
		
		Double zero = 0.0
		Double negZero = -0.0
		println zero == negZero
		println l / r
	}
    
    //this is broken atm, gotta get trees to work
//    @Ignore
    def "Printing for R input"(){
                given:
                Integer maxTreeHeight = 10
                GeneticOperators operators = new GeneticOperators()
                
                Population currentPopulation = new Population('x', 4, maxTreeHeight)
                currentPopulation.createPopulation(500)
                
                DataSet dataSet = new DataSet(20)
                dataSet.createData()
                
                currentPopulation.generateFitness(dataSet.data)
                
                Integer currentGeneration = 0
                Log log = new Log()
                
                
                List<Tree> mostFitThroughHistory = new ArrayList<Tree>()
                Tree mostFitOverall = currentPopulation.getMostFitIndividual()
                mostFitThroughHistory.add(mostFitOverall)
                
                while(currentPopulation.getBestFitness() > 0.1){
                    
                    log.log(currentPopulation, currentGeneration)    
                    
                    currentPopulation = operators.matingSeason(currentPopulation)
                    currentPopulation.generateFitness(dataSet.data)
                    currentGeneration++
                    if(mostFitOverall.fitness > currentPopulation.getBestFitness()){
                        mostFitOverall = currentPopulation.getMostFitIndividual()
                        mostFitThroughHistory.add(mostFitOverall)
                    }
                }
                
                println "Most fit individuals overall:"
                mostFitThroughHistory.each{
                    println it.root.stringForm()
					println ""
                }
                
                expect:
                true
    }
}
package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class GenerateNewGenerationsTest extends Specification{
	
	@Ignore
	def "test node heights"(){
		given:
		Tree tree = new Tree('x', '(((x*x)+x)+((x+x)-x))', 7)
		tree.printTree()
		tree.allNodes.each {
			println "node: " + it.value + "  height: " + it.getNodeHeight()
		}
		
		expect:
		true
	}
	
	def "matingSeason test"(){
		given:
		Integer maxTreeHeight = 8
		GeneticOperators operators = new GeneticOperators()
		
		Population currentPopulation = new Population('x', 4, maxTreeHeight)
		currentPopulation.createPopulation(10)
		
		
		DataSet dataSet = new DataSet(100)
		dataSet.createData()
		
		currentPopulation.generateFitness(dataSet.data)
		
		println "gen0 most fit " + currentPopulation.getMostFitIndividual().fitness
		Integer currentGeneration = 0
		
//		50.times{
//			currentPopulation = operators.matingSeason(currentPopulation)
//			currentPopulation.generateFitness(dataSet.data)
//			println "Ran generation " + currentGeneration 
//			currentGeneration++
//		}
		
		while(currentPopulation.getMostFitIndividual().fitness > 5){
			currentPopulation = operators.matingSeason(currentPopulation)
			currentPopulation.generateFitness(dataSet.data)
			println "Ran generation " + currentGeneration
			currentGeneration++
		}
		println "Generation " + currentGeneration + "'s most fit: " + currentPopulation.getMostFitIndividual().fitness
		println "Function representation: " + currentPopulation.getMostFitIndividual().root.stringForm()
	}
}




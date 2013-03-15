package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class SinTest extends Specification{

	Random random = new Random()
	
	@Ignore
	def "Test Random"(){
		given:
		random.nextInt(-1)
		
		expect:
		true
	}
	
//	@Ignore
	def "BigTest"(){

		given:
		Integer currentGenerationNumber = 0
		Population currentPopulation = new Population('x', 3, 10, 20)
		Population newPopulation
		currentPopulation.createPopulation()

		//setup initial population^

		DataSet dataSet = new DataSet(100)
		dataSet.createData()
		
		println "DataSet()" + dataSet.data
		
		currentPopulation.generateFitness(dataSet.data)
		Tree mostFitIndividual = currentPopulation.getMostFitIndividual()

//		while(mostFitIndividual.fitness >= 0.005){
//			currentPopulation = currentPopulation.matingSeason()
//			currentPopulation.generateFitness(dataSet.data)
//			currentGenerationNumber++
//
//			mostFitIndividual = currentPopulation.getMostFitIndividual()
//		}

		println "Generation" + currentGenerationNumber + " Most fit individual:"
		println "FUNCTION FORM: " + mostFitIndividual.root.stringForm()
//		mostFitIndividual.printTree()

		expect:
		true
	}
	@Ignore
	def "Test toStringFunction"(){
		given:
		Tree tree = new Tree('x', 30)
		tree.createTree()
		
		println "Function IN SRING FORM: " + tree.root.stringForm()
		
		expect:
		true
	}
}

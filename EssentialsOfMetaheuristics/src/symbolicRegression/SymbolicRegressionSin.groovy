package symbolicRegression

class SymbolicRegressionSin {

	static main(args) {

		Random random = new Random()
		Integer currentGenerationNumber = 0
		Population currentPopulation = new Population('x', random.nextInt(5)+2, 500, 12)
//		new Population(terminalValue, depthLimit, populationSize, maxEvolvedHeightLimit)
		Population newPopulation
		currentPopulation.createPopulation()

		//setup initial population^

		DataSet dataSet = new DataSet(20)
		dataSet.createData()

		currentPopulation.generateFitness(dataSet.data)
		Tree mostFitIndividual = currentPopulation.getMostFitIndividual()
		
		Boolean fitnessTermination = mostFitIndividual.fitness >= 1
		Boolean generationTermination = currentGenerationNumber < 50

		while(fitnessTermination && generationTermination){
			currentPopulation = currentPopulation.matingSeason()
			currentPopulation.generateFitness(dataSet.data)
			currentGenerationNumber++

			mostFitIndividual = currentPopulation.getMostFitIndividual()
			println "Generation " + currentGenerationNumber + " has ran."
			fitnessTermination = mostFitIndividual.fitness >= 1
			generationTermination = currentGenerationNumber < 50
		}
		println "Generation" + currentGenerationNumber + " Most fit individual:"
		
		println "Generation" + currentGenerationNumber + "   Most fit individual: " + mostFitIndividual.fitness
		println "FUNCTION FORM: " + mostFitIndividual.root.stringForm()
	}
}

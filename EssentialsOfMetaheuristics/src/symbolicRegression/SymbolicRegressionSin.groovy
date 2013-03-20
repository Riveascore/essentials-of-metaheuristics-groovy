package symbolicRegression

class SymbolicRegressionSin {

	static main(args) {
		Integer maxTreeHeight = 10
		GeneticOperators operators = new GeneticOperators()
		
		Population currentPopulation = new Population('x', 4, maxTreeHeight)
		currentPopulation.createPopulation(500)
		
		
		DataSet dataSet = new DataSet(100)
		dataSet.createData()
		
		currentPopulation.generateFitness(dataSet.data)
		
		println "gen0 most fit " + currentPopulation.getMostFitIndividual().fitness
		Integer currentGeneration = 0
		
		while(currentPopulation.getMostFitIndividual().fitness > 1){
			currentPopulation = operators.matingSeason(currentPopulation)
			currentPopulation.generateFitness(dataSet.data)
			println "Ran generation " + currentGeneration
			currentGeneration++
		}
		println "Generation " + currentGeneration + "'s most fit: " + currentPopulation.getMostFitIndividual().fitness
		println "Function representation: " + currentPopulation.getMostFitIndividual().root.stringForm()
	}
}

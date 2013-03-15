package symbolicRegression

class SymbolicRegressionSin {

	static main(args) {
		/*
		 *Straight up Michael Phelps: 
		 *((((x/x)/(x/x))+x)+(((x-x)*x)-((((x/x)/((((x/x)/(x/x))+x)/x))+x)+(((((x-x)*x)-((x*x)*(x-x)))*x)-((x*x)*(x-x))))))    
		 *and his fitness: 3.039982914735619
		 *BUT HE REDUCES TO!!!
		 *1/(1+x)
		 */
		
		Random random = new Random()
		
		Integer maxGenerations = 100
		Double fitnessThreshold = 1.0
		Integer initialDepthLimit = 5 
		//random.nextInt(5)+2
		Integer populationSize = 500
		Integer maxTreeHeight = 12
		//Initialize stuff hur^
		
		Integer currentGenerationNumber = 0
		Population currentPopulation = new Population('x', initialDepthLimit, populationSize, maxTreeHeight)
//		new Population(terminalValue, depthLimit, populationSize, maxEvolvedHeightLimit)
		Population newPopulation
		currentPopulation.createPopulation()
		Tree michaelPhelps

		DataSet dataSet = new DataSet(20)
		dataSet.createData()
		

		currentPopulation.generateFitness(dataSet.data)
		Tree mostFitIndividual = currentPopulation.getMostFitIndividual()
		michaelPhelps = mostFitIndividual
		
		Boolean fitnessTermination = mostFitIndividual.fitness > fitnessThreshold
		Boolean generationTermination = currentGenerationNumber < maxGenerations

		while(fitnessTermination && generationTermination){
			currentPopulation = currentPopulation.matingSeason()
			currentPopulation.generateFitness(dataSet.data)
			currentGenerationNumber++

			mostFitIndividual = currentPopulation.getMostFitIndividual()
			if(mostFitIndividual.fitness < michaelPhelps.fitness){
				michaelPhelps = mostFitIndividual
			}
			println "Generation " + currentGenerationNumber + " has ran."
			fitnessTermination = mostFitIndividual.fitness > fitnessThreshold
			generationTermination = currentGenerationNumber < maxGenerations
		}
		println "Generation" + currentGenerationNumber + " Most fit individual:"
		
		println "Generation" + currentGenerationNumber + "   Most fit individual: " + mostFitIndividual.fitness
		println "FUNCTION FORM: " + mostFitIndividual.root.stringForm()
		
		println "Straight up Michael Phelps: " + michaelPhelps.root.stringForm() + "    and his fitness: " + michaelPhelps.fitness
		
		
		
	}
}

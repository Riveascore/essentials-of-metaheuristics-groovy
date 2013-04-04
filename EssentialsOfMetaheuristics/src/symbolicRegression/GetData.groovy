package symbolicRegression

class GetData {
	public static main(args){
		multipleRuns(0,Math.PI, "zeroToPi")
//		multipleRuns(0,2*Math.PI, "zeroToTwoPi")
//		multipleRuns(-1,1, "negOneToOne")
	}
	
	public static multipleRuns(lowRange, highRange, rangeName) {
		File fitnessFile = new File("results/${rangeName}Fitness.txt")
		File functionFile = new File("results/${rangeName}BestFunction.txt")
		Tree bestCurentFunction = runData(lowRange, highRange, fitnessFile)
		Tree bestFunctionAllRuns = bestCurentFunction
		println "Run 0 completed"
		2.times{
			File fitnessTempFile = new File("results/${rangeName}FitnessTemp.txt")
			bestCurentFunction = runData(lowRange, highRange, fitnessTempFile)
			if(bestCurentFunction.fitness < bestFunctionAllRuns.fitness){
				bestFunctionAllRuns = bestCurentFunction
				fitnessFile.delete()
				fitnessTempFile.renameTo("results/${rangeName}Fitness.txt")
			}
			else{
				fitnessTempFile.delete()
			}
			println "Run ${it+1} completed"
		}
		functionFile.write("Best function for range [${lowRange}, ${highRange}]:\n${bestFunctionAllRuns.root.stringForm()}")
		DataSet dataSet = new DataSet(20, lowRange, highRange)
		dataSet.createData()
		
		File bestFunctionPlotFile = new File("results/${rangeName}BestFunctionPlotFile")
		bestFunctionPlotFile.write("Function\tx\ty")
		
		dataSet.data.keySet().each {
			def functionName = "Sine"
			Double xValue = it
			Double yValue = Math.sin(it)
			bestFunctionPlotFile.append("\n${functionName}\t${xValue}\t${yValue}")
		}
		dataSet.data.keySet().each {
			def functionName = "OurFunction"
			Double xValue = it
			Double yValue = bestFunctionAllRuns.evaluateTree(it)
			bestFunctionPlotFile.append("\n${functionName}\t${xValue}\t${yValue}")
		}
	}
	
	public static runData(lowRange, highRange, fitnessTempFile){
		Integer maxTreeHeight = 10
		GeneticOperators operators = new GeneticOperators()

		Population currentPopulation = new Population('x', 4, maxTreeHeight)
		currentPopulation.createPopulation(500)

		DataSet dataSet = new DataSet(20, lowRange, highRange)
		dataSet.createData()

		currentPopulation.generateFitness(dataSet.data)

		Integer currentGeneration = 0
		Log log = new Log()
		
		

		Tree mostFitOverall = currentPopulation.getMostFitIndividual()
		
		fitnessTempFile.write("Gen\tBestFitness\tdBestFitness\tAverageFitness\tdAverageFitness")
	
		while(currentGeneration < 1){
			fitnessTempFile.append(log.log(currentPopulation, currentGeneration))

			currentPopulation = operators.matingSeason(currentPopulation)
			currentPopulation.generateFitness(dataSet.data)
			currentGeneration++
			if(mostFitOverall.fitness > currentPopulation.getBestFitness()){
				mostFitOverall = currentPopulation.getMostFitIndividual()
			}
		}
		log.log(currentPopulation, currentGeneration)
		fitnessTempFile.append(log.log(currentPopulation, currentGeneration))
		mostFitOverall
	}
}
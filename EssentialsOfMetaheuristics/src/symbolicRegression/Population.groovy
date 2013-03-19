package symbolicRegression

class Population {
	Double totalFitness = 0
	List<Tree> population = new ArrayList<Tree>()
	def terminalValue
	def currentDepthLimit
	def populationSize
	def maxEvolvedHeightLimit
	def initialGenerationDepth
	Tree mostFitIndividual
	Random random = new Random()

	//TODO Fix it so we don't need to pass populationSize to "create population"
	//TODO LOTS OF REDUNDANCIES TO FIX!!!!
	public Population(def terminalValue, def depthLimit, Integer populationSize, def maxEvolvedHeightLimit){
		this.terminalValue = terminalValue
		this.currentDepthLimit = depthLimit
		this.maxEvolvedHeightLimit = maxEvolvedHeightLimit
		this.populationSize = populationSize
	}
	
	public createPopulation(){
		Tree addedTree
		
		populationSize.times{
			addedTree = new Tree(this.terminalValue, this.currentDepthLimit)
			
			addedTree.createTree()
//			addedTree.maxEvolvedHeightLimit = this.maxEvolvedHeightLimit
			
			this.population.add(addedTree)
		}
	}
	
	public getMostFitIndividual(){
		population.get(0)
	}
	

	public generateFitness(Map<Double, Double> dataSet){
		
		Tree firstTree = population.get(0)
		firstTree.setTreeFitness(dataSet)
		
		Integer bestFitnessIndex = 0
		this.totalFitness += 1.0/firstTree.fitness
		
		(populationSize-1).times{
			population.get(it+1).setTreeFitness(dataSet)
			if(population.get(it+1).fitness > population.get(bestFitnessIndex).fitness){
				bestFitnessIndex = it+1
			}
			this.totalFitness += 1.0/population.get(it+1).fitness
		}
		this.mostFitIndividual = population.get(bestFitnessIndex)
		

		Tree currentTree
		def newFitness
		populationSize.times{
			currentTree = this.population.get(it)
			newFitness = currentTree.fitness/this.totalFitness
			currentTree.setNormalizedFitness(newFitness)
			this.population.set(it, currentTree)
		}
		this.sortByNormalizedFitness()
	}
	//TODO Give mutation/crossover a heightLimit value


	//TODO possibly find more efficient method? But this works for now
	public sortByNormalizedFitness(){
		List<Tree> copiedList = new ArrayList(this.population)
		List<Tree> orderedByNormalizedFitness = new ArrayList<Tree>()

		populationSize.times{
			Integer smallestNormalizedFitnessIndex = 0
			for(int i = 1; i < copiedList.size(); i++){
				if(copiedList.get(i).normalizedFitness < copiedList.get(smallestNormalizedFitnessIndex).normalizedFitness){
					smallestNormalizedFitnessIndex = i
				}
			}
			orderedByNormalizedFitness.add(copiedList.remove(smallestNormalizedFitnessIndex))
		}
		this.population = orderedByNormalizedFitness
	}
	//TODO put MUTATE/CROSSOVER and possibly other stuff into a "GeneticOperators"(better name?) class

	public selectTree(){
		Double chosenTreeIndex = random.nextDouble();
		Double sum = 0
		Double hold = 0

		for(int i = 0; i < this.populationSize; i++){
			hold = this.population.get(i).normalizedFitness
			sum += hold
			if(chosenTreeIndex <= sum){
				return this.population.get(i)
			}
		}
	}
}
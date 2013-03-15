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

	public matingSeason(){
		Population newPopulation = new Population(this.terminalValue, this.currentDepthLimit, this.populationSize, this.maxEvolvedHeightLimit)

		Tree newTree
		populationSize.times{
			newTree = this.reproduce()
			newPopulation.population.add(newTree)
		}
		newPopulation
	}

	public tournamentSelection(Integer tournamentSize){
		Tree bestParticipator = this.selectTree()

		Tree temporaryParticipator
		(tournamentSize-1).times{
			temporaryParticipator = this.selectTree()
			

			if(temporaryParticipator.normalizedFitness > bestParticipator.normalizedFitness){
				bestParticipator = temporaryParticipator
			}
		}
		
		bestParticipator
	}

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
	
	public reproduce(){
		Integer randomNumber = random.nextInt(100)
		if(randomNumber == 0){
			this.mutate(this.selectTree())
		}
		else if(randomNumber > 0 && randomNumber < 10){
			this.selectTree()
		}
		else{
			this.crossover()
		}
	}

	public crossover(){
		Tree tree1 = this.tournamentSelection(this.populationSize)
		Tree tree2 = this.tournamentSelection(this.populationSize)
		
		Tree injectionGeneTree = tree1.cloneTree()
		Tree replacedGeneTree = tree2.cloneTree()
		
		Node replacedNode = replacedGeneTree.pickRandomNode()
		//Not constrained, can pick any node here!
		//But now, the injection node decision will be constrained!
		
		def injectionHeight = replacedNode.getNodeHeight()
		
		Node injectionNode = injectionGeneTree.pickRandomNodeWithLimit(injectionHeight, this.maxEvolvedHeightLimit)
		
		replacedGeneTree.replaceNode(replacedNode, injectionNode)
		//TODO allowed to crossover with same tree? IE tree1 crossover into tree1
		replacedGeneTree
	}
	
	public mutate(Tree originalTree){
		Integer additionHeightLimit = this.maxEvolvedHeightLimit - originalTree.root.getNodeHeight()
		//TODO CALCULATE THIS^ AFTER RANDOMLY PICKED NODE
		
		Tree newTree = originalTree.cloneTree()
		Node replaceThisNode = newTree.pickRandomNode()
		
		Tree injectionTree = new Tree(newTree.terminalValue, additionHeightLimit)
		
		//TODO AT SOME POINT change depthLimit in Tree to height if we have time
		newTree.replaceNode(replaceThisNode, injectionTree.root)
		newTree
	}
}
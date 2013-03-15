package symbolicRegression

class Population {
	Double totalFitness = 0
	List<Tree> population = new ArrayList<Tree>()
	def terminalValue
	def currentDepthLimit
	def populationSize
	def maxEvolvedDepthLimit
	def initialGenerationDepth
	Random random = new Random()

	public Population(def terminalValue, def depthLimit, def populationSize, def maxEvolvedDepthLimit){
		this.terminalValue = terminalValue
		this.currentDepthLimit = depthLimit
		this.populationSize = populationSize
		this.maxEvolvedDepthLimit = maxEvolvedDepthLimit
	}

	public createPopulation(Integer populationSize){
		Tree addedTree = new Tree(this.terminalValue, this.currentDepthLimit)
		addedTree.createTree()
		populationSize.times{
			this.population.add(addedTree)
		}
	}
	


	public generateFitness(Map<Double, Double> dataSet){
		populationSize.times{
			population.get(it).setTreeFitness(dataSet)
		}
		populationSize.times{
			this.totalFitness += population.get(it).fitness
		}

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
		Population newPopulation = new Population(this.terminalValue, this.currentDepthLimit, this.populationSize, this.maxEvolvedDepthLimit)

		Tree newTree
		populationSize.times{
			newTree = this.reproduce()
			newPopulation.population.add(newTree)
		}
		newPopulation
	}

	public tournamentSelection(Integer tournamentSize){
		List<Tree> tournamentParticipators = new ArrayList<Tree>()
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

	public selectTree(){
		Double chosenTreeIndex = random.nextDouble();
		Double sum = 0
		Double hold = 0

		for(int i = 0; i < populationSize; i++){
			hold = this.population.get(i).normalizedFitness
			sum += hold
			if(chosenTreeIndex <= sum){
				return this.population.get(i)
			}
		}
	}
	
	public mutation(Tree originalTree){
		Integer additionHeightLimit = this.maxEvolvedDepthLimit - originalTree.root.getNodeHeight()
		println "node height" + originalTree.root.getNodeHeight()
		//TODO CALCULATE THIS^ AFTER RANDOMLY PICKED NODE
		
		Node oldNode = originalTree.pickRandomNode()
		Tree newTree = new Tree(originalTree.terminalValue, additionHeightLimit)
		Node mutatedBranchNode = newTree.root
		
//		println "old tree"
//		TreePrinter.printNode(originalTree.root)
//		
//		println "mutated branch"
//		TreePrinter.printNode(mutatedBranchNode)
		
		//TODO AT SOME POINT change depthLimit in Tree to height if we have time
		originalTree.replaceNode(oldNode, mutatedBranchNode)
		
//		println "changed tree"
//		TreePrinter.printNode(originalTree.root)
	}
	
	public reproduce(){
		Integer randomNumber = random.nextInt(100)
//		if(randomNumber == 0){
//			this.mutation(this.selectTree())
//		}
//		else if(randomNumber > 0 && randomNumber < 10){
//			this.selectTree()
//		}
//		else{
//			crossover(tournamentSelection(this.populationSize), tournamentSelection(this.populationSize))
//			//TODO gotta finish crossover first?
//		}
		this.mutation(this.selectTree())
	}

	public crossover(Tree injectionGeneTree, Tree replacedGeneTree){
		
		Node replacedNode = replacedGeneTree.pickRandomNode()
		def maxAdditionHeight = this.maxEvolvedDepthLimit - replacedNode.getNodeHeight()
		Node injectionNode = injectionGeneTree.pickRandomNodeWithLimit(maxAdditionHeight)

		replacedGeneTree.replaceNode(replacedNode, injectionNode)
		replacedGeneTree
		//TODO can we crossover with same tree? IE tree1 crossover into tree1
	}
}







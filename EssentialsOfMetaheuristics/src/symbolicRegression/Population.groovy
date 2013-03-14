package symbolicRegression

class Population {
	Double totalFitness = 0
	List<Tree> population = new ArrayList<Tree>()
	def terminalValue
	def depthLimit
	def populationSize
	Random random = new Random()
	
	public Population(def terminalValue, def depthLimit, def populationSize){
		this.terminalValue = terminalValue
		this.depthLimit = depthLimit
		this.populationSize = populationSize
//		this.createPopulation(this.populationSize)
	}
	
	public createPopulation(Integer populationSize){
		populationSize.times{
			this.population.add(new Tree(this.terminalValue, this.depthLimit))
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
	}
	
	public reproduce(){
		Integer randomNumber = random.nextInt(100)
		if(randomNumber == 0){
			this.mutation(this.selectTree())
		}
		else if(randomNumber >0 && randomNumber < 10){
			this.selectTree()
		}
		else{
			crossover(tournamentSelection(this.populationSize), tournamentSelection(this.populationSize))
			//TODO gotta finish crossover first?
		}
	}
	
	public matingSeason(){
		Population newPopulation = new Population(this.terminalValue, this.depthLimit, this.populationSize)
		populationSize.times{
			newPopulation.population.add(reproduce())
		}
	}
	
	public tournamentSelection(Integer tournamentSize){
		List<Tree> tournamentParticipators = new ArrayList<Tree>()
		Tree bestParticipator = this.selectTree()
		Tree temporaryParticipator
		(tournamentSize-1).times{
			temporaryParticipator = this.selectTree()
//			println "best nFittness" + bestParticipator.normalizedFitness
//			println "temporary nFittness" + temporaryParticipator.normalizedFitness
			
			if(temporaryParticipator.normalizedFitness > bestParticipator.normalizedFitness){
				bestParticipator = temporaryParticipator
			}
		}
		bestParticipator
	}
	
	public selectTree(){
		Double decision = random.nextDouble(); 
		Double sum = 0
		Double hold = 0
		println "pop fitnesses"
		populationSize.times{
			hold = this.population.get(it).normalizedFitness
			sum += hold
			if(decision <= sum){
//				return population.get(it)
			}
		}
		println "total" + sum
	}
	public mutation(Tree originalTree){
		Node node = originalTree.pickRandomNode()
		
		def maxDepthAddition = maxDepthLimit - node.depth
		def ourDepth
		
		if(maxDepthAddition < initialPopulationDepth){
			ourDepth = random.nextInt(maxDepthAddition)
		}
		else{
			ourDepth = random.nextInt(initialPopulationDepth)
		}
		Tree mutatedBranch = new Tree(node, ourDepth)
		node = mutatedBranch.root
	}
	
	public crossover(Tree injectionGeneTree, Tree replacedGeneTree){
		Node injectionNode = injectionGeneTree.pickRandomNode()
		Node replacedNode = replacedGeneTree.pickRandomNode()
		
		replacedGeneTree.replaceNode(replacedNode, injectionNode)
		//TODO can we crossover with same tree? IE tree1 crossover into tree1
	}
}







package symbolicRegression

class GeneticOperators {
	Random random = new Random()
	
	public GeneticOperators(){
	}
	
	public matingSeason(population){
		Population newPopulation = new Population(population.terminalValue, population.currentDepthLimit, population.populationSize, population.maxEvolvedHeightLimit)

		Tree newTree
		populationSize.times{
			newTree = this.reproduce()
			newPopulation.population.add(newTree)
		}
		newPopulation
	}
	
	public tournamentSelection(Integer tournamentSize, Population population){
		Tree bestParticipator = population.selectTree()

		Tree temporaryParticipator
		(tournamentSize-1).times{
			temporaryParticipator = population.selectTree()
			

			if(temporaryParticipator.normalizedFitness > bestParticipator.normalizedFitness){
				bestParticipator = temporaryParticipator
			}
		}
		
		bestParticipator
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
		Integer additionHeightLimit = originalTree.maxHeightLimit - originalTree.root.getNodeHeight()
		
		Tree clonedTree = originalTree.cloneTree()
		
		Integer injectionTreeHeight = random.nextInt(clonedTree.maxHeightLimit)+1
		Tree injectionTree = new Tree(clonedTree.terminalValue, injectionTreeHeight, clonedTree.maxHeightLimit)
		
		Node replaceThisNode = clonedTree.pickRandomNode(injectionTree.root.getNodeHeight())
		Node injectionNode = injectionTree.root
		
		clonedTree.replaceNode(replaceThisNode, injectionNode)
		clonedTree
	}
}

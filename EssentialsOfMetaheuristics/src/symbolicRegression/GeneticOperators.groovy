package symbolicRegression

class GeneticOperators {
	Random random = new Random()
	
	public GeneticOperators(){
	}
	
	public matingSeason(Population population){
		Population newPopulation = new Population(population.terminalValue, population.currentDepthLimit, population.maxTreeHeight)
		
		Tree newTree
		population.size().times{
			newTree = reproduce(population)
			newPopulation.add(newTree)
		}
		newPopulation
	}
	
	public reproduce(Population population){
		Integer randomNumber = random.nextInt(100)
		if((randomNumber > 0) && (randomNumber < 25)){
			mutate(population.selectTree())
		}
		else if(randomNumber < 50){
			population.selectTree()
		}
		else{
//			crossover(population)
			mutate(population.selectTree())
		}
	}
	
	public tournamentSelection(Population population, Integer tournamentSize){
		List<Tree> tournament = new ArrayList<Tree>()
		tournamentSize.times{
			tournament.add(population.selectTree())
		}
		tournament.sort{
			it.normalizedFitness
		}
		tournament.get(tournament.size()-1)
	}
	
	public crossover(Population population){
		Tree tree1 = this.tournamentSelection(population, population.population.size())
		Tree tree2 = this.tournamentSelection(population, population.population.size())
		
		Tree injectionGeneTree = tree1.cloneTree()
		Tree replacedGeneTree = tree2.cloneTree()
		
		Node replacedNode = replacedGeneTree.pickRandomNode(0)
		//0 means this is not constrained, can pick any node here!
		//But now, the injection node decision will be constrained!
		
		def injectionHeightLimit = replacedNode.getNodeHeight()
		println "inj height lim" + injectionHeightLimit
		
		Node injectionNode = injectionGeneTree.pickRandomNode(injectionHeightLimit)
		//TODO Right here^ is where it's shitting itself, it's getting a number over maxHeight, this somehow happens with crossover
		//in the first place, I think we're just off by one when restricting or something in tree
		
		replacedGeneTree.replaceNode(replacedNode, injectionNode)
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

package symbolicRegression

class Population {
	Double totalFitness = 0
	List<Tree> population = new ArrayList<Tree>()
	def terminalValue
	def currentDepthLimit
	def maxTreeHeight
	def initialGenerationDepth
	Random random = new Random()

	//TODO Fix it so we don't need to pass populationSize to "create population"
	//TODO LOTS OF REDUNDANCIES TO FIX!!!!
	public Population(def terminalValue, def depthLimit, def maxTreeHeight){
		this.terminalValue = terminalValue
		this.currentDepthLimit = depthLimit
		this.maxTreeHeight = maxTreeHeight
	}
	
	public addTree(Tree tree){
		population.add(tree)
	}
	
	public createPopulation(Integer populationSize){
		Tree addedTree
		
		populationSize.times{
			addedTree = new Tree(terminalValue, currentDepthLimit, maxTreeHeight)
			
			addedTree.createTree()
			
			add(addedTree)
		}
	}
	
	public printPopulation(){
		population.each {
			it.printTree()
		}
	}
	
	public generateFitness(Map<Double, Double> dataSet){
		
		population.each {
			it.setTreeFitness(dataSet)
		}
		
		population.each {
			totalFitness += 1.0/it.fitness
			//sum up for normalization
		}
		
		population.each {
			it.normalizedFitness = 1.0/it.fitness/totalFitness
		}
		population.sort{it.normalizedFitness}
	}
	
	public getMostFitIndividual(){
		population.get(size()-1)
	}
	
	public size(){
		population.size()
	}
	
	public add(Tree tree){
		population.add(tree)
	}
	
	public get(Integer index){
		population.get(index)
	}

	public selectTree(){
		Double chosenTreeIndex = random.nextDouble();
		Double sum = 0
		Double hold = 0

		for(int i = 0; i < this.population.size(); i++){
			hold = this.population.get(i).normalizedFitness
			sum += hold
			if(chosenTreeIndex <= sum){
				return this.population.get(i)
			}
		}
	}
}
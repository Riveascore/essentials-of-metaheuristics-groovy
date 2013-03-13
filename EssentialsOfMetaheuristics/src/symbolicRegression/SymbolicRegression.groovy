package symbolicRegression

class SymbolicRegression {
    
    TreeMap<Double, Double> dataSet = new TreeMap<Double, Double>()
    TreeMap<Integer, TreeOld> treePopulation = new TreeMap<Integer, TreeOld>()
    def numberOfTimeSteps
    def maxDepthLimit = 20
    def initialPopulationDepth = 5
    Random random = new Random()

    public SymbolicRegression(numberOfTimeSteps){
        //populationOfTrees
        this.numberOfTimeSteps = numberOfTimeSteps
        createData()
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
        Node tree1Node = injectionGeneTree.pickRandomNode()
        Node tree2Node = replacedGeneTree.pickRandomNode()
        
        //TODO only beef is, crossovers only happen onto nonLeaf nodes, fix this?
        //^because we have less options
        //TODO make another pickRandomNode() method to allow picking of leaves
        
        
    }
    
    public getTreeFitness(Integer treeNumber){
        
        TreeOld tree = treePopulation.get(treeNumber)
        
        def totalError = 0
        for(Map.Entry<Double, Double> entry : map.entrySet()){
            totalError += singlePointError(entry.getKey(), tree)
        }
        tree.setFitness(Math.sqrt(totalError))
        treePopulation.put(treeNumber, tree)
        //sets the fitness of a tree based on our dataSet and puts it back into our population
    }
    // get error for each tree at each x[i]
    // sum up all the errors and take the square root to get fitness of a tree
    public singlePointError(Double xValue, TreeOld tree){
        def dataSetFunctionValue = this.dataSet.get(xValue)
        def treeFunctionValue = tree.evaluateTree(xValue)
        Math.pow(treeFunctionValue - dataSetFunctionValue, 2.0)
    }
    
    
    public createTreePopulation(Integer populationSize, Integer maxTreeDepth){
        //return list of trees
        
        TreeOld currentTree
        for(Integer i = 0; i < populationSize; i++){
            currentTree = new TreeOld('x', maxTreeDepth)
            currentTree.generateRandTree()
            
            this.treePopulation.put(i, currentTree)
        }
    }
    
    public createData(){
        
        Double lowerRange = 0, topRange = 2*Math.PI
        
        Double timeStepSize = topRange/this.numberOfTimeSteps
        
        Double currentInputValue = 0
        
        for(Integer i = 0; i <= this.numberOfTimeSteps; i++){
            this.dataSet.put(currentInputValue, createFunction(currentInputValue))
            currentInputValue += timeStepSize
        }
    }
    
    public createFunction(Double input){
        Math.sin(input)
        //approximate sin(x) here^
    }
    
}

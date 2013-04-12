package symbolicRegression

import java.util.TreeMap;
import java.util.regex.Pattern.First;

class Tree {
    def depthLimit, terminalValue
    Node root
    Double fitness
    Double normalizedFitness
    def nonTerminalNodes
    def maxHeightLimit
    List<Node> allNodes = new ArrayList<Node>()


    Random random = new Random()
    def nodeValues = [
        {-> ERC()},
        "eE",
		"mE",
		"eA",
		"mA",
		"d",
        "+",
        "-",
        "*",
    ]
	
	public Tree(Integer depthLimit, Integer maxHeightLimit){
		this.depthLimit = depthLimit
		this.maxHeightLimit = maxHeightLimit
		this.createTree()
//		findNodes(root)
	}

    public createTree(){
        this.root = grow(1, this.depthLimit)
    }

    //ephemeral random constant
    public ERC(){
        //Choose from -5 to 5
        Double randomConstant = random.nextDouble()*10.0-5.0
    }

    public grow(depth, maxDepth){
        Node node
		def nodeValue
		def whichType
        if(depth >= maxDepth){
            whichType = random.nextInt(6)
        }
        else{
            whichType = random.nextInt(9)
        }
		
		nodeValue = nodeValues[whichType]
		
		if(whichType == 0){
			node = new Node(nodeValue())
			//^this is so we grab ERC value from closure
		}
		else{
			node = new Node(nodeValue)
		}
		
		if(!node.isTerminal()){
			node.setLeft(grow(depth+1, maxDepth))
			node.setRight(grow(depth+1, maxDepth))
		}
        node
    }

    public printTree(){
        Tree treeClone = this.cloneTree()
        
        
        treeClone.allNodes.each {
            if(it.value instanceof Double){
                it.setValue(Math.round(it.value))
            }
            else{
                it
            }
        }
        
        TreePrinter.printNode(treeClone.root)
    }

    public cloneTree(){
        Tree clonedTree = new Tree(this.terminalValue, this.root.cloneNode(), this.maxHeightLimit)
        clonedTree
    }

    public pickRandomNode(Integer insertedTreeHeight){
        List<Node> heightRestrictedList = new ArrayList<Node>()

        allNodes.each {
            if((it.getDepth() + insertedTreeHeight) <= maxHeightLimit){
                heightRestrictedList.add(it)
            }
        }
        def randomIndex = random.nextInt(heightRestrictedList.size())
        heightRestrictedList.get(randomIndex)
    }

    public findNodes(Node node){
        allNodes.add(node)
        if(node.left != null){
            findNodes(node.left)
        }
        if(node.right != null){
            findNodes(node.right)
        }

    }



    public replaceNode(Node originalNode, Node replacementNode){
        if(originalNode.parent == null){
            this.root = replacementNode
        }
        else{
            replacementNode.setParent(originalNode.parent)
            if(originalNode.directionFromParent == "left"){
                replacementNode.setDirectionFromParent("left")
                replacementNode.parent.setLeft(replacementNode)
            }
            else{
                replacementNode.setDirectionFromParent("right")
                replacementNode.parent.setRight(replacementNode)
            }
        }
    }

    public setTreeFitness(Map<Double, Double> dataSet){

        def totalError = 0

        for(Map.Entry<Double, Double> entry : dataSet.entrySet()){
            totalError += this.singlePointError(entry.getKey(), entry.getValue())
        }
        this.fitness = Math.sqrt(totalError)
    }

    //Dataset is <xValue, functionOutput>
    public singlePointError(Double xValue, Double functionOutput){
        def treeFunctionValue = this.evaluateTree(xValue)
        def output = Math.pow(treeFunctionValue - functionOutput, 2.0)
        output
    }

    public setNormalizedFitness(def normalizedFitnessValue){
        this.normalizedFitness = normalizedFitnessValue
    }

    public pickSubtree(){
        //TODO do I want to be able to crossover between different trees or just
        //do crossover within the same tree? If so, move this method to Node or SymbolicRegression
        Node firstNode, secondNode

        def numberOfNodes = root.countNodes()

        def firstNodeIndex = random.nextInt(numberOfNodes)
        def secondNodeIndex

        if(numberOfNodes > 1){
            while((secondNodeIndex = random.nextInt(numberOfNodes)) == firstNodeIndex){
            }
        }
        else{
            //TODO find a way to do mutation instead.
        }

        //^picks the second index value until it's different than the first
    }

    public equals(Tree tree2){
        def tree2Index = 0
        def firstValue
        def secondValue
        Boolean equivalent = false

        if(this.allNodes.size() == tree2.allNodes.size()){
            equivalent = true
            this.allNodes.each {
                secondValue = tree2.allNodes.get(tree2Index).value
                if(it.value != secondValue){
                    equivalent = false
                }
                tree2Index++
            }
        }
        equivalent
    }

    public evaluateTree(def input){
        this.root.computeNodeValue(input)
    }
}

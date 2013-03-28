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
        "+",
        "-",
        "*",
        "/",
        terminalValue
    ]

    public Tree(def terminalValue, Integer depthLimit, Integer maxHeightLimit){
        this.terminalValue = terminalValue
        this.depthLimit = depthLimit
        this.maxHeightLimit = maxHeightLimit
        this.createTree()
        findNodes(root)
    }

    public Tree(def terminalValue, Node rootNode, Integer maxHeightLimit){
        this.root = rootNode
        this.terminalValue = terminalValue
        this.depthLimit = root.getNodeHeight()-1
        this.maxHeightLimit = maxHeightLimit
        findNodes(root)
    }
    //this^ constructor is for cloneTree()

    public Tree(def terminalValue, String treeFunction, Integer maxHeightLimit){
        NodeMaker nm1 = new NodeMaker(treeFunction)
        Node nodeMakerNode = nm1.makeNode()
        this.root = nodeMakerNode
        this.terminalValue = terminalValue
        this.depthLimit = root.getNodeHeight()-1
        this.maxHeightLimit = maxHeightLimit
        findNodes(root)
    }

    public createTree(){
        //TODO root not be x, but everything else can be x
        this.root = grow(1, this.depthLimit)
    }

    public printTree(){
        TreePrinter.printNode(this.root)
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
        Math.pow(treeFunctionValue - functionOutput, 2.0)
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
    
    //ephemeral random constant
    public ERC(){
        //Choose from -5 to 5
        Double randomConstant = random.nextDouble()*10.0-5.0
    }

    public grow(depth, maxDepth){
        Node node
        if(depth >= maxDepth){
            node = new Node(terminalValue)
        }
        else{
            Integer whichType = random.nextInt(5)
            def nodeValue
            
            //Right here we either pick +,-,/,*,x, or random constant
//            if(whichType == 5){
//                println ERC()
//                nodeValue = ERC()
//            }
//            else{
                nodeValue = nodeValues[whichType]
//            }
            println "node value: " + nodeValue
            
            node = new Node()
            if(!node.isTerminal()){
                node.setLeft(grow(depth+1, maxDepth))
                node.setRight(grow(depth+1, maxDepth))
            }
            node
        }
    }

    public evaluateTree(def input){
        this.root.computeNodeValue(input)
    }
}

package symbolicRegression

import java.util.TreeMap;
import java.util.regex.Pattern.First;

import applications.robocode.BattleRunner;

class Tree implements Cloneable{
    def depthLimit, terminalValue
    Node root
    Double fitness
    Double normalizedFitness
    def nonTerminalNodes
    def maxHeightLimit
    List<Node> allNodes = new ArrayList<Node>()
    def operatorNodeCounter
    def leafNodeCounter
    //	List battleNodesToUseUp = ["en.energy", "myEnergy", "calcAngle(en.pos, p)", "calcAngle(myPos, p)", "p.distanceSq(en.pos)"]
    def battleNodes

    Random random = new Random()
    def allNodesList = [
        {-> ERC()},
        "en.energy",
        "myEnergy",
        "calcAngle(en.pos, p)",
        "calcAngle(myPos, p)",
        "p.distanceSq(en.pos)",
        "+",
        "-",
        "*"
    ]



    public Tree(Integer depthLimit, Integer maxHeightLimit){
        this.depthLimit = depthLimit
        this.maxHeightLimit = maxHeightLimit
//        Collections.shuffle(battleNodesToUseUp)
        operatorNodeCounter = 0
        leafNodeCounter = 0
		battleNodes = ["en.energy" : 0, "myEnergy" : 0, "calcAngle(en.pos, p)" : 0, "calcAngle(myPos, p)" : 0, "p.distanceSq(en.pos)" : 0]
        this.createTree()
        findNodes(root)
    }
	
    public Tree(Integer depthLimit, Integer maxHeightLimit, Map battleNodeMap){
        this.depthLimit = depthLimit
        this.maxHeightLimit = maxHeightLimit
        Collections.shuffle(battleNodesToUseUp)
        operatorNodeCounter = 0
        battleNodeCounter = 0
		battleNodes = battleNodeMap
        this.createTree()
        findNodes(root)
    }

    public createTree(){
        this.root = grow(1, this.depthLimit)
    }

    //ephemeral random constant
    public ERC(){
        //Choose from -5 to 5
        Double randomConstant = random.nextDouble()*2.0*Math.PI
    }

    public boolean needBattleNode(){
        boolean toReturn = false
        battleNodes.each {
            if(it.value == 0){
                toReturn = true
            }
        }
        toReturn
    }
    //Gets next battle node with value=0 and increments value +1
    public def getNextBattleNode(){
        List neededNodes = []
		def returnedNode
        
        battleNodes.each {
            if(it.value == 0){
                neededNodes.add(it.key)
            }
        }

        Collections.shuffle(neededNodes)
		returnedNode = neededNodes.get(0)
		battleNodes.put(returnedNode, 1+battleNodes.get(returnedNode))
		leafNodeCounter++
		returnedNode
    }
	
	public def numberOfBattleNodes(){
		def count = 0
        battleNodes.each {
            count += it.value
        }
	}

	//battleNodes.put(nodeVal, battleNodes.get(nodeVal) + 1)
    public grow(depth, maxDepth){
        Node node
        def nodeVal
        def rand
        def numChildren = 0

        if(depth>=maxDepth){
            //use constant if not all 5 have been used
            if(needBattleNode()){
                nodeVal= getNextBattleNode()
                node = new Node(nodeVal)
            }else{
                rand = random.nextInt(6)
                nodeVal = allNodesList[rand]
                if(rand==0){
                    node = new Node(nodeVal())
                    //if battle const
                }else{
					battleNodes.put(nodeVal, battleNodes.get(nodeVal) + 1)
                    node = new Node(nodeVal)
                }
				leafNodeCounter++
            }
        }else{
            //if all constants not used
            if(needBattleNode()){
                //use operator
                if((leafNodeCounter-operatorNodeCounter) == 0){
                    rand = random.nextInt(3)+6
                    nodeVal = allNodesList[rand]
                    operatorNodeCounter++
                    node = new Node(nodeVal)
                    //depth = depth + 1
                    node.setLeft(grow(depth + 1, maxDepth))
                    node.setRight(grow(depth + 1, maxDepth))

                    //random node
                }else{
                    rand = random.nextInt(9)
                    //if constant
                    if(rand<6){
                        if(rand==0){
                            nodeVal = allNodesList[0]
							leafNodeCounter++
                            node = new Node(nodeVal())
                        }else{
							nodeVal = getNextBattleNode()
//                            nodeVal = battleNodesToUseUp.remove(0)
                            node = new Node(nodeVal)
                        }
                        //if operator
                    }else{
                        nodeVal = allNodesList[rand]
                        operatorNodeCounter++
                        node = new Node(nodeVal)
                        //depth = depth + 1
                        node.setLeft(grow(depth + 1, maxDepth))
                        node.setRight(grow(depth + 1, maxDepth))
                    }
                }
                //if all constants used do random
            }else{
                rand = random.nextInt(9)

                //if operator
                if(rand > 5){
                    nodeVal = allNodesList[rand]
                    node = new Node(nodeVal)
                    //depth = depth + 1
					operatorNodeCounter++
                    node.setLeft(grow(depth + 1, maxDepth))
                    node.setRight(grow(depth + 1, maxDepth))
                    //if rand const
                }else if(rand==0){
                    nodeVal = allNodesList[0]
					leafNodeCounter++
                    node = new Node(nodeVal())
                    //if battle const
                }else{
                    nodeVal = allNodesList[rand]
					battleNodes.put(nodeVal, battleNodes.get(nodeVal)+1)
					leafNodeCounter++
                    node = new Node(nodeVal)
                }
            }
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

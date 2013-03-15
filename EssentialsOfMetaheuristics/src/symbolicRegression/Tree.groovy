package symbolicRegression

import java.util.TreeMap;
import java.util.regex.Pattern.First;

class Tree {
    def depthLimit, terminalValue
    Node root
    Double fitness
	Double normalizedFitness
    def nonTerminalNodes
    def randomNodeIndex
    Node desiredNode
	def maxAdditionHeight
	def maxEvolvedHeightLimit
	
    def numberOfNodes, selectedNodeIndex
    Node selectedNode
    
    Random random = new Random()
    def nodeValues = ["+", "-", "*", "/", terminalValue]
    
    public Tree(def terminalValue, Integer depthLimit){
        this.terminalValue = terminalValue
        this.depthLimit = depthLimit
        this.numberOfNodes = 0
        this.selectedNodeIndex = 1
        this.createTree()
    }
	
	public printTree(){
		TreePrinter.printNode(this.root)
	}
	
	public cloneTree(){
		Tree clonedTree = new Tree(this.terminalValue, this.depthLimit)
		clonedTree.root = this.root.cloneNode()
		clonedTree
	}
	
    public pickRandomNode(){
		this.selectedNodeIndex = 0
		this.numberOfNodes = 0
		this.selectedNode = null
		//Reset these^ to 0 so they don't get passed onto the next generation
		this.countNodes(this.root)
        Integer randomChildNumber = random.nextInt(this.numberOfNodes) + 1
        pickNode(this.root, randomChildNumber)
		this.selectedNode
    }
	
	public pickNode(Node node, Integer desiredIndex){
		if(node != null){
			this.selectedNodeIndex += 1
			if(desiredIndex == this.selectedNodeIndex){
				this.selectedNode = node
			}
			pickNode(node.left, desiredIndex)
			pickNode(node.right, desiredIndex)
		}
	}
	
	public countNodes(Node node){
		if(node != null){
			this.numberOfNodes += 1
			countNodes(node.left)
			countNodes(node.right)
		}
		this.numberOfNodes
		//TODO ^I don't think we need this
	}
	
	public pickRandomNodeWithLimit(Integer injectionHeight, Integer maxEvolvedHeightLimit){
		this.selectedNodeIndex = 0
		this.numberOfNodes = 0
		this.selectedNode = null
		//Reset these^ to 0 so they don't get passed onto the next generation
		this.countNodesWithLimit(this.root, injectionHeight, maxEvolvedHeightLimit)
		
		Integer randomChildNumber = random.nextInt(this.numberOfNodes) + 1
		pickNodeWithLimit(this.root, randomChildNumber, injectionHeight, maxEvolvedHeightLimit)
		this.selectedNode
	}
    
    public pickNodeWithLimit(Node node, Integer desiredIndex, Integer injectionHeight, Integer maxEvolvedHeightLimit){
        if(node != null){
			if(node.depth + injectionHeight <= maxEvolvedHeightLimit){
				this.selectedNodeIndex += 1
	            if(desiredIndex == this.selectedNodeIndex){
	                this.selectedNode = node
	            }
				//if reached height cap we skip last if
			}
            
            pickNodeWithLimit(node.left, desiredIndex, injectionHeight, maxEvolvedHeightLimit)
            pickNodeWithLimit(node.right, desiredIndex, injectionHeight, maxEvolvedHeightLimit)
        }
    }
	
	public countNodesWithLimit(Node node, Integer injectionHeight, Integer maxEvolvedHeightLimit){
		
		if(node != null){
			if(node.depth + injectionHeight <= maxEvolvedHeightLimit){
				this.numberOfNodes += 1
			}
			//if node height is too big, we skip over that node and go to the children
			countNodesWithLimit(node.left, injectionHeight, maxEvolvedHeightLimit)
			countNodesWithLimit(node.right, injectionHeight, maxEvolvedHeightLimit)
		}
		this.numberOfNodes
		//TODO ^I don't think we need this
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
    
    public createTree(){
        //TODO root not be x, but everything else can be x
        this.root = grow(1, this.depthLimit)
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
	

    
    public grow(depth, maxDepth){
        Node node
        if(depth >= maxDepth){
            node = new Node(terminalValue)
        }
        else{
            node = new Node(nodeValues[random.nextInt(5)])
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

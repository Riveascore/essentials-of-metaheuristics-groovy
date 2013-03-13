package symbolicRegression

import java.util.regex.Pattern.First;

class Tree {
    def depthLimit, terminalValue
    Node root
    def fitness
    def nonTerminalNodes
    def randomNodeIndex
    Node desiredNode
    
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
        this.countNodes(this.root)
        //TODO throw exception Exception is: tree contains no non-terminals
    }
    

    
    public pickRandomNode(){
        Integer randomChildNumber = random.nextInt(this.numberOfNodes) + 1
        pickNode(this.root, randomChildNumber)
    }
    
    public pickNode(Node node, Integer desiredIndex){
        if(node != null){
            if(desiredIndex == this.selectedNodeIndex){
                this.selectedNode = node
            }
            this.selectedNodeIndex += 1
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
    }
    
    //TODO delete
    public pickRandomNodeOld(){
        //TODO possibly make it so we can replace leaves with nonleaves
        Integer randomChildNumber = random.nextInt(this.nonTerminalNodes) + 1
        println "random child number: " + randomChildNumber
        if(this.nonTerminalNodes == 0){
            //TODO throw exception Exception is: tree contains no non-terminals
        }
        pickNonTerminalChild(this.root, randomChildNumber)
    }
    
    public pickNonTerminalChild(Node node, Integer desiredIndex){
        if(!node.isTerminal()){
            if(desiredIndex == this.randomNodeIndex){
                this.desiredNode = node
            }
            this.randomNodeIndex += 1
            pickNonTerminalChild(node.left, desiredIndex)
            pickNonTerminalChild(node.right, desiredIndex)
        }
    }
    
    public countNonTerminalNodes(Node node){
        if(!node.isTerminal()){
            this.nonTerminalNodes += 1
            countNonTerminalNodes(node.left)
            countNonTerminalNodes(node.right)
        }
        this.nonTerminalNodes
    }
    //TODO delete
    
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
    
    
    
    
    public setFitness(def fitnessValue){
        this.fitness = fitnessValue
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
    
    public mutation(){
        
    }
    
    public crossover(){
        
    }
}

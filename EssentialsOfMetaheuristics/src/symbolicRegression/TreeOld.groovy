package symbolicRegression

class TreeOld {
    def depthLimit, terminalValue
    Node root
    def fitness
    
    Random random = new Random()
    def nodeValues = ["+", "-", "*", "/", terminalValue]
    
    //depthLimit 20 for now to be safe
    
    //TODO random tree depth or fixed?
    //TODO replace certain nodes with terminals so tree isn't always fully balanced
    
    public TreeOld(def terminalValue, Integer depthLimit){
        this.terminalValue = terminalValue
        this.depthLimit = depthLimit
        this.root = new Node(nodeValues[random.nextInt(4)])
    }
    
    public findYValue(){
        root.getLeft().getLeft().getLeft().computeNodeValue()
    }
    
    public generateRandTree(){
        this.fill(root)
    }
    
    public setFitness(def fitnessValue){
        this.fitness = fitnessValue
    }
    
    public fill(Node node){
        //might need to fix our fill method
        //TODO make it so we can fill trees with x, but later on replace all terminal values
        if(node.depth == depthLimit -2){
            node.setLeft(new Node(terminalValue))
            node.setRight(new Node(terminalValue))
        }
        else{
            Node left = new Node(node.nonTerminals[random.nextInt(4)])
            Node right = new Node(node.nonTerminals[random.nextInt(4)])
            
            node.setLeft(left)
            node.setRight(right)
            
            fill(left)
            fill(right)
        }
    }
    
    public replaceTerminals(def replacementValue){
        //TODO implement a "replaceTerminals" method so we can initially fill trees with no problems
    }
    
    public printTree(){
        List<List> lists = new ArrayList<ArrayList>()
        List emptyList = new ArrayList()
        for(int i = 0; i < depthLimit; i++){
            emptyList = new ArrayList()
            recurse(root, i, emptyList)
            lists.add(emptyList)
        }
        for(int i = 0; i < lists.size(); i++){
            println lists[i]
        }
    }
    
    public evaluateTree(def input){
        this.root.computeNodeValue(input)
    }
    
    public recurse(Node node, Integer depth, List nodes){
        if(node.depth != depth){
            recurse(node.left, depth, nodes)
            recurse(node.right, depth, nodes)
        }
        else{
            nodes.add(node.value)
        }
    }
}

package symbolicRegression

class SubtreeSelection {

    def root
    def count
    Node subtree
    
    public SubtreeSelection(Node root){
        Random random = new Random()
        this.root = root
        this.count = 0
        countNodes(this.root)
        
        if(count == 0){
            return null
        }
        else{
            Integer nodeNumber = (random.nextInt(this.count) + 1)
            this.count = 0
            this.subtree = pickNode(root, nodeNumber)
        }
    }
    
    public countNodes(Node node){
        if(!node.isTerminal()){
            this.count += 1
            countNodes(node.left)
            countNodes(node.right)
            //TODO ^this might have to be outside if statement
        }
    }
    
    public pickNode(Node node, Integer nodeNumber){
        Node nextNode
        
        if(!node.isTerminal()){
            this.count += 1
            if(this.count >= nodeNumber){
                return node
            }
        }
        nextNode = pickNode(node.left, nodeNumber)
        if(nextNode != null){
            return nextNode
        }
        nextNode = pickNode(node.right, nodeNumber)
        if(nextNode != null){
            return nextNode
        }
    }
    
    
}

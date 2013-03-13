package symbolicRegression

class Node {
    def value, depth
    Node parent, left, right
    //TODO discover limit on "depth" of tree
    //TODO ^not sure if should be def?
    
    List nonTerminals = ["+", "-", "*", "/"]
    
    public Node(value){
        this.value = value
        if(this.parent == null){
            this.depth = 0
        }
    }
    
    public setValue(def value){
        this.value = value
    }
    
    public setParent(Node parent){
        this.parent = parent
    }
    
    public setLeft(Node left){
        this.left = left
        left.setParent(this)
        left.depth = this.depth + 1
    }
    
    public setRight(Node right){
        this.right = right
        right.setParent(this)
        right.depth = this.depth + 1
    }
    
    public isTerminal(){
        !nonTerminals.contains(this.value)
    }
    
    public computeNodeValue(def input){
        switch(this.value){
            case '+': 
            this.left.computeNodeValue(input) + this.right.computeNodeValue(input)
            break
            
            case '-': 
            this.left.computeNodeValue(input) - this.right.computeNodeValue(input)
            break
            
            case '*': 
            this.left.computeNodeValue(input) * this.right.computeNodeValue(input)
            break
            
            case '/': 
            if(this.right.computeNodeValue(input) == 0){
                1
            }
            else{
                this.left.computeNodeValue(input) / this.right.computeNodeValue(input)
            }
            //^Used the return 1 as a guard against division by 0
            break
            default: input
            break
            //TODO possibly implement sin, cos, tan, abs, sqrt, log if we have time
        }
    }
}

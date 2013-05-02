package symbolicRegression

class Node {
    def value
    Node parent, left, right
    def directionFromParent

    //TODO discover limit on "depth" of tree
    //TODO ^not sure if should be def?

    List nonTerminals = ["+", "-", "*"]
	def battleNodeList = [
		"en.energy",
		"myEnergy",
		"calcAngle(en.pos, p)",
		"calcAngle(myPos, p)",
		"p.distanceSq(en.pos)",
	]

    public Node(value){
        this.value = value
    }

    public Node(){
    }
    
    
    public stringForm(){
        if(this.nonTerminals.contains(this.value)){
            return "(" + this.left.stringForm() + this.value + this.right.stringForm() + ")"
        }
        else {
            this.value
        }
    }
	
	public isBattleNode(){
		battleNodeList.contains(this.value)
	}

    public cloneNode(){
        Node node = new Node(this.value)

        if(this.left != null){
            node.setLeft(this.left.cloneNode())
        }
        if(this.right != null){
            node.setRight(this.right.cloneNode())
        }
        return node
    }


    public getNodeHeight(){
        Integer height = 1
        Integer leftHeight =0
        Integer rightHeight =0
        Integer maxHeight = 0

        if(this.left != null){
            leftHeight = this.left.getNodeHeight()
        }
        else if(this.right != null){
            rightHeight = this.right.getNodeHeight()
        }
        maxHeight=Math.max(leftHeight, rightHeight)
        return height + maxHeight
    }

    public setValue(def value){
        this.value = value
    }

    public setParent(Node parent){
        this.parent = parent
    }

    public setLeft(Node left){
        left.setParent(this)
        this.left = left
        this.left.directionFromParent = "left"
    }

    public setRight(Node right){
        right.setParent(this)
        this.right = right
        this.right.directionFromParent = "right"
    }

    public setDirectionFromParent(String direction){
        this.directionFromParent = direction
    }

    public isTerminal(){
        !nonTerminals.contains(this.value)
    }

    public getDepth(){
        if(this.parent != null){
            return this.parent.getDepth() + 1
        }
        0
    }

    public computeNodeValue(def input){
		
        switch(this.value){
            case '+':
				def leftSide = this.left.computeNodeValue(input)
				def rightSide = this.right.computeNodeValue(input)
                leftSide + rightSide
                break

            case '-':
				def leftSide = this.left.computeNodeValue(input)
				def rightSide = this.right.computeNodeValue(input)
                leftSide - rightSide
                break

            case '*':
				def leftSide = this.left.computeNodeValue(input)
				def rightSide = this.right.computeNodeValue(input)
                leftSide * rightSide
                break

            case '/':
				def leftSide = this.left.computeNodeValue(input)
				def rightSide = this.right.computeNodeValue(input)
                if(Math.abs(rightSide) == 0.0){
                    1.0
                }
                else{
					leftSide / rightSide
                    //^Used the return 1 as a guard against division by 0
                }
                break
            case Double:
                this.value
                break
            
            default: 
				input
                break
        }
    }
}



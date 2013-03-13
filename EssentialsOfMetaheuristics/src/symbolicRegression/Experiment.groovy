package symbolicRegression

class Experiment {
    static main(args) {
        Node root = new Node("+")
        root.setLeft(new Node('x'))
        root.setRight(new Node('/'))
        root.right.setRight(new Node('x'))
        root.right.setLeft(new Node('x'))
        
        println root.depth
        println root.right.right.depth
        

    }
}

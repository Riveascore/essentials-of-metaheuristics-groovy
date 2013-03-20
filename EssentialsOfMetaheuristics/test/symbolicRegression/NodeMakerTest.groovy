package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class NodeMakerTest extends Specification{

	@Ignore
	def "Test inputString"() {
		given:
		NodeMaker nm1 = new NodeMaker('(((x*x)+x)+((x+x)-x))')
		NodeMaker nm2 = new NodeMaker('(x+x)')
		NodeMaker nm3 = new NodeMaker('x')

		Node bigTree = nm1.makeNode()
		Node small = nm2.makeNode()
		Node justX = nm3.makeNode()

		println "Big tree height: " + bigTree.getNodeHeight()
		TreePrinter.printNode(bigTree)
		println "Small tree"
		TreePrinter.printNode(small)
		println "Just x tree"
		TreePrinter.printNode(justX)


		expect:
		true
	}

	@Ignore
	def "test cloneTree()"(){
		given:
		NodeMaker nm1 = new NodeMaker('(((x*x)+x)+((x+x)-x))')
		Node testRoot = nm1.makeNode()
		Tree tree = new Tree('x', testRoot, 7)

		Tree clonedTree = tree.cloneTree()

		println "original tree"
		tree.printTree()
		println "nodes"
		tree.allNodes.size.times(){
			println tree.allNodes.get(it).value
		}

		println "cloend tree"
		clonedTree.printTree()
		clonedTree.allNodes.size.times(){
			println clonedTree.allNodes.get(it).value
		}
		expect:
		true
	}

	def "test mutation 100 times for height overflow"(){
		given:
		Integer maxTreeHeight = 5
		GeneticOperators operators = new GeneticOperators()
		NodeMaker nm1 = new NodeMaker('(((x*x)+x)+((x+x)-x))')
		Node testRoot = nm1.makeNode()

		Tree tree = new Tree('x', testRoot, maxTreeHeight)

		Boolean overflowHeight = false
		Tree mutatedTree
		Integer height = 0
		
		100.times{
			mutatedTree = null
			mutatedTree = operators.mutate(tree)
			height = mutatedTree.root.getNodeHeight()
			overflowHeight = (height > maxTreeHeight)
		}
		
		expect:
		!overflowHeight
	}

	@Ignore
	def "Test replace node"(){
		given:

		NodeMaker nm1 = new NodeMaker('(((x*x)+x)+((x+x)-x))')
		Node testRoot = nm1.makeNode()

		Tree tree = new Tree('x', testRoot, 7)

		println "original tree"
		tree.printTree()

		Node replacedNode = tree.pickRandomNode(0)
		println "replacedNdoe: " + replacedNode.value
		Node injectionNode = new Node('p')
		tree.replaceNode(replacedNode, injectionNode)

		println "replaced node tree"
		tree.printTree()

		expect:
		true
	}
	@Ignore
	def "Test node depths"(){
		given:
		Tree tree = new Tree('x', 4, 7)
		tree.pickRandomNode(0)

		expect:
		true
	}
}






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
	
	
	def "Test if mutation ever goes crazy"(){
		given:
		
		NodeMaker nm1 = new NodeMaker('(((y*y)+x)+((y+y)-x))')
		Node testRoot = nm1.makeNode()
		
		Tree tree = new Tree(testRoot, 7)
		tree.printTree()
		
		10.times{
			println tree.pickRandomNode(5).value
		}
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






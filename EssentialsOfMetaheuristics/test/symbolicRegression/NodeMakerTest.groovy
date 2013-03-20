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

		println "cloned tree"
		clonedTree.printTree()
		clonedTree.allNodes.size.times(){
			println clonedTree.allNodes.get(it).value
		}
		expect:
		true
	}

	@Ignore
	def "Test crossover with printing"(){
		given:
		Integer maxTreeHeight = 5
		GeneticOperators operators = new GeneticOperators()
		
		Tree tree1 = new Tree('x', '(((x*x)+x)+((x+x)-x))', maxTreeHeight)
		Tree tree2 = new Tree('x', '(x+((x-x)*x))', maxTreeHeight)
		Tree tree3 = new Tree('x', '(x*(x+x))', maxTreeHeight)
		
		tree1.printTree()
		tree2.printTree()
		tree3.printTree()
		
		Population population = new Population('x', 4, 3, maxTreeHeight)
		population.addTree(tree1)
		population.addTree(tree2)
		population.addTree(tree3)
		
		DataSet dataSet = new DataSet(100)
		dataSet.createData()
		
		population.generateFitness(dataSet.data)
		// have to generate fitnesses for dataSet before we can do crossover
		Tree tree4 = operators.crossover(population)
		
		tree4.printTree()
		expect:
		true
	}
	
	def "tree.equals() test"(){
		given:
		Tree tree = new Tree('x', '(((x*x)+x)+((x+x)-x))', 5)
		Tree differentTree = new Tree('x', '(x+((x-x)*x))', 5)
		Tree clonedTree = tree.cloneTree()
		
		expect:
		tree.equals(tree)
		tree.equals(clonedTree)
		!tree.equals(differentTree)
	}
	
	def "tournamentSelection() test to see if we're getting the same tree everytime or not"(){
		given:
		Integer maxTreeHeight = 5
		GeneticOperators operators = new GeneticOperators()
		
		Tree tree1 = new Tree('x', '(((x*x)+x)+((x+x)-x))', maxTreeHeight)
		Tree tree2 = new Tree('x', '(x+((x-x)*x))', maxTreeHeight)
		Tree tree3 = new Tree('x', '(x*(x+x))', maxTreeHeight)
		
		//(x+((x-x)*x)), (((x*x)+x)+((x+x)-x)), (x*(x+x))
		Population population = new Population('x', 4, 3, maxTreeHeight)
		population.addTree(tree1)
		population.addTree(tree2)
		population.addTree(tree3)
		
		DataSet dataSet = new DataSet(100)
		dataSet.createData()
		population.generateFitness(dataSet.data)
		
		population.population.each {
			println "norm fit: " + it.normalizedFitness
		}
		Tree tempTree
		5.times{
			tempTree = population.selectTree()
			if(!tempTree.equals(population.population.get(2))){
				println "we got something else!"
				tempTree.printTree()
			}
		}
		expect:
		true
	}
	
	@Ignore
	def "crossover 100 times for height overflow"(){
		given:
		Integer maxTreeHeight = 5
		GeneticOperators operators = new GeneticOperators()
		
		Tree tree1 = new Tree('x', '(((x*x)+x)+((x+x)-x))', maxTreeHeight)
		Tree tree2 = new Tree('x', '(x+((x-x)*x))', maxTreeHeight)
		Tree tree3 = new Tree('x', '(x*(x+x))', maxTreeHeight)
		
		Population population = new Population('x', 4, 3, maxTreeHeight)
		population.addTree(tree1)
		population.addTree(tree2)
		population.addTree(tree3)
		
		DataSet dataSet = new DataSet(100)
		dataSet.createData()
		population.generateFitness(dataSet.data)
		
		
		Boolean heightOverflow = false
		Tree crossedoverTree
		Integer height = 0
		
		100.times{
			crossedoverTree = null
			crossedoverTree = operators.crossover(population)
			height = crossedoverTree.root.getNodeHeight()
			heightOverflow = (height > maxTreeHeight)
		}
		expect:
		heightOverflow == false
		//TODO find a way to see if population has changed, may be as easy as:
		//make copy of population, do crossovers, at end
		//expect:
		//clonedPopulation == population
	}
	
	def "mutation 100 times for height overflow"(){
		given:
		Integer maxTreeHeight = 5
		GeneticOperators operators = new GeneticOperators()

		Tree tree = new Tree('x', '(((x*x)+x)+((x+x)-x))', maxTreeHeight)

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






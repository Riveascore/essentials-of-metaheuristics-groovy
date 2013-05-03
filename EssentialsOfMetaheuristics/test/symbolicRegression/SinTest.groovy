package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class SinTest extends Specification{

    def "Test Random"(){
        given:

		Tree tree = new Tree(7,7)
		
		Tree clonedTree = tree.cloneTree()

		GeneticOperators geneticOperators = new GeneticOperators()
		Tree mutatedTree = geneticOperators.mutate(tree)
		
		def list = []
		list.add(["hihi", 0.12])
		list.add(["hihi", 0.75])
		list.add(["crumb", 0.25])
		list.add(["bawbbbb", 0.05])
		list.add(["poop", 0.75])
//		
//		list.each{
//			println it[1]
//		}
		
		list.sort() {
			it[1]
		}
		
		println list.size()
		
		println ((Integer)10*0.20)
    }
}

package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class BasicTest extends Specification{
    
	Random random = new Random()
	
    def "Test new tree implementation"() {
        given:
		Tree tree = new Tree(10, 10)
		println tree.root.stringForm()
    }
}

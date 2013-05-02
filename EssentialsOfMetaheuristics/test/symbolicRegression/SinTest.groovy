package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class SinTest extends Specification{

    def "Test Random"(){
        given:


		Tree tree = new Tree(10,10)
		println tree.root.stringForm()
		println "battleNodes: ${tree.battleNodes}"
        expect:
		!tree.needBattleNode()
    }
}

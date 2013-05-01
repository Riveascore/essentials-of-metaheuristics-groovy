package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class SinTest extends Specification{

    def "Test Random"(){
        given:
        def battleNodes = ["en.energy" : 1, "myEnergy" : 0, "calcAngle(en.pos, p)" : 2, "calcAngle(myPos, p)" : 0, "p.distanceSq(en.pos)" : 0]
        List stuff= battleNodes.findAll { battleNode ->
            battleNode.value == 0
        }
        when:

        expect:
        true
    }
}

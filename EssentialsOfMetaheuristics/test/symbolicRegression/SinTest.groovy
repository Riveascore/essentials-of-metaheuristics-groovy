package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class SinTest extends Specification{

    def "Test Random"(){
        given:

        Tree tree = new Tree(10,10)
        Tree tree2 = new Tree(710,10)

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

        list.sort() { it[1] }

        println list.size()

        println ((Integer)10*0.20)

        def listOfObjects = []
        def firstCitizen = ["tree":tree, "fitness":0.6, "normalizedFitness":0.0]
        println "firstCitizen.getClass(): ${firstCitizen.getClass()}"
        println "fC.get(\"tree\").root.stringForm(): ${firstCitizen.get("tree").root.stringForm()}"
        
        def citizen2 = ["tree":tree2, "fitness":0.4, "normalizedFitness":0.0]
        listOfObjects.add(firstCitizen)
//        listOfObjects.add(citizen2)
        listOfObjects << ["tree":tree2, "fitness":0.4, "normalizedFitness":0.0]
        
        firstCitizen.putAt("fitness", 0.52)
        
//        def best = listOfObjects.max { citizen ->
//            citizen.get("fitness")
//        }
//        
//        
//        def funcStr = best.getAt("tree").root.stringForm()
//        println "Best: ${funcStr}"
        
        println listOfObjects
        listOfObjects.sort{citizen ->
            //["tree":tree, "fitness":0.0, "normalizedFitness":0.0]
            citizen.getAt("fitness")
        }
        
        println listOfObjects
        
        listOfObjects.sort{ a, b ->
            b.getAt("fitness") <=> a.getAt("fitness")
        }
        
        def stringFormatPopulation = listOfObjects.collect { ourObject ->
            [
                "tree":ourObject.getAt("tree").stringForm(), 
                "fitness":ourObject.getAt("fitness"),
                "normalizedFitness":ourObject.getAt("normalizedFitness")
            ]
        }
        println "first: ${listOfObjects[2]}"
        
        println listOfObjects[0].keySet()
        
        def elites = []
        elites << ["foo":200, "bar":3, "baz": "wut?"]
        elites << ["foo":2, "bar":33, "baz": "NCage"]
        elites << ["foo":2, "bar":3, "baz": "snoop"]
        
//        [Fir, Sec] = [listOfObjects, elites]
        
        5.times{ index ->
            println index
        }
    }
}

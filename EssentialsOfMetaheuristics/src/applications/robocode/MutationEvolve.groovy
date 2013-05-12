package applications.robocode

import java.awt.Robot.RobotDisposer;
import java.util.concurrent.*

import symbolicRegression.GeneticOperators
import symbolicRegression.Tree
import groovy.time.*

class MutationEvolve {

    static Random randomObject = new Random()

    static def userHome = System.getProperty("user.home")
    static def evolved_robots = "${userHome}/essentials-of-metaheuristics-groovy/EssentialsOfMetaheuristics/evolved_robots"
    static def evolved = "${evolved_robots}/evolved"
    static File outputFile = new File("${userHome}/essentials-of-metaheuristics-groovy/EssentialsOfMetaheuristics/output.txt")
    
    static def enemies = [
        "aw.Gilgalad",
        "gh.GresSuffurd",
        "evolved.Individual_526319",
        "jk.mini.Cunobelin",
        "jk.precise.Wintermute",
        "kc.serpent.WaveSerpent",
        "mue.Ascendant"
    ]
    
    static def populationSize = 100 //256
    static def numberOfRounds = 3
    static def numberOfGenerations = 30

    static main(args) {
        GeneticOperators geneticOperators = new GeneticOperators()
        List population = []
        List clonedPopulation
        def numberOfElites
        def clonesAndElites
        
        outputFile.write("Results:\n")
        
        //create initial population
        
        populationSize.times {
            Tree tree = new Tree(10,10)
            population << ["tree":tree, "fitness":0.0, "normalizedFitness":0.0]
        }

        //Run first battles on randomized population
        population = runBattles(population, "MutationChupaCabra", "MutationBattle", true, numberOfRounds)
        
        //Mutate/Crossover, then run battles again
        numberOfGenerations.times{
            
            //elitism to pass on best
            clonesAndElites = elitism(population)
            
            population = clonesAndElites["elitists"]
            clonedPopulation = clonesAndElites["clonedPopulation"]
            
            numberOfElites = population.size()
            
            //Add mutated/crossedover functions
            (populationSize-numberOfElites).times{
                Tree chosenTree = selectTree(clonedPopulation)
                
                Tree mutatedTree = geneticOperators.mutate(chosenTree)
                
                population << ["tree":mutatedTree, "fitness":0.0, "normalizedFitness":0.0]
            }
            
            population = runBattles(population, "MutationChupaCabra", "MutationBattle", true, numberOfRounds)
        }
        
        //Run final battles, use 50 rounds to be accurate
        population = runBattles(population, "MutationChupaCabra", "MutationBattle", true, 50)
    }
    
    
    
    
    public static runBattles(inputPopulation, robotTemplate, battleTemplate, nodisplay, howManyRounds){
        List battlePopulation = inputPopulation
        def functionString
        def robotBuilderValuesToInsert
        def id = "ChupaCabra"
        def battleRunner
        def fitness
        def totalFitness
        def robotBuilder
        def bestRobot
        Tree citizenTree

        battlePopulation.each {citizen ->
            fitness = 0.0

            cleanDatabase()
            
            citizenTree = citizen["tree"]
            functionString = citizenTree.root.stringForm()
            
            robotBuilderValuesToInsert = ["id" : id, "functionString" : functionString]

            robotBuilder = new RobotBuilder("templates/${robotTemplate}.template")
            robotBuilder.buildJarFile(robotBuilderValuesToInsert)
            
            eraseFiles(id)

            //TODO replace with method
            enemies.each {enemy ->
                battleRunner = new MutationBattleRunner("templates/${battleTemplate}.template")
                battleRunner.buildBattleFile(enemy, howManyRounds)
                fitness += battleRunner.runBattle(id, enemy, nodisplay)
            }
            
            citizen["fitness"] = fitness/enemies.size()

            eraseJar(id)
        }
        
        bestRobot = battlePopulation.max { citizen ->
            citizen["fitness"]
        }
        
        functionString = bestRobot["tree"].stringForm()
        fitness = bestRobot["fitness"]
        
        //Write best bot to file
        outputFile.append("\n${functionString}\t${fitness}")
        
        //THIS IS TO NORMALIZE FITNESS, ALL SHIFT, NO CL!!! goml
        totalFitness = battlePopulation.sum { citizen ->
            citizen["fitness"]
        }
        
        battlePopulation.each { citizen ->
            citizen["normalizedFitness"] = citizen["fitness"]/totalFitness
        }
        
        battlePopulation.sort{ a, b ->
            b.getAt("normalizedFitness") <=> a.getAt("normalizedFitness")
        }
        
        battlePopulation
    }

    public static selectTree(inputPopulation){
        Double chosenTreeIndex = randomObject.nextDouble();
        Double sum = 0.0
        Double hold = 0.0

        for(int i = 0; i < inputPopulation.size(); i++){
            hold = inputPopulation[i]["normalizedFitness"]
            sum += hold
            if(chosenTreeIndex <= sum){
                return inputPopulation[i]["tree"]
            }
        }
    }
    
    public static eraseFiles(id){
        def command
        
        command = "rm ${evolved}/${id}.java"
        command.execute()
        command = "rm ${evolved}/${id}.class"
        command.execute()
        command = "rm ${evolved}/${id}\$microEnemy.class"
        command.execute()
        command = "rm ${evolved}/${id}.properties"
        command.execute()
    }
    
    public static eraseJar(id){
        def command
        
        command = "rm ${evolved_robots}/${id}.jar"
        command.execute()
        //remove symbolic link to .jar
        command = "rm ${userHome}/robocode/robots/${id}.jar"
        command.execute()
    }
    
    public static cleanDatabase(){
        def command = "rm ${userHome}/robocode/robot.database"
        command.execute()
    }
    
    public static battleEnemies(templateName, id, citizen){
        enemies.each {enemy ->
            def battleRunner
            def fitness

            battleRunner = new MutationBattleRunner("templates/${templateName}.template")
            battleRunner.buildBattleFile(enemy)
            fitness = battleRunner.runBattle(id, enemy)
            citizen[1] += fitness
        }
    }
    
    public static elitism(previousPopulation){
        def elite
        def clonedPopulation = []
        
        //clone population so mutations/crossovers won't affect previous
        previousPopulation.each { citizen ->
            clonedPopulation << ["tree":citizen["tree"].cloneTree(), "fitness":citizen["fitness"], "normalizedFitness":citizen["normalizedFitness"]]
        }

        def elitists = []
        
        //add elitism for best 3
        [0.10, 0.03, 0.02].each { percentage ->
            ((Integer)(populationSize*percentage)).times { index ->
                elite = clonedPopulation[populationSize-1-index] 
                elitists << elite
            }
        }
        
        // return clones of previous and elitists
        ["clonedPopulation":clonedPopulation, "elitists":elitists]
    }
    
    public static printPopulation(populationInput){
        def functionStringTemp
        def fitnessTemp
        def normalizedFitnessTemp
        
        populationInput.each { citizen ->
            functionStringTemp = citizen["tree"].stringForm()
            fitnessTemp = citizen["fitness"]
            normalizedFitnessTemp = citizen["normalizedFitness"]
            println "${fitnessTemp}\t${normalizedFitnessTemp}"
        }
    }
}


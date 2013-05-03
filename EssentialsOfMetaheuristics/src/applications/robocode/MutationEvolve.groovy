package applications.robocode

import java.awt.Robot.RobotDisposer;
import java.util.concurrent.*

import symbolicRegression.GeneticOperators
import symbolicRegression.Tree
import groovy.time.*

class MutationEvolve {

	static Random randomObject = new Random()

	static def userHome = System.getProperty("user.home")
	static def evolved_robots = "${userHome}/git/essentials-of-metaheuristics-groovy/EssentialsOfMetaheuristics/evolved_robots"
	static def evolved = "${evolved_robots}/evolved"
	static def bestAllTime = []

	static def enemies = [
		"aw.Gilgalad",
		"gh.GresSuffurd",
		"evolved.Individual_526319",
		"evolved.HawkOnFire",
		"jk.mini.Cunobelin",
		"jk.precise.Wintermute",
		"kc.serpent.WaveSerpent",
		"xander.cat.XanderCat",
		"mue.Ascendant"
	]
	static def populationSize = 100 //256
	static def numberOfRounds = 4
	static def numberOfGenerations = 30

	static main(args) {

		//create initial population
		List population = []
		populationSize.times {
			Tree tree = new Tree(10,10)
			population.add([tree, 0.0])
		}

		println "Old ass gen 1:"
		population = runBattles(population)
		List clonedPopulation = []




		numberOfGenerations.times{
			//Do this x times, x = numberOfGenerations
			//Nauuu we clone population
			population.each {
				clonedPopulation.add([it[0].cloneTree(), it[1]])
			}

			population = []
			//add elitism
			((Integer)populationSize*0.06).times {
				population.add([
					clonedPopulation[populationSize-1][0],
					0.0
				])
			}
			((Integer)populationSize*0.04).times  {
				population.add([
					clonedPopulation[populationSize-2][0],
					0.0
				])
			}
			((Integer)populationSize*0.02).times  {
				population.add([
					clonedPopulation[populationSize-3][0],
					0.0
				])
			}


			//Then runBattles again on MutatedPopulation
			//		226 left

			//Mutate stuffs on it
			GeneticOperators gen = new GeneticOperators()
			(populationSize-(Integer)populationSize*0.12).times{
				//def randomIndex = random.nextInt(clonedPopulation.size())
				Tree chosenTree = selectTree(clonedPopulation)
				Tree mutatedTree = gen.mutate(chosenTree)
				population.add([mutatedTree, 0.0])
			}
			println "\n New mutated population:"
			population = runBattles(population)
		}
	}

	public static runBattles(inputPopulation){
		List population = inputPopulation
		def functionString
		def robotBuilderValuesToInsert
		def id = "ChupaCabra"
		def numberOfThreads = enemies.size()
		def timeStart
		def timeStop
		TimeDuration duration

		population.each {citizen ->
			def robotBuilder
			def command
//			def pool = Executors.newFixedThreadPool(numberOfThreads)
//			def completionService = new ExecutorCompletionService<>(pool)

			functionString = citizen[0].root.stringForm()
			robotBuilderValuesToInsert = ["id" : id, "functionString" : functionString]

			robotBuilder = new RobotBuilder("templates/MutationChupaCabra.template")
			robotBuilder.buildJarFile(robotBuilderValuesToInsert)

			timeStart = new Date()
			enemies.each {enemy ->
				def battleRunner
				def enemyID = enemy
				def fitness

				// No threads
				battleRunner = new MutationBattleRunner("templates/MutationBattle.template")
				battleRunner.buildBattleFile(enemyID)

				fitness = battleRunner.runBattle(id, enemyID)
				citizen[1] += fitness
				
				//Threads
//				completionService.submit({
//					battleRunner = new MutationBattleRunner("templates/MutationBattle.template")
//					battleRunner.buildBattleFile(enemyID)
//
//					battleRunner.runBattle(id, enemyID)
//				});
			}

//			numberOfThreads.times {
//				final Future<Double> future = completionService.take();
//				final Double content = future.get();
//				citizen[1] += content
//			}
//			pool.shutdown()

			timeStop = new Date()
			duration = TimeCategory.minus(timeStop, timeStart)
			println duration
			
			citizen[1] /= enemies.size()

			command = "rm ${evolved_robots}/${id}.jar"
			command.execute()
			//remove symbolic link to .jar
			command = "rm ${userHome}/robocode/robots/${id}.jar"
			command.execute()


			//Cuz apparently rm Word* doesn't work...
			command = "rm ${evolved}/${id}.java"
			command.execute()
			command = "rm ${evolved}/${id}.class"
			command.execute()
			command = "rm ${evolved}/${id}\$microEnemy.class"
			command.execute()
			command = "rm ${evolved}/${id}.properties"
			command.execute()
		}


		//THIS IS TO NORMALIZE FITNESS, ALL SHIFT, NO CL!!! goml

		def totalFitness = 0.0
		population.each { totalFitness += it[1] }
		population.each {
			it[1] = it[1]/totalFitness
		}

		population.sort{ it[1] }

		//		population.each { println "${it[0].root.stringForm()}\t${it[1]}" }
		def best = population.max { it[1] }
		bestAllTime.add(best)
		println "${best[0].root.stringForm()}\t${best[1]}"
		population
	}


	public static selectTree(inputPopulation){
		Double chosenTreeIndex = randomObject.nextDouble();
		Double sum = 0
		Double hold = 0

		for(int i = 0; i < inputPopulation.size(); i++){
			hold = inputPopulation[i][1]
			sum += hold
			if(chosenTreeIndex <= sum){
				return inputPopulation[i][0]
			}
		}
	}
}
package applications.robocode

import java.awt.Robot.RobotDisposer;

import symbolicRegression.Tree

class EvolveChupa {


	static Random random = new Random()

	static def userHome = System.getProperty("user.home")
	static def evolved_robots = "${userHome}/essentials-of-metaheuristics-groovy/EssentialsOfMetaheuristics/evolved_robots"
	static def evolved = "${evolved_robots}/evolved"

	static def bestMeleeScore = 0.0
	static def bestMeleeFunction

	
	static def bestOneVOneScore = 0.0
	static def bestOneVOneFuction


	static main(args) {
		
//		150.times {
//			melee()
//		}
//		println "\n\nBest score: ${bestMeleeScore}"
//		println "Most beast function: ${bestMeleeFunction}"
		
		1000.times {
			oneVone()
		}
		println "\n\nBest score: ${bestOneVOneScore}"
		println "Most beast function: ${bestOneVOneFuction}"
	}
	
	public static void melee(){
		def id = "Chupa"
		def robotBuilder
		def battleRunner
		def functionString
		def scores
		def command

		def currentMeleeScore
		def currentMeleeFunction
		
		def totalScore = 0

		Tree tree = new Tree(7,7)
		functionString = tree.root.stringForm()
		currentMeleeFunction = functionString

		//^not need these two

		def values = ["id" : id, "functionString" : functionString]

		robotBuilder = new RobotBuilder("templates/ChupaCabra.template")
		robotBuilder.buildJarFile(values)
		battleRunner = new MeleeBattleRunner("templates/melee.template")

		battleRunner.buildBattleFile(id)

		scores = battleRunner.runBattle(id)
		
		/*
		 * DOING BEST FUNCTION UPDATE
		 */
		scores.each{
			totalScore += it
		}
		currentMeleeScore = scores[0]/totalScore
		
//		println "Robot ${id}'s eval function: ${currentMeleeFunction}"
//		println "${id}'s fitness: ${currentMeleeScore}"
//		println "${id}'s scoreNumberReal: ${scores[0]}"
		
		if(currentMeleeScore > bestMeleeScore){
			bestMeleeScore = currentMeleeScore
			bestMeleeFunction = currentMeleeFunction
		}
		/*
		 * DOING BEST FUNCTION UPDATE
		 */
		
		
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
	
	
	
	
	public static def oneVone(){
		def id = "Chupa"
		def robotBuilder
		def battleRunner
		def functionString
		def scores
		def command
		def currentOneVOneFunction
		def currentOneVOneScore
		def totalScore = 0

		Tree tree = new Tree(7, 7)
		functionString = tree.root.stringForm()
		currentOneVOneFunction = functionString

		//^not need these two

		def values = ["id" : id, "functionString" : functionString]

		robotBuilder = new RobotBuilder("templates/ChupaCabra.template")
		robotBuilder.buildJarFile(values)
		battleRunner = new OurBattleRunner("templates/oneVone.template")

		battleRunner.buildBattleFile(id)

		scores = battleRunner.runBattle(id)
		
		totalScore = scores[0] + scores[1]
		currentOneVOneScore = scores[0]/totalScore
		
		if(currentOneVOneScore > bestOneVOneScore){
			bestOneVOneScore = currentOneVOneScore
			bestOneVOneFuction = currentOneVOneFunction
		}
		
		println "Robot ${id}'s eval function: ${functionString}"
		println "${id}'s score: ${currentOneVOneScore}"
		

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
		
		return scores
	}
}

package applications.robocode

import groovy.text.SimpleTemplateEngine

// I'm adding a comment via GitHub - how weird is that?!?

/*
 * This assumes that there is a copy of Robocode in your home directory,
 * and that it has been configured (via the GUI) to be able to load robot
 * files from the evolved_robots directory in this project.
 */
class OneVOneBattleRunner {
	def userHome = System.getProperty("user.home")
	def robotDirectory = "evolved_robots"
	def robotDirectoryAbsolute = new File(robotDirectory).absolutePath
	def template

	def OneVOneBattleRunner(String templateFileName) {
		def engine = new SimpleTemplateEngine()
		template = engine.createTemplate(new File(templateFileName))
	}

	def buildBattleFile(id) {
		File battleFile = new File("${robotDirectory}/evolve.battle")
		battleFile.delete()
		battleFile.createNewFile()
		def result = template.make(["id" : id])
		battleFile << result.toString()
	}

	def runBattle(id) {
		linkJarFile(id)
		File battleFile = new File("${robotDirectory}/evolve.battle")
		def command = "${userHome}/robocode/robocode.sh -battle ${battleFile.absolutePath}"
//		def command = "${userHome}/robocode/robocode.sh -battle ${battleFile.absolutePath} -nodisplay"
		
		def proc = command.execute(null, new File(robotDirectory))
		proc.waitFor()
		assert proc.exitValue() == 0		//**** should keep ******************//
		assert proc.err.text.equals("")		//**** should keep ******************//
		def lines = proc.in.text.split("\n")
		
		
		def resultUs = -1
		lines.each { line ->
			def pattern = ~/evolved\.${id}\s+(\d+)/
			def m = (line =~ pattern)
			if (m) {
				resultUs = Integer.parseInt(m[0][1])
			}
		}
		
		def resultHawk = -1
		lines.each { line ->
			def pattern = ~/evolved\.HawkOnFire\s+(\d+)/
			def m = (line =~ pattern)
			if (m) {
				resultHawk = Integer.parseInt(m[0][1])
			}
		}
		return [resultUs, resultHawk]
		
//		if (result >= 0) {
//			return result
//		} else {
//			throw new RuntimeException("Didn't find score for evolved robot")
//		}
	}
	
	def linkJarFile(id) {
		def robotDir = new File("${userHome}/robocode/robots/")
		def command = "ln -s ${robotDirectoryAbsolute}/${id}.jar ."
		def proc = command.execute(null, robotDir)
		proc.waitFor()
		assert proc.err.text.equals("")
		assert proc.exitValue() == 0
	}
}
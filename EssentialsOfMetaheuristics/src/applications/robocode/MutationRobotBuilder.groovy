package applications.robocode

import groovy.text.SimpleTemplateEngine

class MutationRobotBuilder {
	def template
	def robotDirectory = "evolved_robots"
	def robotPackage = "evolved"
	
	def RobotBuilder(String templateFileName) {
		def engine = new SimpleTemplateEngine()
		template = engine.createTemplate(new File(templateFileName))
	}
	
	def buildJarFile(values) {
		buildClassFile(values)
		buildPropertiesFile(values)
		def fileNamePrefix = "${values['id']}"
		def command = "jar -cf ${fileNamePrefix}.jar"
		[".java", ".class", "\$microEnemy.class", ".properties"].each { suffix ->
			command += " ${robotPackage}/${fileNamePrefix}${suffix}"
		}
		def proc = command.execute(null, new File(robotDirectory))
		proc.waitFor()
        println(proc.in.text)			//**** should keep ******************//
		assert proc.err.text.equals("")	//**** should keep ******************//
		assert proc.exitValue() == 0  	//**** should keep ******************//
	}
	
	def buildPropertiesFile(values) {
		def filenamePrefix = "${values['id']}"
		def propertiesFileName = "${filenamePrefix}.properties"
		def propertiesFile = new File("${robotDirectory}/${robotPackage}/${propertiesFileName}")
		propertiesFile << "robots: ${robotPackage}/${filenamePrefix}.class"
	}
	
	def buildClassFile(values) {
		def javaFileName = buildJavaFile(values)
		
		def command = "javac -cp ../lib/robocode.jar ${robotPackage}/${javaFileName}"
//		def command = "javac -cp ../lib/robocode.jar ${robotPackage}/${javaFileName}"
		def proc = command.execute(null, new File(robotDirectory))
		proc.waitFor()
		assert proc.exitValue() == 0 		//**** should keep ******************//
		assert proc.err.text.equals("")		//**** should keep ******************//
	}
	
	def buildJavaFile(values) {
		def javaFileName = makeJavaFileName(values)
		File javaFile = createFile(javaFileName)
		writeFile(javaFile, values)
		return javaFileName
	}
	
	def makeJavaFileName(values) {
		def filename = "${values['id']}.java"
	}

	private File createFile(javaFileName) {
		new File(robotDirectory).mkdir()
		new File("${robotDirectory}/${robotPackage}").mkdir()
		File javaFile = new File("${robotDirectory}/${robotPackage}/${javaFileName}")
		assert !javaFile.exists()
		javaFile.createNewFile()
		return javaFile
	}

	private writeFile(javaFile, values) {
		def result = template.make(values)
		javaFile << result.toString()
	}

}
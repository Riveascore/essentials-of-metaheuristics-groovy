package populationMethods

import operators.Crossovers

class DifferentialEvolution {
	def maximize(problem, mutationRate, populationSize, crossover= new Crossovers().uniformCrossover){
		
		Random random = new Random()
		def best = problem.create()
		def bestQuality = problem.quality(best)
		
		def population = new Vector<>(populationSize)
		def parents = new Vector<>(populationSize)
		
		populationSize.times {
			population.add(problem.random())
			//add random individual to population
		}
		
		while (!problem.terminate(best, bestQuality)) {
			
			populationSize.times {
				def child = population.get(it)
				def childFitness = problem.quality(child)
				if(!parents.isEmpty()){
					def parent = parents.get(it)
					def parentFitness = problem.quality(parent)
					if(parentFitness > childFitness){
						population.set(it, parent)
					}
				}
				
				def currentIndividual = population.get(it)
				
				if(problem.quality(currentIndividual) > problem.quality(best)){
					best = currentIndividual
				}
			}
			
			parents = population.clone()
			
			parents.size().times {
				
				def randomA = getRandomWithExclusion(random, 0, population.size()-1, it),
					randomB = getRandomWithExclusion(random, 0, population.size()-1, it, randomA),
					randomC = getRandomWithExclusion(random, 0, population.size()-1, it, randomA, randomA)
				
				def parent = parents.get(it),
					vectorA = parents.get(randomA),
					vectorB = parents.get(randomB),
					vectorC = parents.get(randomC),
					vectorD = vectorA + problem.tweak(vectorB-vectorC, mutationRate)
			    
			    def uniformCrossover = new operators.UniformCrossover()
				def child = crossover(vectorD, parent)
			    parents.set(it, problem.copy(child))
			}
		}
		best
	}
	
	
	public getRandomWithExclusion(Random rand, start, end, int... exclude) {
		def random = start + rand.nextInt(end - start + 1 - exclude.length)
		exclude.each {
			if (random < it) {
				return random
			}
			random++
		}
		random
	}
}

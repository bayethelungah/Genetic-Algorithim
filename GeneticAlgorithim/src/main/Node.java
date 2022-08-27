package main;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import processing.core.PVector;

public class Node {

	private static String[] moves = new String[5];
	private static final int size = 5;
	private static final int lifeTime = 10;
	private static final int acc = 1;
	private float timeTillInZone = 0.0f;
	private float speed = Float.POSITIVE_INFINITY;
	private boolean inZone = false;
	private PVector pos;
	private PVector initialPos;
	private final int weightVariation = 10;
	private static final int geneLength = 12;
	public String gene;

	public HashMap<String, Integer> weights = new HashMap<String, Integer>();

	public Node(PVector pos) {
		this.pos = pos;
		this.initialPos = new PVector(pos.x, pos.y);
		this.gene = initRandomGene();

		moves[0] = "L";
		moves[1] = "R";
		moves[2] = "D";
		moves[3] = "U";
		moves[4] = " ";

		weights.put("L", GeneticAlgorithim.rand.nextInt(weightVariation));
		weights.put("R", GeneticAlgorithim.rand.nextInt(weightVariation));
		weights.put("U", GeneticAlgorithim.rand.nextInt(weightVariation));
		weights.put("D", GeneticAlgorithim.rand.nextInt(weightVariation));
		weights.put(" ", GeneticAlgorithim.rand.nextInt(weightVariation));

	}

	public void show(GeneticAlgorithim screen) {
		screen.noStroke();
		screen.fill(0);
		if (inZone) {
			screen.fill(255, 0, 0);
		}
		screen.ellipse(pos.x, pos.y, size, size);

	}

	public void run(float deltaTime) {
		timeTillInZone += deltaTime;
		String move = chooseMove(weights);

		switch (move) {
			case "L":
				pos.x -= acc;
				break;
			case "R":
				pos.x += acc;
				break;
			case "U":
				pos.y -= acc;
				break;
			case "D":
				pos.y += acc;

		}
	}

	private String chooseMove(HashMap<String, Integer> weights) {
		ArrayList<String> flatWeights = new ArrayList<String>();

		weights.forEach(
				(key, value) -> {
					for (int i = 0; i < value; i++) {
						flatWeights.add(key);
					}
				});

		return flatWeights.get(GeneticAlgorithim.rand.nextInt(flatWeights.size()));

	}

	public void checkInZone(Zone zone) {
		if (zone.contains(pos)) {
			inZone = true;
		} else {
			inZone = false;
		}

	}

	public void updateWeights(HashMap<String, Integer> newWeights, int mutationRate) {
		Random rand = GeneticAlgorithim.rand;
		weights.put("L", isMutation(mutationRate, rand) ? rand.nextInt(weightVariation) : newWeights.get("L"));
		weights.put("R", isMutation(mutationRate, rand) ? rand.nextInt(weightVariation) : newWeights.get("R"));
		weights.put("U", isMutation(mutationRate, rand) ? rand.nextInt(weightVariation) : newWeights.get("U"));
		weights.put("D", isMutation(mutationRate, rand) ? rand.nextInt(weightVariation) : newWeights.get("D"));

		weights.put(" ", rand.nextInt(weightVariation));
	}

	private boolean isMutation(int mutationRate, Random rand) {
		return rand.nextInt(100) <= mutationRate;
	}

	public void reset() {
		pos = new PVector(initialPos.x, initialPos.y);
		inZone = false;
		timeTillInZone = 0.0f;
	}

	public void parseGene(float deltaTime, int index) {
		timeTillInZone += deltaTime;
		String[] moves = gene.split("");

		String move = gene.split("")[index];
		switch (move) {
			case "L":
				pos.x -= acc;
				break;
			case "R":
				pos.x += acc;
				break;
			case "U":
				pos.y -= acc;
				break;
			case "D":
				pos.y += acc;

		}

	}

	private String initRandomGene() {
		char[] possibleMoves = { 'L', 'R', 'U', 'D' };
		String gene = "";

		for (int i = 0; i < geneLength - 1; i++) {
			gene += possibleMoves[GeneticAlgorithim.rand.nextInt(possibleMoves.length - 1)];
		}

		gene += " ";

		return gene;

	}

	public boolean isInZone() {
		return inZone;
	}

	public float getSpeed() {
		return speed;
	}

	public PVector getPos() {
		return pos;
	}

	public static int getLifeTime() {
		return lifeTime;
	}

	public static int getSize() {
		return size;
	}

	public static int getGeneLength() {
		return geneLength;
	}

}

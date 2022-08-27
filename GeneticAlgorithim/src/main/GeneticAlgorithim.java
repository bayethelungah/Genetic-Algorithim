package main;

import java.util.Random;

import main.utils.NodeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;

public class GeneticAlgorithim extends PApplet {

	private static final int WINDOW_WIDTH = 700;
	private static final int WINDOW_HEIGHT = 500;
	private final int numberOfNodes = 200;
	private int generations = 1;
	private Node[] nodes;
	private boolean isRunning = false;
	private float time;
	private float deltaTime;
	private float lifeRemaining;
	private int deviance = 2;
	private int fontSize = 15;
	private Zone zone;
	private float delay = 2;
	private float timeDelay = delay;
	private float netGain = 0.0f;
	public static Random rand = new Random();
	public static final int mutationRate = 1;
	public static int fitNodes = 0;
	private float bestTime = Float.POSITIVE_INFINITY;
	private float avgTime = 0.0f;
	private boolean showInfo = true;
	private float prevTime;
	private int lifeIndex = Node.getGeneLength();

	public static void main(String[] args) {
		PApplet.main("main.GeneticAlgorithim");

	}

	public void settings() {
		size(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void setup() {
		lifeRemaining = Node.getGeneLength();
		prevTime = lifeRemaining;
		nodes = initNodes();
		surface.setTitle("Genetic Algorithim");
		time = millis();
		zone = new Zone();

	}

	public void draw() {
		long currentTime = millis();
		deltaTime = (currentTime - time) * 0.001f;
		background(255);

		if (showInfo) {
			fill(0, 0, 100);
			textSize(fontSize);
			text("Generations: " + generations, 10, fontSize);
			text("Time Remaining: " + (int) (lifeRemaining), 10, (fontSize * 2));
			text("Fit Nodes: " + fitNodes, 10, (fontSize * 3));
			text("Average Time: " + avgTime, 10, (fontSize * 4));
			text("Best Time: " + bestTime, 10, (fontSize * 5));
		}

		if (netGain < 0) {
			fill(255, 0, 0);
		} else {
			fill(0, 255, 0);
		}
		text("Net Gain: " + netGain, 10, (fontSize * 6));

		zone.updateCoords(new PVector(mouseX, mouseY));
		zone.show(this);

		for (Node node : nodes) {
			node.show(this);
			node.checkInZone(zone);
		}

		if (isRunning) {

			if (lifeRemaining <= 0) {
				lifeRemaining = Node.getLifeTime();
				return;
			}

			float diff = prevTime - lifeRemaining;

			if (diff < 1) {

				run(deltaTime, zone, (int) (prevTime - 1) != 0 ? (int) (prevTime - 1) : 0);
				prevTime = lifeRemaining;
				if (prevTime < 0) {
					prevTime = Node.getGeneLength();
				}
			}

		}

		time = currentTime;
	}

	public void keyReleased() {
		if (key == ' ') {
			isRunning = true;
		}

		if (key == 's') {
			showInfo = !showInfo;
		}

	}

	public void mousePressed() {
		zone.set();
	}

	private Node[] initNodes() {
		Node[] nodes = new Node[numberOfNodes];

		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(
					new PVector(rand.nextInt(0, WINDOW_WIDTH), rand.nextInt(WINDOW_HEIGHT - 50, WINDOW_HEIGHT)));
		}

		return nodes;

	}

	private void run(float deltaTime, Zone zone, int index) {
		lifeRemaining -= deltaTime;
		if (lifeRemaining > 0) {
			for (Node node : nodes) {
				node.parseGene(deltaTime, index);
			}
		} else {
			generations++;
			selection(nodes, zone);
			delay = timeDelay;
		}
	}

	private void selection(Node[] nodes, Zone zone) {
		ArrayList<Node> fitNodes = new ArrayList<Node>();
		Node parentOne;
		Node parentTwo;

		for (Node node : nodes) {
			if (node.isInZone()) {
				fitNodes.add(node);
				avgTime += node.getSpeed();
			}
		}

		if (fitNodes.size() > 0) {
			Collections.sort(fitNodes, new NodeComparator());
			bestTime = fitNodes.get(0).getSpeed();

			parentOne = fitNodes.get(0);
			parentTwo = fitNodes.get(1);

			avgTime /= fitNodes.size();
		} else {
			Node[] parents = zone.getClosestDuoToZone(nodes);
			parentOne = parents[0];
			parentTwo = parents[1];

			avgTime = 0;
		}

		for (int i = 0; i < nodes.length; i++) {
			nodes[i].reset();
			GeneticAlgorithim.fitNodes = 0;
		}

	}

	private String crossover(Node parentOne, Node parentTwo) {

		return "";

	}

}

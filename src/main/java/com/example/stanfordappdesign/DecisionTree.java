package com.example.stanfordappdesign;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class TreeNode<Item> {
    Item data;
    int score = 0;
    List<TreeNode<Item>> children;

    public TreeNode(Item data) {
        this.data = data;
        this.children = new ArrayList<>();
    }
    public TreeNode(Item data, int score) {
        this.data = data;
        this.score = score;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode<Item> child) { this.children.add(child); }

}
public class DecisionTree<Item> {
    private final TreeNode<Item> root;
    static final int minApplicationScore = 10;
    static final int medianApplicationScore = 15;
    static final int maxApplicationScore = 20;

    public DecisionTree(Item rootData) {
        root = new TreeNode<>(rootData);
    }

    private TreeNode<Item> getRoot() {
        return root;
    }

    private void addNode(TreeNode<Item> parent, Item data, int score) {
        TreeNode<Item> newNode = new TreeNode<>(data, score);
        parent.addChild(newNode);
    }

    private void addNode(TreeNode<Item> parent, Item data) {
        TreeNode<Item> newNode = new TreeNode<>(data);
        parent.addChild(newNode);
    }

    public static int evaluateMcatScore(Applicant applicant) {
        final int minMcatScore = 472;
        final int q1McatScore = 492;
        final int medianMcatScore = 511;
        final int q3McatScore = 520;
        final int maxMcatScore = 528;
        final int applicantMcatScore = applicant.getMcatScore();

        if (applicantMcatScore > maxMcatScore || applicantMcatScore < minMcatScore) {
            System.out.println("\tMCAT score is not within the accepted range (497-528).\n");
        }

        else {
            DecisionTree<Integer> mcatScoreTree = new DecisionTree<>(0);
            TreeNode<Integer> root = mcatScoreTree.getRoot();

            mcatScoreTree.addNode(root, q3McatScore, maxApplicationScore);
            mcatScoreTree.addNode(root, medianMcatScore, medianApplicationScore);
            mcatScoreTree.addNode(root, q1McatScore, minApplicationScore);

            TreeNode<Integer> currentNode = root;

            while (!currentNode.children.isEmpty()) {
                boolean found = false;
                for (TreeNode<Integer> child : currentNode.children) {
                    if (applicantMcatScore >= child.data) {
                        found = true;
                        currentNode = child;
                        applicant.totalScore += child.score;
                        break;
                    }
                }
                if (!found) { break; }
            }

            return currentNode.score;

        }
        return 0;
    }

    public static int evaluateGpa(Applicant applicant) {
        final double minGpa = 0.0;
        final double q1Gpa = 3.50;
        final double medianGpa = 3.75;
        final double q3Gpa = 3.90;
        final double maxGpa = 4.0;
        final double applicantGpa = applicant.getGpa();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        if (applicantGpa > maxGpa || applicantGpa < minGpa) {
            System.out.println("\tGpa is not within the accepted range (0.0-4.0).\n");
        }

        else {
            DecisionTree<Double> gpaTree = new DecisionTree<>(0.0);
            TreeNode<Double> root = gpaTree.getRoot();

            gpaTree.addNode(root, q3Gpa, maxApplicationScore);
            gpaTree.addNode(root, medianGpa, medianApplicationScore);
            gpaTree.addNode(root, q1Gpa, minApplicationScore);

            TreeNode<Double> currentNode = root;

            while (!currentNode.children.isEmpty()) {
                boolean found = false;
                for (TreeNode<Double> child : currentNode.children) {
                    if (applicantGpa >= child.data) {
                        found = true;
                        currentNode = child;
                        applicant.totalScore += child.score;
                        break;
                    }
                }
                if (!found) { break; }
            }

            return currentNode.score;

        }
        return 0;
    }

    public static int evaluateCoursework(Applicant applicant) {
        final String applicantCoursework = applicant.getCoursework();
        int tempScore = evaluatePsuScore(applicantCoursework);
        applicant.totalScore += tempScore;
        return tempScore;
    }

    public static int evaluateLettersOfRecommendation(Applicant applicant) {
        final String applicantLettersOfRecommendation = applicant.getLettersOfRecommendation();
        int tempScore = evaluatePsuScore(applicantLettersOfRecommendation);
        applicant.totalScore += tempScore;
        return tempScore;
    }

    public static int evaluateWorkExperience(Applicant applicant) {
        final String applicantWorkExperience = applicant.getWorkExperience();
        int tempScore = evaluatePsuScore(applicantWorkExperience);
        applicant.totalScore += tempScore;
        return tempScore;
    }

    public static int evaluateEssay(Applicant applicant) {
        final String applicantEssay = applicant.getEssay();
        int tempScore = evaluatePsuScore(applicantEssay);
        applicant.totalScore += tempScore;
        return tempScore;
    }

    public static int evaluatePsuScore(String applicantPsu) {
        int score = 0;
        DecisionTree<String> psuTree = new DecisionTree<>("");
        TreeNode<String> root = psuTree.getRoot();

        psuTree.addNode(root, "Perfect", 10);
        psuTree.addNode(root, "Satisfactory", 5);

        TreeNode<String> currentNode = root;
        while (!currentNode.children.isEmpty()) {
            boolean found = false;
            for (TreeNode<String> child : currentNode.children) {
                if (applicantPsu.equals(child.data)) {
                    found = true;
                    currentNode = child;
                    score += child.score;
                    break;
                }
            }
            if (!found) { break; }
        }
        return score;
    }

    public static int evaluateDegree(Applicant applicant) {
        DecisionTree<String> degreeTree = new DecisionTree<>("");
        TreeNode<String> root = degreeTree.getRoot();
        degreeTree.addNode(root, "Doctorate", 10);
        degreeTree.addNode(root, "Masters", 5);

        TreeNode<String> currentNode = root;
        while (!currentNode.children.isEmpty()) {
            boolean found = false;
            for (TreeNode<String> child : currentNode.children) {
                if (applicant.getHighestDegree().equals(child.data)) {
                    found = true;
                    currentNode = child;
                    applicant.totalScore += child.score;
                    break;
                }
            }
            if (!found) { break; }
        }

        return currentNode.score;

    }

    public static int evaluateSchool(Applicant applicant) {
        DecisionTree<String> schoolTree = new DecisionTree<>("");
        TreeNode<String> root = schoolTree.getRoot();

        schoolTree.addNode(root, "Tier 1", 5);
        schoolTree.addNode(root, "Tier 2", 3);

        TreeNode<String> currentNode = root;
        while (!currentNode.children.isEmpty()) {
            boolean found = false;
            for (TreeNode<String> child : currentNode.children) {
                if (applicant.getSchoolAttended().equals(child.data)) {
                    found = true;
                    currentNode = child;
                    applicant.totalScore += child.score;
                    break;
                }
            }
            if (!found) { break; }
        }
        return currentNode.score;

    }

    public static int evaluateFirstGen(Applicant applicant) {
        if (applicant.isFirstGeneration()) {
            applicant.totalScore += 5;
            return 5;
        }
        else {
            return 0;
        }
    }

    public static boolean evaluateEligibility(Applicant applicant) {
        DecisionTree<String> eligibilityTree = new DecisionTree<>("Determining eligibility...");
        TreeNode<String> root = eligibilityTree.getRoot();

        eligibilityTree.addNode(root, "Pass.");
        eligibilityTree.addNode(root,"Applicant is a foreign citizen who has not studied at least one year in the United States. ");

        eligibilityTree.addNode(root.children.get(0), "Pass.");
        eligibilityTree.addNode(root.children.get(0),"Applicant does not possess at least a Bachelors degree.");

        eligibilityTree.addNode(root.children.get(0).children.get(0), "Pass.");
        eligibilityTree.addNode(root.children.get(0).children.get(0),"Applicant has not taken the MCAT exam.");

        eligibilityTree.addNode(root.children.get(0).children.get(0).children.get(0), "Pass.");
        eligibilityTree.addNode(root.children.get(0).children.get(0).children.get(0),"Applicant has previously attended medical school.");

        TreeNode<String> currentNode = root;

        // check country of origin
        if (applicant.getCountryOfOrigin().equals("United States")) {
            currentNode = currentNode.children.get(0);
        }
        else if (applicant.isStudiedInUs()) {
            currentNode = currentNode.children.get(0);
        }
        else {
            currentNode = currentNode.children.get(1);
            return false;
        }
            // check degree
        if (applicant.getHighestDegree().equals("Doctorate") ||
                applicant.getHighestDegree().equals("Masters") ||
                applicant.getHighestDegree().equals("Bachelors")) {
            currentNode = currentNode.children.get(0);
        }
        else {
            currentNode = currentNode.children.get(1);
            return false;
        }

        // check MCAT taken
        if (applicant.isMcatTaken()) {
            currentNode = currentNode.children.get(0);
        }
        else {
            currentNode = currentNode.children.get(1);
            return false;
        }

        // check previous matriculation
        if (!applicant.isPrevMatriculation()) {
            currentNode = currentNode.children.get(0);
            return true;
        }
        else {
            currentNode = currentNode.children.get(1);
            return false;
        }
    }

    public static List<Integer> evaluate(Applicant applicant) {

        List<Integer> scoreList = new ArrayList<>();


        if (DecisionTree.evaluateEligibility(applicant)) {
            int mcatScore = DecisionTree.evaluateMcatScore(applicant);
            scoreList.add(mcatScore);
            int gpaScore = DecisionTree.evaluateGpa(applicant);
            scoreList.add(gpaScore);
            int courseworkScore = DecisionTree.evaluateCoursework(applicant);
            scoreList.add(courseworkScore);
            int lettersScore = DecisionTree.evaluateLettersOfRecommendation(applicant);
            scoreList.add(lettersScore);
            int workExperienceScore = DecisionTree.evaluateWorkExperience(applicant);
            scoreList.add(workExperienceScore);
            int essayScore = DecisionTree.evaluateEssay(applicant);
            scoreList.add(essayScore);
            int degreeScore = DecisionTree.evaluateDegree(applicant);
            scoreList.add(degreeScore);
            int schoolScore = DecisionTree.evaluateSchool(applicant);
            scoreList.add(schoolScore);
            int firstGenScore = DecisionTree.evaluateFirstGen(applicant);
            scoreList.add(firstGenScore);
            return scoreList;
        }
        else {
            return null;
        }
    }
}
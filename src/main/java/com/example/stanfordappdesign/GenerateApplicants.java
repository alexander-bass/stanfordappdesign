package com.example.stanfordappdesign;

import net.datafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateApplicants {

    private static final String[] gradingPossibilities = {"Perfect", "Satisfactory", "Unsatisfactory"};
    private static final String[] schoolPossibilities = {"Tier 1", "Tier 2", "Tier 3"};
    private static final String[] degreePossibilities = {"Doctorate", "Masters", "Bachelors"};
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");


    public static Applicant generateRandom(Random random, Faker faker) {

        Applicant applicant = new Applicant();

        applicant.setName(faker.name().fullName());
        applicant.setCountryOfOrigin(faker.country().name());
        applicant.setStudiedInUs((random.nextDouble() < 0.90));
        applicant.setHighestDegree(degreePossibilities[random.nextInt(degreePossibilities.length)]);
        applicant.setMcatTaken((random.nextDouble() < 0.90));
        applicant.setPrevMatriculation((random.nextDouble() < 0.10));
        applicant.setGpa(3.25 + (Math.random() * (4.0 - 3.25)));
        applicant.setMcatScore((int) (472 + (Math.random() * ((528 - 472) + 1))));
        applicant.setCoursework(gradingPossibilities[random.nextInt(gradingPossibilities.length)]);
        applicant.setLettersOfRecommendation(gradingPossibilities[random.nextInt(gradingPossibilities.length)]);
        applicant.setWorkExperience(gradingPossibilities[random.nextInt(gradingPossibilities.length)]);
        applicant.setEssay(gradingPossibilities[random.nextInt(gradingPossibilities.length)]);
        applicant.setSchoolAttended(schoolPossibilities[random.nextInt(schoolPossibilities.length)]);
        applicant.setFirstGeneration(random.nextBoolean());

        return applicant;
    }

    public static String generatePSU(Random random) { return gradingPossibilities[random.nextInt(gradingPossibilities.length)]; }
    public static String generateSchoolTier(Random random) { return schoolPossibilities[random.nextInt(schoolPossibilities.length)]; }




}

package com.yourplatform.leetcodeclone;

import com.yourplatform.leetcodeclone.model.Problem;
import com.yourplatform.leetcodeclone.model.StudyGroup;
import com.yourplatform.leetcodeclone.model.User;
import com.yourplatform.leetcodeclone.repository.ProblemRepository;
import com.yourplatform.leetcodeclone.repository.StudyGroupRepository;
import com.yourplatform.leetcodeclone.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeetcodeCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeetcodeCloneApplication.class, args);
	}

	// Bean to pre-load data into the H2 database on startup for demonstration
	@Bean
	CommandLineRunner initDatabase(StudyGroupRepository groupRepo, ProblemRepository problemRepo, UserRepository userRepo) {
		return args -> {
			// Create Users
			User user1 = new User();
			user1.setUsername("alice");
			user1.setEmail("alice@example.com");
			user1.setPassword("password123"); // In a real app, hash this!
			userRepo.save(user1);

			User user2 = new User();
			user2.setUsername("bob");
			user2.setEmail("bob@example.com");
			user2.setPassword("password123");
			userRepo.save(user2);

			// Create Problems
			Problem problem1 = new Problem();
			problem1.setTitle("Two Sum");
			problem1.setDescription("Given an array of integers, return indices of the two numbers such that they add up to a specific target.");
			problem1.setTopic("Arrays");
			problem1.setDifficulty("Easy");
			problemRepo.save(problem1);

			Problem problem2 = new Problem();
			problem2.setTitle("Longest Substring Without Repeating Characters");
			problem2.setDescription("Given a string, find the length of the longest substring without repeating characters.");
			problem2.setTopic("Sliding Window");
			problem2.setDifficulty("Medium");
			problemRepo.save(problem2);

			// Create Study Groups
			StudyGroup group1 = new StudyGroup();
			group1.setName("FAANG Interview Prep");
			group1.setDescription("A group dedicated to tackling hard-level problems for FAANG interviews.");
			groupRepo.save(group1);

			StudyGroup group2 = new StudyGroup();
			group2.setName("Dynamic Programming Masters");
			group2.setDescription("Let's master DP together, from basics to advanced.");
			group2.getMembers().add(user1); // Add alice to this group
			groupRepo.save(group2);
		};
	}
}
package com.yourplatform.leetcodeclone;

import com.yourplatform.leetcodeclone.model.Problem;
import com.yourplatform.leetcodeclone.model.StudyGroup;
import com.yourplatform.leetcodeclone.model.User;
import com.yourplatform.leetcodeclone.model.Post; // Import Post
import com.yourplatform.leetcodeclone.repository.ProblemRepository;
import com.yourplatform.leetcodeclone.repository.StudyGroupRepository;
import com.yourplatform.leetcodeclone.repository.UserRepository;
import com.yourplatform.leetcodeclone.repository.PostRepository; // Import PostRepository
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Arrays; // Import Arrays

@SpringBootApplication
public class BitCode_Application {

	public static void main(String[] args) {
		SpringApplication.run(BitCode_Application.class, args);
	}

	// Bean to pre-load data into the H2 database on startup for demonstration
	@Bean
	CommandLineRunner initDatabase(StudyGroupRepository groupRepo, ProblemRepository problemRepo, UserRepository userRepo, PostRepository postRepo) {
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
			problem1.setDescription(
					"<p>Given an array of integers <code>nums</code> and an integer <code>target</code>, return <em>indices of the two numbers such that they add up to <code>target</code></em>.</p>" +
							"<p>You may assume that each input would have <strong>exactly one solution</strong>, and you may not use the <em>same</em> element twice.</p>" +
							"<p>You can return the answer in any order.</p>" +
							"<p><strong>Example 1:</strong></p>" +
							"<pre><strong>Input:</strong> nums = [2,7,11,15], target = 9\n" +
							"<strong>Output:</strong> [0,1]\n" +
							"<strong>Explanation:</strong> Because nums[0] + nums[1] == 9, we return [0, 1].</pre>"
			);
			problem1.setTopic("Arrays");
			problem1.setDifficulty("Easy");

			// --- UPDATED STARTER CODE (Full Program for Java) ---
			problem1.setStarterCodeJava(
					"import java.util.*;\n" +
							"import java.util.stream.Stream;\n\n" +
							"class Solution {\n\n" +
							"    // --- WRITE YOUR CODE HERE --- \n" +
							"    public int[] twoSum(int[] nums, int target) {\n" +
							"        // e.g., return new int[]{0, 1};\n" +
							"        throw new UnsupportedOperationException(\"Not implemented yet\");\n" +
							"    }\n" +
							"    // --- END OF YOUR CODE --- \n\n" +

							"    // --- BOILERPLATE TO READ STDIN --- \n" +
							"    public static void main(String[] args) {\n" +
							"        Scanner sc = new Scanner(System.in);\n" +
							"        String line1 = sc.nextLine();\n" +
							"        int target = Integer.parseInt(sc.nextLine());\n" +
							"        line1 = line1.replace(\"[\", \"\").replace(\"]\", \"\");\n" +
							"        String[] numsStr = line1.split(\",\");\n" +
							"        int[] nums = Stream.of(numsStr).map(String::trim).mapToInt(Integer::parseInt).toArray();\n" +
							"        Solution s = new Solution();\n" +
							"        int[] result = s.twoSum(nums, target);\n" +
							"        System.out.println(Arrays.toString(result));\n" +
							"    }\n" +
							"}");

			// --- UPDATED STARTER CODE (Full Program for Python) ---
			problem1.setStarterCodePython(
					"import sys\n\n" +
							"class Solution:\n" +
							"    # --- WRITE YOUR CODE HERE --- \n" +
							"    def twoSum(self, nums, target):\n" +
							"        # e.g., return [0, 1]\n" +
							"        raise NotImplementedError(\"Not implemented yet\")\n" +
							"    # --- END OF YOUR CODE --- \n\n" +

							"    # --- BOILERPLATE TO READ STDIN --- \n" +
							"if __name__ == \"__main__\":\n" +
							"    line1 = sys.stdin.readline().strip()\n" +
							"    target = int(sys.stdin.readline().strip())\n" +
							"    nums = [int(n) for n in line1.replace(\"[\", \"\").replace(\"]\", \"\").split(\",\")]\n" +
							"    s = Solution()\n" +
							"    result = s.twoSum(nums, target)\n" +
							"    print(result)\n");

			problem1.setStarterCodeCpp("/* C++ boilerplate not included for brevity */");

			// --- ADD TEST CASE DATA ---
			problem1.setTestCaseInput("[2,7,11,15]\n9");
			problem1.setTestCaseOutput("[0, 1]"); // Note the space, as Arrays.toString() adds it

			problemRepo.save(problem1);

			Problem problem2 = new Problem();
			problem2.setTitle("Longest Substring Without Repeating Characters");
			problem2.setDescription("<p>Given a string <code>s</code>, find the length of the <strong>longest substring</strong> without repeating characters.</p>");
			problem2.setTopic("Sliding Window");
			problem2.setDifficulty("Medium");
			problem2.setStarterCodeJava("class Solution {\n    public int lengthOfLongestSubstring(String s) {\n        \n    }\n}");
			problem2.setStarterCodePython("class Solution:\n    def lengthOfLongestSubstring(self, s: str) -> int:\n        ");
			problem2.setStarterCodeCpp("class Solution {\npublic:\n    int lengthOfLongestSubstring(string s) {\n        \n    }\n};");
			// Test cases not added for this problem
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

			// Create Posts
			Post post1 = new Post();
			post1.setTitle("Help with Two Sum logic?");
			post1.setTags("arrays,interviews");
			post1.setVotes(12);
			post1.setViews(150);
			postRepo.save(post1);

			Post post2 = new Post();
			post2.setTitle("Best approach for DP problems?");
			post2.setTags("dp,algorithms");
			post2.setVotes(45);
			post2.setViews(800);
			postRepo.save(post2);

			Post post3 = new Post();
			post3.setTitle("Spring Boot circular JSON issue");
			post3.setTags("spring,java,json");
			post3.setVotes(8);
			post3.setViews(220);
			postRepo.save(post3);
		};
	}
}
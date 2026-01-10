package com.yourplatform.leetcodeclone;

import com.yourplatform.leetcodeclone.model.Problem;
import com.yourplatform.leetcodeclone.model.StudyGroup;
import com.yourplatform.leetcodeclone.model.User;
import com.yourplatform.leetcodeclone.model.Post;
import com.yourplatform.leetcodeclone.repository.ProblemRepository;
import com.yourplatform.leetcodeclone.repository.StudyGroupRepository;
import com.yourplatform.leetcodeclone.repository.UserRepository;
import com.yourplatform.leetcodeclone.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BitCode_Application {

	public static void main(String[] args) {
		SpringApplication.run(BitCode_Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(StudyGroupRepository groupRepo, ProblemRepository problemRepo, UserRepository userRepo, PostRepository postRepo) {
		return args -> {
			// Skip if already seeded
			if (problemRepo.count() > 0) {
				System.out.println("✅ Database already seeded!");
				return;
			}

			// ===== CREATE USERS =====
			User user1 = new User();
			user1.setUsername("alice");
			user1.setEmail("alice@example.com");
			user1.setPassword("password123");
			userRepo.save(user1);

			User user2 = new User();
			user2.setUsername("bob");
			user2.setEmail("bob@example.com");
			user2.setPassword("password123");
			userRepo.save(user2);

			// ===== PROBLEM 1: TWO SUM =====
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
			problem1.setTags("array,hash");
			problem1.setCompany("Google");
			problem1.setLikes(156);
			problem1.setDiscussions(45);
			problem1.setSolved(8932);

			problem1.setStarterCodeJava(
					"import java.util.*;\n" +
							"import java.util.stream.Stream;\n\n" +
							"class Solution {\n\n" +
							"    public int[] twoSum(int[] nums, int target) {\n" +
							"        // Your code here\n" +
							"        throw new UnsupportedOperationException(\"Not implemented yet\");\n" +
							"    }\n\n" +
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

			problem1.setStarterCodePython(
					"import sys\n\n" +
							"class Solution:\n" +
							"    def twoSum(self, nums, target):\n" +
							"        raise NotImplementedError(\"Not implemented yet\")\n\n" +
							"if __name__ == \"__main__\":\n" +
							"    line1 = sys.stdin.readline().strip()\n" +
							"    target = int(sys.stdin.readline().strip())\n" +
							"    nums = [int(n) for n in line1.replace(\"[\", \"\").replace(\"]\", \"\").split(\",\")]\n" +
							"    s = Solution()\n" +
							"    result = s.twoSum(nums, target)\n" +
							"    print(result)\n");

			problem1.setStarterCodeCpp(
					"#include <vector>\n" +
							"#include <unordered_map>\n" +
							"using namespace std;\n\n" +
							"class Solution {\n" +
							"public:\n" +
							"    vector<int> twoSum(vector<int>& nums, int target) {\n" +
							"        // Your code here\n" +
							"        return {};\n" +
							"    }\n" +
							"};");

			problem1.setTestCaseInput("[2,7,11,15]\n9");
			problem1.setTestCaseOutput("[0,1]");
			problemRepo.save(problem1);

			// ===== PROBLEM 2: 3SUM =====
			Problem problem2 = new Problem();
			problem2.setTitle("3Sum");
			problem2.setDescription(
					"<p>Given an integer array <code>nums</code>, return all the triplets <code>[nums[i], nums[j], nums[k]]</code> such that <code>i != j</code>, <code>i != k</code>, and <code>j != k</code>, and <code>nums[i] + nums[j] + nums[k] == 0</code>.</p>" +
							"<p>Notice that the solution set must not contain duplicate triplets.</p>" +
							"<p><strong>Example:</strong></p>" +
							"<pre><strong>Input:</strong> nums = [-1,0,1,2,-1,-4]\n" +
							"<strong>Output:</strong> [[-1,-1,2],[-1,0,1]]</pre>"
			);
			problem2.setTopic("Arrays");
			problem2.setDifficulty("Medium");
			problem2.setTags("array,two-pointer");
			problem2.setCompany("Amazon");
			problem2.setLikes(234);
			problem2.setDiscussions(67);
			problem2.setSolved(5421);

			problem2.setStarterCodeJava(
					"import java.util.*;\n\n" +
							"class Solution {\n" +
							"    public List<List<Integer>> threeSum(int[] nums) {\n" +
							"        // Your code here\n" +
							"        return new ArrayList<>();\n" +
							"    }\n" +
							"}");

			problem2.setStarterCodePython(
					"class Solution:\n" +
							"    def threeSum(self, nums):\n" +
							"        # Your code here\n" +
							"        return []\n");

			problem2.setStarterCodeCpp(
					"#include <vector>\n" +
							"using namespace std;\n\n" +
							"class Solution {\n" +
							"public:\n" +
							"    vector<vector<int>> threeSum(vector<int>& nums) {\n" +
							"        // Your code here\n" +
							"        return {};\n" +
							"    }\n" +
							"};");

			problem2.setTestCaseInput("[-1,0,1,2,-1,-4]");
			problem2.setTestCaseOutput("[[-1,-1,2],[-1,0,1]]");
			problemRepo.save(problem2);

			// ===== PROBLEM 3: LONGEST SUBSTRING WITHOUT REPEATING =====
			Problem problem3 = new Problem();
			problem3.setTitle("Longest Substring Without Repeating Characters");
			problem3.setDescription(
					"<p>Given a string <code>s</code>, find the length of the <strong>longest substring</strong> without repeating characters.</p>" +
							"<p><strong>Example 1:</strong></p>" +
							"<pre><strong>Input:</strong> s = \"abcabcbb\"\n" +
							"<strong>Output:</strong> 3\n" +
							"<strong>Explanation:</strong> The answer is \"abc\", with the length of 3.</pre>" +
							"<p><strong>Example 2:</strong></p>" +
							"<pre><strong>Input:</strong> s = \"bbbbb\"\n" +
							"<strong>Output:</strong> 1</pre>"
			);
			problem3.setTopic("Sliding Window");
			problem3.setDifficulty("Medium");
			problem3.setTags("string,sliding-window");
			problem3.setCompany("Meta");
			problem3.setLikes(234);
			problem3.setDiscussions(56);
			problem3.setSolved(4567);

			problem3.setStarterCodeJava(
					"class Solution {\n" +
							"    public int lengthOfLongestSubstring(String s) {\n" +
							"        // Your code here\n" +
							"        return 0;\n" +
							"    }\n" +
							"}");

			problem3.setStarterCodePython(
					"class Solution:\n" +
							"    def lengthOfLongestSubstring(self, s: str) -> int:\n" +
							"        # Your code here\n" +
							"        return 0\n");

			problem3.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    int lengthOfLongestSubstring(string s) {\n" +
							"        // Your code here\n" +
							"        return 0;\n" +
							"    }\n" +
							"};");

			problem3.setTestCaseInput("\"abcabcbb\"");
			problem3.setTestCaseOutput("3");
			problemRepo.save(problem3);

			// ===== PROBLEM 4: VALID PARENTHESES =====
			Problem problem4 = new Problem();
			problem4.setTitle("Valid Parentheses");
			problem4.setDescription(
					"<p>Given a string <code>s</code> containing just the characters <code>'('</code>, <code>')'</code>, <code>'{'</code>, <code>'}'</code>, <code>'['</code> and <code>']'</code>, determine if the input string is valid.</p>" +
							"<p>An input string is valid if:</p>" +
							"<ol><li>Open brackets must be closed by the same type of brackets.</li>" +
							"<li>Open brackets must be closed in the correct order.</li></ol>" +
							"<p><strong>Example 1:</strong></p>" +
							"<pre><strong>Input:</strong> s = \"()\"\n<strong>Output:</strong> true</pre>"
			);
			problem4.setTopic("Stack");
			problem4.setDifficulty("Easy");
			problem4.setTags("string,stack");
			problem4.setCompany("Google");
			problem4.setLikes(123);
			problem4.setDiscussions(34);
			problem4.setSolved(6789);

			problem4.setStarterCodeJava(
					"class Solution {\n" +
							"    public boolean isValid(String s) {\n" +
							"        // Your code here\n" +
							"        return false;\n" +
							"    }\n" +
							"}");

			problem4.setStarterCodePython(
					"class Solution:\n" +
							"    def isValid(self, s: str) -> bool:\n" +
							"        # Your code here\n" +
							"        return False\n");

			problem4.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    bool isValid(string s) {\n" +
							"        // Your code here\n" +
							"        return false;\n" +
							"    }\n" +
							"};");

			problem4.setTestCaseInput("\"()[]{}\"");
			problem4.setTestCaseOutput("true");
			problemRepo.save(problem4);

			// ===== PROBLEM 5: COIN CHANGE =====
			Problem problem5 = new Problem();
			problem5.setTitle("Coin Change");
			problem5.setDescription(
					"<p>You are given an integer array <code>coins</code> representing coins of different denominations and an integer <code>amount</code> representing a total amount of money.</p>" +
							"<p>Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return <code>-1</code>.</p>" +
							"<p><strong>Example 1:</strong></p>" +
							"<pre><strong>Input:</strong> coins = [1,2,5], amount = 5\n" +
							"<strong>Output:</strong> 1\n" +
							"<strong>Explanation:</strong> 5 = 5 (1 coin)</pre>"
			);
			problem5.setTopic("DP");
			problem5.setDifficulty("Medium");
			problem5.setTags("dp,bfs");
			problem5.setCompany("Microsoft");
			problem5.setLikes(212);
			problem5.setDiscussions(78);
			problem5.setSolved(4123);

			problem5.setStarterCodeJava(
					"class Solution {\n" +
							"    public int coinChange(int[] coins, int amount) {\n" +
							"        // Your code here\n" +
							"        return -1;\n" +
							"    }\n" +
							"}");

			problem5.setStarterCodePython(
					"class Solution:\n" +
							"    def coinChange(self, coins, amount: int) -> int:\n" +
							"        # Your code here\n" +
							"        return -1\n");

			problem5.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    int coinChange(vector<int>& coins, int amount) {\n" +
							"        // Your code here\n" +
							"        return -1;\n" +
							"    }\n" +
							"};");

			problem5.setTestCaseInput("[1,2,5]\n5");
			problem5.setTestCaseOutput("1");
			problemRepo.save(problem5);

			// ===== PROBLEM 6: HOUSE ROBBER =====
			Problem problem6 = new Problem();
			problem6.setTitle("House Robber");
			problem6.setDescription(
					"<p>You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the catch is that adjacent houses have connected security systems.</p>" +
							"<p>Given an integer array <code>nums</code> representing the amount of money in each house, return the maximum amount of money you can rob without alerting the police.</p>" +
							"<p><strong>Example:</strong></p>" +
							"<pre><strong>Input:</strong> nums = [1,2,3,1]\n" +
							"<strong>Output:</strong> 4\n" +
							"<strong>Explanation:</strong> Rob house 1 (money = 1) and house 3 (money = 3). Total = 1 + 3 = 4.</pre>"
			);
			problem6.setTopic("DP");
			problem6.setDifficulty("Easy");
			problem6.setTags("dp");
			problem6.setCompany("Apple");
			problem6.setLikes(145);
			problem6.setDiscussions(45);
			problem6.setSolved(5432);

			problem6.setStarterCodeJava(
					"class Solution {\n" +
							"    public int rob(int[] nums) {\n" +
							"        // Your code here\n" +
							"        return 0;\n" +
							"    }\n" +
							"}");

			problem6.setStarterCodePython(
					"class Solution:\n" +
							"    def rob(self, nums) -> int:\n" +
							"        # Your code here\n" +
							"        return 0\n");

			problem6.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    int rob(vector<int>& nums) {\n" +
							"        // Your code here\n" +
							"        return 0;\n" +
							"    }\n" +
							"};");

			problem6.setTestCaseInput("[1,2,3,1]");
			problem6.setTestCaseOutput("4");
			problemRepo.save(problem6);

			// ===== PROBLEM 7: NUMBER OF ISLANDS =====
			Problem problem7 = new Problem();
			problem7.setTitle("Number of Islands");
			problem7.setDescription(
					"<p>Given an <code>m x n</code> 2D binary grid <code>grid</code> which represents a map of <code>'1'</code>s (land) and <code>'0'</code>s (water), return the number of islands.</p>" +
							"<p>An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.</p>" +
							"<p><strong>Example:</strong></p>" +
							"<pre><strong>Input:</strong> grid = [[\"1\",\"1\",\"1\",\"1\",\"0\"],[\"1\",\"1\",\"0\",\"1\",\"0\"],[\"1\",\"1\",\"0\",\"0\",\"0\"],[\"0\",\"0\",\"0\",\"0\",\"0\"]]\n" +
							"<strong>Output:</strong> 1</pre>"
			);
			problem7.setTopic("Graph");
			problem7.setDifficulty("Medium");
			problem7.setTags("graph,dfs,bfs");
			problem7.setCompany("Amazon");
			problem7.setLikes(198);
			problem7.setDiscussions(67);
			problem7.setSolved(3456);

			problem7.setStarterCodeJava(
					"class Solution {\n" +
							"    public int numIslands(char[][] grid) {\n" +
							"        // Your code here\n" +
							"        return 0;\n" +
							"    }\n" +
							"}");

			problem7.setStarterCodePython(
					"class Solution:\n" +
							"    def numIslands(self, grid) -> int:\n" +
							"        # Your code here\n" +
							"        return 0\n");

			problem7.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    int numIslands(vector<vector<char>>& grid) {\n" +
							"        // Your code here\n" +
							"        return 0;\n" +
							"    }\n" +
							"};");

			problem7.setTestCaseInput("[[\"1\",\"1\",\"0\"],[\"1\",\"0\",\"0\"],[\"0\",\"0\",\"1\"]]");
			problem7.setTestCaseOutput("3");
			problemRepo.save(problem7);

			// ===== PROBLEM 8: NETWORK DELAY TIME =====
			Problem problem8 = new Problem();
			problem8.setTitle("Network Delay Time");
			problem8.setDescription(
					"<p>You are given a network of <code>n</code> nodes labeled from <code>1</code> to <code>n</code>. You are also given <code>times</code>, a list of travel times as directed edges <code>times[i] = (ui, vi, wi)</code>.</p>" +
							"<p>Return the time it takes for all nodes to receive the signal. If it is impossible, return <code>-1</code>.</p>" +
							"<p><strong>Example:</strong></p>" +
							"<pre><strong>Input:</strong> times = [[1,2,1],[2,3,2],[1,3,4]], n = 3, k = 1\n" +
							"<strong>Output:</strong> 4</pre>"
			);
			problem8.setTopic("Graph");
			problem8.setDifficulty("Hard");
			problem8.setTags("graph,dijkstra,heap");
			problem8.setCompany("Google");
			problem8.setLikes(267);
			problem8.setDiscussions(89);
			problem8.setSolved(2345);

			problem8.setStarterCodeJava(
					"class Solution {\n" +
							"    public int networkDelayTime(int[][] times, int n, int k) {\n" +
							"        // Your code here\n" +
							"        return -1;\n" +
							"    }\n" +
							"}");

			problem8.setStarterCodePython(
					"class Solution:\n" +
							"    def networkDelayTime(self, times, n: int, k: int) -> int:\n" +
							"        # Your code here\n" +
							"        return -1\n");

			problem8.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    int networkDelayTime(vector<vector<int>>& times, int n, int k) {\n" +
							"        // Your code here\n" +
							"        return -1;\n" +
							"    }\n" +
							"};");

			problem8.setTestCaseInput("[[1,2,1],[2,3,2],[1,3,4]]\n3\n1");
			problem8.setTestCaseOutput("4");
			problemRepo.save(problem8);

			// ===== PROBLEM 9: BINARY TREE INORDER TRAVERSAL =====
			Problem problem9 = new Problem();
			problem9.setTitle("Binary Tree Inorder Traversal");
			problem9.setDescription(
					"<p>Given the root of a binary tree, return the inorder traversal of its nodes' values.</p>" +
							"<p>Inorder traversal visits nodes in Left -> Root -> Right order.</p>" +
							"<p><strong>Example:</strong></p>" +
							"<pre><strong>Input:</strong> root = [1,null,2,3]\n" +
							"<strong>Output:</strong> [1,3,2]</pre>"
			);
			problem9.setTopic("Tree");
			problem9.setDifficulty("Easy");
			problem9.setTags("tree,dfs,recursion");
			problem9.setCompany("Meta");
			problem9.setLikes(167);
			problem9.setDiscussions(34);
			problem9.setSolved(5678);

			problem9.setStarterCodeJava(
					"class Solution {\n" +
							"    public List<Integer> inorderTraversal(TreeNode root) {\n" +
							"        // Your code here\n" +
							"        return new ArrayList<>();\n" +
							"    }\n" +
							"}");

			problem9.setStarterCodePython(
					"class Solution:\n" +
							"    def inorderTraversal(self, root):\n" +
							"        # Your code here\n" +
							"        return []\n");

			problem9.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    vector<int> inorderTraversal(TreeNode* root) {\n" +
							"        // Your code here\n" +
							"        return {};\n" +
							"    }\n" +
							"};");

			problem9.setTestCaseInput("[1,null,2,3]");
			problem9.setTestCaseOutput("[1,3,2]");
			problemRepo.save(problem9);

			// ===== PROBLEM 10: LOWEST COMMON ANCESTOR OF BST =====
			Problem problem10 = new Problem();
			problem10.setTitle("Lowest Common Ancestor of BST");
			problem10.setDescription(
					"<p>Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.</p>" +
							"<p>The lowest common ancestor is defined between two nodes p and q as the lowest node in a tree that has both p and q as descendants (where we allow a node to be a descendant of itself).</p>" +
							"<p><strong>Example:</strong></p>" +
							"<pre><strong>Input:</strong> root = [6,2,8,0,4,7,9], p = 2, q = 8\n" +
							"<strong>Output:</strong> 6</pre>"
			);
			problem10.setTopic("Tree");
			problem10.setDifficulty("Medium");
			problem10.setTags("tree,bst,recursion");
			problem10.setCompany("Apple");
			problem10.setLikes(201);
			problem10.setDiscussions(56);
			problem10.setSolved(4234);

			problem10.setStarterCodeJava(
					"class Solution {\n" +
							"    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {\n" +
							"        // Your code here\n" +
							"        return null;\n" +
							"    }\n" +
							"}");

			problem10.setStarterCodePython(
					"class Solution:\n" +
							"    def lowestCommonAncestor(self, root, p, q):\n" +
							"        # Your code here\n" +
							"        return None\n");

			problem10.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {\n" +
							"        // Your code here\n" +
							"        return nullptr;\n" +
							"    }\n" +
							"};");

			problem10.setTestCaseInput("[6,2,8,0,4,7,9]\np=2, q=8");
			problem10.setTestCaseOutput("6");
			problemRepo.save(problem10);

			// ===== PROBLEM 11: TOP K FREQUENT ELEMENTS =====
			Problem problem11 = new Problem();
			problem11.setTitle("Top K Frequent Elements");
			problem11.setDescription(
					"<p>Given an integer array <code>nums</code> and an integer <code>k</code>, return the <code>k</code> most frequent elements. You may return the answer in any order.</p>" +
							"<p><strong>Example 1:</strong></p>" +
							"<pre><strong>Input:</strong> nums = [1,1,1,2,2,3], k = 2\n" +
							"<strong>Output:</strong> [1,2]</pre>" +
							"<p><strong>Example 2:</strong></p>" +
							"<pre><strong>Input:</strong> nums = [4,1,1,1,2,2,3], k = 2\n" +
							"<strong>Output:</strong> [1,2]</pre>"
			);
			problem11.setTopic("Heap");
			problem11.setDifficulty("Medium");
			problem11.setTags("heap,hash,quickselect");
			problem11.setCompany("Amazon");
			problem11.setLikes(234);
			problem11.setDiscussions(78);
			problem11.setSolved(3890);

			problem11.setStarterCodeJava(
					"class Solution {\n" +
							"    public int[] topKFrequent(int[] nums, int k) {\n" +
							"        // Your code here\n" +
							"        return new int[]{};\n" +
							"    }\n" +
							"}");

			problem11.setStarterCodePython(
					"class Solution:\n" +
							"    def topKFrequent(self, nums, k: int):\n" +
							"        # Your code here\n" +
							"        return []\n");

			problem11.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    vector<int> topKFrequent(vector<int>& nums, int k) {\n" +
							"        // Your code here\n" +
							"        return {};\n" +
							"    }\n" +
							"};");

			problem11.setTestCaseInput("[1,1,1,2,2,3]\n2");
			problem11.setTestCaseOutput("[1,2]");
			problemRepo.save(problem11);

			// ===== PROBLEM 12: MEDIAN OF TWO SORTED ARRAYS =====
			Problem problem12 = new Problem();
			problem12.setTitle("Median of Two Sorted Arrays");
			problem12.setDescription(
					"<p>Given two sorted arrays <code>nums1</code> and <code>nums2</code> of size <code>m</code> and <code>n</code> respectively, return the median of the two sorted arrays.</p>" +
							"<p>The overall run time complexity should be <code>O(log (m+n))</code>.</p>" +
							"<p><strong>Example 1:</strong></p>" +
							"<pre><strong>Input:</strong> nums1 = [1,3], nums2 = [2]\n" +
							"<strong>Output:</strong> 2.0</pre>"
			);
			problem12.setTopic("Arrays");
			problem12.setDifficulty("Hard");
			problem12.setTags("array,binary-search");
			problem12.setCompany("Google");
			problem12.setLikes(289);
			problem12.setDiscussions(123);
			problem12.setSolved(2145);

			problem12.setStarterCodeJava(
					"class Solution {\n" +
							"    public double findMedianSortedArrays(int[] nums1, int[] nums2) {\n" +
							"        // Your code here\n" +
							"        return 0.0;\n" +
							"    }\n" +
							"}");

			problem12.setStarterCodePython(
					"class Solution:\n" +
							"    def findMedianSortedArrays(self, nums1, nums2) -> float:\n" +
							"        # Your code here\n" +
							"        return 0.0\n");

			problem12.setStarterCodeCpp(
					"class Solution {\n" +
							"public:\n" +
							"    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {\n" +
							"        // Your code here\n" +
							"        return 0.0;\n" +
							"    }\n" +
							"};");

			problem12.setTestCaseInput("[1,3]\n[2]");
			problem12.setTestCaseOutput("2.0");
			problemRepo.save(problem12);

			// ===== CREATE STUDY GROUPS =====
			StudyGroup group1 = new StudyGroup();
			group1.setName("FAANG Interview Prep");
			group1.setDescription("A group dedicated to tackling hard-level problems for FAANG interviews.");
			group1.setMembers(new ArrayList<>());
			groupRepo.save(group1);

			StudyGroup group2 = new StudyGroup();
			group2.setName("Dynamic Programming Masters");
			group2.setDescription("Let's master DP together, from basics to advanced.");
			group2.setMembers(new ArrayList<>(List.of(user1)));
			groupRepo.save(group2);

			StudyGroup group3 = new StudyGroup();
			group3.setName("Graph & Tree Specialists");
			group3.setDescription("Focus on complex graph and tree problems.");
			group3.setMembers(new ArrayList<>(List.of(user1, user2)));
			groupRepo.save(group3);

			// ===== CREATE POSTS =====
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

			Post post4 = new Post();
			post4.setTitle("Graph algorithms explained");
			post4.setTags("graph,algorithms,tutorial");
			post4.setVotes(67);
			post4.setViews(1200);
			postRepo.save(post4);

			Post post5 = new Post();
			post5.setTitle("Tree traversal techniques");
			post5.setTags("tree,dfs,bfs");
			post5.setVotes(34);
			post5.setViews(560);
			postRepo.save(post5);

			System.out.println("✅ Database initialized with 12 problems + 3 groups + 5 posts!");
		};
	}
}
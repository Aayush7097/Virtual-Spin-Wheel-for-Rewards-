import java.util.Random;
import java.util.Scanner;
// import java.util.Arrays;

public class VirtualSpinWheel {

    // Configuration
    private static final String[] REWARDS = {
        "Free Coffee",
        "Extra Credit",
        "Amazon Gift Card",
        "Try Again",
        "Movie Ticket",
        "5% Discount Coupon"
    };

    // Weights for each reward. Must match REWARDS.length.
    // Higher weight => higher chance. Use equal weights for uniform selection.
    private static final double[] WEIGHTS = {
        2.0, // Free Coffee
        1.5, // Extra Credit
        1.0, // Amazon Gift Card
        3.0, // Try Again (commonly occurs)
        1.0, // Movie Ticket
        1.5  // 5% Discount Coupon
    };

    // Maximum allowed spins per session (optional enhancement)
    private static final int MAX_SPINS = 10;

    public static void main(String[] args) {
        if (REWARDS.length != WEIGHTS.length) {
            System.err.println("ERROR: REWARDS and WEIGHTS arrays must have the same length.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int[] winsCount = new int[REWARDS.length];
        int totalSpins = 0;

        printWelcome();

        showRewards();

        boolean keepPlaying = true;

        while (keepPlaying) {
            if (totalSpins >= MAX_SPINS) {
                System.out.println("\nYou've reached the maximum number of spins for this session (" + MAX_SPINS + ").");
                break;
            }

            System.out.print("\nPress Enter to spin the wheel...");
            scanner.nextLine(); // wait for Enter

            // Spin animation (simulate wheel spin)
            simulateSpinAnimation(random);

            // Pick reward using weights
            int index = pickWeightedIndex(random, WEIGHTS);
            String reward = REWARDS[index];
            winsCount[index]++;
            totalSpins++;

            System.out.println("\n--- RESULT ---");
            System.out.println("You won: " + reward);

            // Ask user to spin again or exit
            String response = "";
            while (true) {
                System.out.print("\nDo you want to spin again? (yes/no): ");
                response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes") || response.equals("y")) {
                    break;
                } else if (response.equals("no") || response.equals("n")) {
                    keepPlaying = false;
                    break;
                } else {
                    System.out.println("Please enter 'yes' or 'no'.");
                }
            }
        }

        // Show summary and thank you message
        System.out.println("\n==============================");
        System.out.println("Thank you for playing!");
        System.out.println("Total spins: " + totalSpins);
        System.out.println("Rewards summary:");
        for (int i = 0; i < REWARDS.length; i++) {
            System.out.printf(" - %-20s : %d\n", REWARDS[i], winsCount[i]);
        }
        System.out.println("==============================");

        scanner.close();
    }

    private static void printWelcome() {
        System.out.println("========================================");
        System.out.println("   VIRTUAL SPIN WHEEL — Spin to Win!    ");
        System.out.println("========================================");
        System.out.println("Welcome! This is a simple Java console application that simulates a virtual spin wheel.");
        System.out.println("Try your luck — press Enter to spin. You can spin up to " + MAX_SPINS + " times.");
    }

    private static void showRewards() {
        System.out.println("\nAvailable rewards:");
        for (int i = 0; i < REWARDS.length; i++) {
            System.out.printf(" %d) %-20s (weight: %.1f)\n", i + 1, REWARDS[i], WEIGHTS[i]);
        }
    }
    // Weighted random selection - returns index of selected reward
    private static int pickWeightedIndex(Random random, double[] weights) {
        double total = 0.0;
        for (double w : weights) total += w;
        double r = random.nextDouble() * total; // 0 <= r < total
        double cumulative = 0.0;
        for (int i = 0; i < weights.length; i++) {
            cumulative += weights[i];
            if (r < cumulative) {
                return i;
            }
        }
        // Fallback (shouldn't happen)
        return weights.length - 1;
    }

    // Simple spin animation: prints a few random reward names quickly
    private static void simulateSpinAnimation(Random random) {
        System.out.print("\nSpinning");
        try {
            // number of animation frames (randomize a bit to feel natural)
            int frames = 10 + random.nextInt(8); // 10..17 frames
            for (int i = 0; i < frames; i++) {
                // pick a temporary random reward to display during animation
                String temp = REWARDS[random.nextInt(REWARDS.length)];
                System.out.print(".");
                Thread.sleep(120); // pause for animation feel (120ms)
            }
            System.out.println();
        } catch (InterruptedException ex) {
            // If sleep is interrupted, just continue
            Thread.currentThread().interrupt();
        }
    }
}
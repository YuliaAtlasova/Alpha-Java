import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReleaseScheduler {
    private static final int SPRINT_END = 10;
    private static final String INPUT_FILE_PATH = "src/resources/input/incomeTasks01.txt";
    private static final String EXPECTED_OUTPUT_FILE_PATH = "src/resources/output/outputTasks01.txt";

    public static void main(String[] args) throws IOException {
        List<int[]> releases = readInputFile(INPUT_FILE_PATH);
        List<int[]> result = calculateValidReleases(releases);
        validateOutput(result);
    }

    private static List<int[]> readInputFile(String filePath) throws IOException {
        return Files.lines(Path.of(filePath), StandardCharsets.UTF_8)
                .map(line -> {
                    String[] parts = line.split(" ");
                    return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
                })
                .toList();
    }

    private static List<int[]> calculateValidReleases(List<int[]> releases) {
        List<int[]> sortedReleases = releases.stream()
                .map(release -> new int[]{release[0], release[0] + release[1] - 1}) // Calculate end date
                .sorted(Comparator.comparingInt((int[] r) -> r[1]) // Sort by end date first
                        .thenComparingInt(r -> r[0])) // Then sort by start date
                .toList();

        List<int[]> result = new ArrayList<>();
        var currentDay = 1;

        for (var release : sortedReleases) {
            int startDay = release[0];
            int endDay = release[1];

            if (startDay >= currentDay && endDay <= SPRINT_END) {
                result.add(new int[]{startDay, endDay});
                currentDay = endDay + 1;
            }
        }
        return result;
    }

    private static void validateOutput(List<int[]> actualResults) throws IOException {
        List<String> expectedLines = Files.readAllLines(Path.of(EXPECTED_OUTPUT_FILE_PATH));

        List<List<Integer>> expectedResults = expectedLines.stream()
                .skip(1) // Skip the first line (count)
                .map(line -> {
                    String[] parts = line.split(" ");
                    return List.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                })
                .toList();
        
        Assertions.assertThat(actualResults.size())
                .as("The number of actual results should match the expected count.")
                .isEqualTo(Integer.parseInt(expectedLines.get(0)));

        List<List<Integer>> actualResultsList = actualResults.stream()
                .map(release -> List.of(release[0], release[1]))
                .toList();

        Assertions.assertThat(actualResultsList)
                .as("Comparing the actual output with expected output from files")
                .isEqualTo(expectedResults);

        System.out.println("Validation passed!");
    }
}

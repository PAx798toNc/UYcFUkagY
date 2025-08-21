// 代码生成时间: 2025-08-21 10:30:50
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SortServiceImpl implements SortService {

    private static final String ERROR_MESSAGE = "Error occurred while sorting";

    @Override
    public List<Integer> sort(List<Integer> unsortedList) {
        try {
            // Sorting the list using Java streams
            return unsortedList.stream()
                .sorted()
                .collect(Collectors.toList());
        } catch (Exception e) {
            // Logging and handling the error
            // Log.error("SortServiceImpl.sort", e);
            throw new RuntimeException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Integer> sortDescending(List<Integer> unsortedList) {
        try {
            // Sorting the list in descending order using Java streams
            return unsortedList.stream()
                .sorted((a, b) -> b.compareTo(a))
                .collect(Collectors.toList());
        } catch (Exception e) {
            // Logging and handling the error
            // Log.error("SortServiceImpl.sortDescending", e);
            throw new RuntimeException(ERROR_MESSAGE, e);
        }
    }
}

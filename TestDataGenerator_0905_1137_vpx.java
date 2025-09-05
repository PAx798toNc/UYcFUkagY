// 代码生成时间: 2025-09-05 11:37:35
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestDataGenerator {

    private static final Random random = new Random();

    // Inject a configuration property for the seed data count
    @Value("${test.data.count:100}")
    private int dataCount;

    private static final String[] FIRST_NAMES = {
        "John", "Jane", "Michael", "Emily", "David"
    };

    private static final String[] LAST_NAMES = {
        "Doe", "Smith", "Johnson", "Williams", "Brown"
    };

    // Simulated entity class for demonstration
    public static class Person {
        private String firstName;
        private String lastName;
        private int age;

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }
    }

    // Generate a list of Person objects
    public List<Person> generatePeople() {
        List<Person> people = new ArrayList<>();
        try {
            for (int i = 0; i < dataCount; i++) {
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
                int age = 18 + random.nextInt(75); // Random age between 18 and 90
                people.add(new Person(firstName, lastName, age));
            }
        } catch (Exception e) {
            // Handle any unexpected errors
            System.err.println("Error generating test data: " + e.getMessage());
        }
        return people;
    }

    // Getters and setters for dataCount
    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }
}

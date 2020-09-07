import java.util.concurrent.atomic.AtomicInteger;

public class Actor {
	int age;
	String country;
	String gender;
	int id;
	private static final AtomicInteger count = new AtomicInteger(0);

	Actor(int age, String country, String gender) {
		this.age = age;
		this.country = country;
		this.gender = gender;
		this.id = count.incrementAndGet();
	}
}

<h1>Gym Plan Management System</h1>

<p>
The purpose of this web application is to help users manage their gym subscriptions and workout plans. 
Users can register and log into their account to access the dashboard. 
After logging in, users can view their subscription details, choose a gym plan, and manage their membership through the system.
</p>

<h2>Features uses</h2>

<p>
Authentication: you can Register, login, and logout using Spring Security<br>
Dashboard: this display user information, active subscription, and subscription history<br>
Subscription Management: Subscribe to gym plans such as Starter, Pro, or Elite<br>
Plan Period Selection: Choose subscription duration such as 1 Month, 3 Months, or 1 Year<br>
Cancel Subscription: Users can also cancel their active gym plan<br>
</p>

<h2>Tool uses</h2>

<p>
For this project I used Gradle as the build tool and implemented the system using the following dependencies:<br>
Lombok,Thymeleaf,Spring Security,JDBC API,Spring Data JPA,PostgreSQL,CycloneDX SBOM Support
</p>

<h2>Link to the website</h2>

<a href="https://gym-plan-production.up.railway.app/">Website</a>

<h2>Data Structure</h2>

<p>

@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role;

}

@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String planName;
    private String period;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

}

</p>

<h2>How to deploy on railway</h2>

<p>
First, create a Railway project and deploy a PostgreSQL database.<br>
Next, deploy the Spring Boot web application to Railway.<br>
After the database is created, copy the environment variables from PostgreSQL such as PGHOST, PGPORT, PGDATABASE, PGUSER, and PGPASSWORD.<br>
Then update the Spring Boot application configuration by replacing localhost with the Railway variables such as ${PGHOST}.<br>
After updating the configuration, Railway will automatically build and deploy the application.<br>
If errors occur, they may be caused by dependency or environment differences, so rebuilding the project may fix the issue.<br>
Finally, Railway will generate a public domain which can be used to access and test the application.
</p

<h3>ai declosure</h3>
<p>Ai used: Claude, ChatGPT
The Ai used prompt: "Create gym plan management with themlef bootstrap and postgreSQL, feature sign up, log in and managing plans"
Instead of copying the whole code, i analyzed it and make adjustment to my preference.
i also used chatgpt to help me fix the bug during deployment</p>

# Blog Application API's
This application provides api's for a blog application.

## Naming Convention

##### For Database:
1. Table names are in Singular
	* Example: **post** table for storing all the post. **user** table for storing all the user. 
2. Fields are singular or plural based on situation

##### For Rest API url:
1. Url domain name that represent Each Entity Controller is in Plural form
	* For example api url for post update: localhost:9090/api/posts/105.
	* See posts (plural form is used)

##### For Spring Code:
1. Each Entity Classes are used as Singular.
	* For example: Post, User etc
2. If it's a list of Entity object type then Plural name is used.
	* For example: List<Post> posts;
3. 

## Designing and Structuring the Application
### Which package/folder does what?
1. **com.diptopaul.blog** - contains the main file to execute the Application
2. com.diptopaul.blog.annotations - contains custom annotations and its constraints/validator.
	1. com.diptopaul.blog.annotations.constraints - contains the custom validator files
3. com.diptopaul.blog.config - contains all the configuration files related to security, mapper, hosting, Mail (JavaMail) and Constant files.
4. com.diptopaul.blog.controllers - contains all the controller methods and it's functionality. This is where all the requests are handlled based on the api-end-points and request type Post, Get etc.
5. com.diptopaul.blog.entities - contains all the entities, an entity is a class representation of the db table. PasswordResetToken entity is used for storing the reset token information in the db, such as Token, user of the token and expiry.
6. com.diptopaul.blog.exceptions - contains custom exceptions and it's handler classes. Custom exceptions are used in many places in the service impl classes and other classes to have more controll on the exceptions and provide response with custom messages accordingly.
7. com.diptopaul.blog.payloads - contains Data Transfer Objects(DTO), Custom Response classes. Special Class for accepting/catching and mapping the data, when sent inside a request body (eg: AuthRequest class to accept and maps the username and password data when a user try to login). DTO's are also used for this type of request data mapping purpose in some cases. In addition to that DTO's are used for data transfering in between the classes of Controller, Service etc layer. Using DTO's is better cause,
	1. we avoid having sensitive and too many information in the DTO's, as result secured and fast data transfering in between is ensured.
	2. Less chance of affecting actual database table in case of any accidental change and save in the DTO's.
8. com.diptopaul.blog.repositories - contains all the repositories.
9. com.diptopaul.blog.security 
	1. CustomPermissionEvaluator - Contains custom method implementation such as hasPermission to evaluates a user and then giving permission or restriction based on the ownership of the resource/api. 3rd person to trying to acess Delete or Update api's such as User data, Comment, Post will be restricted. 
	2.
10. com.diptopaul.blog.services 
	1. EmailService Interface - contains methods to construct an email, methods for sending a email using JavaMail
	2. FileService Interface - contains methods to upload file(images) to a cloud hosting server, method for getting/reading the file etc. --> Will add destroy/delete method.
	3. com.diptopaul.blog.services.impl - Contains Implementation of services
11. com.diptopaul.blog.utils 
12. com.diptopaul.blog.validationgroup - contains all the validation group interfaces for organising groups and perform validation check on DTO's based on the validation group specified in the controller method @Validated(UpdateValidation.class) annotation, check update method of UserController.

##### When Writing a DTO
1. When Writing a DTO ask, What user would provide as the Data and What the user Does not provide? For example, For creating a Comment user will only provide the comment content data. So the DTO should have it. Does the user Provide postId or Post object? No, user does not provide it. But again we might get the postId from URL. So, **DTO should have the property what the user provide as data and what the user might get as data, as the name says data transfer object.** No need to include extra stuffs.

### Common code structure used on each Layer or Type of Class(Entity or DTO or CustomException)

### ModelMapper
1. Its recommended to use same filed name for the Entity class and DTO class, thus avoiding confusion and unnecessary mapping tasks.
2. However if different field is used then the custom filed names need to be told to the ModdelMapper by calling addMappings.

```java
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureMappings(modelMapper);
        return modelMapper;
    }

    private void configureMappings(ModelMapper modelMapper) {
        // Mapping configuration for SourceClass1 to DestinationDTO1
        modelMapper.addMappings(new PropertyMap<SourceClass1, DestinationDTO1>() {
            @Override
            protected void configure() {
                map().setDestField1(source.getSourceFieldA());
                map().setDestField2(source.getSourceFieldB());
            }
        });

        // Mapping configuration for SourceClass2 to DestinationDTO2
        modelMapper.addMappings(new PropertyMap<SourceClass2, DestinationDTO2>() {
            @Override
            protected void configure() {
                map().setDestField3(source.getSourceFieldX());
                map().setDestField4(source.getSourceFieldY());
            }
        });

        // Add more mappings for other DTOs as needed
    }
}
```
## Features
1. Reset Password: resetting password with a link that concates a reset token as a parameter.
	* Files involve in this feature:
		+ PasswordResetController
		+ MailConfig
		+ PasswordResetToken Entity class
		+ PasswordResetTokenDto
		+ PasswordResetTokenRepo
		+ PasswordResetTokenService
		+ PasswordResetTokenServiceImpl
		+ EmailServiceImpl
	1. Future possible updates: send verification code to take that as an 	input and unable the 	user to reset the password. Similar to current 	implementation, just need to send a 	verification code to the user email 	aka username.
	2. Use mobile number to send text to reset with mobile number
	3. Recover account or password with recovery email. Similar to current one, just send a reset 	email to recover email.
	4. Next
2. 

## Note
Test user and their passwords
Hero Alom heloalomno1
vombol12345
julius123
shasina1971
diptopro1994
rolexsince1994

# Preparation

1. Review Java Generics
    1. [Java Generics Example Tutorial - Generic Method, Class, Interface | DigitalOcean](https://www.digitalocean.com/community/tutorials/java-generics-example-method-class-interface)
    2. [Generic Constructors and Interfaces in Java - GeeksforGeeks](https://www.geeksforgeeks.org/generic-constructors-and-interfaces-in-java/)
    3. [Generics in Java - javatpoint](https://www.javatpoint.com/generics-in-java)
    4. [Java Generics (With Examples) (programiz.com)](https://www.programiz.com/java-programming/generics)
    5. Java Generics Interface and Abstract
2. Review Interface
    6. Default Method
    7. Java 8 Method Interface
    8. Nested Interface
3. Inner Class Review
4. Java Annotations
5. Lambda Expression
    9. Introduced in Java 8
    10. [Arrow Operator in Java - Javatpoint](https://www.javatpoint.com/arrow-operator-in-java)
    11. [What does the arrow operator, '->', do in Java? - Stack Overflow](https://stackoverflow.com/questions/15146052/what-does-the-arrow-operator-do-in-java)
6. Java Optional
    12. Introduced in Java 8
    13. Optional orElseThrow() method
        1. [Throw Exception in Optional in Java 8 | Baeldung](https://www.baeldung.com/java-optional-throw-exception)
        2. [Optional orElseThrow() method in Java with examples - GeeksforGeeks](https://www.geeksforgeeks.org/optional-orelsethrow-method-in-java-with-examples/)

## Java and Spring Knowledge Gap for Creating Blog App

1. Optional orElseThrow() method
2. Class ResponseEntity&lt;T>
    1. Basic Understanding
    2. In-depth
        1. [ResponseEntity (Spring Framework 6.0.10 API)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
        2. [Using Spring ResponseEntity to Manipulate the HTTP Response | Baeldung](https://www.baeldung.com/spring-response-entity)
        3. [Spring ResponseEntity - Using ResponseEntity in Spring Application (javaguides.net)](https://www.javaguides.net/2019/08/spring-responseentity-using-responseentity-in-spring-application.html)
        4. [When using ResponseEntity&lt;T> and @RestController for Spring RESTful applications - Stack Overflow](https://stackoverflow.com/questions/26549379/when-use-responseentityt-and-restcontroller-for-spring-restful-applications)
        5. Get inside the ResponseEntity Class and Learn how the Inner Interface **BodyBuilder **and **HeadersBuilder **are implemented and how they work.
3. @ControllerAdvice and @RestControllerAdvice and @ExceptionHandler
    3. [Error Handling for REST with Spring | Baeldung](https://www.baeldung.com/exception-handling-for-rest-with-spring)
    4. [@RestControllerAdvice example in Spring Boot - BezKoder](https://www.bezkoder.com/spring-boot-restcontrolleradvice/)
4. ModelMapper library
5. Validation 
    5. [Validation in Spring Boot | Baeldung](https://www.baeldung.com/spring-boot-bean-validation)
    6. [java - In Hibernate Validator 4.1+, what is the difference between @NotNull, @NotEmpty, and @NotBlank? - Stack Overflow](https://stackoverflow.com/questions/17137307/in-hibernate-validator-4-1-what-is-the-difference-between-notnull-notempty)
6. StreamAPI


### Optional Throw

findById() method returns an Optional class object


```
Optional<User> user = this.userRepo.findById(userId)
```


But, the orElseThrow method returns a value if it is available(this value is already generated by the call of findById() in the optional object), or else if there is no value will throw the exception object we have passed.


```
User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
```


userRepo.findById(userId) => returns Optional object let's say it is opt. Since findById() made this object and returned it, it probably passed the value property of it as well or maybe it did not. Method signature should be like private Optional&lt;T> findById(Integer userId); where T, in this case, is User

this could return 2 scenarios, 



1. return new Optional&lt;>(user)//the Optional Constructor reads the user as the value property
2. return null

So when we call the OrElseThrow method it checks if the value property is assigned or it is null and thus can act upon it.


### Class ResponseEntity&lt;T>

Code Sources: [ResponseEntity | BaeldungSpring](https://www.baeldung.com/spring-response-entity) and [ResponseEntity (javaguides.net)](https://www.javaguides.net/2019/08/spring-responseentity-using-responseentity-in-spring-application.html)

```java
@Repository
@RestController
public class UserController {

   //ResponseEntity is a generic type. Consequently, we can use any type as the response body
   //returning a ResponseEntity object with the user as the body and Status code
   @GetMapping("/users")
   public ResponseEntity < List < User >> users() {
       List < User > users = new ArrayList < > ();
       users.add(new User(1, "Ramesh"));
       users.add(new User(2, "Tony"));
       return new ResponseEntity<>(users,HttpStatus.BAD_REQUEST);
   }
   //can also return a ResponseEntity object with the user as the body, headers, and status code
   @GetMapping("/users1")
   public ResponseEntity < List < User >> users1() {
       List < User > users = new ArrayList < > ();
       users.add(new User(1, "Ramesh"));
       users.add(new User(2, "Tony"));
       HttpHeaders headers = new HttpHeaders();
       headers.add("Responded", "UserController");
       return new ResponseEntity<>(users,headers,HttpStatus.OK);
   }

   //ResponseEntity provides two nested builder interfaces: HeadersBuilder and its subinterface, BodyBuilder.
   //Therefore, we can access their capabilities through the static methods of ResponseEntity.
   //The simplest case is a response with a body and HTTP 200 response code:
   @GetMapping("/users2")
   public ResponseEntity < List < User >> users2() {
       List < User > users = new ArrayList < > ();
       users.add(new User(1, "Ramesh"));
       users.add(new User(2, "Tony"));
       return ResponseEntity.accepted(users);
   }
   //In addition, we can use the BodyBuilder status(HttpStatus status) and the BodyBuilder status(int status) methods to set any HTTP status.
   @GetMapping("/users2")
   public ResponseEntity < List < User >> users2() {
       List < User > users = new ArrayList < > ();
       users.add(new User(1, "Ramesh"));
       users.add(new User(2, "Tony"));
       HttpHeaders headers = new HttpHeaders();
       headers.add("Responded", "UserController");
       return ResponseEntity.status(HttpStatus.accepted).headers(headers).body(users);
       //return ResponseEntity.status(202).headers(headers).body(users);

   }

   //we can also use @ResponseBody instead of using ResponsEntity
   @GetMapping("/users3")
   @ResponseBody
   public List < User > users3() {
       List < User > users = new ArrayList < > ();
       users.add(new User(1, "Ramesh"));
       users.add(new User(2, "Tony"));
       users.add(new User(3, "Tom"));
       return users;
   }
}
```

### StreamAPI

```java
List<Post> posts = this.postRepo.findByCategory(category);


//convert the posts to PostDtos
List<PostDto> postDtos = posts.stream()
            .map((post)->this.modelMapper.map(posts, PostDto.class))
            .collect(Collectors.toList());
```



### Spring Boot and Spring Framework

#### Pagination
##### **FAQ**
1. **[java - Spring data JPA pagination happens at the db level or at the application level - Stack Overflow](https://stackoverflow.com/questions/72645520/spring-data-jpa-pagination-happens-at-db-level-or-at-application-level)**
    1. **<code>With Pagination, instead of fetching all the data, spring takes chunks of data from the database. Generated query is using limit and offset with a select query to fetch a chunk of the data.</code></strong>
2. If Spring does Paginition as a chunk on the database level? How does Spring know the total number of pages beforehand?
    2. Here's how it generally works:
    3. Retrieving Subset of Data: When executing the query with pagination, Spring retrieves a subset or chunk of the result set from the database based on the specified page size and page number.
    4. <strong>Count Query:</strong> <strong>To determine the total number of records </strong>available for pagination, Spring executes a separate query that counts the total number of records in the entire result set, without applying pagination limits. This count query usually involves using a <code>COUNT</code> function or similar database-specific mechanisms.
    5. Calculating Total Pages: Based on the count obtained from the count query and the specified page size, Spring calculates the total number of pages by dividing the total count by the page size and rounding up to the nearest whole number.
    6. Returning Response: Along with the retrieved subset of data, Spring includes the calculated total number of pages in the response, allowing the client to navigate through the paginated results effectively.
    7. 


### Working with files - Input Stream and Output Stream


### HttpServletResponse


### MediaType (MIME type)


### Functional Interface in Java


```
public interface Function<T, R>
```


### *Java Mail Sender


### *Reset Password

[Spring Security – Reset Your Password | Baeldung](https://www.baeldung.com/spring-security-registration-i-forgot-my-password)


### *Validation Group - To exclude some fields in different scenarios.

[Validation groups in Spring MVC (javacodegeeks.com)](https://www.javacodegeeks.com/2014/08/validation-groups-in-spring-mvc.html)

[Grouping Javax Validation Constraints | Baeldung](https://www.baeldung.com/javax-validation-groups)

**Usage: when you want to update basic fields of user and avoid validation check on password and email/username, phone etc.

### *Partial Update In Spring Boot

Read: [Partial Data Update With Spring Data | Baeldung](https://www.baeldung.com/spring-data-partial-update)

#### Using ModelMapper for Partial Update
Setting the modelMapper to skip any null value when mapping from Dto to Entity works for partial update. Usually, Null Value indicates no changes. So this technique works.

```java
@Configuration
public class ModelMapperConfig {
	
  @Bean
  public ModelMapper modelMapper() {
	ModelMapper modelMapper = new ModelMapper();
	//this setting will make sure that the modelMapper is able to map and thus convert the nested objects as well;
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
	
	//added this for mapping when user partial update is performed, when mapping just skipping the null value passed as update works.
	modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
   modelMapper.getConfiguration().setFieldMatchingEnabled(true);
   modelMapper.getConfiguration().setSkipNullEnabled(true);
   //modelMapper.setNullValueMappingStrategy(MatchingStrategies.);
	
    return modelMapper;
  }
}
```

# Steps

1. Technology Review
2. Project Setup
3. Making API for User
4. Exception Handling
5. ModelMapper
    1. Add dependency
    2. Create Config class
    3. Update the code
6. Validation
    4. Add dependency spring-boot-starter-validation
    5. Now the question is where and what field should we validate?
        1. Well, whenever a Rest request is performed say a POST request, inside the rest controller, we are receiving the data by UserDto which is bonded by the data passed inside the Body of the request. We need to put our validation code inside the fields of UserDto class.

        ```
        

        @PostMapping("/users")
        public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
           UserDto createdUserDto = this.userService.createUser(userDto);
           return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
        }
        ```


        2. 
    6. Add validation annotation @Size, @NotNull, etc;
    7. Add @Valid annotation before all the arguments of Rest Controller method

        ```
        @PostMapping("/users")
        public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
           UserDto createdUserDto = this.userService.createUser(userDto);
           return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
        }
        ```


    8. Handle the exception in the GlobalException class with the Help of @RestControllerAdvice and @ExceptionalHandler annotation
    9. Test the API using Postman
7. Making API for Category and also using the model mapper [4:12:31](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=15151s)
    10. Task Pending?: Maybe I should change the name of the fields of Category Entity and Dto to title instead of categoryTitle? This way during the PostMan test we don't have to use,categoryTitle and categoryDescription.

        {


            "categoryTitle":"Fashion",


            "categoryDescription":"This Category contains topics related to Fashion"


        }


        Instead, we can use just the title and description. Will look more professional.

8. Validation for Category
9. Test the API using Postman
10. Creating Post APIs
    11. Testing them
11. [6:27:15](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=23235s) Pagination(Only PostDtos list related to particular page)

->pageSize and pageNumber

-> Sorting by any one field

[http://localhost:9090/posts?pageSize=5&pageNo=2&sortBy=title](http://localhost:9090/posts?pageSize=5&pageNo=2&sortBy=title)

	Highlighted stuff followed by? are parameter list



    12. Proposed step: Handling negative and 0 passed in pagination page and pageSize value. That generates IllegalArgument Exception.
12. [07:32:19](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=27139s) Working on the post image
    13. Image Upload
    14. Image serving
        3. Image can be served in several ways,
            1. As a URL
            2. As a Byte array
            3. As an InputStream
13. [07:57:51](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=28671s) Creating Comments APIs 
14. [8:29:04](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=30544s) Securing REST Apis and [08:41:07](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=31267s) Spring Security Basic Auth
    15. Create Security Basic Config
    16. Create a Roles Entity for creating a Role table
    17. Use the relationship between User and Roles and make a Jointable ‘user_role’
    18.  **Implements UserDetailsService: **Create a class named **_CustomUserDetailService _**that Implements **_UserDetailsService_**
        4. Override **_UserDetails loadUserByUsername _**method that will define **_Email _**as our customize **_Username_**
        5. It must return a _UserDetails _object or its child object. To achieve this Implement the UserDetais by a class. See **step e**.
    19. **Implement UserDetails_: _**To have an Instance of UserDetails for spring to inject the Bean of type UserDetails, implement the **_UserDetails_**.
        6. Option 1 (Main Project Tutorial): Extend UserDetails by the User class and override its methods accordingly
        7. Option 2 (Recommended): Extend UserDetails by a CustomUserDetails class and override its methods accordingly. Thus ensuring decoupling and making sure separation of concern.

            ```
            import org.springframework.security.core.GrantedAuthority;
            import org.springframework.security.core.userdetails.UserDetails;

            import java.util.Collection;

            public class CustomUserDetails implements UserDetails {

                private final User user; // Your domain User entity

                public CustomUserDetails(User user) {
                    this.user = user;
                }

                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    // Return the user's authorities (roles) here
                    return user.getAuthorities();
                }

                @Override
                public String getPassword() {
                    // Return the user's password here
                    return user.getPassword();
                }

                @Override
                public String getUsername() {
                    // Return the user's username here
                    return user.getUsername();
                }

                // Implement other methods of UserDetails with appropriate logic for your User entity

                // ...
            }
            ```


    20. Update the Config to provide an instance of CustomUserDetailsService
    21. Update the Config to Specify PasswordEncoder Type by creating a Bean
15. [09:47:25](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=35245s) JWT implementing in the project - [Spring Boot 3 + Spring Security 6 - JWT Authentication and Authorisation [NEW] [2023] - YouTube](https://www.youtube.com/watch?v=KxqlJblhzfI&ab_channel=Amigoscode)
    22. Add dependency(io.jsonwebtoken)
    23. Create JWT authenticationEntryPoint implements AuthenticationEntryPoint
    24. Create JWTTokenHelper
    25. JwtAuthenticationFilter extends OnceRequestFilter
        8. Get jwt token from response
        9. Validate token I
        10. Get user from token
        11.  Load user associated with token
        12. Set spring security
    26. Create JwtAuthResponse
16. [10:44:18](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=38658s) Role-Specific Access
17. [11:02:31](https://www.youtube.com/watch?v=Uh-N_6Lccr4&t=39751s) Challenges while registering app


## Working on the Image Upload
### Hosting Image in System File
#### Serving image By the URL

#### Serving image as a Byte array

```
//sending the image as a byte array
	@GetMapping(value = "imagestest/{imageName}")
	public ResponseEntity<byte[]> getImageTest(@PathVariable("imageName") String imageName, HttpServletResponse httpServletResponse) throws IOException,FileNotFoundException {
		InputStream is = this.fileService.getFile(UPLOAD_DIR_PATH, imageName);
		byte[] media = StreamUtils.copyToByteArray(is);


		//dynamically get the mediaType
		String mimeType = URLConnection.guessContentTypeFromStream(is);
		//source: https://stackoverflow.com/questions/51438/how-to-get-a-files-media-type-mime-type


		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", mimeType);
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());


		return new ResponseEntity<>(media,headers,HttpStatus.OK);


	}
```
#### Serving image as an Input Stream

```
//	@GetMapping(value = "images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE )
//	public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse httpServletResponse) throws IOException,FileNotFoundException {
//		InputStream is = this.fileService.getFile(UPLOAD_DIR_PATH, imageName);
//		StreamUtils.copy(is, httpServletResponse.getOutputStream());
//		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
//		StreamUtils.copy(is, httpServletResponse.getOutputStream());
//		
//	}


	//sending the image mimeType Dynamic
	@GetMapping(value = "images"
			+ "/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE )
	public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse httpServletResponse) throws IOException,FileNotFoundException {
		InputStream is = this.fileService.getFile(UPLOAD_DIR_PATH, imageName);


		//dynamically get the mediaType
		String mimeType = URLConnection.guessContentTypeFromStream(is);
		httpServletResponse.setContentType(mimeType);
		httpServletResponse.addHeader("Content-Type", mimeType);
		//source: https://stackoverflow.com/questions/51438/how-to-get-a-files-media-type-mime-type


		StreamUtils.copy(is, httpServletResponse.getOutputStream());
	}
```
### Hosting Image in a Hosting Service [Cloudinary]

## Working with JWT

[Spring Boot 3 + Spring Security 6 - JWT Authentication and Authorisation [NEW] [2023] - YouTube](https://www.youtube.com/watch?v=KxqlJblhzfI&ab_channel=Amigoscode)

Yes, if you don’t use JWT, Spring Security will create a UsernamePasswordAuthenticationToken object and add it to the SecurityContext to set the user details provided by its internal authentication system 12. This allows Spring Security to hold the principal information of each authenticated user in a ThreadLocal, represented as an Authentication object. [[2](https://www.baeldung.com/manually-set-user-authentication-spring-security)]

```
UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
username, null, jwtHelper.extractAuthorities(token)
);

```

1. `UsernamePasswordAuthenticationToken` is a specific implementation of the `Authentication` interface provided by Spring Security. It represents a token used for username and password-based authentication. In this case, even though we are using JWT-based authentication, we use `UsernamePasswordAuthenticationToken` because it's a convenient way to carry the username and authorities extracted from the JWT token.
2. The second parameter in the `UsernamePasswordAuthenticationToken` constructor is the credentials (usually the password), but since we are using JWT-based authentication and don't have a password, we pass `null`.
3. By creating and setting the `UsernamePasswordAuthenticationToken` in the `SecurityContextHolder`, we are effectively telling Spring Security that the user represented by the JWT token has been authenticated and has the provided authorities. This ensures that the user can access protected resources within the application based on their roles/permissions defined in the JWT token.
4. In summary, this code is essential for establishing the user's authentication and authorization within the Spring Security framework, allowing the user to interact with the application securely based on the information provided in the JWT token.
5. The `WebAuthenticationDetailsSource` is used to create an instance of `WebAuthenticationDetails`, which provides additional details about the authentication request. Here, we use it to set the request details (such as the remote IP address and session ID) to the `UsernamePasswordAuthenticationToken`.

```java
authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

```
By creating and setting the `UsernamePasswordAuthenticationToken `and its details in the `SecurityContextHolder`, we make the authenticated user available throughout the request processing and authorization process in the application.  This way, Spring Security can identify the authenticated user and apply appropriate security rules based on the user's roles and permissions.

Alternatively, in straight words, we are effectively telling Spring Security that the user represented by the JWT token has been authenticated and has the provided authorities. This ensures that the user can access protected resources within the application based on their roles/permissions defined in the JWT token.

.


# Frequent Mistakes and Errors

1. Forget to add @Valid inside the RestController method
2. Forget to add @Service annotation inside the Service layer implementation class
3. Jackson Databinding error or issue for Giving wrong Generic Type
	1. Below
	<code>at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(<span style="text-decoration:underline;">BeanSerializerBase.java:772</span>) ~[jackson-databind-2.15.2.jar:2.15.2]</code>
       <code>at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178) ~[jackson-databind-2.15.2.jar:2.15.2]</code>
        <code>at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732) ~[jackson-databind-2.15.2.jar:2.15.2]</code>
   	2. Solution: convert fields of DTO class from Entity to DTO class object. Example,
   		1. Convert Category and User to CategoryDto and UserDto
        
        ```java
        public class PostDto {
            	private Integer id;
            	private String title;
            	private String content;
            	private Category category;//change it
            	private User user;//change it
	       }
         ```
  		2. Correct

        ```java
        public class PostDto {
            	private Integer id;
            	private String title;
            	private String content;
            	private CategoryDto category;//keep the variable name same in the Dto and Entity, during mapping same names may be used for mapping
            	private UserDto user;
        }
        ```
4. ModelMapper makes the nested class objects null. How to use the modelMapper to convert nested classes
   a. [java - How to use modelMapper to convert nested classes - Stack Overflow](https://stackoverflow.com/questions/36717365/how-to-use-modelmapper-to-convert-nested-classes?rq=3)

        ```
        ModelMapper modelMapper = new ModelMapper();
        	//this setting will make sure that the modelMapper is able to map and thus convert the nested objects as well;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ```
5. ModelMapper makes the List object of the DTO class null or Empty array [].
	a. One solution could be the above one
   	b. Check if the DTO class field name is different from the Entity class field name. If they are different ModelMapper won't be able to convert them appropriately and will end up showing null or []. 
   		i. **Solution 1: **Make the field names the same for DTO and Entity class
        ii. **Solution 2: **provide explicit mapping for the fields with different names. Like so,
        
		  ```java
			 @Configuration
		    public class MappingConfig {
		    @Bean
		    public ModelMapper modelMapper() {
			    ModelMapper modelMapper = new ModelMapper();
			    configureMappings(modelMapper);
			    return modelMapper;
		    }
		    private void configureMappings(ModelMapper modelMapper) {
		    // Mapping configuration for SourceClass1 to DestinationDTO1
		    modelMapper.addMappings(new PropertyMap<SourceClass1, DestinationDTO1>() {
			    @Override
			    protected void configure() {
			    map().setDestField1(source.getSourceFieldA());
			    map().setDestField2(source.getSourceFieldB());
		    }
		    });
		    // Mapping configuration for SourceClass2 to DestinationDTO2
		    modelMapper.addMappings(new PropertyMap<SourceClass2, DestinationDTO2>() {
			    @Override
			    protected void configure() {
				    map().setDestField3(source.getSourceFieldX());
				    map().setDestField4(source.getSourceFieldY());
			    }
		    });
		    // Add more mappings for other DTOs as needed
		    }
		    }
		    ```
6. While converting one List type to Another using stream API sometimes I write the plural <code><em>posts </em></code>instead of singular <code><em>post</em></code>, see highlighted
    <code>List&lt;PostDto> postDtos = posts.stream().map((posts)->modelMapper.map(<strong>~~post~~</strong>,PostDto.class)).collect(Collectors.<em>toList</em>());</code>
7. Cannot delete or update a parent row: a foreign key constraint fails (`blog_app_apis`.`user_role`, CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`))
    1. Solution: remove the foreign key constraint before deletion. For example:
        1. <code>user.setRoles(Collections.<em>emptySet</em>()); // making it empty will remove any relationship with the role thus no foreign key constraint.</code>
        2. Change the cascadeType accordingly


# New Techniques to Try

- [ ] Setting validation message using property file instead of using hard coded.
	1. [spring - How to customize Hibernate @Size error message to indicate the length of the entered field - Stack Overflow](https://stackoverflow.com/questions/31773594/how-to-customize-hibernate-size-error-message-to-indicate-length-of-entered-fie)
- [x] Add/ make uniqueness validation for username or email.
	1. [java - hibernate unique key validation - Stack Overflow](https://stackoverflow.com/questions/4613055/hibernate-unique-key-validation)
   	2. [java - Can Spring Boot be configured to check unique constraints in my rest controller? - Stack Overflow](https://stackoverflow.com/questions/62537836/can-spring-boot-be-configured-to-check-unique-constraints-in-my-rest-controller/62650045#62650045)
     - [ ] Make the uniqueness annotation general to check all types of unique fields that will not be limited to emails.
- [x] How do we use our own implementation methods with our own sql alongside JpaRepository provided methods?

        ```
         public interface PostRepo extends JpaRepository&lt;Post, Integer>
         {
        		//spring will provide implementation and use the String query to execute a query. All will be done by spring

        		//List<Post> findByTitleContaining(String query);
        
            	//adding your own query, you can name the function as you want
            	@Query("Select p from Post p where p.title like %:key%")
            	List<Post> findByTitleContaining(@Param("key") String query);
        }
        ```
- [x] Handling negative and 0 passed in pagination page and pageSize value. That generates IllegalArgument Exception
    1. Handled with a custom Exception handler of IllegalArgument Exception
- [ ] How to store articles or blog that has text with multiple images in between like a medium blog?
    1. [php - What datatype is best for storing articles in SQL database? - Stack Overflow](https://stackoverflow.com/questions/9212828/what-datatype-is-best-for-storing-articles-in-sql-database)
    2. [database - How to save images within texts like medium blog post - Stack Overflow](https://stackoverflow.com/questions/68962473/how-to-save-images-within-texts-like-medium-blog-post)
    3. [xml - How to store articles or other large texts in a database - Stack Overflow](https://stackoverflow.com/questions/1084506/how-to-store-articles-or-other-large-texts-in-a-database)
- [ ] Add file upload validation
    8. [java - File upload in Spring Boot: Uploading, validation, and exception handling - Stack Overflow](https://stackoverflow.com/questions/40355743/file-upload-in-spring-boot-uploading-validation-and-exception-handling)
- [x] How to allow a User only access their own data in Spring Boot?
    1. [rest - How to allow a User only access their own data in Spring Boot / Spring Security? - Stack Overflow](https://stackoverflow.com/questions/51712724/how-to-allow-a-user-only-access-their-own-data-in-spring-boot-spring-security)
    2. [Spring Security: allow user only to access their own administration page - Stack Overflow](https://stackoverflow.com/questions/6871203/spring-security-allow-user-only-to-access-their-own-administration-page)
     	- [x] Restricting specific post edit or delete access for users other than the owner
- [x] Can we exclude some fields of the entity from the validation check? Specificly for user?
    1. [https://stackoverflow.com/a/29798591/7828981](https://stackoverflow.com/a/29798591/7828981)
    2. [Validation groups in Spring MVC (javacodegeeks.com)](https://www.javacodegeeks.com/2014/08/validation-groups-in-spring-mvc.html)
    3. [Chapter 2. Validation step by step (jboss.org)](https://docs.jboss.org/hibernate/validator/4.1/reference/en-US/html/validator-usingvalidator.html#validator-usingvalidator-validationgroups)
- [x] Enabling Partial Update for User, where I don't have to update Password and Email every time.
    1. 
- [ ] Not sure, Should I remove the JWT token after login or logout??
- [ ] Keep the user logged in until logout is performed or until a certain period is expired
- [ ] JWT + OAUTH1 or 2 + Spring Security
- [x] Exclude fields from the Java object or entity when sending as JSON response. Like not sending sensitive data like password etc.
    1. [Ignore fields from Java objects dynamically while sending as JSON from Spring MVC - Stack Overflow](https://stackoverflow.com/questions/23101260/ignore-fields-from-java-object-dynamically-while-sending-as-json-from-spring-mvc)
    2. [java - How could I ignore a field in json response? - Stack Overflow](https://stackoverflow.com/questions/44302538/how-could-i-ignore-a-field-in-json-response)
    3. [How to ignore JSON field in response alone in java - Stack Overflow](https://stackoverflow.com/questions/53528431/how-to-ignore-json-field-in-response-alone-in-java)
- [x] Add forget the password or reset password
	1. [Spring Security – Reset Your Password | Baeldung](https://www.baeldung.com/spring-security-registration-i-forgot-my-password)
	2. ChatGPT
		- [ ] Password validation check
		- [ ] Add checking of the old password
			1. Maybe add a custom annotation to check @NotOldPassword
		- [ ] Automatically delete the reset token entry from the db after expiry

## Proposed Changes, maybe pending task

1. Decide a suitable naming convention for the Service layer and controller layer methods and then change them accordingly. Currently, it can be improved.
    1. 
2. *Imp* Add a new Entity and DB table to store images URL and their public id, signature for destroying/deleting the image. Currently it's not possible to delete image, cause not storing the signature and public_id anywhere. Altough public_id can be trimmed from url but unable to retrive signature for not storing it.
3. 

## Testing
1. If admin deletes a user, does their posts, comments, user_role info get deleted as well?
	- [x]Passed
2. If a Post gets deleted, does their comments get deleted?
	- [x]Passed
3. Is a 3rd person unable to acess a user's personal apis/information or protected(+admin apis) api's?
	- [x]Passed
4. 

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
##### When Writing a DTO
1. When Writing a DTO ask, What user would provide as the Data and What the user Does not provide? For example, For creating a Comment user will only provide the comment content data. So the DTO should have it. Does the user Provide postId or Post object? No, user does not provide it. But again we might get the postId from URL. So, **DTO should have the property what the user provide as data and what the user might get as data, as the name says data transfer object.** No need to include extra stuffs.

## Common code structure used on each Layer or Type of Class(Entity or DTO or CustomException)

## ModelMapper
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
#Note
Test user and their passwords
Hero Alom heloalomno1
vombol12345
julius123
shasina1971
diptopro1994
rolexsince1994
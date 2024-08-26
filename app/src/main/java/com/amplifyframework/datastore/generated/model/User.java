package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byEmail", fields = {"email"})
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField FIRSTNAME = field("User", "firstname");
  public static final QueryField LASTNAME = field("User", "lastname");
  public static final QueryField EMAIL = field("User", "email");
  public static final QueryField AGE = field("User", "age");
  public static final QueryField GENDER = field("User", "gender");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String firstname;
  private final @ModelField(targetType="String", isRequired = true) String lastname;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="Int") Integer age;
  private final @ModelField(targetType="String") String gender;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getFirstname() {
      return firstname;
  }
  
  public String getLastname() {
      return lastname;
  }
  
  public String getEmail() {
      return email;
  }
  
  public Integer getAge() {
      return age;
  }
  
  public String getGender() {
      return gender;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, String firstname, String lastname, String email, Integer age, String gender) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.age = age;
    this.gender = gender;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getFirstname(), user.getFirstname()) &&
              ObjectsCompat.equals(getLastname(), user.getLastname()) &&
              ObjectsCompat.equals(getEmail(), user.getEmail()) &&
              ObjectsCompat.equals(getAge(), user.getAge()) &&
              ObjectsCompat.equals(getGender(), user.getGender()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstname())
      .append(getLastname())
      .append(getEmail())
      .append(getAge())
      .append(getGender())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstname=" + String.valueOf(getFirstname()) + ", ")
      .append("lastname=" + String.valueOf(getLastname()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("age=" + String.valueOf(getAge()) + ", ")
      .append("gender=" + String.valueOf(getGender()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static FirstnameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static User justId(String id) {
    return new User(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      firstname,
      lastname,
      email,
      age,
      gender);
  }
  public interface FirstnameStep {
    LastnameStep firstname(String firstname);
  }
  

  public interface LastnameStep {
    EmailStep lastname(String lastname);
  }
  

  public interface EmailStep {
    BuildStep email(String email);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep age(Integer age);
    BuildStep gender(String gender);
  }
  

  public static class Builder implements FirstnameStep, LastnameStep, EmailStep, BuildStep {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Integer age;
    private String gender;
    public Builder() {
      
    }
    
    private Builder(String id, String firstname, String lastname, String email, Integer age, String gender) {
      this.id = id;
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
      this.age = age;
      this.gender = gender;
    }
    
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          firstname,
          lastname,
          email,
          age,
          gender);
    }
    
    @Override
     public LastnameStep firstname(String firstname) {
        Objects.requireNonNull(firstname);
        this.firstname = firstname;
        return this;
    }
    
    @Override
     public EmailStep lastname(String lastname) {
        Objects.requireNonNull(lastname);
        this.lastname = lastname;
        return this;
    }
    
    @Override
     public BuildStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep age(Integer age) {
        this.age = age;
        return this;
    }
    
    @Override
     public BuildStep gender(String gender) {
        this.gender = gender;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String firstname, String lastname, String email, Integer age, String gender) {
      super(id, firstname, lastname, email, age, gender);
      Objects.requireNonNull(firstname);
      Objects.requireNonNull(lastname);
      Objects.requireNonNull(email);
    }
    
    @Override
     public CopyOfBuilder firstname(String firstname) {
      return (CopyOfBuilder) super.firstname(firstname);
    }
    
    @Override
     public CopyOfBuilder lastname(String lastname) {
      return (CopyOfBuilder) super.lastname(lastname);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
    
    @Override
     public CopyOfBuilder gender(String gender) {
      return (CopyOfBuilder) super.gender(gender);
    }
  }
  

  public static class UserIdentifier extends ModelIdentifier<User> {
    private static final long serialVersionUID = 1L;
    public UserIdentifier(String id) {
      super(id);
    }
  }
  
}
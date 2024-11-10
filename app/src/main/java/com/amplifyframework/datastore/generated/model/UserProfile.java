package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
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

/** This is an auto generated class representing the UserProfile type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserProfiles", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byEmail", fields = {"email"})
public final class UserProfile implements Model {
  public static final QueryField ID = field("UserProfile", "id");
  public static final QueryField FIRSTNAME = field("UserProfile", "firstname");
  public static final QueryField LASTNAME = field("UserProfile", "lastname");
  public static final QueryField EMAIL = field("UserProfile", "email");
  public static final QueryField AGE = field("UserProfile", "age");
  public static final QueryField GENDER = field("UserProfile", "gender");
  public static final QueryField CONSENT_GIVEN = field("UserProfile", "consentGiven");
  public static final QueryField SKINTYPE = field("UserProfile", "skintype");
  public static final QueryField PRODUCT_TYPE = field("UserProfile", "productType");
  public static final QueryField SKIN_PROBLEMS = field("UserProfile", "skinProblems");
  public static final QueryField NOTABLE_EFFECTS = field("UserProfile", "notableEffects");
  public static final QueryField RECOMMENDED_PRODUCTS = field("UserProfile", "recommendedProducts");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String firstname;
  private final @ModelField(targetType="String", isRequired = true) String lastname;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="Int") Integer age;
  private final @ModelField(targetType="String") String gender;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean consentGiven;
  private final @ModelField(targetType="String") String skintype;
  private final @ModelField(targetType="String") String productType;
  private final @ModelField(targetType="String") List<String> skinProblems;
  private final @ModelField(targetType="String") List<String> notableEffects;
  private final @ModelField(targetType="String") List<String> recommendedProducts;
  private final @ModelField(targetType="SkinAssessment") @HasMany(associatedWith = "userProfileID", type = SkinAssessment.class) List<SkinAssessment> assessments = null;
  private final @ModelField(targetType="UserMedication") @HasMany(associatedWith = "userProfileID", type = UserMedication.class) List<UserMedication> medications = null;
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
  
  public Boolean getConsentGiven() {
      return consentGiven;
  }
  
  public String getSkintype() {
      return skintype;
  }
  
  public String getProductType() {
      return productType;
  }
  
  public List<String> getSkinProblems() {
      return skinProblems;
  }
  
  public List<String> getNotableEffects() {
      return notableEffects;
  }
  
  public List<String> getRecommendedProducts() {
      return recommendedProducts;
  }
  
  public List<SkinAssessment> getAssessments() {
      return assessments;
  }
  
  public List<UserMedication> getMedications() {
      return medications;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private UserProfile(String id, String firstname, String lastname, String email, Integer age, String gender, Boolean consentGiven, String skintype, String productType, List<String> skinProblems, List<String> notableEffects, List<String> recommendedProducts) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.age = age;
    this.gender = gender;
    this.consentGiven = consentGiven;
    this.skintype = skintype;
    this.productType = productType;
    this.skinProblems = skinProblems;
    this.notableEffects = notableEffects;
    this.recommendedProducts = recommendedProducts;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserProfile userProfile = (UserProfile) obj;
      return ObjectsCompat.equals(getId(), userProfile.getId()) &&
              ObjectsCompat.equals(getFirstname(), userProfile.getFirstname()) &&
              ObjectsCompat.equals(getLastname(), userProfile.getLastname()) &&
              ObjectsCompat.equals(getEmail(), userProfile.getEmail()) &&
              ObjectsCompat.equals(getAge(), userProfile.getAge()) &&
              ObjectsCompat.equals(getGender(), userProfile.getGender()) &&
              ObjectsCompat.equals(getConsentGiven(), userProfile.getConsentGiven()) &&
              ObjectsCompat.equals(getSkintype(), userProfile.getSkintype()) &&
              ObjectsCompat.equals(getProductType(), userProfile.getProductType()) &&
              ObjectsCompat.equals(getSkinProblems(), userProfile.getSkinProblems()) &&
              ObjectsCompat.equals(getNotableEffects(), userProfile.getNotableEffects()) &&
              ObjectsCompat.equals(getRecommendedProducts(), userProfile.getRecommendedProducts()) &&
              ObjectsCompat.equals(getCreatedAt(), userProfile.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userProfile.getUpdatedAt());
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
      .append(getConsentGiven())
      .append(getSkintype())
      .append(getProductType())
      .append(getSkinProblems())
      .append(getNotableEffects())
      .append(getRecommendedProducts())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserProfile {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstname=" + String.valueOf(getFirstname()) + ", ")
      .append("lastname=" + String.valueOf(getLastname()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("age=" + String.valueOf(getAge()) + ", ")
      .append("gender=" + String.valueOf(getGender()) + ", ")
      .append("consentGiven=" + String.valueOf(getConsentGiven()) + ", ")
      .append("skintype=" + String.valueOf(getSkintype()) + ", ")
      .append("productType=" + String.valueOf(getProductType()) + ", ")
      .append("skinProblems=" + String.valueOf(getSkinProblems()) + ", ")
      .append("notableEffects=" + String.valueOf(getNotableEffects()) + ", ")
      .append("recommendedProducts=" + String.valueOf(getRecommendedProducts()) + ", ")
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
  public static UserProfile justId(String id) {
    return new UserProfile(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
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
      gender,
      consentGiven,
      skintype,
      productType,
      skinProblems,
      notableEffects,
      recommendedProducts);
  }
  public interface FirstnameStep {
    LastnameStep firstname(String firstname);
  }
  

  public interface LastnameStep {
    EmailStep lastname(String lastname);
  }
  

  public interface EmailStep {
    ConsentGivenStep email(String email);
  }
  

  public interface ConsentGivenStep {
    BuildStep consentGiven(Boolean consentGiven);
  }
  

  public interface BuildStep {
    UserProfile build();
    BuildStep id(String id);
    BuildStep age(Integer age);
    BuildStep gender(String gender);
    BuildStep skintype(String skintype);
    BuildStep productType(String productType);
    BuildStep skinProblems(List<String> skinProblems);
    BuildStep notableEffects(List<String> notableEffects);
    BuildStep recommendedProducts(List<String> recommendedProducts);
  }
  

  public static class Builder implements FirstnameStep, LastnameStep, EmailStep, ConsentGivenStep, BuildStep {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Boolean consentGiven;
    private Integer age;
    private String gender;
    private String skintype;
    private String productType;
    private List<String> skinProblems;
    private List<String> notableEffects;
    private List<String> recommendedProducts;
    public Builder() {
      
    }
    
    private Builder(String id, String firstname, String lastname, String email, Integer age, String gender, Boolean consentGiven, String skintype, String productType, List<String> skinProblems, List<String> notableEffects, List<String> recommendedProducts) {
      this.id = id;
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
      this.age = age;
      this.gender = gender;
      this.consentGiven = consentGiven;
      this.skintype = skintype;
      this.productType = productType;
      this.skinProblems = skinProblems;
      this.notableEffects = notableEffects;
      this.recommendedProducts = recommendedProducts;
    }
    
    @Override
     public UserProfile build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserProfile(
          id,
          firstname,
          lastname,
          email,
          age,
          gender,
          consentGiven,
          skintype,
          productType,
          skinProblems,
          notableEffects,
          recommendedProducts);
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
     public ConsentGivenStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep consentGiven(Boolean consentGiven) {
        Objects.requireNonNull(consentGiven);
        this.consentGiven = consentGiven;
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
    
    @Override
     public BuildStep skintype(String skintype) {
        this.skintype = skintype;
        return this;
    }
    
    @Override
     public BuildStep productType(String productType) {
        this.productType = productType;
        return this;
    }
    
    @Override
     public BuildStep skinProblems(List<String> skinProblems) {
        this.skinProblems = skinProblems;
        return this;
    }
    
    @Override
     public BuildStep notableEffects(List<String> notableEffects) {
        this.notableEffects = notableEffects;
        return this;
    }
    
    @Override
     public BuildStep recommendedProducts(List<String> recommendedProducts) {
        this.recommendedProducts = recommendedProducts;
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
    private CopyOfBuilder(String id, String firstname, String lastname, String email, Integer age, String gender, Boolean consentGiven, String skintype, String productType, List<String> skinProblems, List<String> notableEffects, List<String> recommendedProducts) {
      super(id, firstname, lastname, email, age, gender, consentGiven, skintype, productType, skinProblems, notableEffects, recommendedProducts);
      Objects.requireNonNull(firstname);
      Objects.requireNonNull(lastname);
      Objects.requireNonNull(email);
      Objects.requireNonNull(consentGiven);
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
     public CopyOfBuilder consentGiven(Boolean consentGiven) {
      return (CopyOfBuilder) super.consentGiven(consentGiven);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
    
    @Override
     public CopyOfBuilder gender(String gender) {
      return (CopyOfBuilder) super.gender(gender);
    }
    
    @Override
     public CopyOfBuilder skintype(String skintype) {
      return (CopyOfBuilder) super.skintype(skintype);
    }
    
    @Override
     public CopyOfBuilder productType(String productType) {
      return (CopyOfBuilder) super.productType(productType);
    }
    
    @Override
     public CopyOfBuilder skinProblems(List<String> skinProblems) {
      return (CopyOfBuilder) super.skinProblems(skinProblems);
    }
    
    @Override
     public CopyOfBuilder notableEffects(List<String> notableEffects) {
      return (CopyOfBuilder) super.notableEffects(notableEffects);
    }
    
    @Override
     public CopyOfBuilder recommendedProducts(List<String> recommendedProducts) {
      return (CopyOfBuilder) super.recommendedProducts(recommendedProducts);
    }
  }
  

  public static class UserProfileIdentifier extends ModelIdentifier<UserProfile> {
    private static final long serialVersionUID = 1L;
    public UserProfileIdentifier(String id) {
      super(id);
    }
  }
  
}

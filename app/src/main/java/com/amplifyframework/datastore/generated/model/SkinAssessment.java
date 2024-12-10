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

/** This is an auto generated class representing the SkinAssessment type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "SkinAssessments", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byUserProfile", fields = {"userProfileID"})
public final class SkinAssessment implements Model {
  public static final QueryField ID = field("SkinAssessment", "id");
  public static final QueryField USER_PROFILE_ID = field("SkinAssessment", "userProfileID");
  public static final QueryField IMAGE_URI = field("SkinAssessment", "imageUri");
  public static final QueryField CONDITION = field("SkinAssessment", "condition");
  public static final QueryField SEVERITY = field("SkinAssessment", "severity");
  public static final QueryField PROBABILITY = field("SkinAssessment", "probability");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String userProfileID;
  private final @ModelField(targetType="String", isRequired = true) String imageUri;
  private final @ModelField(targetType="String", isRequired = true) String condition;
  private final @ModelField(targetType="String", isRequired = true) String severity;
  private final @ModelField(targetType="Int", isRequired = true) Integer probability;
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
  
  public String getUserProfileId() {
      return userProfileID;
  }
  
  public String getImageUri() {
      return imageUri;
  }
  
  public String getCondition() {
      return condition;
  }
  
  public String getSeverity() {
      return severity;
  }
  
  public Integer getProbability() {
      return probability;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private SkinAssessment(String id, String userProfileID, String imageUri, String condition, String severity, Integer probability) {
    this.id = id;
    this.userProfileID = userProfileID;
    this.imageUri = imageUri;
    this.condition = condition;
    this.severity = severity;
    this.probability = probability;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      SkinAssessment skinAssessment = (SkinAssessment) obj;
      return ObjectsCompat.equals(getId(), skinAssessment.getId()) &&
              ObjectsCompat.equals(getUserProfileId(), skinAssessment.getUserProfileId()) &&
              ObjectsCompat.equals(getImageUri(), skinAssessment.getImageUri()) &&
              ObjectsCompat.equals(getCondition(), skinAssessment.getCondition()) &&
              ObjectsCompat.equals(getSeverity(), skinAssessment.getSeverity()) &&
              ObjectsCompat.equals(getProbability(), skinAssessment.getProbability()) &&
              ObjectsCompat.equals(getCreatedAt(), skinAssessment.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), skinAssessment.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserProfileId())
      .append(getImageUri())
      .append(getCondition())
      .append(getSeverity())
      .append(getProbability())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("SkinAssessment {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userProfileID=" + String.valueOf(getUserProfileId()) + ", ")
      .append("imageUri=" + String.valueOf(getImageUri()) + ", ")
      .append("condition=" + String.valueOf(getCondition()) + ", ")
      .append("severity=" + String.valueOf(getSeverity()) + ", ")
      .append("probability=" + String.valueOf(getProbability()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserProfileIdStep builder() {
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
  public static SkinAssessment justId(String id) {
    return new SkinAssessment(
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
      userProfileID,
      imageUri,
      condition,
      severity,
      probability);
  }
  public interface UserProfileIdStep {
    ImageUriStep userProfileId(String userProfileId);
  }
  

  public interface ImageUriStep {
    ConditionStep imageUri(String imageUri);
  }
  

  public interface ConditionStep {
    SeverityStep condition(String condition);
  }
  

  public interface SeverityStep {
    ProbabilityStep severity(String severity);
  }
  

  public interface ProbabilityStep {
    BuildStep probability(Integer probability);
  }
  

  public interface BuildStep {
    SkinAssessment build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UserProfileIdStep, ImageUriStep, ConditionStep, SeverityStep, ProbabilityStep, BuildStep {
    private String id;
    private String userProfileID;
    private String imageUri;
    private String condition;
    private String severity;
    private Integer probability;
    public Builder() {
      
    }
    
    private Builder(String id, String userProfileID, String imageUri, String condition, String severity, Integer probability) {
      this.id = id;
      this.userProfileID = userProfileID;
      this.imageUri = imageUri;
      this.condition = condition;
      this.severity = severity;
      this.probability = probability;
    }
    
    @Override
     public SkinAssessment build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new SkinAssessment(
          id,
          userProfileID,
          imageUri,
          condition,
          severity,
          probability);
    }
    
    @Override
     public ImageUriStep userProfileId(String userProfileId) {
        Objects.requireNonNull(userProfileId);
        this.userProfileID = userProfileId;
        return this;
    }
    
    @Override
     public ConditionStep imageUri(String imageUri) {
        Objects.requireNonNull(imageUri);
        this.imageUri = imageUri;
        return this;
    }
    
    @Override
     public SeverityStep condition(String condition) {
        Objects.requireNonNull(condition);
        this.condition = condition;
        return this;
    }
    
    @Override
     public ProbabilityStep severity(String severity) {
        Objects.requireNonNull(severity);
        this.severity = severity;
        return this;
    }
    
    @Override
     public BuildStep probability(Integer probability) {
        Objects.requireNonNull(probability);
        this.probability = probability;
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
    private CopyOfBuilder(String id, String userProfileId, String imageUri, String condition, String severity, Integer probability) {
      super(id, userProfileID, imageUri, condition, severity, probability);
      Objects.requireNonNull(userProfileID);
      Objects.requireNonNull(imageUri);
      Objects.requireNonNull(condition);
      Objects.requireNonNull(severity);
      Objects.requireNonNull(probability);
    }
    
    @Override
     public CopyOfBuilder userProfileId(String userProfileId) {
      return (CopyOfBuilder) super.userProfileId(userProfileId);
    }
    
    @Override
     public CopyOfBuilder imageUri(String imageUri) {
      return (CopyOfBuilder) super.imageUri(imageUri);
    }
    
    @Override
     public CopyOfBuilder condition(String condition) {
      return (CopyOfBuilder) super.condition(condition);
    }
    
    @Override
     public CopyOfBuilder severity(String severity) {
      return (CopyOfBuilder) super.severity(severity);
    }
    
    @Override
     public CopyOfBuilder probability(Integer probability) {
      return (CopyOfBuilder) super.probability(probability);
    }
  }
  

  public static class SkinAssessmentIdentifier extends ModelIdentifier<SkinAssessment> {
    private static final long serialVersionUID = 1L;
    public SkinAssessmentIdentifier(String id) {
      super(id);
    }
  }
  
}

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

/** This is an auto generated class representing the UserMedication type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserMedications", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byUserProfile", fields = {"userProfileID"})
@Index(name = "byMedication", fields = {"medicationID"})
public final class UserMedication implements Model {
  public static final QueryField ID = field("UserMedication", "id");
  public static final QueryField USER_PROFILE_ID = field("UserMedication", "userProfileID");
  public static final QueryField MEDICATION_ID = field("UserMedication", "medicationID");
  public static final QueryField MEDICATION_NAME = field("UserMedication", "medicationName");
  public static final QueryField COMPANY_NAME = field("UserMedication", "companyName");
  public static final QueryField DOSAGE = field("UserMedication", "dosage");
  public static final QueryField FORM = field("UserMedication", "form");
  public static final QueryField START_DATE = field("UserMedication", "startDate");
  public static final QueryField END_DATE = field("UserMedication", "endDate");
  public static final QueryField DAYS_OF_WEEK = field("UserMedication", "daysOfWeek");
  public static final QueryField TIME = field("UserMedication", "time");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String userProfileID;
  private final @ModelField(targetType="ID", isRequired = true) String medicationID;
  private final @ModelField(targetType="String", isRequired = true) String medicationName;
  private final @ModelField(targetType="String", isRequired = true) String companyName;
  private final @ModelField(targetType="String", isRequired = true) String dosage;
  private final @ModelField(targetType="String", isRequired = true) String form;
  private final @ModelField(targetType="String") String startDate;
  private final @ModelField(targetType="String") String endDate;
  private final @ModelField(targetType="String") List<String> daysOfWeek;
  private final @ModelField(targetType="String") String time;
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
  
  public String getMedicationId() {
      return medicationID;
  }
  
  public String getMedicationName() {
      return medicationName;
  }
  
  public String getCompanyName() {
      return companyName;
  }
  
  public String getDosage() {
      return dosage;
  }
  
  public String getForm() {
      return form;
  }
  
  public String getStartDate() {
      return startDate;
  }
  
  public String getEndDate() {
      return endDate;
  }
  
  public List<String> getDaysOfWeek() {
      return daysOfWeek;
  }
  
  public String getTime() {
      return time;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private UserMedication(String id, String userProfileID, String medicationID, String medicationName, String companyName, String dosage, String form, String startDate, String endDate, List<String> daysOfWeek, String time) {
    this.id = id;
    this.userProfileID = userProfileID;
    this.medicationID = medicationID;
    this.medicationName = medicationName;
    this.companyName = companyName;
    this.dosage = dosage;
    this.form = form;
    this.startDate = startDate;
    this.endDate = endDate;
    this.daysOfWeek = daysOfWeek;
    this.time = time;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserMedication userMedication = (UserMedication) obj;
      return ObjectsCompat.equals(getId(), userMedication.getId()) &&
              ObjectsCompat.equals(getUserProfileId(), userMedication.getUserProfileId()) &&
              ObjectsCompat.equals(getMedicationId(), userMedication.getMedicationId()) &&
              ObjectsCompat.equals(getMedicationName(), userMedication.getMedicationName()) &&
              ObjectsCompat.equals(getCompanyName(), userMedication.getCompanyName()) &&
              ObjectsCompat.equals(getDosage(), userMedication.getDosage()) &&
              ObjectsCompat.equals(getForm(), userMedication.getForm()) &&
              ObjectsCompat.equals(getStartDate(), userMedication.getStartDate()) &&
              ObjectsCompat.equals(getEndDate(), userMedication.getEndDate()) &&
              ObjectsCompat.equals(getDaysOfWeek(), userMedication.getDaysOfWeek()) &&
              ObjectsCompat.equals(getTime(), userMedication.getTime()) &&
              ObjectsCompat.equals(getCreatedAt(), userMedication.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userMedication.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserProfileId())
      .append(getMedicationId())
      .append(getMedicationName())
      .append(getCompanyName())
      .append(getDosage())
      .append(getForm())
      .append(getStartDate())
      .append(getEndDate())
      .append(getDaysOfWeek())
      .append(getTime())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserMedication {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userProfileID=" + String.valueOf(getUserProfileId()) + ", ")
      .append("medicationID=" + String.valueOf(getMedicationId()) + ", ")
      .append("medicationName=" + String.valueOf(getMedicationName()) + ", ")
      .append("companyName=" + String.valueOf(getCompanyName()) + ", ")
      .append("dosage=" + String.valueOf(getDosage()) + ", ")
      .append("form=" + String.valueOf(getForm()) + ", ")
      .append("startDate=" + String.valueOf(getStartDate()) + ", ")
      .append("endDate=" + String.valueOf(getEndDate()) + ", ")
      .append("daysOfWeek=" + String.valueOf(getDaysOfWeek()) + ", ")
      .append("time=" + String.valueOf(getTime()) + ", ")
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
  public static UserMedication justId(String id) {
    return new UserMedication(
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userProfileID,
      medicationID,
      medicationName,
      companyName,
      dosage,
      form,
      startDate,
      endDate,
      daysOfWeek,
      time);
  }
  public interface UserProfileIdStep {
    MedicationIdStep userProfileId(String userProfileId);
  }
  

  public interface MedicationIdStep {
    MedicationNameStep medicationId(String medicationId);
  }
  

  public interface MedicationNameStep {
    CompanyNameStep medicationName(String medicationName);
  }
  

  public interface CompanyNameStep {
    DosageStep companyName(String companyName);
  }
  

  public interface DosageStep {
    FormStep dosage(String dosage);
  }
  

  public interface FormStep {
    BuildStep form(String form);
  }
  

  public interface BuildStep {
    UserMedication build();
    BuildStep id(String id);
    BuildStep startDate(String startDate);
    BuildStep endDate(String endDate);
    BuildStep daysOfWeek(List<String> daysOfWeek);
    BuildStep time(String time);
  }
  

  public static class Builder implements UserProfileIdStep, MedicationIdStep, MedicationNameStep, CompanyNameStep, DosageStep, FormStep, BuildStep {
    private String id;
    private String userProfileID;
    private String medicationID;
    private String medicationName;
    private String companyName;
    private String dosage;
    private String form;
    private String startDate;
    private String endDate;
    private List<String> daysOfWeek;
    private String time;
    public Builder() {
      
    }
    
    private Builder(String id, String userProfileID, String medicationID, String medicationName, String companyName, String dosage, String form, String startDate, String endDate, List<String> daysOfWeek, String time) {
      this.id = id;
      this.userProfileID = userProfileID;
      this.medicationID = medicationID;
      this.medicationName = medicationName;
      this.companyName = companyName;
      this.dosage = dosage;
      this.form = form;
      this.startDate = startDate;
      this.endDate = endDate;
      this.daysOfWeek = daysOfWeek;
      this.time = time;
    }
    
    @Override
     public UserMedication build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserMedication(
          id,
          userProfileID,
          medicationID,
          medicationName,
          companyName,
          dosage,
          form,
          startDate,
          endDate,
          daysOfWeek,
          time);
    }
    
    @Override
     public MedicationIdStep userProfileId(String userProfileId) {
        Objects.requireNonNull(userProfileId);
        this.userProfileID = userProfileId;
        return this;
    }
    
    @Override
     public MedicationNameStep medicationId(String medicationId) {
        Objects.requireNonNull(medicationId);
        this.medicationID = medicationId;
        return this;
    }
    
    @Override
     public CompanyNameStep medicationName(String medicationName) {
        Objects.requireNonNull(medicationName);
        this.medicationName = medicationName;
        return this;
    }
    
    @Override
     public DosageStep companyName(String companyName) {
        Objects.requireNonNull(companyName);
        this.companyName = companyName;
        return this;
    }
    
    @Override
     public FormStep dosage(String dosage) {
        Objects.requireNonNull(dosage);
        this.dosage = dosage;
        return this;
    }
    
    @Override
     public BuildStep form(String form) {
        Objects.requireNonNull(form);
        this.form = form;
        return this;
    }
    
    @Override
     public BuildStep startDate(String startDate) {
        this.startDate = startDate;
        return this;
    }
    
    @Override
     public BuildStep endDate(String endDate) {
        this.endDate = endDate;
        return this;
    }
    
    @Override
     public BuildStep daysOfWeek(List<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
        return this;
    }
    
    @Override
     public BuildStep time(String time) {
        this.time = time;
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
    private CopyOfBuilder(String id, String userProfileId, String medicationId, String medicationName, String companyName, String dosage, String form, String startDate, String endDate, List<String> daysOfWeek, String time) {
      super(id, userProfileID, medicationID, medicationName, companyName, dosage, form, startDate, endDate, daysOfWeek, time);
      Objects.requireNonNull(userProfileID);
      Objects.requireNonNull(medicationID);
      Objects.requireNonNull(medicationName);
      Objects.requireNonNull(companyName);
      Objects.requireNonNull(dosage);
      Objects.requireNonNull(form);
    }
    
    @Override
     public CopyOfBuilder userProfileId(String userProfileId) {
      return (CopyOfBuilder) super.userProfileId(userProfileId);
    }
    
    @Override
     public CopyOfBuilder medicationId(String medicationId) {
      return (CopyOfBuilder) super.medicationId(medicationId);
    }
    
    @Override
     public CopyOfBuilder medicationName(String medicationName) {
      return (CopyOfBuilder) super.medicationName(medicationName);
    }
    
    @Override
     public CopyOfBuilder companyName(String companyName) {
      return (CopyOfBuilder) super.companyName(companyName);
    }
    
    @Override
     public CopyOfBuilder dosage(String dosage) {
      return (CopyOfBuilder) super.dosage(dosage);
    }
    
    @Override
     public CopyOfBuilder form(String form) {
      return (CopyOfBuilder) super.form(form);
    }
    
    @Override
     public CopyOfBuilder startDate(String startDate) {
      return (CopyOfBuilder) super.startDate(startDate);
    }
    
    @Override
     public CopyOfBuilder endDate(String endDate) {
      return (CopyOfBuilder) super.endDate(endDate);
    }
    
    @Override
     public CopyOfBuilder daysOfWeek(List<String> daysOfWeek) {
      return (CopyOfBuilder) super.daysOfWeek(daysOfWeek);
    }
    
    @Override
     public CopyOfBuilder time(String time) {
      return (CopyOfBuilder) super.time(time);
    }
  }
  

  public static class UserMedicationIdentifier extends ModelIdentifier<UserMedication> {
    private static final long serialVersionUID = 1L;
    public UserMedicationIdentifier(String id) {
      super(id);
    }
  }
  
}

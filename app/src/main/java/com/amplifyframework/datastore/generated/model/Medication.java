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

/** This is an auto generated class representing the Medication type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Medications", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byDIN", fields = {"drug_identification_number"})
public final class Medication implements Model {
  public static final QueryField ID = field("Medication", "id");
  public static final QueryField DRUG_CODE = field("Medication", "drug_code");
  public static final QueryField DRUG_IDENTIFICATION_NUMBER = field("Medication", "drug_identification_number");
  public static final QueryField BRAND_NAME = field("Medication", "brand_name");
  public static final QueryField CLASS_NAME = field("Medication", "class_name");
  public static final QueryField COMPANY_NAME = field("Medication", "company_name");
  public static final QueryField LAST_UPDATE_DATE = field("Medication", "last_update_date");
  public static final QueryField NUMBER_OF_AIS = field("Medication", "number_of_ais");
  public static final QueryField AI_GROUP_NO = field("Medication", "ai_group_no");
  public static final QueryField DESCRIPTOR = field("Medication", "descriptor");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int", isRequired = true) Integer drug_code;
  private final @ModelField(targetType="String", isRequired = true) String drug_identification_number;
  private final @ModelField(targetType="String", isRequired = true) String brand_name;
  private final @ModelField(targetType="String") String class_name;
  private final @ModelField(targetType="String") String company_name;
  private final @ModelField(targetType="String") String last_update_date;
  private final @ModelField(targetType="String") String number_of_ais;
  private final @ModelField(targetType="String") String ai_group_no;
  private final @ModelField(targetType="String") String descriptor;
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
  
  public Integer getDrugCode() {
      return drug_code;
  }
  
  public String getDrugIdentificationNumber() {
      return drug_identification_number;
  }
  
  public String getBrandName() {
      return brand_name;
  }
  
  public String getClassName() {
      return class_name;
  }
  
  public String getCompanyName() {
      return company_name;
  }
  
  public String getLastUpdateDate() {
      return last_update_date;
  }
  
  public String getNumberOfAis() {
      return number_of_ais;
  }
  
  public String getAiGroupNo() {
      return ai_group_no;
  }
  
  public String getDescriptor() {
      return descriptor;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Medication(String id, Integer drug_code, String drug_identification_number, String brand_name, String class_name, String company_name, String last_update_date, String number_of_ais, String ai_group_no, String descriptor) {
    this.id = id;
    this.drug_code = drug_code;
    this.drug_identification_number = drug_identification_number;
    this.brand_name = brand_name;
    this.class_name = class_name;
    this.company_name = company_name;
    this.last_update_date = last_update_date;
    this.number_of_ais = number_of_ais;
    this.ai_group_no = ai_group_no;
    this.descriptor = descriptor;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Medication medication = (Medication) obj;
      return ObjectsCompat.equals(getId(), medication.getId()) &&
              ObjectsCompat.equals(getDrugCode(), medication.getDrugCode()) &&
              ObjectsCompat.equals(getDrugIdentificationNumber(), medication.getDrugIdentificationNumber()) &&
              ObjectsCompat.equals(getBrandName(), medication.getBrandName()) &&
              ObjectsCompat.equals(getClassName(), medication.getClassName()) &&
              ObjectsCompat.equals(getCompanyName(), medication.getCompanyName()) &&
              ObjectsCompat.equals(getLastUpdateDate(), medication.getLastUpdateDate()) &&
              ObjectsCompat.equals(getNumberOfAis(), medication.getNumberOfAis()) &&
              ObjectsCompat.equals(getAiGroupNo(), medication.getAiGroupNo()) &&
              ObjectsCompat.equals(getDescriptor(), medication.getDescriptor()) &&
              ObjectsCompat.equals(getCreatedAt(), medication.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), medication.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDrugCode())
      .append(getDrugIdentificationNumber())
      .append(getBrandName())
      .append(getClassName())
      .append(getCompanyName())
      .append(getLastUpdateDate())
      .append(getNumberOfAis())
      .append(getAiGroupNo())
      .append(getDescriptor())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Medication {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("drug_code=" + String.valueOf(getDrugCode()) + ", ")
      .append("drug_identification_number=" + String.valueOf(getDrugIdentificationNumber()) + ", ")
      .append("brand_name=" + String.valueOf(getBrandName()) + ", ")
      .append("class_name=" + String.valueOf(getClassName()) + ", ")
      .append("company_name=" + String.valueOf(getCompanyName()) + ", ")
      .append("last_update_date=" + String.valueOf(getLastUpdateDate()) + ", ")
      .append("number_of_ais=" + String.valueOf(getNumberOfAis()) + ", ")
      .append("ai_group_no=" + String.valueOf(getAiGroupNo()) + ", ")
      .append("descriptor=" + String.valueOf(getDescriptor()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static DrugCodeStep builder() {
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
  public static Medication justId(String id) {
    return new Medication(
      id,
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
      drug_code,
      drug_identification_number,
      brand_name,
      class_name,
      company_name,
      last_update_date,
      number_of_ais,
      ai_group_no,
      descriptor);
  }
  public interface DrugCodeStep {
    DrugIdentificationNumberStep drugCode(Integer drugCode);
  }
  

  public interface DrugIdentificationNumberStep {
    BrandNameStep drugIdentificationNumber(String drugIdentificationNumber);
  }
  

  public interface BrandNameStep {
    BuildStep brandName(String brandName);
  }
  

  public interface BuildStep {
    Medication build();
    BuildStep id(String id);
    BuildStep className(String className);
    BuildStep companyName(String companyName);
    BuildStep lastUpdateDate(String lastUpdateDate);
    BuildStep numberOfAis(String numberOfAis);
    BuildStep aiGroupNo(String aiGroupNo);
    BuildStep descriptor(String descriptor);
  }
  

  public static class Builder implements DrugCodeStep, DrugIdentificationNumberStep, BrandNameStep, BuildStep {
    private String id;
    private Integer drug_code;
    private String drug_identification_number;
    private String brand_name;
    private String class_name;
    private String company_name;
    private String last_update_date;
    private String number_of_ais;
    private String ai_group_no;
    private String descriptor;
    public Builder() {
      
    }
    
    private Builder(String id, Integer drug_code, String drug_identification_number, String brand_name, String class_name, String company_name, String last_update_date, String number_of_ais, String ai_group_no, String descriptor) {
      this.id = id;
      this.drug_code = drug_code;
      this.drug_identification_number = drug_identification_number;
      this.brand_name = brand_name;
      this.class_name = class_name;
      this.company_name = company_name;
      this.last_update_date = last_update_date;
      this.number_of_ais = number_of_ais;
      this.ai_group_no = ai_group_no;
      this.descriptor = descriptor;
    }
    
    @Override
     public Medication build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Medication(
          id,
          drug_code,
          drug_identification_number,
          brand_name,
          class_name,
          company_name,
          last_update_date,
          number_of_ais,
          ai_group_no,
          descriptor);
    }
    
    @Override
     public DrugIdentificationNumberStep drugCode(Integer drugCode) {
        Objects.requireNonNull(drugCode);
        this.drug_code = drugCode;
        return this;
    }
    
    @Override
     public BrandNameStep drugIdentificationNumber(String drugIdentificationNumber) {
        Objects.requireNonNull(drugIdentificationNumber);
        this.drug_identification_number = drugIdentificationNumber;
        return this;
    }
    
    @Override
     public BuildStep brandName(String brandName) {
        Objects.requireNonNull(brandName);
        this.brand_name = brandName;
        return this;
    }
    
    @Override
     public BuildStep className(String className) {
        this.class_name = className;
        return this;
    }
    
    @Override
     public BuildStep companyName(String companyName) {
        this.company_name = companyName;
        return this;
    }
    
    @Override
     public BuildStep lastUpdateDate(String lastUpdateDate) {
        this.last_update_date = lastUpdateDate;
        return this;
    }
    
    @Override
     public BuildStep numberOfAis(String numberOfAis) {
        this.number_of_ais = numberOfAis;
        return this;
    }
    
    @Override
     public BuildStep aiGroupNo(String aiGroupNo) {
        this.ai_group_no = aiGroupNo;
        return this;
    }
    
    @Override
     public BuildStep descriptor(String descriptor) {
        this.descriptor = descriptor;
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
    private CopyOfBuilder(String id, Integer drugCode, String drugIdentificationNumber, String brandName, String className, String companyName, String lastUpdateDate, String numberOfAis, String aiGroupNo, String descriptor) {
      super(id, drug_code, drug_identification_number, brand_name, class_name, company_name, last_update_date, number_of_ais, ai_group_no, descriptor);
      Objects.requireNonNull(drug_code);
      Objects.requireNonNull(drug_identification_number);
      Objects.requireNonNull(brand_name);
    }
    
    @Override
     public CopyOfBuilder drugCode(Integer drugCode) {
      return (CopyOfBuilder) super.drugCode(drugCode);
    }
    
    @Override
     public CopyOfBuilder drugIdentificationNumber(String drugIdentificationNumber) {
      return (CopyOfBuilder) super.drugIdentificationNumber(drugIdentificationNumber);
    }
    
    @Override
     public CopyOfBuilder brandName(String brandName) {
      return (CopyOfBuilder) super.brandName(brandName);
    }
    
    @Override
     public CopyOfBuilder className(String className) {
      return (CopyOfBuilder) super.className(className);
    }
    
    @Override
     public CopyOfBuilder companyName(String companyName) {
      return (CopyOfBuilder) super.companyName(companyName);
    }
    
    @Override
     public CopyOfBuilder lastUpdateDate(String lastUpdateDate) {
      return (CopyOfBuilder) super.lastUpdateDate(lastUpdateDate);
    }
    
    @Override
     public CopyOfBuilder numberOfAis(String numberOfAis) {
      return (CopyOfBuilder) super.numberOfAis(numberOfAis);
    }
    
    @Override
     public CopyOfBuilder aiGroupNo(String aiGroupNo) {
      return (CopyOfBuilder) super.aiGroupNo(aiGroupNo);
    }
    
    @Override
     public CopyOfBuilder descriptor(String descriptor) {
      return (CopyOfBuilder) super.descriptor(descriptor);
    }
  }
  

  public static class MedicationIdentifier extends ModelIdentifier<Medication> {
    private static final long serialVersionUID = 1L;
    public MedicationIdentifier(String id) {
      super(id);
    }
  }
  
}

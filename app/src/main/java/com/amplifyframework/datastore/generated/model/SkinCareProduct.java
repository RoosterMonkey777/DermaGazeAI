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

/** This is an auto generated class representing the SkinCareProduct type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "SkinCareProducts", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class SkinCareProduct implements Model {
  public static final QueryField ID = field("SkinCareProduct", "id");
  public static final QueryField PRODUCT_NAME = field("SkinCareProduct", "productName");
  public static final QueryField BRAND = field("SkinCareProduct", "brand");
  public static final QueryField DESCRIPTION = field("SkinCareProduct", "description");
  public static final QueryField LABELS = field("SkinCareProduct", "labels");
  public static final QueryField NOTABLE_EFFECTS = field("SkinCareProduct", "notableEffects");
  public static final QueryField PICTURE_SRC = field("SkinCareProduct", "pictureSrc");
  public static final QueryField PRICE = field("SkinCareProduct", "price");
  public static final QueryField PRODUCT_HREF = field("SkinCareProduct", "productHref");
  public static final QueryField PRODUCT_TYPE = field("SkinCareProduct", "productType");
  public static final QueryField SKINTYPE = field("SkinCareProduct", "skintype");
  public static final QueryField SKIN_TYPES = field("SkinCareProduct", "skinTypes");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String productName;
  private final @ModelField(targetType="String") String brand;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="Int") Integer labels;
  private final @ModelField(targetType="String") List<String> notableEffects;
  private final @ModelField(targetType="String") String pictureSrc;
  private final @ModelField(targetType="String") String price;
  private final @ModelField(targetType="String") String productHref;
  private final @ModelField(targetType="String") String productType;
  private final @ModelField(targetType="String") List<String> skintype;
  private final @ModelField(targetType="String") List<String> skinTypes;
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
  
  public String getProductName() {
      return productName;
  }
  
  public String getBrand() {
      return brand;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Integer getLabels() {
      return labels;
  }
  
  public List<String> getNotableEffects() {
      return notableEffects;
  }
  
  public String getPictureSrc() {
      return pictureSrc;
  }
  
  public String getPrice() {
      return price;
  }
  
  public String getProductHref() {
      return productHref;
  }
  
  public String getProductType() {
      return productType;
  }
  
  public List<String> getSkintype() {
      return skintype;
  }
  
  public List<String> getSkinTypes() {
      return skinTypes;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private SkinCareProduct(String id, String productName, String brand, String description, Integer labels, List<String> notableEffects, String pictureSrc, String price, String productHref, String productType, List<String> skintype, List<String> skinTypes) {
    this.id = id;
    this.productName = productName;
    this.brand = brand;
    this.description = description;
    this.labels = labels;
    this.notableEffects = notableEffects;
    this.pictureSrc = pictureSrc;
    this.price = price;
    this.productHref = productHref;
    this.productType = productType;
    this.skintype = skintype;
    this.skinTypes = skinTypes;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      SkinCareProduct skinCareProduct = (SkinCareProduct) obj;
      return ObjectsCompat.equals(getId(), skinCareProduct.getId()) &&
              ObjectsCompat.equals(getProductName(), skinCareProduct.getProductName()) &&
              ObjectsCompat.equals(getBrand(), skinCareProduct.getBrand()) &&
              ObjectsCompat.equals(getDescription(), skinCareProduct.getDescription()) &&
              ObjectsCompat.equals(getLabels(), skinCareProduct.getLabels()) &&
              ObjectsCompat.equals(getNotableEffects(), skinCareProduct.getNotableEffects()) &&
              ObjectsCompat.equals(getPictureSrc(), skinCareProduct.getPictureSrc()) &&
              ObjectsCompat.equals(getPrice(), skinCareProduct.getPrice()) &&
              ObjectsCompat.equals(getProductHref(), skinCareProduct.getProductHref()) &&
              ObjectsCompat.equals(getProductType(), skinCareProduct.getProductType()) &&
              ObjectsCompat.equals(getSkintype(), skinCareProduct.getSkintype()) &&
              ObjectsCompat.equals(getSkinTypes(), skinCareProduct.getSkinTypes()) &&
              ObjectsCompat.equals(getCreatedAt(), skinCareProduct.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), skinCareProduct.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getProductName())
      .append(getBrand())
      .append(getDescription())
      .append(getLabels())
      .append(getNotableEffects())
      .append(getPictureSrc())
      .append(getPrice())
      .append(getProductHref())
      .append(getProductType())
      .append(getSkintype())
      .append(getSkinTypes())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("SkinCareProduct {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("productName=" + String.valueOf(getProductName()) + ", ")
      .append("brand=" + String.valueOf(getBrand()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("labels=" + String.valueOf(getLabels()) + ", ")
      .append("notableEffects=" + String.valueOf(getNotableEffects()) + ", ")
      .append("pictureSrc=" + String.valueOf(getPictureSrc()) + ", ")
      .append("price=" + String.valueOf(getPrice()) + ", ")
      .append("productHref=" + String.valueOf(getProductHref()) + ", ")
      .append("productType=" + String.valueOf(getProductType()) + ", ")
      .append("skintype=" + String.valueOf(getSkintype()) + ", ")
      .append("skinTypes=" + String.valueOf(getSkinTypes()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ProductNameStep builder() {
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
  public static SkinCareProduct justId(String id) {
    return new SkinCareProduct(
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
      productName,
      brand,
      description,
      labels,
      notableEffects,
      pictureSrc,
      price,
      productHref,
      productType,
      skintype,
      skinTypes);
  }
  public interface ProductNameStep {
    BuildStep productName(String productName);
  }
  

  public interface BuildStep {
    SkinCareProduct build();
    BuildStep id(String id);
    BuildStep brand(String brand);
    BuildStep description(String description);
    BuildStep labels(Integer labels);
    BuildStep notableEffects(List<String> notableEffects);
    BuildStep pictureSrc(String pictureSrc);
    BuildStep price(String price);
    BuildStep productHref(String productHref);
    BuildStep productType(String productType);
    BuildStep skintype(List<String> skintype);
    BuildStep skinTypes(List<String> skinTypes);
  }
  

  public static class Builder implements ProductNameStep, BuildStep {
    private String id;
    private String productName;
    private String brand;
    private String description;
    private Integer labels;
    private List<String> notableEffects;
    private String pictureSrc;
    private String price;
    private String productHref;
    private String productType;
    private List<String> skintype;
    private List<String> skinTypes;
    public Builder() {
      
    }
    
    private Builder(String id, String productName, String brand, String description, Integer labels, List<String> notableEffects, String pictureSrc, String price, String productHref, String productType, List<String> skintype, List<String> skinTypes) {
      this.id = id;
      this.productName = productName;
      this.brand = brand;
      this.description = description;
      this.labels = labels;
      this.notableEffects = notableEffects;
      this.pictureSrc = pictureSrc;
      this.price = price;
      this.productHref = productHref;
      this.productType = productType;
      this.skintype = skintype;
      this.skinTypes = skinTypes;
    }
    
    @Override
     public SkinCareProduct build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new SkinCareProduct(
          id,
          productName,
          brand,
          description,
          labels,
          notableEffects,
          pictureSrc,
          price,
          productHref,
          productType,
          skintype,
          skinTypes);
    }
    
    @Override
     public BuildStep productName(String productName) {
        Objects.requireNonNull(productName);
        this.productName = productName;
        return this;
    }
    
    @Override
     public BuildStep brand(String brand) {
        this.brand = brand;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep labels(Integer labels) {
        this.labels = labels;
        return this;
    }
    
    @Override
     public BuildStep notableEffects(List<String> notableEffects) {
        this.notableEffects = notableEffects;
        return this;
    }
    
    @Override
     public BuildStep pictureSrc(String pictureSrc) {
        this.pictureSrc = pictureSrc;
        return this;
    }
    
    @Override
     public BuildStep price(String price) {
        this.price = price;
        return this;
    }
    
    @Override
     public BuildStep productHref(String productHref) {
        this.productHref = productHref;
        return this;
    }
    
    @Override
     public BuildStep productType(String productType) {
        this.productType = productType;
        return this;
    }
    
    @Override
     public BuildStep skintype(List<String> skintype) {
        this.skintype = skintype;
        return this;
    }
    
    @Override
     public BuildStep skinTypes(List<String> skinTypes) {
        this.skinTypes = skinTypes;
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
    private CopyOfBuilder(String id, String productName, String brand, String description, Integer labels, List<String> notableEffects, String pictureSrc, String price, String productHref, String productType, List<String> skintype, List<String> skinTypes) {
      super(id, productName, brand, description, labels, notableEffects, pictureSrc, price, productHref, productType, skintype, skinTypes);
      Objects.requireNonNull(productName);
    }
    
    @Override
     public CopyOfBuilder productName(String productName) {
      return (CopyOfBuilder) super.productName(productName);
    }
    
    @Override
     public CopyOfBuilder brand(String brand) {
      return (CopyOfBuilder) super.brand(brand);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder labels(Integer labels) {
      return (CopyOfBuilder) super.labels(labels);
    }
    
    @Override
     public CopyOfBuilder notableEffects(List<String> notableEffects) {
      return (CopyOfBuilder) super.notableEffects(notableEffects);
    }
    
    @Override
     public CopyOfBuilder pictureSrc(String pictureSrc) {
      return (CopyOfBuilder) super.pictureSrc(pictureSrc);
    }
    
    @Override
     public CopyOfBuilder price(String price) {
      return (CopyOfBuilder) super.price(price);
    }
    
    @Override
     public CopyOfBuilder productHref(String productHref) {
      return (CopyOfBuilder) super.productHref(productHref);
    }
    
    @Override
     public CopyOfBuilder productType(String productType) {
      return (CopyOfBuilder) super.productType(productType);
    }
    
    @Override
     public CopyOfBuilder skintype(List<String> skintype) {
      return (CopyOfBuilder) super.skintype(skintype);
    }
    
    @Override
     public CopyOfBuilder skinTypes(List<String> skinTypes) {
      return (CopyOfBuilder) super.skinTypes(skinTypes);
    }
  }
  

  public static class SkinCareProductIdentifier extends ModelIdentifier<SkinCareProduct> {
    private static final long serialVersionUID = 1L;
    public SkinCareProductIdentifier(String id) {
      super(id);
    }
  }
  
}

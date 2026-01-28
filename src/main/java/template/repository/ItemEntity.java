package template.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// VIOLATION: @Data generates public setters - entities should protect invariants
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class ItemEntity {

    @Id
    private Long id;

    private String name;

}

// FIX: Replace @Data with @Getter + @Setter(AccessLevel.PROTECTED)
 @Entity
 @Getter
 @Setter(AccessLevel.PROTECTED)
 @Builder
 @NoArgsConstructor(access = AccessLevel.PROTECTED)
 @AllArgsConstructor
 @Table(name = "item")
 public class ItemEntity {

     @Id
     private Long id;

     private String name;

     // Domain method to update name with validation
     public void updateName(String newName) {
         if (newName == null || newName.isBlank()) {
             throw new IllegalArgumentException("Name cannot be empty");
         }
         this.name = newName;
     }
 }

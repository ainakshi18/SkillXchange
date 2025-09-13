//package com.SkillExchange.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import org.springframework.data.annotation.Id;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Skill {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long skillId;
//
//    @Column(unique = true, nullable = false)
//    private String skillName;
//
//    private String category;
//    private String description;
//    private boolean isTrending = false;
//}
package com.SkillExchange.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "skills") // Name of Mongo collection
public class Skill {

    @Id
    private String skillId; // MongoDB _id will be stored here

    private String skillName;
    private String category;
    private String description;
    private boolean isTrending = false;
}

package com.SkillExchange.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    private String id;  // MongoDB generates ObjectId automatically

    private String skillName;   // unique skill name
    private String category;    // Tech, Art, Fitness, etc.
    private String description;
    private boolean isTrending = false;
}

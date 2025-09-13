package com.SkillExchange.controller;

import com.SkillExchange.model.Skill;
import com.SkillExchange.service.SkillService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    // ✅ Create
    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        return ResponseEntity.ok(skillService.createSkill(skill));
    }

    // ✅ Get All
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable String id) {
        return skillService.getSkillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update
    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable String id, @RequestBody Skill updatedSkill) {
        Skill skill = skillService.updateSkill(id, updatedSkill);
        return skill != null ? ResponseEntity.ok(skill) : ResponseEntity.notFound().build();
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable String id) {
        boolean deleted = skillService.deleteSkill(id);
        if (deleted) {
            return ResponseEntity.ok("Skill deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Skill not found!");
    }
}

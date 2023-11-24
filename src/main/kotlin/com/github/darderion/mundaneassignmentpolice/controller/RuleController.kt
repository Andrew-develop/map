package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.services.RuleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rule")
class RuleController (
        private val ruleService: RuleService
) {
    @GetMapping("/all")
    fun getAllRules(): ResponseEntity<*> {
        return ResponseEntity.ok(ruleService.getAll())
    }
}
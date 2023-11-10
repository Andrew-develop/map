package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetRequest
import com.github.darderion.mundaneassignmentpolice.services.PresetService
import com.github.darderion.mundaneassignmentpolice.services.RuleService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/preset")
class PresetController (
    private val presetService: PresetService,
    private val ruleService: RuleService,
    private val userService: UserService
) {
    @GetMapping("/all")
    fun getUserPresets(principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return presetService.getUserPresets(user)
    }

    @PostMapping("/create")
    fun createPreset(@RequestBody request: PresetRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        val selectedRules = ruleService.getRuleBy(request.rules)
        presetService.createNewPreset(request.name, user, selectedRules)
        return ResponseEntity.ok("Preset has been added")
    }

    @PutMapping("/update/{id}")
    fun updatePreset(@PathVariable id: Long, @RequestBody request: PresetRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        val selectedRules = ruleService.getRuleBy(request.rules)
        presetService.updatePreset(id, request.name, user, selectedRules)
        return ResponseEntity.ok("Preset has been updated")
    }

    @DeleteMapping("/delete/{id}")
    fun deletePreset(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        presetService.deletePresetBy(id, user)
        return ResponseEntity.ok("Preset has been deleted")
    }
}
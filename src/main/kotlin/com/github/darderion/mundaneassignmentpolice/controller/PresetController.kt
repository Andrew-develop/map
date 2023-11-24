package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetRequest
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetResponse
import com.github.darderion.mundaneassignmentpolice.services.PresetService
import com.github.darderion.mundaneassignmentpolice.services.RuleService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/preset")
class PresetController (
    private val presetService: PresetService,
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
        return presetService.createPreset(request, user)
    }

    @PutMapping("/update/{id}")
    fun updatePreset(@PathVariable id: Long, @RequestBody request: PresetRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return presetService.updatePreset(request, id, user)
    }

    @DeleteMapping("/delete/{id}")
    fun deletePreset(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return presetService.deletePresetBy(id, user)
    }
}
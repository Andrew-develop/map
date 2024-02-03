package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetRequest
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.services.PresetService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.HttpStatus
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
        return ResponseEntity.ok(presetService.getUserPresets(user.id))
    }

    @PostMapping("/create")
    fun createPreset(@RequestBody request: PresetRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return ResponseEntity.ok(presetService.createPreset(request, user))
    }

    @PutMapping("/update/{id}")
    fun updatePreset(@PathVariable id: Long, @RequestBody request: PresetRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        val preset = presetService.updatePreset(request, id, user)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have preset"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(preset)
    }

    @DeleteMapping("/delete/{id}")
    fun deletePreset(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        presetService.deletePresetBy(id, user)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User isn't owner of preset"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(HttpStatus.OK)
    }
}
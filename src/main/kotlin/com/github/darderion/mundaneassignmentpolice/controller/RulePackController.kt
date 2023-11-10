package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.RulePackDto
import com.github.darderion.mundaneassignmentpolice.services.RulePackService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/rulepack")
class RulePackController (
    private val rulePackService: RulePackService,
    private val userService: UserService
) {
    @GetMapping("/current")
    fun getCurrentRulePacks(principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return ResponseEntity.ok(user.rulePacks.map {
            RulePackDto(it)
        })
    }

    @GetMapping("/all")
    fun getAllRulePacks(principal: Principal): ResponseEntity<*> {
        return ResponseEntity.ok(rulePackService.getAllRulePacks().map {
            RulePackDto(it)
        })
    }

    @PostMapping("/add/{id}")
    fun addRulePack(@PathVariable id: Int, principal: Principal): ResponseEntity<*> {
        val rulePack = rulePackService.getRulePackBy(id)
        return userService.addRulePack(rulePack, principal.name)
    }
}
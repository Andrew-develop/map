package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.user.UserRequest
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController (
        private val userService: UserService
) {
    @GetMapping("/info")
    fun getUserInfo(principal: Principal): ResponseEntity<*> {
        return ResponseEntity.ok(userService.findByEmail(principal.name))
    }

    @PutMapping("/update")
    fun updateUser(@RequestBody request: UserRequest, principal: Principal): ResponseEntity<*> {
        return userService.updateUser(request, principal.name)
    }
}

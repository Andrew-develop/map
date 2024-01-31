package com.github.darderion.mundaneassignmentpolice.services

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class EmailService (
        private val mailSender: JavaMailSender
) {
    fun sendCode(code: Int, address: String, reason: String) {
        val message = SimpleMailMessage()
        message.setTo(address)
        message.setSubject("Confirm code")
        message.setText("Code for $reason: $code")
        mailSender.send(message)
    }
}
package fr.agaspardcilia.boilerplate.utils

import fr.agaspardcilia.boilerplate.users.MailAlreadyInUserException
import fr.agaspardcilia.boilerplate.users.UserNotActivatedException
import fr.agaspardcilia.boilerplate.users.UserNotFoundException
import fr.agaspardcilia.boilerplate.utils.problem.Problem
import fr.agaspardcilia.boilerplate.utils.problem.ProblemTypes
import fr.agaspardcilia.boilerplate.utils.problem.toProblem
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AppControllerAdvice {

    @ExceptionHandler
    fun handleBadCredentialException(ex: BadCredentialsException): ResponseEntity<Problem> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ProblemTypes.BAD_CREDENTIALS.toProblem())
    }

    @ExceptionHandler
    fun handleUserNotFoundException(ex: UserNotFoundException): ResponseEntity<Problem> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ProblemTypes.USER_NOT_FOUND.toProblem())
    }

    @ExceptionHandler
    fun handleUserNotActivatedException(ex: UserNotActivatedException): ResponseEntity<Problem> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ProblemTypes.USER_NOT_ACTIVATED.toProblem())
    }

    @ExceptionHandler
    fun handleMailAlreadyInUserException(ex: MailAlreadyInUserException): ResponseEntity<Problem> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ProblemTypes.MAIL_ALREADY_IN_USE.toProblem())
    }
}

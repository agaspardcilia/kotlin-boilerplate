package fr.agaspardcilia.boilerplate.utils

import fr.agaspardcilia.boilerplate.users.InvalidTokenException
import fr.agaspardcilia.boilerplate.users.MailAlreadyInUserException
import fr.agaspardcilia.boilerplate.users.UserNotActivatedException
import fr.agaspardcilia.boilerplate.users.UserNotFoundException
import fr.agaspardcilia.boilerplate.utils.problem.Problem
import fr.agaspardcilia.boilerplate.utils.problem.ProblemTypes
import fr.agaspardcilia.boilerplate.utils.problem.toProblem
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class AppControllerAdvice {

    @ExceptionHandler
    fun handleInvalidBeanException(ex: MethodArgumentNotValidException): ResponseEntity<Problem> {
        val fieldName = ex.parameter.parameterName
        val errors = ex.bindingResult.allErrors.map {
            Pair(
                "$fieldName.${(it as FieldError).field}",
                it.defaultMessage
            )
        }.toMap()
        val problem = ProblemTypes.INVALID_PARAMETER.toProblem(errors)

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(problem)
    }

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

    @ExceptionHandler
    fun handleInvalidTokenException(ex: InvalidTokenException): ResponseEntity<Problem> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ProblemTypes.INVALID_TOKEN.toProblem())
    }
}

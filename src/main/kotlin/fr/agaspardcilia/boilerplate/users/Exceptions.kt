package fr.agaspardcilia.boilerplate.users

class MailAlreadyInUserException: Exception("This mail is already in use by another user!")

class UserNotFoundException: Exception()

class UserNotActivatedException(userMail: String? = "ukn"): Exception("User $userMail is not activated!")

class AuthenticationException(message: String): Exception(message)

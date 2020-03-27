package fr.agaspardcilia.boilerplate.utils.problem

enum class ProblemTypes(
    val type: String,
    val title: String
) {
    FORBIDDEN_ACCESS_EXCEPTION("forbidden-acess", "You don't have the required authorization elevation to perform this!"),
    BAD_CREDENTIALS("bad-credentials", "Wrong credentials!"),
    USER_NOT_FOUND("user-not-found", "User can't be found!"),
    MAIL_ALREADY_IN_USE("mail-already-in-use", "This mail is already in use!"),
    USER_NOT_ACTIVATED("user-not-activated", "User hasn't been activated!")
}

fun ProblemTypes.toProblem() = Problem(type = this.type, title = this.title)

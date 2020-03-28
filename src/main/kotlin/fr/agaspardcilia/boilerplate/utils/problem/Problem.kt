package fr.agaspardcilia.boilerplate.utils.problem

class Problem(
    val type: String,
    val title: String,
    val errors: Map<String, String?>? = null,
    val details: String? = null
)

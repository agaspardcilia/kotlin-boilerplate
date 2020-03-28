package fr.agaspardcilia.boilerplate.config.properties

class Mail {
    val server: Server = Server()

    class Server {
        lateinit var url: String
        lateinit var mailAddress: String
    }
}

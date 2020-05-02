package Model

class Message{
     lateinit var sender: String
     lateinit var receiver: String
     lateinit var msg: String

    constructor(sender: String, receiver: String, msg: String) {
        this.sender = sender
        this.receiver = receiver
        this.msg = msg
    }

    constructor()
}


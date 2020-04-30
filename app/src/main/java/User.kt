class User {
    lateinit var id:String
    lateinit var username:String
    lateinit var imageURL:String

    constructor(id: String, username: String, imageURL: String) {
        this.id = id
        this.username = username
        this.imageURL = imageURL
    }

    constructor()
}
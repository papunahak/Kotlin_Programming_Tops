package Serialization


data class User(val id: Int, val name: String, val emailid: String)

class UserSerializer {

    fun serialize(user: User): String {
        return "${user.id}:${user.name}:${user.emailid}"
    }

    fun deserialize(data: String): User {
        val parts = data.split(":")
        require(parts.size == 3) { "Invalid data format" }
        val (id, name, emailid) = parts
        return User(id.toInt(), name, emailid)
    }
}

fun main() {
    val user = User(101, "Adhyansh Shashamal", "adhyansh@123gmail.com")
    val serializer = UserSerializer()


    val serializedData = serializer.serialize(user)
    println("Serialized Data: $serializedData")

    val deserializedUser = serializer.deserialize(serializedData)
    println("Deserialized User: $deserializedUser")
}

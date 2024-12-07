package Collection


open class Person(val name:String, val age:String)

class Students(name:String, age:String, val grade:Int):Person(name,age){
fun printDetails(){
    println("Student name:$name, age:$age, grade:$grade")
}
}

class Teacher(name:String,age:String,val Subject:String):Person(name, age){
    fun printDetails(){
        println("Teacher name:$name, age:$age, subject:$Subject")
    }
}

object School{
   private var totalPeople: Int = 0

    fun addPerson(){
        totalPeople++
    }
    fun getTotalPeople():Int{
        return totalPeople
    }
}



fun main() {

    var s1= Students("PAPU NAHAK", "24", 10)
    var s2=Students("SMIT", "23", 9)

    var t1=Teacher("Prakruti ma'am", "27", "Kotlin")

    School.addPerson()
    School.addPerson()
    School.addPerson()

    s1.printDetails()
    s2.printDetails()

    t1.printDetails()

    println("total people in school ${School.getTotalPeople()}")

}
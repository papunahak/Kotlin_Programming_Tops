package MultiThreading

class Senders{
    fun send(msg :String){
        println("Message Sending $msg")
        Thread.sleep(10000)
        println("Message sended $msg")
    }
}
class RunnableSend(var sender: Senders, var msg:String):Runnable{
        override fun run() {
        synchronized(sender){
            sender.send(msg)
        }
    }
}


fun main() {

   var sender=Senders()

    var r1=RunnableSend(sender,"Hiii")
    var r2=RunnableSend(sender,"Byyy")

    var t1=Thread(r1)
    var t2=Thread(r2)

    t1.start()
    t2.start()


}
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.*

fun main() {
    val string = "abc"

    //----SOFT BALL----

    //Most popular word
    val word = string.getMostPopularWord();
    println(word)

    //Reverse string
    val reverse = string.reverseString()
    println(reverse)

    //Bonus: Question mark problem
    println(checkSumTarget(10,"?asdfas4?5?3???6")) //F
    println(checkSumTarget(10,"4??2?6")) //T
    println(checkSumTarget(10,"????43???6")) //T
    println(checkSumTarget(10,"43??asdf?6")) //T
    println(checkSumTarget(10,"4?3abc???6")) //F
    println(checkSumTarget(10,"43?abc6?5???4")) //F
    println(checkSumTarget(10,"4??3dw3ds3?6")) //T
    println(checkSumTarget(10,"asdf22sjfq01jfskdn19????????????????4???6")) // T
    println(checkSumTarget(10,"???????awc4??????2s???wq38")) // T

    //Bonus: Largest word in a string
    val para = "I think one of the hardest things in the world for people to do is to love themselves. If you loved yourself you would take better care of yourself, and respect the things around you because you respect yourself. Even the condition of your home whether clean or dirty can reveal how much you love yourself. Just don't expect someone else to give what you neglect to give yourself which is love. That's why relationships don't work out so well most times. I find it is accommodating to me."
    println(para.getLargestWord())



    //----ALGORITHM----

    //Physically closest
    val foundPerson = getPersonClosestTo(Person("Smith", "", Location(10.23092309f, 101.91091209f) , ""))
    println(foundPerson)

    //Bonus: Group People
    val groups = groupPeople(arrayOf(
        Person("John", "", Location(10.19191902f, 101.43092131f), "A street, Los Angeles, CA 44502"),
        Person("John", "", Location(10.19191232f, 101.43094302f), "C street, San Jose, CA 44502"),
        Person("John", "", Location(10.19191111f, 101.43091231f), "B street, Columbus, OH 44502"),
        Person("John", "", Location(10.19191223f, 101.43129742f), "E street, Los Angeles, CA 44502")
    ))
    for (group in groups){
        for(person in group){
            println("${person.address}")
        }
        println("----------")
    }

}


// Time: O(n), Space: O(n)
fun String.getMostPopularWord(): String{
    val listOfWords = this.trim().splitToSequence(' ')
        .filter { it.isNotEmpty() }
        .toList()

    if(listOfWords.isEmpty()) return "";

    val hashMap = HashMap<String, Int>()
    var max = 0;
    var mostPopularWord = listOfWords[0];

    for(word in listOfWords){
        if(hashMap[word] != null){
            val currentNumb = hashMap[word]!!
            if(currentNumb > max){
                max = currentNumb
                mostPopularWord = word
            }
            hashMap[word] = currentNumb + 1
        } else {
            hashMap.put(word, 1)
        }
    }

    return mostPopularWord;
}

// Time: O(n), Space: O(n)
fun String.reverseString(): String{
    var reverseString = ""
    for(i in this.length - 1 downTo 0){
        reverseString += this[i]
    }
    return reverseString
}

fun isInt(string: String): Boolean{
    val value = string.toIntOrNull()
    return value != null
}


fun checkSumTarget(target: Int, str: String): Boolean{
    val queue: Queue<Int> = LinkedList<Int>()
    var questionMarkCounter = 0
    var questionMarkCounterFromLastNumb = 0

    for(char in str){
        if(isInt(char + "")){
            val numb = (char + "").toInt()
            if(questionMarkCounter == 3){
                queue.peek()?.let {head ->
                    if(numb + head == target){
                        return true
                    }
                }
            }
            if(queue.isEmpty()) questionMarkCounter = 0
            queue.add(numb)
            questionMarkCounterFromLastNumb = 0
        } else if(char == '?') {
            if(questionMarkCounter >= 3){
                queue.poll()
                questionMarkCounter = questionMarkCounterFromLastNumb
            }
            if(queue.isNotEmpty()) {
                questionMarkCounter++
                questionMarkCounterFromLastNumb++
            }
        }
    }


    return false
}


// Time: O(n), Space: O(n)
fun String.getLargestWord(): String{
    val listOfWords = this.trim().splitToSequence(' ')
        .filter { it.isNotEmpty() }
        .toList()

    println("$listOfWords")

    var largest = 0
    var largestWord = ""

    for(word in listOfWords){
        if (word.length > largest){
            largest = word.length
            largestWord = word
        }
    }
    return largestWord
}


class Location(val lat: Float, val lng: Float)
class Person (val name: String,
              val birthDay: String,
              val location: Location,
              val address: String)



fun getPersonClosestTo(mainPerson: Person): Person?{

    val listOfPeople = getListOfPeople()

    var closestDistance: Double = Double.POSITIVE_INFINITY
    var closestPerson: Person? = null

    for(person in listOfPeople){
        val distance = differenceBetween(mainPerson.location, person.location)
        if(distance < closestDistance){
            closestDistance = distance
            closestPerson = person
        }
    }

    return closestPerson
}



fun differenceBetween(location1: Location, location2: Location): Double{

    val earthRadius = 6371 // in km
    val latDeg = (location2.lat - location1.lat) * (Math.PI/180)
    val lngDeg = (location2.lng - location1.lng) * (Math.PI/180)

    val a = sin(latDeg / 2).pow(2.0) + cos(location1.lat * (Math.PI / 180)).pow(2.0) + sin(lngDeg).pow(2.0)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return earthRadius * c
}


fun getListOfPeople(): List<Person>{
    return listOf(
        Person("John", "", Location(10.19191902f, 101.43092131f), ""),
        Person("John", "", Location(10.19191232f, 101.43094302f), ""),
        Person("John", "", Location(10.19191111f, 101.43091231f), ""),
        Person("John", "", Location(10.19191018f, 101.43094323f), ""),
        Person("John", "", Location(10.19191223f, 101.43129742f), ""),
        Person("John", "", Location(10.19192189f, 101.43124632f), ""),
        Person("John", "", Location(10.18289381f, 101.43123456f), ""),
        Person("John", "", Location(10.19209420f, 101.43123678f), ""),
        Person("John", "", Location(10.19128941f, 101.43123678f), ""),
        Person("John", "", Location(10.19191266f, 101.43126453f), "")
    )
}

fun groupPeople(people: Array<Person>): Array<Array<Person>>{

    val map = HashMap<String, Array<Person>>()
    val groups = ArrayList<Array<Person>>()

    for (person in people){
        val cityState = getCityState(person.address)
        if(map[cityState] == null){
            map[cityState] = arrayOf(person)
        } else {
            val list = map[cityState]!!.toMutableList()
            list.add(person)
            map[cityState] = list.toTypedArray()
        }
    }

    println("$map")

    for(entry in map){
        groups.add(entry.value)
    }

    return groups.toTypedArray()
}

fun getCityState(address: String): String{
    val str =  address.substringAfter(", ")
    var endIdx: Int = 0

    for (i in str.indices){
        if(isInt(str[i] + "")) {
            endIdx = i
            break
        }
    }

    return str.substring(0, endIdx)
}
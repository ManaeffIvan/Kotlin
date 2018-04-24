var myCommand: String = ""
var myKey: String = ""
var myVal: String = ""
var myMap: MutableMap<String, String> = mutableMapOf()

fun mySearch(myFunc: String){
    val tmpMap: Map<String, String>

    if (myFunc == "d")
        tmpMap = myMap.filter { (it.key == myKey) and (it.value == myVal)}
    else
        tmpMap = myMap.filter { it.key.contains(myKey) and it.value.contains(myVal)}

    if (tmpMap.isNotEmpty()){
        println("По заданным параметрам поиска в базе данных обнаружены " + (if (myFunc.contains("d")) "и удалены " else "") + "записи:")
        for (prnItem in tmpMap) {
            println("\"" + prnItem.key + "\" \"" + prnItem.value + "\"")
            if (myFunc.contains("d"))
                myMap.remove(prnItem.key)
        }
    }
    else
        println("По заданным параметрам поиска в базе данных записей не обнаружено.")
}

fun inputValues(toDo: String){
    val strInputKey = "Введите ключ"
    val strInputValue = "Введите значение"
    val strInputEmptyKey = " или оставьте это поле пустым для обработки всех ключей"
    val strInputEmptyValue = " или оставьте это поле пустым для обработки всех значений"

    println("$strInputKey" + (if (toDo == "d") strInputEmptyKey else ""))
    myKey = readLine()!!
    println("$strInputValue" + (if (toDo == "d") strInputEmptyValue else ""))
    myVal = readLine()!!
}

fun main(args: Array<String>) {
    val strInputCommand = "Введите команду (add|find|del|fragdel):"
    val strHelp = "Допустимые команды: add - добавление пары, del - удаление пары, fragdel - удаление пары по части ключа и/или значения" +
            ", find - поиск пары\nПримеры ввода команд:\n\tfind (с пустыми полями ключа и значения) - вывод всей базы данных\n\tdel (с " +
            "пустыми полями ключа и значения) - удаление пары с пустым ключом и пустым значением\n\tfragdel (с пустыми полями ключа и " +
            "значения) - удаление всей базы данных\nВ поиске допустимо использовать ключ или значение не целиком, а любую их часть."

    println(strHelp)
    var myLoop = true
    while (myLoop){
        println("\n$strInputCommand")
        myCommand = readLine()!!.trim().toLowerCase()
        when (myCommand){
            "exit", "x", "q", "break", "quit" -> myLoop = false
            "add", "put", "p", "a" -> {
                inputValues("a")
                if (myMap.filter { it.key == myKey}.isNotEmpty())
                    println("В паре с ключом \"$myKey\" значение изменено на \"$myVal\"")
                else
                    println("Пара с ключом \"$myKey\" и значением \"$myVal\" успешно добавлена")
                myMap.put(myKey, myVal)
            }
            "del", "delete", "d" ->  {
                inputValues("d")
                mySearch("d")
            }
            "find", "search", "f" -> {
                inputValues("d")
                mySearch("f")
            }
            "fd", "fragdelete", "fragdel" -> {
                inputValues("d")
                mySearch("fd")
            }
            else -> println(strHelp)
        }
    }
}
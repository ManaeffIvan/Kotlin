var myCommand: List<String> = listOf()
var myMap: MutableMap<String, String> = mutableMapOf()

fun mySearch(myFunc: String){
    if (myCommand.size < 2)
        println("В команде $myFunc отсутствуют необходимые параметры!")
    else {
        val tmpMap = myMap.filter { it.key.contains(if (myCommand[1] == "*") "" else if (myCommand[1] == "\\*") "*" else myCommand[1]) and
                (if (myCommand.size > 2) it.value.contains(myCommand[2]) else true)}
        if (tmpMap.isNotEmpty()){
            println("По заданным параметрам поиска в базе данных обнаружены " + (if (myFunc == "удаления") "и удалены " else "") + "записи:")
            for (prnItem in tmpMap) {
                println(prnItem.key + " " + prnItem.value)
                if (myFunc == "удаления")
                    myMap.remove(prnItem.key)
            }
        }
        else
            println("По заданным параметрам поиска в базе данных записей не обнаружено.")
    }
}
fun main(args: Array<String>) {

    val strInput = "Введите строку в формате: \"команда ключ значение\""
    val strHelp = "Формат ввода: command key value\nДопустимые команды: add - добавление пары, del - удаление пары," +
            " find - поиск пары\nПри отсутствии ключа в командах del или find, вместо него необходимо поставить символ \"*\"\n" +
            "В случае, когда ключом является символ звездочки, для поиска или удаления пары с таким\n\tключом, в поле ключ " +
            "звездочку необходимо экранировать слэшем: \"\\*\"" +
            "\nПримеры ввода команд:\n\tadd key01 value01\n\tdel key01\n\tdel * value01\n\tdel key01 value01" +
            "\n\tfind key01\n\tfind * value01\n\tfind key01 value01\n\tfind * - вывод всей базы данных\nДопустимо использовать" +
            " ключ или значение не целиком, а любую их часть. В этом \n\tслучае операция будет произведена со всеми элементами, " +
            "соответствующими заданному критерию отбора."
    println(strHelp)

    var myLoop = true
    while (myLoop){
        println("\n$strInput")
        myCommand = readLine()!!.split(" +".toRegex())
        when (myCommand[0].toLowerCase()){
            "exit", "x", "q", "break", "quit" -> myLoop = false
            "add", "put", "p" -> {
                if (myCommand.size < 2)
                    println("В команде добавления отсутствует ключ!")
                else {
                    if (myMap.filter { it.key.contains(myCommand[1])}.isNotEmpty()){
                        println("В паре с ключом " + myCommand[1] + " значение изменено на " + (if (myCommand.size > 2) myCommand[2]
                        else "\"\""))
                    }
                    else {
                        println("Пара с ключом " + myCommand[1] + " и значением " + (if (myCommand.size > 2) myCommand[2]
                        else "\"\"") + " успешно добавлена")
                    }
                    myMap.put(myCommand[1], if (myCommand.size > 2) myCommand[2] else "")
                }
            }
            "del", "delete", "d" ->  mySearch("удаления")
            "find", "search", "f" -> mySearch("поиска")
            else -> println(strHelp)
        }
    }
}
package cinema

private const val MAX_SEATS_IN_SMALL_HALL = 60
private const val BACKSEAT_PRICE = 8
private const val NORMAL_SEAT_PRICE = 10


class Cinema(
    private val rows: Int,
    private val seatsPerRow: Int,
) {

    private val cinemaHall2D = MutableList(rows) { MutableList(seatsPerRow) { 'S' } }

    fun printCinemaHall() {
        print("Cinema:\n ")
        for (i in 1..cinemaHall2D[0].size) print(" $i")
        println()

        for (i in cinemaHall2D.indices) {
            println("${i + 1} " + cinemaHall2D[i].joinToString(" "))
        }
    }

    fun bookTicket() {
        println("Enter a row number:")
        val rowPick = readln().toInt()
        println("Enter a seat number in that row:")
        val seatPick = readln().toInt()

        val ticketPrice =
            if (rows * seatsPerRow > MAX_SEATS_IN_SMALL_HALL && rowPick > rows / 2)
                BACKSEAT_PRICE
            else
                NORMAL_SEAT_PRICE
        println("Ticket price: $$ticketPrice")

        cinemaHall2D[rowPick - 1][seatPick - 1] = 'B'
    }
}


fun main() {

    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()

    val cinema = Cinema(rows, seatsPerRow)

    var action: Int
    while (true) {
        println(
            "\n" + """
                1. Show the seats
                2. Buy a ticket
                0. Exit 
                """.trimIndent() + "\n"
        )
        action = readln().toInt()

        when (action) {
            1 -> cinema.printCinemaHall()
            2 -> cinema.bookTicket()
            0 -> break
        }
    }
}
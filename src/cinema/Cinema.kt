package cinema

private const val MAX_SEATS_IN_SMALL_HALL = 60
private const val BACKSEAT_PRICE = 8
private const val NORMAL_SEAT_PRICE = 10
private const val BOOKED_SEAT = "B"
private const val FREE_SEAT = "S"


class Seat {

    var isBooked = false
        private set

    fun book() {
        if (isBooked)
            throw Exception("That ticket has already been purchased!")
        isBooked = true
    }

    override fun toString() =
        if (isBooked) BOOKED_SEAT else FREE_SEAT
}


class Cinema(
    private val rows: Int,
    private val seatsPerRow: Int,
) {

    private val cinemaHall2D = MutableList(rows) { MutableList(seatsPerRow) { Seat() } }
    private val ticketsTotal = rows * seatsPerRow

    private val frontRows = rows / 2
    private val backRows = rows / 2 + rows % 2
    private val totalIncome: Int = seatsPerRow *
            (frontRows * getTicketPrice(frontRows) + backRows * getTicketPrice(frontRows + 1))


    fun printCinemaHall() {
        print("Cinema:\n ")
        for (i in 1..cinemaHall2D[0].size) print(" $i")
        println()

        for (i in cinemaHall2D.indices) {
            println("${i + 1} " + cinemaHall2D[i].joinToString(" "))
        }
    }

    fun bookTicket() {
        var rowPick: Int
        var seatPick: Int
        while (true) {
            rowPick = readPositiveInt("Enter a row number:")
            seatPick = readPositiveInt("Enter a seat number in that row:")
            println()

            try {
                cinemaHall2D[rowPick - 1][seatPick - 1].book()
                break
            } catch (e: IndexOutOfBoundsException) {
                println("Wrong input!" + "\n")
            } catch (e: Exception) {
                println(e.message + "\n")
            }
        }

        println("Ticket price: $${getTicketPrice(rowPick)}")
    }

    fun showStatistics() {
        var income = 0
        var bookedTickets = 0
        for (row in cinemaHall2D.indices) {
            for (seat in cinemaHall2D[row]) {
                if (seat.isBooked) {
                    income += getTicketPrice(row + 1)
                    bookedTickets++
                }
            }
        }
        val bookedTicketsPercentage: Double = bookedTickets * 100.0 / ticketsTotal

        println("Number of purchased tickets: $bookedTickets")
        println("Percentage: ${"%.2f".format(bookedTicketsPercentage)}%")
        println("Current income: $$income")
        println("Total income: $$totalIncome")
    }

    private fun getTicketPrice(rowPick: Int): Int {
        return if (ticketsTotal > MAX_SEATS_IN_SMALL_HALL && rowPick > rows / 2)
            BACKSEAT_PRICE
        else
            NORMAL_SEAT_PRICE
    }
}


fun main() {

    val rows = readPositiveInt("Enter the number of rows:")
    val seatsPerRow = readPositiveInt("Enter the number of seats in each row:")

    val cinema = Cinema(rows, seatsPerRow)
    while (true) {
        println(
            "\n" + """
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit 
                """.trimIndent()
        )
        val action = readPositiveInt()
        println()

        when (action) {
            1 -> cinema.printCinemaHall()
            2 -> cinema.bookTicket()
            3 -> cinema.showStatistics()
            0 -> break
        }
    }
}


fun readPositiveInt(accompanyingText: String = ""): Int {
    println(accompanyingText)
    while (true) {
        try {
            return readln().toUInt().toInt()
        } catch (e: NumberFormatException) {
            println("Invalid value. Please enter whole positive number:")
        }
    }
}
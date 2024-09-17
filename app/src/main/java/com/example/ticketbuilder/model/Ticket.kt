import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket_table")
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val priority: String,
    val dueDate: String?
)

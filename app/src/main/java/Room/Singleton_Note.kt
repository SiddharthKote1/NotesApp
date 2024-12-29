import android.content.Context
import androidx.room.Room

object Singleton_Note {
    @Volatile
    private var notesDao: NotesAdd? = null

    fun getNoteDao(context: Context): NotesAdd {
        return notesDao ?: synchronized(this) {
            val database = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_database"
            ).build()
            notesDao = database.notesAdd()
            notesDao!!
        }
    }
}

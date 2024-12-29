import android.util.Log
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NotesAdd) {

    // Fetch all notes
    suspend fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    // Add a note
    suspend fun addNote(note: Note) {
        try {
            notesDao.addNote(note)
        } catch (e: Exception) {
            Log.e("NotesRepository", "Error adding note", e)
        }
    }

    // Update a note
    suspend fun updateNote(note: Note) {
        try {
            notesDao.updateNote(note)
        } catch (e: Exception) {
            Log.e("NotesRepository", "Error updating note", e)
        }
    }

    // Delete a note
    suspend fun deleteNote(note: Note) {
        try {
            notesDao.deleteNote(note)
        } catch (e: Exception) {
            Log.e("NotesRepository", "Error deleting note", e)
        }
    }
}

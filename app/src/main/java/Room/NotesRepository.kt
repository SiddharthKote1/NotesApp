import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NotesAdd) {

    val firebaseAnalytics:FirebaseAnalytics= Firebase.analytics

    // Fetch all notes
    suspend fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    // Add a note
    suspend fun addNote(note: Note) {
        try {
            notesDao.addNote(note)
            val bundle= Bundle().apply{
                putString("note_id",note.id.toString())
                putString("note_title",note.title)
                putString("note_content",note.content)
            }
            firebaseAnalytics.logEvent("note_added",bundle)
        } catch (e: Exception) {
            Log.e("NotesRepository", "Error adding note", e)
        }
    }

    // Update a note
    suspend fun updateNote(note: Note) {
        try {
            notesDao.updateNote(note)
            val bundle = Bundle().apply {
                putString("note_id", note.id.toString())
                putString("note_title", note.title)
                putString("note_content", note.content)
            }
            firebaseAnalytics.logEvent("note_updated", bundle)
        } catch (e: Exception) {
            Log.e("NotesRepository", "Error updating note", e)
        }
    }

    // Delete a note
    suspend fun deleteNote(note: Note) {
        try {
            notesDao.deleteNote(note)
            val bundle = Bundle().apply {
                putString("note_id", note.id.toString())
                putString("note_title", note.title)
                putString("note_content", note.content)
            }
            firebaseAnalytics.logEvent("note_deleted", bundle)
        } catch (e: Exception) {
            Log.e("NotesRepository", "Error deleting note", e)
        }
    }
}

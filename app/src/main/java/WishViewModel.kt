import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WishViewModel(private val notesDao: NotesAdd) : ViewModel() {
    private val repository = NotesRepository(notesDao)
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> get() = _notes

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            try {
                repository.getAllNotes().collect { notesList ->
                    _notes.value = notesList
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error loading notes: ${e.message}"
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.addNote(note)
                loadNotes()
            } catch (e: Exception) {
                _errorMessage.value = "Error adding note: ${e.message}"
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.updateNote(note)
                loadNotes()
            } catch (e: Exception) {
                _errorMessage.value = "Error updating note: ${e.message}"
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
                loadNotes()
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting note: ${e.message}"
            }
        }
    }
}

class PostNotFoundException(message: String) : RuntimeException(message)

class NoteService<T> {
    private val errorNotFound = 180
    private val operationSuccessfully = 1
    private var notes: MutableList<Note> = mutableListOf()
    private var comments: MutableList<Comment> = mutableListOf()


    fun add(item: T): String {
        if (item is Note) {
            notes.add(item)
            return item.noteId
        } else if (item is Comment) {
            comments.add(item)
            return item.commentId
        }
        return errorNotFound.toString()
    }

    fun del(item: T): String {
        if (item is Note) {
            if (notes.none { it.noteId == item.noteId }) return errorNotFound.toString()
            notes.remove(item)
            comments.removeAll(comments.filter { it.noteId == item.noteId })
            return operationSuccessfully.toString()
        } else if (item is Comment) {
            if (comments.none { it.commentId == item.commentId }) return errorNotFound.toString()
            comments[comments.indexOf(item)].delete = true
            return operationSuccessfully.toString()
        }
        return errorNotFound.toString()
    }

    fun edit(item: T): String {
        if (item is Note) {
            if (!notes.none { it.noteId == item.noteId }) {
                notes[notes.indexOf(notes.find { it.noteId== item.noteId })] = item
                return operationSuccessfully.toString()
            }
            return errorNotFound.toString()
        } else if (item is Comment) {
            if (!comments.none { it.commentId == item.commentId }) {
                if (comments[comments.indexOf(comments.find { it.commentId == item.commentId })].delete) {
                    throw PostNotFoundException("Комментарий с id:${item.commentId} - удален")
                }
                comments[comments.indexOf(comments.find { it.commentId == item.commentId })] = item
                return operationSuccessfully.toString()
            }
            return errorNotFound.toString()
        }
        return errorNotFound.toString()
    }

    fun get(): Any {
        if (notes.isNotEmpty()) {
            return notes
        }
        return errorNotFound
    }

    fun getById(idNote: String): Any {
        if (!notes.none { it.noteId == idNote }) {
            return notes[notes.indexOf(notes.find { it.noteId == idNote })]
        }
        return errorNotFound.toString()
    }

    fun getComments(idNote: String): Any {
        if (comments.any { it.noteId == idNote }) {
            return comments.filter { it.noteId == idNote && !it.delete }
        }
        return errorNotFound.toString()
    }

    fun restoreComment(idComment: String): Any {
        if (!comments.none { it.commentId == idComment }) {
            comments[comments.indexOf(comments.find { it.commentId == idComment })].delete = false
            return operationSuccessfully.toString()
        }
        return errorNotFound
    }

}

interface Notes

data class Note(
    val noteId: String,
    val title: String,
    val text: String
) : Notes

data class Comment(
    val commentId: String,
    val text: String,
    val noteId: String,
    var delete: Boolean = false
) : Notes